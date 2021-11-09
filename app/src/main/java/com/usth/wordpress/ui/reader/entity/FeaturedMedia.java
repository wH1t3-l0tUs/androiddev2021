package com.usth.wordpress.ui.reader.entity;

public class FeaturedMedia {
    private String uri;
    private long width;
    private long height;
    private String type;

    public FeaturedMedia(String uri) {
        this.uri = uri;
    }

    public String getURI() { return uri; }
    public void setURI(String value) { this.uri = value; }

    public long getWidth() { return width; }
    public void setWidth(long value) { this.width = value; }

    public long getHeight() { return height; }
    public void setHeight(long value) { this.height = value; }

    public String getType() { return type; }
    public void setType(String value) { this.type = value; }
}

