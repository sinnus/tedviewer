package com.example.kirill.myapplication.vo;

import java.io.Serializable;

public class FeedVO implements Serializable {

    private final String title;
    private final MediaContentVO mediaContent;

    public  FeedVO(String title, MediaContentVO mediaContent) {
        this.title = title;
        this.mediaContent = mediaContent;
    }

    public String getTitle() {
        return title;
    }

    public MediaContentVO getMediaContent() {
        return mediaContent;
    }
}

