import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.PrintWriter;

//Name: Kimberly Colector
//Course: Software development 1 CEN 3024C
//Date: 10/13/2025
//Class Name: Inventory Manager
// Class Function : The class works as the engine of the program. it deals with main logic
// for managing the flower list like loading it from the file, saving it back and handling.
// Such as adding,finding, updating and deleting flowers.



public class InventoryManager {
    List<Flower> inventory = new ArrayList<>();
    private int nextFlowerId = 100000;
    private  String currentFilePath;

    public  InventoryManager(){
        this.currentFilePath = "FlowerInventory.txt";
        loadFromFile(this.currentFilePath);
        resetIDCounter();
    }
// Method name: resetIDCounter
//Purpose:  the method make sure there is no duplicate IDs.
// it looks through the flower in the inevntory and find the highest ID.
//Then set the next flower ID to be one higher. which is use after loading the file.
// Arguments: none
// Return Value: void as it creating the ID
    public void resetIDCounter() {
        // Finds the highest  ID from the inventory to prevent creating duplicates ID
        int maxId = 0;
        for (Flower flower: inventory){
            try{
                int currentID = Integer.parseInt(flower.getFlowerID().substring(2));
                if (currentID > maxId){
                    maxId = currentID;
                }
            } catch (NumberFormatException e) {
                System.out.println("Something went wrong");
            }

        }
        if (maxId >= 100000){
            this.nextFlowerId = maxId +1;
        }else{
            this.nextFlowerId = 100000;
        }
    }

    public List<Flower> getInventory(){
        return inventory;
    }

    // Method name: saveToFile
    // Purpose: the Method saves the current inventory back tot he current file.
    // Then uses the file stored in the currentFilePath varible, so when
    // user opens their own file it save the changes to it.
    //Arguments: none
    // Return Value: Void  due to only job is perform an action which is saving the data to the txt file.
    // doesn't need to return anything.

    public void saveToFile(){
        try{
         File inventoryFile = new File(this.currentFilePath);
         PrintWriter writer = new PrintWriter(inventoryFile);
       for (Flower flower: inventory){
           // changes true/false values back to yes and no so the file format stay consistent.
       String lineToWrite = flower.getFlowerID()+","+flower.getFlowerName()+","+
               flower.getFlowerColor()+","+ flower.getFlowerQuantity()+","+
               flower.getFlowerPrice()+","+(flower.isInSeason() ? "yes":"no")+","+(
               flower.isSafeForCats()?"yes":"no");
       writer.println(lineToWrite);

       }
       writer.close();
        } catch (Exception e) {
            System.out.println(" Error could not save to file"+ this.currentFilePath);
        }
    }// Method name: loadFromFile
    // Purpose: it loads a new inventory form the file. then takes the file path  as
    // input  and clears the old Inventory to avoid duplicates
    // and reads  the file line by line .
    //Arguments: FilePath -String the full file path of the txt file to load
    // Return Value: Boolean it returns true if it works if not false.

// choice 1
    public boolean loadFromFile(String filePath){
        this.currentFilePath = filePath;
        File file = new File(filePath);
        try{
            Scanner fileScanner = new Scanner(file);
            inventory.clear();
            while(fileScanner.hasNextLine()){
                String line = fileScanner.nextLine();
                if (line.isEmpty()){
                    continue;}

                try {
                    String[] data = line.split(",");

                    if (data.length < 7) {
                        System.out.println("not enough data" + line);
                        continue;
                    }

                    String idFromFile = data[0].trim();
                    String nameFromFile = data[1].trim();
                    String colorFromFile = data[2].trim();
                    // dealing with numbers
                    int quantityFromFile = Integer.parseInt(data[3].trim());
                    double priceFromFile = Double.parseDouble(data[4].trim());

                    //Deals with boolean
                    boolean seasonFromFile = data[5].trim().equalsIgnoreCase("yes");
                    boolean isSafeFromFile = data[6].trim().equalsIgnoreCase("yes");
                    Flower flowerFromfile = new Flower(idFromFile, nameFromFile, colorFromFile, quantityFromFile, priceFromFile, seasonFromFile, isSafeFromFile);
                    inventory.add(flowerFromfile);
                } catch (NumberFormatException e) {
                    System.out.println("Error: skipping line couldn't be read " + line);
                }




            }fileScanner.close();
            return true;

        } catch (FileNotFoundException e) {
            System.out.println("Inventory file not found");
            return false;
        }
    }
    // Method name: addFlower
    // Purpose: makes a new flower object by using the details the user enters and add it to the list.
    //Arguments: name string , the name of the flower, color string , color of the flower, quantity int , the stock quantity
    // price - double, the packet price, isInseason string , user input yes/no and is safe for cats string also yes/no.
    // Return Value: the flower object if it was created successfully or null if was not like something went wrong.
    //Choice 2
    public Flower addFlower(String name,String color,int quantity,double price, String isInSeason,String isSafeForCats){
        // creates the Id
        String newId = "KP"+ nextFlowerId;
        boolean isSeasonBoolean;

        // Handles is in season input
        if (isInSeason.equalsIgnoreCase("yes")){
            isSeasonBoolean = true;
        }else if (isInSeason.equalsIgnoreCase("no")){
            isSeasonBoolean = false;
        }else{
            System.out.println("Invaild input type. Please type in yes or no.");
            return null;

        }
        // handles is  safe for cats input
        boolean isSafeForCatsboolean;
        if (isSafeForCats.equalsIgnoreCase("yes")){
            isSafeForCatsboolean = true;
        } else if (isSafeForCats.equalsIgnoreCase("no")) {
            isSafeForCatsboolean = false;
        }else{System.out.println("Invalid input. type in yes or no"); return null;}
        // creates new flower instance
        Flower newFlower = new Flower(newId,name,color,quantity,price,isSeasonBoolean,isSafeForCatsboolean);
        // add the new flower to list
        inventory.add(newFlower);
        nextFlowerId++;
        return  newFlower;
    }
    // Method name: searchByID
    // Purpose: searches the inventory list for flower with matching ID.
    //Arguments: idToSearch - String the Id to search.
    // Return Value: the flower if found or null if cannot found.

