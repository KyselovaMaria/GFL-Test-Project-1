package file;

import com.fasterxml.jackson.databind.ObjectMapper;

import composite.Manufacturer;
import composite.Souvenir;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileStorage {
    private final String souvenirFileName = "souvenirs.json";
    private final String manufacturerFileName = "manufacturers.json";
    private final ObjectMapper objectMapper;

    public FileStorage(String s, String s1) {
        objectMapper = new ObjectMapper();
    }

    public void saveSouvenirs(List<Souvenir> souvenirs) {
        try {
            objectMapper.writeValue(new File(souvenirFileName), souvenirs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Souvenir> loadSouvenirs() {
        List<Souvenir> souvenirs = new ArrayList<>();
        try {
            souvenirs = objectMapper.readValue(new File(souvenirFileName), objectMapper.getTypeFactory().constructCollectionType(List.class, Souvenir.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return souvenirs;
    }

    public void saveManufacturers(List<Manufacturer> manufacturers) {
        try {
            objectMapper.writeValue(new File(manufacturerFileName), manufacturers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Manufacturer> loadManufacturers() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        try {
            manufacturers = objectMapper.readValue(new File(manufacturerFileName), objectMapper.getTypeFactory().constructCollectionType(List.class, Manufacturer.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return manufacturers;
    }
}
