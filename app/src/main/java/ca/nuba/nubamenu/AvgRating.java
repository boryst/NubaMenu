package ca.nuba.nubamenu;


/**
 * Created by Borys on 2017-06-14.
 */

public class AvgRating {


    private float currentAvgRating;
    private int numOfRatings;

    AvgRating(){}
    AvgRating(float currentAvgRating, int numOfRatings){
        this.currentAvgRating = currentAvgRating;
        this.numOfRatings = numOfRatings;
    }
    public float getCurrentAvgRating() {
        return currentAvgRating;
    }

    public void setCurrentAvgRating(float currentAvgRating) {
        this.currentAvgRating = currentAvgRating;
    }

    public int getNumOfRatings() {
        return numOfRatings;
    }

    public void setNumOfRatings(int numOfRatings) {
        this.numOfRatings = numOfRatings;
    }

}
