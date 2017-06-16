package ca.nuba.nubamenu;


/**
 * Created by Borys on 2017-06-14.
 */

public class AvgRating {


    private float current_avg_rating;
    private int num_of_ratings;

    AvgRating(){}
    AvgRating(float current_avg_rating, int num_of_ratings){
        this.current_avg_rating = current_avg_rating;
        this.num_of_ratings = num_of_ratings;
    }
    public float getCurrent_avg_rating() {
        return current_avg_rating;
    }

    public void setCurrent_avg_rating(float current_avg_rating) {
        this.current_avg_rating = current_avg_rating;
    }

    public int getNum_of_ratings() {
        return num_of_ratings;
    }

    public void setNum_of_ratings(int num_of_ratings) {
        this.num_of_ratings = num_of_ratings;
    }

}
