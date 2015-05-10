package com.example.kirill.myapplication.vo;

import java.io.Serializable;

public class FeedVO implements Serializable {

    private final String title;
    private final String description;
    private final MediaContentVO mediaContent;

    public  FeedVO(String title, String description, MediaContentVO mediaContent) {
        this.title = title;
        this.description = description;
        this.mediaContent = mediaContent;
    }

    public String getTitle() {
        return title;
    }

    public MediaContentVO getMediaContent() {
        return mediaContent;
    }

    public String getDescription() {
        return description;
    }
}

