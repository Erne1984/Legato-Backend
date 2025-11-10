package com.floriano.legato_api.controllers.ChatController;

import com.floriano.legato_api.model.ChatMessage.ChatMessage;
import com.floriano.legato_api.services.ChatMessageService;
import com.floriano.legato_api.services.UserSevice.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
public class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;
    private final UserService userService;

    public ChatController(SimpMessagingTemplate messagingTemplate,
                          ChatMessageService chatMessageService,
                          UserService userService) {
        this.messagingTemplate = messagingTemplate;
        this.chatMessageService = chatMessageService;
        this.userService = userService;
    }

    @MessageMapping("/sendMessage")
    public void sendPrivateMessage(ChatMessage message,
                                   SimpMessageHeaderAccessor headerAccessor,
                                   Principal principal) {
        try {
            String fromUser = principal.getName();
            message.setFromUser(fromUser);

            if (!userService.userExists(fromUser) || !userService.userExists(message.getToUser())) {
                logger.error("Remetente ({}) ou destinatário ({}) inexistente", fromUser, message.getToUser());
                return;
            }

            if (message.getTimestamp() == null) {
                message.setTimestamp(LocalDateTime.now());
            }

            if (message.getContent() == null) {
                message.setContent("");
            }

            ChatMessage saved = chatMessageService.saveMessage(message);
            logger.info("Mensagem salva com sucesso com ID {}", saved.getId());

            // Enviar para destinatário
            String recipientDestination = "/user/" + message.getToUser() + "/queue/messages";
            messagingTemplate.convertAndSend(recipientDestination, saved);
            logger.info("Mensagem enviada para {}", recipientDestination);

            // Enviar cópia para o remetente
            String senderDestination = "/user/" + fromUser + "/queue/messages";
            messagingTemplate.convertAndSend(senderDestination, saved);
            logger.info("Confirmação enviada para {}", senderDestination);

        } catch (Exception e) {
            logger.error("Erro ao enviar mensagem: {}", e.getMessage(), e);
        }
    }
}
