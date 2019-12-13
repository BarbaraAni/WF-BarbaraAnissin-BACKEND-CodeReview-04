package sample;

import javafx.scene.image.Image;

import java.io.InputStream;

public class Product {
    String title;
    String quantity;
    String description;
    double oldPrice;
    double newPrice;
    String picName;
    Image image;

    public Product(String title, String quantity, String description, double oldPrice, double newPrice, String picName) {
        this.title = title;
        this.quantity = quantity;
        this.description = description;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
        InputStream img = this.getClass().getResourceAsStream("/images/"+picName+"__600x600.jpg" );
        this.picName = picName;
        this.image =  new Image(img);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(double newPrice) {
        this.newPrice = newPrice;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Items{" +
                "title='" + title + "'" +
                ", old=" + oldPrice +
                ", new=" + newPrice +
                '}';
    }
}
