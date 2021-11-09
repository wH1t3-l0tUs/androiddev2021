package com.usth.wordpress.ui.reader.entity;

public class Author {
    private long id;
    private String email;
    private String niceName;
    private String name;
    private String url;
    private String avatarURL;
    private String profileURL;

    public Author(String name, String avatarURL) {
        this.name = name;
        this.avatarURL = avatarURL;
    }

    public long getID() { return id; }
    public void setID(long value) { this.id = value; }

    public String getEmail() { return email; }
    public void setEmail(String value) { this.email = value; }

    public String getNiceName() { return niceName; }
    public void setNiceName(String value) { this.niceName = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    public String getURL() { return url; }
    public void setURL(String value) { this.url = value; }

    public String getAvatarURL() { return avatarURL; }
    public void setAvatarURL(String value) { this.avatarURL = value; }

    public String getProfileURL() { return profileURL; }
    public void setProfileURL(String value) { this.profileURL = value; }
}