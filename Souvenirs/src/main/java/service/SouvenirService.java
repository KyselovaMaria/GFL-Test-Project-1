package service;

import composite.Manufacturer;
import composite.Souvenir;

import java.util.*;
import java.util.stream.Collectors;


public class SouvenirService {
    private List<Souvenir> souvenirs;
    private List<Manufacturer> manufacturers;

    public SouvenirService(List<Souvenir> souvenirs, List<Manufacturer> manufacturers) {
        this.souvenirs = souvenirs;
        this.manufacturers = manufacturers;
    }

    public SouvenirService() {

    }

    public void addSouvenir(Souvenir souvenir) {
        souvenirs.add(souvenir);
    }

    public void editSouvenir(String oldName, UUID manufacturerId, Souvenir newSouvenir) {
        souvenirs = souvenirs.stream()
                .map(souvenir -> {
                    if (souvenir.getName().equals(oldName) && souvenir.getManufacturer().getId().equals(manufacturerId)) {
                        return newSouvenir;
                    } else {
                        return souvenir;
                    }
                })
                .collect(Collectors.toList());
    }


    //public void deleteSouvenir(String name) {
    //    souvenirs.removeIf(souvenir -> souvenir.getName().equals(name));
    //}

    public void deleteSouvenir(String souvenirName, UUID manufacturerId) {
        souvenirs.removeIf(souvenir -> souvenir.getName().equals(souvenirName) &&
                souvenir.getManufacturer().getId().equals(manufacturerId));
    }


    public void addManufacturer(Manufacturer manufacturer) {
        manufacturers.add(manufacturer);
    }

    public void editManufacturer(UUID manufacturerId, Manufacturer newManufacturer) {
        manufacturers = manufacturers.stream()
                .map(m -> m.getId().equals(manufacturerId) ? newManufacturer : m)
                .collect(Collectors.toList());
    }

    public void deleteManufacturer(UUID manufacturerId) {
        manufacturers.removeIf(manufacturer -> manufacturer.getId().equals(manufacturerId));
        souvenirs.removeIf(souvenir -> souvenir.getManufacturer().getId().equals(manufacturerId));
    }

    public List<Souvenir> getAllSouvenirs() {
        return souvenirs;
    }

    public List<Souvenir> getSouvenirsByManufacturer(String manufacturerName) {
        return souvenirs.stream()
                .filter(s -> s.getManufacturer().getName().equals(manufacturerName))
                .collect(Collectors.toList());
    }

    public List<Souvenir> getSouvenirsByCountry(String country) {
        return souvenirs.stream()
                .filter(s -> {
                    Manufacturer manufacturer = manufacturers.stream()
                            .filter(m -> m.getName().equals(s.getManufacturer().getName()))
                            .findFirst()
                            .orElse(null);
                    return manufacturer != null && manufacturer.getCountry().equals(country);
                })
                .collect(Collectors.toList());
    }

    public List<Souvenir> getSouvenirsByPrice(double maxPrice) {
        return souvenirs.stream()
                .filter(s -> s.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }

    public List<Manufacturer> getAllManufacturers() {
        return manufacturers;
    }

    public List<Souvenir> getSouvenirsByManufacturerAndYear(String manufacturerName, int year) {
        return souvenirs.stream()
                .filter(s -> s.getManufacturer().getName().equals(manufacturerName))
                .filter(s -> s.getReleaseDate().endsWith(Integer.toString(year)))
                .collect(Collectors.toList());
    }

    public Map<Integer, List<Souvenir>> groupSouvenirsByYear() {
        return souvenirs.stream()
                .collect(Collectors.groupingBy(s -> Integer.parseInt(s.getReleaseDate().substring(s.getReleaseDate().lastIndexOf('/') + 1))));
    }

    public List<Manufacturer> getManufacturersOfSouvenirInYear(String souvenirName, int year) {
        List<Manufacturer> manufacturersOfSouvenirInYear = new ArrayList<>();
        for (Souvenir souvenir : souvenirs) {
            if (souvenir.getName().equals(souvenirName) && souvenir.getReleaseDate().endsWith(Integer.toString(year))) {
                manufacturersOfSouvenirInYear.add(souvenir.getManufacturer());
            }
        }
        return manufacturersOfSouvenirInYear;
    }

    public void deleteManufacturerAndRelatedSouvenirs(String manufacturerName) {
        souvenirs.removeIf(souvenir -> souvenir.getManufacturer().getName().equals(manufacturerName));
        manufacturers.removeIf(manufacturer -> manufacturer.getName().equals(manufacturerName));
    }

    public void deleteManufacturerAndRelatedSouvenirsbyID(UUID manufacturerId) {
        souvenirs.removeIf(souvenir -> souvenir.getManufacturer().getId() == manufacturerId);
        manufacturers.removeIf(manufacturer -> manufacturer.getId() == manufacturerId);
    }

    public Map<Manufacturer, List<Souvenir>> getManufacturersWithSouvenirs() {
        Map<Manufacturer, List<Souvenir>> manufacturersWithSouvenirs = new HashMap<>();

        for (Souvenir souvenir : souvenirs) {
            Manufacturer manufacturer = souvenir.getManufacturer();
            manufacturersWithSouvenirs
                    .computeIfAbsent(manufacturer, k -> new ArrayList<>())
                    .add(souvenir);
        }

        return manufacturersWithSouvenirs;
    }

    public List<Manufacturer> getManufacturersBelowPrice(double maxPrice) {
        return manufacturers.stream()
                .filter(manufacturer ->
                        souvenirs.stream()
                                .anyMatch(souvenir -> souvenir.getManufacturer().getId().equals(manufacturer.getId()) && souvenir.getPrice() <= maxPrice))
                .toList();
    }

}
