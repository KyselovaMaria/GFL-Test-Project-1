package composite;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.UUID;

public class Manufacturer implements Item, Serializable {
    private UUID id; // Унікальний ідентифікатор
    private String name;
    private String country;

    @JsonCreator
    public Manufacturer(@JsonProperty("name") String name, @JsonProperty("country") String country) {
        this.id = UUID.randomUUID(); // Генерируем уникальный id
        this.name = name;
        this.country = country;
    }

    public UUID getId() {
        return id;
    }

    // Геттери та сеттери для полів класу

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Manufacturer{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    @Override
    public void display() {
        System.out.println("Manufacturer: " + name);
        System.out.println("Country: " + country);
    }
}
