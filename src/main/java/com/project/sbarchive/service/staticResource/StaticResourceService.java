package com.project.sbarchive.service.staticResource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class StaticResourceService {

    @Value("${static.resource.directory}")
    private String staticResourceDirectory;

    private final ResourceLoader resourceLoader;

    public StaticResourceService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public String getStaticFolderPath() {
        Resource resource = resourceLoader.getResource(staticResourceDirectory);
        try {
            return resource.getFile().getAbsolutePath();
        } catch (IOException e) {
            throw new RuntimeException("Failed to get static folder path", e);
        }
    }

}
