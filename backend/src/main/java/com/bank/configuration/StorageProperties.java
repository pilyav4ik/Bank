package com.bank.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

    private String locationSavedFiles = "src/main/resources/upload";

    public String getLocationSavedFiles() {
        return locationSavedFiles;
    }

    public void setLocationSavedFiles(String locationSavedFiles) {
        this.locationSavedFiles = locationSavedFiles;
    }

}