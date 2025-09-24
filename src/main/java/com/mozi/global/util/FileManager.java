package com.mozi.global.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileManager {

    String upload(MultipartFile file, String dirName) throws IOException;
    String getUrl(String key);
    void delete(String key);
}
