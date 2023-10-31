import composite.*;
import facade.SouvenirFacade;
import file.FileStorage;
import service.SouvenirService;

import java.io.*;
import java.util.*;


public class Main {
    public static void main(String[] args) {

        File souvenirsFile = new File("souvenirs.json");
        File manufacturersFile = new File("manufacturers.json");

        if (!souvenirsFile.exists()) {
            try {
                souvenirsFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!manufacturersFile.exists()) {
            try {
                manufacturersFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        FileStorage fileStorage = new FileStorage("souvenirs.json", "manufacturers.json");

        // Завантаження даних з файлів
        List<Souvenir> souvenirs = fileStorage.loadSouvenirs();
        List<Manufacturer> manufacturers = fileStorage.loadManufacturers();

        SouvenirService souvenirService = new SouvenirService(souvenirs, manufacturers);
        SouvenirFacade souvenirFacade = new SouvenirFacade(souvenirService);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Меню:");
            System.out.println("1. Додати сувенір");
            System.out.println("2. Редагувати сувенір");
            System.out.println("3. Видалити сувенір");

            System.out.println("4. Додати виробника");
            System.out.println("5. Редагувати виробника");
            System.out.println("6. Видалити виробника");

            System.out.println("7. Вивести інформацію про всі сувеніри");
            System.out.println("8. Вивести інформацію про сувеніри заданого виробника");
            System.out.println("9. Вивести інформацію про сувеніри, виготовлені в заданій країні");
            System.out.println("10. Вивести інформацію про виробників, чиї ціни на сувеніри менше заданої");
            System.out.println("11. Вивести інформацію по всіх виробниках та сувенірах, які вони виробляють");
            System.out.println("12. Вивести інформацію про виробників заданого сувеніра, виробленого у заданому році");
            System.out.println("13. Вивести список сувенірів, зроблених у заданому році");
            System.out.println("14. Видалити заданого виробника за назвою та його сувеніри");
            System.out.println("15. Видалити заданого виробника за унікальним id та його сувеніри");
            System.out.println("16. Завершити");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Очищення буфера

            switch (choice) {
                case 1:
                    // Додавання сувеніру
                    System.out.println("Введіть назву сувеніру:");
                    String name = scanner.nextLine();
                    System.out.println("Введіть назву виробника:");
                    String manufacturer_name = scanner.nextLine();
                    System.out.println("Введіть країну виробника:");
                    String manufacturer_country = scanner.nextLine();
                    // Check if the manufacturer already exists
                    Manufacturer newManufacturer = manufacturers.stream()
                            .filter(m -> m.getName().equals(manufacturer_name) && m.getCountry().equals(manufacturer_country))
                            .findFirst()
                            .orElse(null);

                    if (newManufacturer == null) {
                        // If the manufacturer doesn't exist, create a new one
                        newManufacturer = new Manufacturer(manufacturer_name, manufacturer_country);
                        manufacturers.add(newManufacturer);
                    }

                    //Manufacturer newManufacturer = new Manufacturer(manufacturer_name, manufacturer_country);
                    System.out.println("Введіть дату випуску (у форматі dd/mm/yyyy):");
                    String releaseDate = scanner.nextLine();
                    System.out.println("Введіть ціну:");
                    double price = scanner.nextDouble();
                    Souvenir newSouvenir = new Souvenir(name, newManufacturer, releaseDate, price);
                    souvenirFacade.addSouvenir(newSouvenir);
                    // Save the updated manufacturers list
                    fileStorage.saveManufacturers(manufacturers);
                    System.out.println("Сувенір додано.");
                    break;
                case 2:
                    // Редагування сувеніру
                    System.out.println("Введіть назву сувеніру, який ви хочете редагувати:");
                    String oldName = scanner.nextLine();
                    Souvenir editedSouvenir = souvenirs.stream()
                            .filter(souvenir -> souvenir.getName().equals(oldName))
                            .findFirst()
                            .orElse(null);
                    if (editedSouvenir != null) {
                        System.out.println("Enter the ID of the manufacturer:");
                        String manufacturerIdInput_2 = scanner.nextLine();
                        UUID manufacturerId_2 = UUID.fromString(manufacturerIdInput_2);
                        System.out.println("Введіть нову назву сувеніру:");
                        name = scanner.nextLine();
                        System.out.println("Введіть назву виробника:");
                        manufacturer_name = scanner.nextLine();
                        System.out.println("Введіть країну виробника:");
                        manufacturer_country = scanner.nextLine();
                        newManufacturer = new Manufacturer(manufacturer_name, manufacturer_country);
                        System.out.println("Введіть нову дату випуску (у форматі dd/mm/yyyy):");
                        releaseDate = scanner.nextLine();
                        System.out.println("Введіть нову ціну:");
                        price = scanner.nextDouble();
                        Souvenir newEditedSouvenir = new Souvenir(name, newManufacturer, releaseDate, price);
                        souvenirFacade.editSouvenir(oldName, manufacturerId_2, newEditedSouvenir);
                        System.out.println("Сувенір відредаговано.");
                    } else {
                        System.out.println("Сувенір не знайдено.");
                    }
                    break;

                case 3:
                    // Видалення сувеніру
                    System.out.println("Введіть назву сувеніру, який ви хочете видалити:");
                    String souvenirToDelete = scanner.nextLine();
                    System.out.println("Enter the ID of the manufacturer to delete:");
                    String manufacturerIdInput_3 = scanner.nextLine();
                    UUID manufacturerId_3 = UUID.fromString(manufacturerIdInput_3);
                    souvenirFacade.deleteSouvenir(souvenirToDelete,manufacturerId_3);
                    System.out.println("Сувенір видалено, якщо за такою інформацією він існував.");
                    break;

                case 4:
                    // Adding a manufacturer
                    System.out.println("Enter the name of the manufacturer:");
                    String manufacturerName = scanner.nextLine();
                    System.out.println("Enter the country of the manufacturer:");
                    String manufacturerCountry = scanner.nextLine();
                    Manufacturer newManufacturer4 = new Manufacturer(manufacturerName, manufacturerCountry);
                    souvenirFacade.addManufacturer(newManufacturer4);
                    System.out.println("Manufacturer added.");
                    break;

                case 5:
                    // Editing a manufacturer
                    try {
                        System.out.println("Enter the ID of the manufacturer to edit:");
                        String manufacturerIdInput = scanner.nextLine();
                        UUID manufacturerId = UUID.fromString(manufacturerIdInput);
                        System.out.println("Enter the new name of the manufacturer:");
                        String manufacturerName5 = scanner.nextLine();
                        System.out.println("Enter the new country of the manufacturer:");
                        String manufacturerCountry5 = scanner.nextLine();
                        Manufacturer newManufacturer5 = new Manufacturer(manufacturerName5, manufacturerCountry5);
                        souvenirFacade.editManufacturer(manufacturerId, newManufacturer5);
                        System.out.println("Manufacturer edited.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid manufacturer id. Please enter a valid UUID string.");
                    }
                    break;

                case 6:
                    // Deleting a manufacturer
                    try {
                        System.out.println("Enter the ID of the manufacturer to delete:");
                        String manufacturerIdInput_d = scanner.nextLine();
                        UUID manufacturerId_d = UUID.fromString(manufacturerIdInput_d);
                        souvenirFacade.deleteManufacturer(manufacturerId_d);
                        System.out.println("Manufacturer and related souvenirs have been deleted.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid manufacturer id. Please enter a valid UUID string.");
                    }
                    break;


                case 7:
                    // Виведення інформації про всі сувеніри
                    List<Souvenir> allSouvenirs = souvenirFacade.getAllSouvenirs();
                    for (Souvenir souvenir : allSouvenirs) {
                        souvenir.display();
                        System.out.println();
                    }
                    break;

                case 8:
                    // View souvenirs by manufacturer
                    System.out.println("Enter the manufacturer name:");
                    String manufacturerName8 = scanner.nextLine();
                    List<Souvenir> souvenirsByManufacturer = souvenirFacade.getSouvenirsByManufacturer(manufacturerName8);
                    for (Souvenir souvenir : souvenirsByManufacturer) {
                        souvenir.display();
                        System.out.println();
                    }
                    break;

                case 9:
                    // View souvenirs by country
                    System.out.println("Enter the country:");
                    String country = scanner.nextLine();
                    List<Souvenir> souvenirsByCountry = souvenirFacade.getSouvenirsByCountry(country);
                    for (Souvenir souvenir : souvenirsByCountry) {
                        souvenir.display();
                        System.out.println();
                    }
                    break;

                case 10:
                    // View manufacturers with prices below a given amount
                    System.out.println("Enter the maximum price:");
                    double maxPrice = scanner.nextDouble();
                    List<Manufacturer> manufacturersBelowPrice = souvenirFacade.getManufacturersBelowPrice(maxPrice);
                    System.out.println("Manufacturers Below Price:" + manufacturersBelowPrice);
                    for (Manufacturer manufacturer : manufacturersBelowPrice) {
                        manufacturer.display(); // Check the manufacturer's display method
                        System.out.println();
                    }
                    break;

                case 11:
                    // View manufacturers and their souvenirs
                    Map<Manufacturer, List<Souvenir>> manufacturersWithSouvenirs = souvenirFacade.getManufacturersWithSouvenirs();
                    for (Map.Entry<Manufacturer, List<Souvenir>> entry : manufacturersWithSouvenirs.entrySet()) {
                        entry.getKey().display();
                        for (Souvenir souvenir : entry.getValue()) {
                            souvenir.display();
                        }
                        System.out.println();
                    }
                    break;

                case 12:
                    // View manufacturers of a specific souvenir produced in a given year
                    System.out.println("Enter the souvenir name:");
                    String souvenirName = scanner.nextLine();
                    System.out.println("Enter the production year:");
                    int year = scanner.nextInt();
                    List<Manufacturer> manufacturersOfSouvenirInYear = souvenirFacade.getManufacturersOfSouvenirInYear(souvenirName, year);
                    for (Manufacturer manufacturer : manufacturersOfSouvenirInYear) {
                        manufacturer.display();
                        System.out.println();
                    }
                    break;


                case 13:
                    // List souvenirs by production year
                    Map<Integer, List<Souvenir>> souvenirsByYear = souvenirFacade.groupSouvenirsByYear();
                    for (Map.Entry<Integer, List<Souvenir>> entry : souvenirsByYear.entrySet()) {
                        System.out.println("Year: " + entry.getKey());
                        for (Souvenir souvenir : entry.getValue()) {
                            souvenir.display();
                        }
                        System.out.println();
                    }
                    break;

                case 14:
                    // Delete all manufacturer with such name and related souvenirs
                    System.out.println("Enter the manufacturer name to delete:");
                    String manufacturerToDelete = scanner.nextLine();
                    souvenirFacade.deleteManufacturerAndRelatedSouvenirs(manufacturerToDelete);
                    System.out.println("Manufacturer and related souvenirs have been deleted.");
                    break;

                case 15:
                    try {
                        System.out.println("Enter the manufacturer id to delete:");
                        String manufacturerIdInput15 = scanner.nextLine();
                        UUID manufacturerId15 = UUID.fromString(manufacturerIdInput15);
                        souvenirFacade.deleteManufacturerAndRelatedSouvenirsbyID(manufacturerId15);
                        System.out.println("Manufacturer and related souvenirs have been deleted.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid manufacturer id. Please enter a valid UUID string.");
                    }

                    break;

                case 16:
                    // Збереження даних в файли та завершення програми
                    fileStorage.saveSouvenirs(souvenirs);
                    fileStorage.saveManufacturers(manufacturers);
                    System.out.println("Дані збережено. Програма завершила роботу.");
                    return;
                default:
                    System.out.println("Некоректний вибір. Спробуйте ще раз.");
                    break;

            }

        }
    }
}
