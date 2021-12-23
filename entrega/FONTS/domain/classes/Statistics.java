package domain.classes;

import java.util.ArrayList;

/**
 * @author Sergi Casau Pueyo
 */

public class Statistics {
    private Integer nRatings;
    private Double averageRating;

    /**
     * Constructora buida
     */
    public Statistics() {
        this.nRatings = 0;
        this.averageRating = 0.;
    }

    /**
     * Getter de nRatings
     * @return nRatings
     */
    public int getnRatings() {
        return nRatings;
    }

    /**
     * Getter de averageRating
     * @return averageRating
     */
    public Double getAverageRating() {
        return averageRating;
    }

    /**
     * Afegeix un rating a les estadístiques i actualitza les dades
     * @param rating
     */
    public void add_rating(double rating) {
        ++this.nRatings;
        Double temp = (this.averageRating * (this.nRatings - 1)) + rating;
        this.averageRating = (temp / this.nRatings);
    }

    /**
     * Updates the rating average if a rating has changed its value
     * @param new_rating
     * @param old_rating
     */
    public void update_rating(double new_rating, double old_rating) {
        Double temp = (this.averageRating * this.nRatings) - old_rating + new_rating;
        this.averageRating = (temp / this.nRatings);
    }

    public ArrayList<String> discretize() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add(String.valueOf(this.averageRating));
        temp.add(String.valueOf(this.nRatings));
        return temp;
    }
}
