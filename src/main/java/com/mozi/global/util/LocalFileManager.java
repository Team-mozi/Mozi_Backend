package com.mozi.global.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
@Profile({"local", "test"})
public class LocalFileManager implements FileManager {

    @Value("${upload.path}")
    private String uploadDir;

    @Override
    public String upload(MultipartFile file, String dirName) throws IOException {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path savePath = Paths.get(uploadDir, dirName, fileName);
        Files.createDirectories(savePath.getParent());
        file.transferTo(savePath.toFile());
        return savePath.toString();
    }

    @Override
    public String getUrl(String key) {
        return "/upload/" + key;
    }

    @Override
    public void delete(String key) {
        File file = new File(key);
        if (file.exists()) {
            file.delete();
        }
    }
}
