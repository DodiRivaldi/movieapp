package linknet.cataloguemovieuiux.model;

/**
 * Created by Dodi Rivaldi on 10/11/2017.
 */

public class Upcoming {
    private String title, review, releaseDate, image;
    public Upcoming(){}

    public Upcoming(String title, String review, String releaseDate, String image){
        this.title = title;
        this.review = review;
        this.releaseDate = releaseDate;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
