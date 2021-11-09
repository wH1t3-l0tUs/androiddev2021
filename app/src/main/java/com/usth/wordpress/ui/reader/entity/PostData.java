package com.usth.wordpress.ui.reader.entity;

// Post.java


import java.time.OffsetDateTime;

public class PostData {
    private long id;
    private Author author;
    private OffsetDateTime date;
    private OffsetDateTime modified;
    private String title;
    private String url;
    private String shortURL;
    private String content;
    private String excerpt;
    private boolean isExternal;
    private String siteID;
    private String siteName;
    private String siteURL;
    private boolean siteIsPrivate;
    private FeaturedMedia featuredMedia;
    private long feedID;

    public PostData(Author author, FeaturedMedia featuredMedia, String title, String content) {
        this.author = author;
        this.featuredMedia = featuredMedia;
        this.title = title;
        this.content = content;
    }

    public long getID() { return id; }
    public void setID(long value) { this.id = value; }

    public Author getAuthor() { return author; }
    public void setAuthor(Author value) { this.author = value; }

    public OffsetDateTime getDate() { return date; }
    public void setDate(OffsetDateTime value) { this.date = value; }

    public OffsetDateTime getModified() { return modified; }
    public void setModified(OffsetDateTime value) { this.modified = value; }

    public String getTitle() { return title; }
    public void setTitle(String value) { this.title = value; }

    public String getURL() { return url; }
    public void setURL(String value) { this.url = value; }

    public String getShortURL() { return shortURL; }
    public void setShortURL(String value) { this.shortURL = value; }

    public String getContent() { return content; }
    public void setContent(String value) { this.content = value; }

    public String getExcerpt() { return excerpt; }
    public void setExcerpt(String value) { this.excerpt = value; }

    public boolean getIsExternal() { return isExternal; }
    public void setIsExternal(boolean value) { this.isExternal = value; }

    public String getSiteID() { return siteID; }
    public void setSiteID(String value) { this.siteID = value; }

    public String getSiteName() { return siteName; }
    public void setSiteName(String value) { this.siteName = value; }

    public String getSiteURL() { return siteURL; }
    public void setSiteURL(String value) { this.siteURL = value; }

    public boolean getSiteIsPrivate() { return siteIsPrivate; }
    public void setSiteIsPrivate(boolean value) { this.siteIsPrivate = value; }

    public FeaturedMedia getFeaturedMedia() { return featuredMedia; }
    public void setFeaturedMedia(FeaturedMedia value) { this.featuredMedia = value; }

    public long getFeedID() { return feedID; }
    public void setFeedID(long value) { this.feedID = value; }
}






