package com.example.mad.inclassassgn09;

/**
 * Created by atulb on 10/31/2016.
 */

public class Message {
    private String UserFname;
    private String UserLname;
    private String Id;
    private String Comment;
    private String FileThumbnailId;
        private String Type;
    private String CreatedAt;

    public String getUserFname() {
        return UserFname;
    }

    public void setUserFname(String userFname) {
        UserFname = userFname;
    }

    public String getUserLname() {
        return UserLname;
    }

    public void setUserLname(String userLname) {
        UserLname = userLname;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getFileThumbnailId() {
        return FileThumbnailId;
    }

    public void setFileThumbnailId(String fileThumbnailId) {
        FileThumbnailId = fileThumbnailId;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        CreatedAt = createdAt;
    }
}