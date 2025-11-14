/**
 *  Name: Kimberly Colector
 *  Course: Software development 1 CEN 3024C
 *  Date: 10/13/2025
 *  Class Name: Flower
 *  Class function: Acts as the blueprint for a single flower.Its purpose is to stores the information like ID,name,color,price,if its in season and if the flower is safe for cats.
 *  it holds the data for each flower. It does not perform any business logic it is simply a container for data.
 */

public class Flower {
    String flowerID;
    String flowerName;
    String flowerColor;
    int flowerQuantity;
    double flowerPrice;
    boolean isInSeason;
    boolean isSafeForCats;

    /**
     * Method Name: Flower(Constructor)
     * Purpose: Creates a new Flower object with all its properties.
     * @param flowerID The unique 6 digit ID
     * @param flowerName The name
     * @param flowerColor The color
     * @param flowerQuantity The number in stock
     * @param flowerPrice The price
     * @param inSeason True if in season , false if not
     * @param isSafeForCats True if safe for cats, false if not
     */


    public Flower(String flowerID,String flowerName,String flowerColor, int flowerQuantity,double flowerPrice,boolean inSeason, boolean isSafeForCats){
       this.flowerID = flowerID;
       this.flowerName = flowerName;
       this.flowerColor = flowerColor;
       this.flowerQuantity = flowerQuantity;
       this.flowerPrice = flowerPrice;
       this.isInSeason = inSeason;
       this.isSafeForCats = isSafeForCats;

    }
    // Getters

    /**
     *
     * @return The flower's  6 digit ID as a String.
     */
    public String getFlowerID(){
        return this.flowerID;
    }

    /**
     *
     * @return The flower's name as String.
     */
    public String getFlowerName(){
        return this.flowerName;
    }

    /**
     *
     * @return The flower's color as a String.
     */
    public String getFlowerColor(){
        return this.flowerColor;
    }

    /**
     *
     * @return The flower's quantity as an int.
     */
    public int getFlowerQuantity(){
        return this.flowerQuantity;
    }

    /**
     *
     * @return The flower's price as a double.
     */
    public double getFlowerPrice(){
        return this.flowerPrice;
    }

    /**
     *
     * @return True if in season, false if not.
     */
    public boolean isInSeason(){
        return  this.isInSeason;
    }

    /**
     *
     * @return True if safe for cats,false if not.
     */
    public  boolean isSafeForCats(){
        return this.isSafeForCats;
    }

    // Setters

    /**
     *
     * Method Name: setFlowerName
     * Purpose:  Updates the flower's name with validation.
     * @param newName The new name to set.
     * @return boolean true if update was successful( passes validation), false if not.
     */
    public boolean setFlowerName(String newName){
        for (int i = 0; i < newName.length(); i++){
            char currentChar = newName.charAt(i);
            if(!Character.isLetter(currentChar)&& !Character.isSpaceChar(currentChar)){
                return false;
            }
        }
        this.flowerName = newName;
        return true;

    }

    /**
     * Method Name: setFlowerColor
     * Purpose: Updates the flower's color with validation.
     * @param newColor The new color to set.
     * @return boolean true if update was successful (passes validation), false if not.
     */
    public boolean setFlowerColor(String newColor){
        for(int i = 0; i < newColor.length(); i++){
            char currentCharC = newColor.charAt(i);
            if(!Character.isLetter(currentCharC)&& !Character.isSpaceChar(currentCharC)){
                return false;
            }
        }
        this.flowerColor = newColor;
        return true;
    }

    /**
     * Method Name: setFlowerQuantity
     * Purpose: Updates the flower's quantity with validation.
     * @param newQuantity The new quantity to set.
     * @return boolean , true if update was successful 0 or more , false if not.
     *
     */
    public boolean setFlowerQuantity(int newQuantity){
        if (newQuantity >= 0) {
            this.flowerQuantity = newQuantity;
            return true;
        }else{
            return false;
        }
    }

    /**
     * Method Name : setFlowerPrice
     * Purpose: Updates the flower's price with validation.
     * @param newPrice The new price to set.
     * @return boolean, true if update was successful 0.0 or more, false if not.
     */
    public boolean setFlowerPrice(double newPrice){
        if(newPrice > 0){
            this.flowerPrice = newPrice;
            return true;
        } else { return false;}
    }

    /**
     * Method Name: setIsInSeason
     * Purpose: Updates the flower's season status.
     * @param newStatus Then new boolean status.
     * @return boolean, always return true.
     */
    public boolean setIsInSeason(boolean newStatus){
        this.isInSeason = newStatus;
        return true;
    }

    /**
     * Method Name: setISSafeForCats
     * Purpose: Updates the flower's cat safety status.
     * @param newStatus The new boolean status.
     * @return boolean, always return true.
     */
    public boolean setIsSafeForCats(boolean newStatus){
        this.isSafeForCats = newStatus;
        return true;
    }

    /**
     * Method Name: toString
     * Purpose: Provides a clean readable string that representation of a Flower object.
     * @return A formatted String with the flower's details.
     */

@Override
    public String toString(){
     return this.flowerID+" - " +this.flowerName+"(" + this.flowerColor+"), QTY: "+ this.flowerQuantity+ ", Price: $"+ this.flowerPrice+",In Season: "+ this.isInSeason + ", Safe For Cats: "+this.isSafeForCats;
    }

}
