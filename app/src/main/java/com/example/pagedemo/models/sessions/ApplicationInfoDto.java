package com.example.pagedemo.models.sessions;

import java.util.Date;
import java.util.HashMap;

public class ApplicationInfoDto {

    private String version;

    private Date releaseDate;

    private HashMap<String, Boolean> features;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public HashMap<String, Boolean> getFeatures() {
        return features;
    }

    public void setFeatures(HashMap<String, Boolean> features) {
        this.features = features;
    }
}
