
// Name: Kimberly Colector
// Course: Software development 1 CEN 3024C
// Date: 10/13/2025
// Class function: Acts as the blueprint for a single flower.
// It stores the information like ID,name,color,price,if its in season and if the flower is safe for cats.
// it holds the data for each flower.
public class Flower {
    String flowerID;
    String flowerName;
    String flowerColor;
    int flowerQuantity;
    double flowerPrice;
    boolean isInSeason;
    boolean isSafeForCats;


    public Flower(String flowerID,String flowerName,String flowerColor, int flowerQuantity,double flowerPrice,boolean inSeason, boolean isSafeForCats){
       this.flowerID = flowerID;
       this.flowerName = flowerName;
       this.flowerColor = flowerColor;
       this.flowerQuantity = flowerQuantity;
       this.flowerPrice = flowerPrice;
       this.isInSeason = isInSeason;
       this.isSafeForCats = isSafeForCats;

    }
    // getters
    public String getFlowerID(){
        return this.flowerID;
    }
    public String getFlowerName(){
        return this.flowerName;
    }
    public String getFlowerColor(){
        return this.flowerColor;
    }
    public int getFlowerQuantity(){
        return this.flowerQuantity;
    }
    public double getFlowerPrice(){
        return this.flowerPrice;
    }
    public boolean isInSeason(){
        return  this.isInSeason;
    }
    public  boolean isSafeForCats(){
        return this.isSafeForCats;
    }

    //this section below is for setters
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
    public boolean setFlowerQuantity(int newQuantity){
        if (newQuantity >= 0) {
            this.flowerQuantity = newQuantity;
            return true;
        }else{
            return false;
        }
    }
    public boolean setFlowerPrice(double newPrice){
        if(newPrice >+ 0){
            this.flowerPrice = newPrice;
            return true;
        } else { return false;}
    }
    public boolean setIsInSeason(boolean newStatus){
        this.isInSeason = newStatus;
        return true;
    }
    public boolean setIsSafeForCats(boolean newStatusy){
        this.isSafeForCats = newStatusy;
        return true;
    }

    // give simple and clear description  of the flower obj making it easy to read and print.
@Override
    public String toString(){
     return this.flowerID+" - " +this.flowerName+"(" + this.flowerColor+"), QTY: "+ this.flowerQuantity+ ", Price: $"+ this.flowerPrice+",In Season: "+ this.isInSeason + ", Safe For Cats: "+this.isSafeForCats;
    }

}
