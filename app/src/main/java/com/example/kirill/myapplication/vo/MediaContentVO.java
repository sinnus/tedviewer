package com.example.kirill.myapplication.vo;

import java.io.Serializable;

public class MediaContentVO implements Serializable {

    private final String url;
    private final int fileSize;
    private final String type;

    public MediaContentVO(String url, int fileSize, String type) {
        this.url = url;
        this.fileSize = fileSize;
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public int getFileSize() {
        return fileSize;
    }

    public String getType() {
        return type;
    }
}
