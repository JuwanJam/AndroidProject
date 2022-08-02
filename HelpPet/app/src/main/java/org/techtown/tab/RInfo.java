package org.techtown.tab;

public class RInfo {

    int hid;
    String name;
    String review;
    float rating;

    public RInfo(String name, String review, float rating, int hid) {
        this.name = name;
        this.review = review;
        this.rating = rating;
        this.hid = hid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review){ this.review = review; }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getHid() {
        return hid;
    }

    public void setHid(int hid) {
        this.hid = hid;
    }

    @Override
    public String toString() {
        return "PetInfo{" +
                "name='" + name + '\'' +
                ", review='" + review + '\'' +
                ", rating='" + rating + '\'' +
                ", hid='" + hid + '\'' +
                '}';
    }
}
