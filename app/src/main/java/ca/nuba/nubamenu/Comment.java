package ca.nuba.nubamenu;

/**
 * Created by Borys on 2017-06-04.
 */

public class Comment {
    private String author;
    private String commentText;
    private float rating;
    private String userId;

    Comment(){}
    Comment(String author, String commentText, float rating, String userId){
        this.author = author;
        this.commentText = commentText;
        this.rating = rating;
        this.userId = userId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
