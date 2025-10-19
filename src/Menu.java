
import java.util.List;
import java.util.Scanner;
public class Menu {
    private InventoryManager manager;
    private Scanner userInput;
//Name: Kimberly Colector
//Course: Software development 1 CEN 3024C
//Date: 10/13/2025
//Class Name: Menu
// Class function: this class is the main user interface for the program.
// it shows the menu options for the user and get their input.
// Then tell the Inventory Manager what method to perform base on user choice.

    public Menu(){
        this.manager = new InventoryManager();
        this.userInput = new Scanner(System.in);
    }
    // Method name: run
    // Purpose:  method for the menu it starts a loop that keeps showing the user
    // the options and the process their choice.
    //Arguments: none
    // Return Value: void due to it only to sun the menu loop.
    public void run(){
        while (true){
            System.out.println("1. View All Flowers");
            System.out.println("2. Add New Flower");
            System.out.println("3. Search by ID");
            System.out.println("4. Update Flower");
            System.out.println("5. Delete Flower");
            System.out.println("6. In total of inventory");
            System.out.println("7. Filter for only safe for cats");
            System.out.println("8.Load own Inventory File");
            System.out.println("9.Exit");
            String choice = userInput.nextLine();


            // choices sections
            // choice 1
            if (choice.equals("1")){
                System.out.println(" Current Inventory ");
                List<Flower> flowers_List = manager.getInventory();

                if (flowers_List.isEmpty()){
                    System.out.println(" Inventory is empty");
                }else {
                    for (Flower flower : flowers_List){
                        System.out.println(flower);}
                }


                // choice 2
            }else if(choice.equals("2")){
                System.out.println("Enter Flower Name : ");
                String name = userInput.nextLine();

                System.out.println("Enter Flower Color : ");
                String color = userInput.nextLine();
                // dealing with number hooray -_-
                int quantity;
                while(true) {
                    System.out.println("Enter quantity : ");
                    String quantityInput = userInput.nextLine();
                    try {
                        quantity = Integer.parseInt(quantityInput);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println(" Sorry only whole number. Try again" );
                    }
                }

                double price;
                while(true) {
                    System.out.println("Enter Price : ");
                    String priceInput = userInput.nextLine();
                    // Handles user input to ensure number is valid and prevents system crash.
                    try{
                        price = Double.parseDouble(priceInput);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Sorry please Eneter a Price (Ex: 5.99) Try again");
                    }
                }

                // that deals for boolean In Season
                String isInSeason;
                while(true) {
                    System.out.println("Is in season? (yes/no): ");
                    isInSeason = userInput.nextLine();
                    if (isInSeason.equalsIgnoreCase("yes")|| isInSeason.equalsIgnoreCase("no")){
                        break;
                    }else {System.out.println(" Please only type in yes or no.");}
                }
                // deals with is safe for cats
                    String safe;
                    while(true){
                        System.out.println("Is the flower safe for cats?(yes/no): ");
                        safe = userInput.nextLine();
                        if (safe.equalsIgnoreCase("yes")|| safe.equalsIgnoreCase("no")){
                            break;
                        }else {System.out.println(" Please only type in yes or no");}
                    }

                        Flower newAddedFlower = manager.addFlower(name, color, quantity, price, isInSeason, safe);
                        // message
                        if (newAddedFlower != null) {
                            System.out.println("Success! " + newAddedFlower.getFlowerName() + " ID: " + newAddedFlower.getFlowerID() + " was added.");
                        } else {
                            System.out.println(" Invalid choice try again.");
                        }


                //Choice 3
            } else if (choice.equals("3")) {
                System.out.println("Enter flower id to search for: ");
                String idToSearch = userInput.nextLine();
                Flower foundFlower = manager.searchByID(idToSearch);
                if (foundFlower != null) {
                    System.out.println("Found: " + foundFlower);
                } else {
                    System.out.println("Flower with that ID not found");
                }
                //choice 4
            }else if (choice.equals("4")){
                System.out.println(" Enter ID of flower to update: ");
                String idToUpdate = userInput.nextLine();
                Flower flowerToUpdate = manager.searchByID(idToUpdate);


                if ( flowerToUpdate != null) {
                    System.out.println("Found: "+ flowerToUpdate);
                    System.out.println("1.Update Color ");
                    System.out.println("2.Update Quantity");
                    System.out.println("3.Update Price");
                    System.out.println("4.Cancel");
                    String updateChoice = userInput.nextLine();

                    //actual start of the sub menu
                    if (updateChoice.equals("1")) {
                        System.out.println("Whats the new color: ");
                        String newColor = userInput.nextLine();
                        flowerToUpdate.setFlowerColor(newColor);
                    } else if (updateChoice.equals("2")){
                        int newQuantity;
                        while (true) {
                            System.out.println("Whats the new quantity: ");
                            try {
                                newQuantity = Integer.parseInt(userInput.nextLine());
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println(" Only whole number please");
                            }

                        }
                        flowerToUpdate.setFlowerQuantity(newQuantity);
                        System.out.println("Update is complete");


                    } else if (updateChoice.equals("3")) {
                        double newPrice;
                        while (true) {
                            System.out.println("Whats the new price: ");
                            try {
                                newPrice = Double.parseDouble(userInput.nextLine());
                                 break;
                            } catch (NumberFormatException e) {
                                System.out.println(" try again enter price (ex: 5.99) ");
                            }
                        }flowerToUpdate.setFlowerPrice(newPrice);
                        System.out.println( "Update successful...");

                    } else if (updateChoice.equals("4")) {
                        System.out.println(" Update is Canceled...");
                    }

                }else { System.out.println("Flower was not found ");}
            } else if (choice.equals("5")) {
                System.out.println("Enter Flower Id to delete from Inventory: ");
                String deleteflowerById = userInput.nextLine();
                boolean wasSuccessful = manager.deleteFlower(deleteflowerById);
                if (wasSuccessful){
                    System.out.println("Flower was deleted");
                }else {
                    System.out.println("Sorry could not delete");
                }

                
            } else if (choice.equals("6")) {
                double grandTotal = manager.calculateTotalValue();
                String formatt = String.format("%.2f", grandTotal);
                System.out.println("Total Inventory Value: $"+ formatt);
                
            } else if (choice.equals("7")) {
                System.out.println("Here are the flowers safe for cats:");
                List<Flower> safeFlowers = manager.getCatSafeFlower();
                if (safeFlowers.isEmpty()){
                    System.out.println(" No cat safe flowers found in inventory");
                }else {
                    for (Flower flower : safeFlowers){
                        System.out.println(flower);
                    }
                }

            } else if (choice.equals("8")) {
                System.out.println("Please enter full file path");
                String customFilePath = userInput.nextLine();
                boolean didLoad = manager.loadFromFile(customFilePath);

                if (didLoad){
                    manager.resetIDCounter();
                    System.out.println("Successfully loaded new Inventory from: "+ customFilePath);
                }else{
                    System.out.println(" Error ");
                }


            } else if (choice.equals("9")) {
            System.out.println("Saving any changes....");
            manager.saveToFile();
            System.out.println("System is exiting");
            break;}



        }
    }

}
