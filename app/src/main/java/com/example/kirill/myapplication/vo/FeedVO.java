package com.example.kirill.myapplication.vo;

import java.io.Serializable;
import java.util.Date;

public class FeedVO implements Serializable {

    private final String title;
    private final String description;
    private final Date pubDate;
    private final MediaContentVO mediaContent;

    public  FeedVO(String title, String description, Date pubDate, MediaContentVO mediaContent) {
        this.title = title;
        this.description = description;
        this.pubDate = pubDate;
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

    public Date getPubDate() {
        return pubDate;
    }
}

