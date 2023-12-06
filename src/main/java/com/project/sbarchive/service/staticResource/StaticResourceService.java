package com.project.sbarchive.service.staticResource;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

@Log4j2
@Service
public class StaticResourceService {

    @Value("${static.resource.directory}")
    private String staticResourceDirectory;

    private final ResourceLoader resourceLoader;

    public StaticResourceService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public String getStaticFolderPath() {
        Resource resource = resourceLoader.getResource(staticResourceDirectory+"images\\");
        try {
            URL url = resource.getURL();
            String path = url.getPath();

            log.info(path);
            return path;
        } catch (IOException e) {
            throw new RuntimeException("Failed to get static folder path", e);
        }
//        return null;
    }


}
