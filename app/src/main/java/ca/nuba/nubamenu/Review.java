package ca.nuba.nubamenu;

/**
 * Review fields for Firebase
 */
public class Review {
    private String author;
    private String reviewText;
    private float rating;
    private String userId;

    Review(){}
    Review(String author, String reviewText, float rating, String userId){
        this.author = author;
        this.reviewText = reviewText;
        this.rating = rating;
        this.userId = userId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
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
