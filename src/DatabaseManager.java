import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Name: Kimberly Colector
 * Course:Software development 1 CEN 3024C
 * Date: 10/29/25
 * Class Name: DatabaseManager
 * Class Function:This class is the new engine for the inventory system. It replaces the
 * old InventoryManager. The class job is to handle the inventory database.
 * The FlowerGUI class will call methods from here to get , add , update or delete flowers from the database.
 *
 */



public class DatabaseManager {
private  String databaseURL;

    /**
     * Method Name: DatabaseManager (Constructor)
     * Purpose: This is what runs when the Login creates a new DatabaseManager.
     * It saves the database file path and loads the special SQLite driver so java can understand
     * how to talk the  database file.
     * @param databaseURL A string (Example: jdbc:sqlite:C:\temp\inventory.db) that
     * tells the manager where the database file is.
     */
    public  DatabaseManager(String databaseURL){
        this.databaseURL = databaseURL;
        try{
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC not found. ");
            e.printStackTrace();
        }
    }

    /**
     * Method: connect
     * Purpose: It a private helper method it opens a new connection to the database.
     * @return A Connection object
     * @throws SQLException if it cant connect to the database.
     */

private Connection connect() throws SQLException{
        return DriverManager.getConnection(databaseURL);
}

    /**
     * Method Name: getAllFlowers
     * Purpose: This garbs every single flower from teh 'products' table
     * and puts them into  a big list.This is what the GUI uses to fill teh JTable.
     * @return A List<Flower> with all the flower.
     */


public List<Flower> getAllFlowers(){
        String sql = "SELECT id,name,color,quantity,price,is_in_season,is_safe_for_cats FROM products";
        List<Flower> flowerList = new ArrayList<>();
        try(Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String color = rs.getString("color");
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");

                boolean inSeason = (rs.getInt("is_in_season")==1);
                boolean catSafe = (rs.getInt("is_safe_for_cats")== 1);

                String flowerIdString = String.valueOf(id);
                Flower flower = new Flower(flowerIdString,name,color,quantity,price,inSeason,catSafe);
                flowerList.add(flower);
            }
        } catch (SQLException e) {
            System.out.println("Error getting products"+ e.getMessage());
            e.printStackTrace();
        }
        return flowerList;
}
    /*
    Method: addFlower
    Purpose: Takes new info provided by user from pop up boxes and insert it as a new row in the
    products table.it also converts the yes and no in 1 and 0s for the database.
    Arguments: name - string the flower name, color -string the flower color,quantity - int the stock quantity
    price-double the flower price, isInSeason - string yes or no, isSafeForCats-String yes or no.

    Return value: A new flower object it just created with new 6 digit ID or return null if something went wrong.
     */

    /**
     * Method Name : addFlower
     * Purpose: Takes all the info from the GUI pop up boxes and inserts
     * it as new row in the 'products' table.
     * @param name The flower's name.
     * @param color The flower's color.
     * @param quantity The stock quantity.
     * @param price The flower's price.
     * @param isInSeason "yes"or"no"
     * @param isSafeForCats "yes" or "no"
     * @return The newly created Flower object it just created with the new 6 digit ID. Returns null if something went wrong.
     */

    public Flower addFlower(String name,String color,int quantity,double price,String isInSeason, String isSafeForCats){
        String sql ="INSERT INTO products(name,color,quantity,price,is_in_Season,is_safe_for_cats) Values(?,?,?,?,?,?)";
        int inSeasonInt = isInSeason.equalsIgnoreCase("yes")? 1:0;
        int catSafeInt = isSafeForCats.equalsIgnoreCase("yes")? 1:0;
        try(Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
           pstmt.setString(1,name);
           pstmt.setString(2,color);
           pstmt.setInt(3,quantity);
           pstmt.setDouble(4,price);
           pstmt.setInt(5,inSeasonInt);
           pstmt.setInt(6,catSafeInt);
           int affectedRows = pstmt.executeUpdate();

           if (affectedRows >0 ){
               try(ResultSet rs = pstmt.getGeneratedKeys()){
                   if (rs.next()){
                       int id = rs.getInt(1); // The new 6 digit ID
                       return  new Flower(String.valueOf(id),name,color,quantity,price,inSeasonInt == 1, catSafeInt ==1);  // Convert back to string
                   }
               }
           }

        }catch (SQLException e){
            System.out.println("Error adding flower: "+e.getMessage());
        }
        return null;
}

    /**
     * Method: deleteFlower
     * Purpose: Deletes a specific flower record from the products table using its ID.
     * @param flowerId The 6 digit ID of the flower to delete.
     */


