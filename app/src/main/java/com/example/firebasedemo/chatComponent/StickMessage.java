package com.example.firebasedemo.chatComponent;

public class StickMessage {
    private String author;
    private String time;
    private String content;
   // private String image;
    private boolean isImage;

    public StickMessage() {
    }

    public StickMessage(String author,String time, String content,boolean isImage) {
        this.author=author;
        this.time = time;
        this.content = content;
        this.isImage=isImage;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean getisImage() {
        return isImage;
    }

    public void setisImage(boolean image) {
        isImage = image;
    }

    @Override
    public String toString() {
        return "time= " + time + "\n"+
                "text= " + content+"\n"+
                "author= "+author;

    }
}