    // choice 3
    public Flower searchByID(String idtoSearch){
     for(Flower flower: inventory){
        if (idtoSearch.equals(flower.getFlowerID())){
            System.out.println(" We Found a match.");
            return flower;
        }
     }
        return null;
    }

    // Method name: UpdateFlower
    // Purpose: the method find the flower the user wants to update.
    // it takes the Id and returns with flower if its found.
    //Arguments: idToUpDate - string  which is the ID  of the flower
    // Return Value: The matching Flower that ah s the ID and if not returns null;

    // choice 4
    public Flower updateFlower(String idToUpdate){
    Flower flowerToUpdate = searchByID(idToUpdate);
    if (flowerToUpdate != null){
        System.out.println("Brining flower to update...");
        return flowerToUpdate;
    }else {return null;}
    }
    // Method name: deleteFlower
    // Purpose: inds and removes a flower from the flower inventory base on ID.
    //Arguments: deleteFlowerById - String the Id of the flower to remove.
    // Return Value: boolean, true if the deletion was successful and false if not.

    // choice 5
    public boolean deleteFlower(String deleteFlowerById){
        Flower searchForDeleting = searchByID(deleteFlowerById);
        if (searchForDeleting != null) {
            inventory.remove(searchForDeleting);
            System.out.println("Flower has been deleted");
            return true;
        }else {
            System.out.println(" Flower was not found");
            return false;
        }
    }
    // Method name: calculateTotalValue
    // Purpose: To calculate the total value of the entire inventory.
    //Arguments: none
    // Return Value: double the total value of the inventory.
    // choice 6
    public double calculateTotalValue(){
        double totalValue = 0.0;
        for (Flower flower: inventory){
            totalValue += flower.getFlowerQuantity()*flower.getFlowerPrice();
        }
        return totalValue;

    }

    // Method name:getCatSafeFlower
    // Purpose: create and returns a new list containing only flowers that are marked as
    // safe for cats (yes) .
    //Arguments:none
    // Return Value: A List flower containing only safe flowers for cats.
    // choice 7
    public List<Flower> getCatSafeFlower(){
        List<Flower> catsafeList = new ArrayList<>();
        for (Flower flower : inventory){
            if (flower.isSafeForCats()){
                catsafeList.add(flower);
            }
        }
        return catsafeList;
    }
    // will delete after testing
    //Method name: Clear for testing
    // Purpose: method for test. it clears the Inventory
    // and resets the Id counter. make sure we start new each one.
    // Arguments: None
    // return Value: void

    public  void  clearForTesting(){
        inventory.clear();
        nextFlowerId = 100000;
    }
}


