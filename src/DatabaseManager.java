import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//Course:Software development 1 CEN 3024C
// Date: 10/29/25
//Class Name: DatabaseManager
// Class Function: this class is the new engine for the inventory. it replaces the
// old InventoryManager. the class job is to handle the inventory database.
// it connects to the FlowerGUI by using the methods from here.


public class DatabaseManager {
private  String databaseURL;
/*
Method Name: DatabaseManager
Purpose: it saves the database filePath and load the special SQLite so Java can understand
and talk to database file.
Arguments:databaseURL - string ex: C:\temp\inventory.db. that tell the manager where teh database file is.
Return Value: none
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
/*
Method: connect
Purpose: It opens  a new connection to the database
Arguments: None
Return value: a connection object.
 */
private Connection connect() throws SQLException{
        return DriverManager.getConnection(databaseURL);
}
    /*
    Method: getAllFlowers
    Purpose: grabs evry flower from the products table and put them into a list.
    the GUI uses to fill the Table.
    Arguments: None
    Return value: a List <Flower> with all the flowers.
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
                       int id = rs.getInt(1);
                       return  new Flower(String.valueOf(id),name,color,quantity,price,inSeasonInt == 1, catSafeInt ==1);
                   }
               }
           }

        }catch (SQLException e){
            System.out.println("Error adding flower: "+e.getMessage());
        }
        return null;
}

/*
Method: deleteFlower
Purpose: Delete a flower from the products table. it gets the 6 digit ID  string then converts it
to numbers and runs the sql DELETE command.
Arguments: flowerID - string  the 6 digit Id  of the flower to delete.
Return value: void
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
/*
Method: updateFlowerQuantity
Purpose: update the quantity for the flower selected in the database.
Arguments: flowerId - string Id of the flower.
* newQuantity int the new number to save.
Return value: boolean true if it worked or false if it failed.
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
    /*
Method: updateFlowerPrice
Purpose: Updates the price for the flower selected from the database.
Arguments: flowerId - string Id of the flower.
          * newPrice - double the new price to save.
Return value: boolean true if it worked or false if it failed.
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

    /*
    Method: searchBYNameorColor
    Purpose: finds all flower that match the search term. it checks both name and color columns.
    Arguments: searchTerm - String text of what the user type in ex: Rose or pink.
    Return value: a List <Flower> with all the flowers that match or empty list if no matches are found.
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

/*
Method: CalculateTotalValue
Purpose: Calculates the total value of all flowers in the inventory by (quantity * price).
Arguments: none
Return value: double with total Value of the inventory.
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