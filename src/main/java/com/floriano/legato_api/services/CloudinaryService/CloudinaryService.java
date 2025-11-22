package com.floriano.legato_api.services.CloudinaryService;

import com.cloudinary.Cloudinary;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryService {

    @Resource
    private Cloudinary cloudinary;

    public String uploadFile(MultipartFile file, String folderName) {
        try {
            Map<String, Object> options = new HashMap<>();
            options.put("folder", folderName);
            options.put("resource_type", "auto");

            Map uploadedFile = cloudinary.uploader().upload(file.getBytes(), options);

            return (String) uploadedFile.get("secure_url");

        } catch (IOException e) {
            throw new RuntimeException("Erro ao fazer upload no Cloudinary", e);
        }
    }

}
