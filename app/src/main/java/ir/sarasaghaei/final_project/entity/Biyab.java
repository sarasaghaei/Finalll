package ir.sarasaghaei.final_project.entity;

import androidx.annotation.NonNull;

public class Biyab {
    private int id;
    private String title, description, category, sub_category, Tell, price,
            image1,image2,image3,rank,offer;

    public Biyab(int id, String rank) {
        this.id = id;
        this.rank = rank;
    }

    public Biyab(String title, String description, String category, String sub_category, String tell,
                 String price, String image1, String image2, String image3, String offer, String rank) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.sub_category = sub_category;
        this.Tell = tell;
        this.price = price;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.offer = offer;
        this.rank = rank;
    }

    public Biyab(int id,String title, String category, String price, String image1, String offer) {
       this.id = id;
        this.title = title;
        this.category = category;
        this.price = price;
        this.image1 = image1;
        this.offer = offer;
    }

    public Biyab(int id, String title, String description, String category, String sub_category, String tell,
                 String price, String image1, String image2, String image3, String offer, String rank) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.sub_category = sub_category;
        Tell = tell;
        this.price = price;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.offer = offer;
        this.rank = rank;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {     return description;    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSub_category() {
        return sub_category;
    }

    public void setSub_category(String sub_category) {
        this.sub_category = sub_category;
    }

    public String getTell() {
        return Tell;
    }

    public void setTell(String tell) {
        Tell = tell;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage1() { return image1;    }

    public void setImage1(String image1) {    this.image1 = image1;    }

    public String getImage2() {    return image2;   }

    public void setImage2(String image2) {   this.image2 = image2;  }

    public String getImage3() {     return image3;  }

    public void setImage3(String image3) {     this.image3 = image3;  }

    public String getRank() {      return rank;  }

    public void setRank(String rank) {     this.rank = rank; }

    public String getOffer() {     return offer;  }

    public void setOffer(String offer) {   this.offer = offer;  }

    @NonNull
    @Override
    public String toString() {
        return "Biyab{" +
                "id=" + id + ", title=" + title + '\'' +
                ", description ='" + description + '\'' +
                ", category='" + category + '\'' +
                ", sub_category='" + sub_category + '\'' +
                ", Tell='" + Tell + '\'' +
                ", price='" + price + '\'' +
                ", image1='" + image1 + '\'' +
                ", image2='" + image2 + '\'' +
                ", image3='" + image3 + '\'' +
                ", offer='" + offer + '\'' +
                ", rank='" + rank + '\'' +
                '}';
    }
}