public  void  deleteFlower(String flowerId){
      String sql = "DELETE FROM products WHERE id = ?";
      try(Connection conn = connect();
      PreparedStatement pstmt = conn.prepareStatement(sql)){
          int id = Integer.parseInt(flowerId);
          pstmt.setInt(1,id);
          pstmt.executeUpdate();
      }catch (SQLException e){
          System.out.println("Error in deleting flower: "+ e.getMessage());
      }catch (NumberFormatException e){
          System.out.println("Error: ID to delete was not a valid number: "+ flowerId);
      }
}

    /**
     * Method Name: updateFlowerColor
     * Purpose: Updates just the color for one flower in the database.
     * @param flowerId The 6 digit ID of the flower to update.
     * @param newColor The new color to save.
     * @return true if it worked , false if it failed.
     */

// Update Section//
    /*
Method:updateFlowerColor
Purpose: updates just the color for the flower that was selected from the database.
Arguments: flowerId - tsring , the ID of the flower to update,
newColor - string the new color to save.
Return value: boolean, true if it works or false if it failed.
 */

public  boolean updateFlowerColor(String flowerId,String newColor){
        String  sql = "UPDATE products Set color = ? WHERE id = ?";
        try(Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)){
             pstmt.setString(1,newColor);
             pstmt.setInt(2,Integer.parseInt(flowerId));
             pstmt.executeUpdate();
             return  true;
        }catch (Exception e){
            System.out.println("Error updating color "+ e.getMessage());
            return false;
        }

}

    /**
     * Method: updateFlowerQuantity
     * Purpose: Updates just the quantity for one flower in the database.
     * @param flowerId The 6 didgit ID of the flower to update.
     * @param newQuantity The new number to save.
     * @return true if it worked, false if it failed.
     */

public boolean updateFlowerQuantity(String flowerId, int newQuantity){
        String sql = "UPDATE products SET quantity = ? WHERE id = ?";
        try(Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,newQuantity);
            pstmt.setInt(2,Integer.parseInt(flowerId));
            pstmt.executeUpdate();
                    return true;
        }catch (Exception e){
            System.out.println("Error updating quantity "+ e.getMessage());
            return false;
        }
    }

    /**
     * Method: updateFlowerPrice
     * Purpose: Updates just the price for the flower in the database.
     * @param flowerId The 6 digit ID of the flower to update.
     * @param newPrice Then new price to save.
     * @return true if it worked , false if it failed
     */

public boolean updateFlowerPrice(String flowerId, double newPrice){
        String sql = "UPDATE products SET price = ? WHERE id = ?";
        try(Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setDouble(1,newPrice);
            pstmt.setInt(2,Integer.parseInt(flowerId));
            pstmt.executeUpdate();
            return true;
        }catch (Exception e){
            System.out.println("Error updating price "+ e.getMessage());
            return  false;
        }
}

    /**
     * Method: searchBYNameorColor
     * @param searchTerm The text the user typed examples red or roses.
     * @return a List <Flower > with all the flowers that matched or an empty list
     * if no matches were found.
     */


public List<Flower> searchBYNameorColor(String searchTerm){
        String sql = "SELECT * FROM products WHERE name LIKE ? OR color LIKE?";
        List<Flower> flowerList = new ArrayList<>();

        String searchPattern = "%"+ searchTerm + "%";
        try(Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String color = rs.getString("color");
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");
                boolean inSeason = (rs.getInt("is_in_season")== 1);
                boolean catSafe = (rs.getInt("is_safe_for_cats")==1);

                Flower flower = new Flower(
                        String.valueOf(id),name,color,quantity,price,inSeason,catSafe);

                flowerList.add(flower);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Error searching by name or color: "+e.getMessage());

        }
        return flowerList;
}

    /**
     * Method: CalculateTotalValue
     * Purpose: Calculates the total value of all flowers in the inventory.
     * @return a double with the total value Example: 121.23
     */
public double calculateTotalValue(){
        String sql = "SELECT sum (quantity * price)FROM products";
        double totalValue = 0.0;
        try(Connection conn = connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)){
            if (rs.next()){
                totalValue = rs.getDouble(1);
            }
        }catch (SQLException e){
            System.out.println("Error calculating total value"+ e.getMessage());
        }
        return  totalValue;
}

}// End of the class