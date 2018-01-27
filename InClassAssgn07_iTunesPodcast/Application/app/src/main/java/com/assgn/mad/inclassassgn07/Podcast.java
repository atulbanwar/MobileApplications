package com.assgn.mad.inclassassgn07;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by atulb on 10/3/2016.
 */
public class Podcast implements Serializable {
    private String title;
    private String summary;
    private Date releaseDate;
    private String smallImageUrl;
    private String largeImageUrl;
    private boolean isMatched = false;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getSmallImageUrl() {
        return smallImageUrl;
    }

    public void setSmallImageUrl(String smallImageUrl) {
        this.smallImageUrl = smallImageUrl;
    }

    public String getLargeImageUrl() {
        return largeImageUrl;
    }

    public void setLargeImageUrl(String largeImageUrl) {
        this.largeImageUrl = largeImageUrl;
    }

    public boolean isMatched() {
        return isMatched;
    }

    public void setMatched(boolean matched) {
        isMatched = matched;
    }

    @Override
    public String toString() {
        return "Podcast{" +
                "title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", releaseDate=" + releaseDate +
                ", smallImageUrl='" + smallImageUrl + '\'' +
                ", largeImageUrl='" + largeImageUrl + '\'' +
                '}';
    }
}
