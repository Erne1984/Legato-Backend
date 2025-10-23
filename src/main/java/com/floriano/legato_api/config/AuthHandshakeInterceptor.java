package com.floriano.legato_api.config;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

public class AuthHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Map<String, Object> attributes) throws Exception {

        // Extrai o header Authorization
        var headers = request.getHeaders();
        var authHeader = headers.getFirst("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("❌ Token ausente");
            return false; // rejeita handshake
        }

        String token = authHeader.substring(7);

        // Aqui você validaria o token (ex: JWTUtil.validate(token))
        boolean valido = validarToken(token);

        if (!valido) {
            System.out.println("❌ Token inválido");
            return false;
        }

        // Se quiser, armazene info do usuário
        attributes.put("user", extrairUsuario(token));

        return true; // prossegue
    }

    @Override
    public void afterHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Exception exception) {
        // nada necessário aqui
    }

    // Mock de validação só para exemplo
    private boolean validarToken(String token) {
        return token.equals("meu-token-valido");
    }

    private String extrairUsuario(String token) {
        return "ernesto"; // exemplo
    }
}

