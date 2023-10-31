package facade;

import composite.Manufacturer;
import composite.Souvenir;
import service.SouvenirService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SouvenirFacade {
    private SouvenirService souvenirService;

    public SouvenirFacade(SouvenirService souvenirService) {
        this.souvenirService = souvenirService;
    }

    public void addSouvenir(Souvenir souvenir) {
        souvenirService.addSouvenir(souvenir);
    }

    public void editSouvenir(String oldName, UUID manufacturerId, Souvenir newSouvenir) {
        souvenirService.editSouvenir(oldName, manufacturerId, newSouvenir);
    }

    public void deleteSouvenir(String name, UUID manufacturerId) {
        souvenirService.deleteSouvenir(name, manufacturerId);
    }

    public void addManufacturer(Manufacturer manufacturer) {
        souvenirService.addManufacturer(manufacturer);
    }

    public void editManufacturer(UUID manufacturerId, Manufacturer newManufacturer) {
        // can add validation logic
        souvenirService.editManufacturer(manufacturerId, newManufacturer);
    }

    public void deleteManufacturer(UUID manufacturerId) {
        // can add validation logic
        souvenirService.deleteManufacturer(manufacturerId);
    }

    public List<Souvenir> getAllSouvenirs() {
        return souvenirService.getAllSouvenirs();
    }

    public List<Souvenir> getSouvenirsByManufacturer(String manufacturerName) {
        return souvenirService.getSouvenirsByManufacturer(manufacturerName);
    }

    public List<Souvenir> getSouvenirsByCountry(String country) {
        return souvenirService.getSouvenirsByCountry(country);
    }

    public List<Souvenir> getSouvenirsByPrice(double maxPrice) {
        return souvenirService.getSouvenirsByPrice(maxPrice);
    }

    public List<Manufacturer> getAllManufacturers() {
        return souvenirService.getAllManufacturers();
    }

    public List<Souvenir> getSouvenirsByManufacturerAndYear(String manufacturerName, int year) {
        return souvenirService.getSouvenirsByManufacturerAndYear(manufacturerName, year);
    }

    public Map<Integer, List<Souvenir>> groupSouvenirsByYear() {
        return souvenirService.groupSouvenirsByYear();
    }

    public void deleteManufacturerAndRelatedSouvenirs(String manufacturerName) {
        souvenirService.deleteManufacturerAndRelatedSouvenirs(manufacturerName);
    }

    public void deleteManufacturerAndRelatedSouvenirsbyID(UUID manufacturerId) {
        souvenirService.deleteManufacturerAndRelatedSouvenirsbyID(manufacturerId);
    }


    // New method: Get manufacturers with souvenirs
    public List<Manufacturer> getManufacturersOfSouvenirInYear(String souvenirName, int year) {
        return souvenirService.getManufacturersOfSouvenirInYear(souvenirName, year);
    }

    public Map<Manufacturer, List<Souvenir>> getManufacturersWithSouvenirs() {
        return souvenirService.getManufacturersWithSouvenirs();
    }

    public List<Manufacturer> getManufacturersBelowPrice(double maxPrice) {
        return souvenirService.getManufacturersBelowPrice(maxPrice);
    }

}
