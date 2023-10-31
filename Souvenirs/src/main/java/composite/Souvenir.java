package composite;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Souvenir implements Item, Serializable {
    private String name;
    private Manufacturer manufacturer;
    private String releaseDate;
    private double price;

    @JsonCreator
    public Souvenir(@JsonProperty("name") String name,
                    @JsonProperty("manufacturer") Manufacturer manufacturer,
                    @JsonProperty("releaseDate") String releaseDate,
                    @JsonProperty("price") double price) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.releaseDate = releaseDate;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Souvenir{" +
                "name='" + name + '\'' +
                ", manufacturer=" + manufacturer +
                ", releaseDate='" + releaseDate + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public void display() {
        System.out.println("Souvenir: " + name);
        System.out.println("Manufacturer: " + manufacturer.getName()); // Display manufacturer's name
        System.out.println("Country: " + manufacturer.getCountry()); // Display manufacturer's country
        System.out.println("Release Date: " + releaseDate);
        System.out.println("Price: " + price);
    }
}
