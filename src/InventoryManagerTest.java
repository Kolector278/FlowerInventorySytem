import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
//Name: Kimberly Colector
//Course: Software development 1 CEN 3024C
//Date: 10/18/25
//Class name: Inventory Manager Test

class InventoryManagerTest {

    private  InventoryManager manager;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        manager = new InventoryManager();
        manager.clearForTesting();
    }
/*
Method name: addFlower positive testing
Purpose: To test that add flower method works as attended. With the correct input.
It makes sure the flower gets added to the inventory.
Arguments: none
Return Value:void

 */
    @org.junit.jupiter.api.Test
    void addFlower_PositiveTesting() {
    manager.addFlower("Rose","Pink",10,15.99,"yes","yes");

    // check the results
        assertEquals(1,manager.getInventory().size(),"Error flower was not accounted for failed test 1");
        assertEquals("Rose", manager.getInventory().get(0).getFlowerName(),"Error Flower was not added failed test 2");
    }

    /*
    Method name: addFlower Negative testing (testing failure)
    Purpose:To test the addFlower ia able to correctly handle invalid inputs as mention in comments in part one.
    It should return null for invalid inputs.
    Arguments: none
    Return Value:void
     */

    @Test
    void  addFlower_NegativeTesting(){
        Flower faildFlower = manager.addFlower("Orchid","White",12,11.99,"potatos","banana");
        assertNull(faildFlower,"error on failedFlower");
    }
    // end of add flower

    /*
    Method name: SearchByID positive testing
    Purpose: The method looks for the specific flower in the inventory.
    Arguments: String ID to search for.
    Return Value: it should return the flower object and messages that there a match.

     */

    @org.junit.jupiter.api.Test
    void searchByID_PositiveTesting() {
        Flower flowerToFind = manager.addFlower("Daisy","White",12,9.99,"yes","no");
        String idToFind = flowerToFind.getFlowerID();
        Flower foundFlower = manager.searchByID(idToFind);

        //check results
        assertNotNull(foundFlower);
        assertEquals("Daisy", foundFlower.getFlowerName(),"Error failed test 2");
    }
    /*
    Method name: SearchByID negativeTesting
    Purpose: to test Search by ID is able to handle a non-existent flower id.
    it should return null and the message id not found.
    Arguments: String ID to search for
    Return Value: null.
     */
        @Test
    void searchByID_NegativeTesting(){
        Flower failedSearch = manager.searchByID("KP278242");
        assertNull(failedSearch);
    }
// end of searchByID


    /*
    Method name: update Flower Price Positive Testing
    Purpose: To test if we are able to change a flower price using the setter method.
    testing the third option of sub menu in update using valid data.
    Arguments: none
    Return Value: void
     */
    // start of Updating section
    @org.junit.jupiter.api.Test
    void updateFlowerPrice_PositiveTesting() {
        Flower flowerToUpdate = manager.addFlower("Rose Lily","Pink",5,19.99,"no","no");
        String idToUpdate = flowerToUpdate.getFlowerID();
        Flower foundFlower = manager.searchByID(idToUpdate);
        // What we are updating
        flowerToUpdate.setFlowerPrice(25.99);
        // checking if it updated
        assertEquals(25.99,flowerToUpdate.getFlowerPrice(),"test failed for Update price");
    }

    /*
    Method name: updateFlowerQuantity positive testing
    Purpose: To test if we are able to change a flower quantity using the setter method.
    testing the second option of the sub menu in update using the correct data.
    Arguments: none
    Return Value:void
     */
    @Test
    void  updateFlowerQuantity_PostiveTesting(){
        manager.addFlower("Rose Lily","Pink",5,19.99,"no","no");
        String id = manager.getInventory().get(0).getFlowerID();

        Flower flowerToUpdate = manager.searchByID(id);
        flowerToUpdate.setFlowerQuantity(20);

        Flower updatedFlower = manager.searchByID(id);
        assertEquals(20,updatedFlower.getFlowerQuantity(),"test failed for updating quantity");
    }

    /*
    Method name: updateFlowerColor_PostiveTesting
    Purpose:test if we are able to change the color of the flower using correct data.
    Arguments:none
    Return Value:void
     */
    @Test
    void updateFlowerColor_PostiveTesting(){
        manager.addFlower("Rose Lily","Pink",5,19.99,"no","no");
        String id = manager.getInventory().get(0).getFlowerID();

        Flower flowerToUpdate = manager.searchByID(id);
        flowerToUpdate.setFlowerColor("White");

        Flower updatedFlower = manager.searchByID(id);
        assertEquals("White",updatedFlower.getFlowerColor(),"test failed for updating color");
    }


    // start of negative testing for Update section
    /*
    Method name:upDateFlowerPrice_NegativeTesting
    Purpose: To test that the method handle invalid inputs like negative numbers.
    Arguments: none
    Return Value: void
     */
   @Test
    void upDateFlowerPrice_NegativeTesting(){
        Flower flowerToTest = manager.addFlower("Tulip","Yellow",8,9.99,"no","no");
        boolean updateResult = flowerToTest.setFlowerPrice(-5.00);

        assertFalse(updateResult," the setter should have return false due to price");
        assertEquals(9.99,flowerToTest.getFlowerPrice()," the flower price should have stayed the same ?");



    }

    /*
    Method name:upDateFlowerQuantity_NegativeTesting
    Purpose:To test that the method handle invalid inputs like negative numbers.
    Arguments:none
    Return Value: void
     */
    @Test
    void upDateFlowerQuantity_NegativeTesting(){
        Flower flowerToTest = manager.addFlower("Rose Lily","Pink",5,19.99,"no","no");
        boolean updateResult = flowerToTest.setFlowerQuantity(-10);

        assertFalse(updateResult,"negative testing failed for quantity");
        assertEquals(5,flowerToTest.getFlowerQuantity(), "2nd Negative testing for quantity");

    }

    /*
    Method name:upDateFlowerColor_NegativeTesting
    Purpose: to test that the sub menu option for color  handles correctly invalid inputs.
    Arguments: none
    Return Value: void
     */
    @Test
    void upDateFlowerColor_NegativeTesting(){
        Flower flowerToTest = manager.addFlower("Tulips","Yellow",5,12.99,"no","no");
        boolean updateResult = flowerToTest.setFlowerColor("Pink2131e");

        assertFalse(updateResult,"Should return false the color is not a color");
        assertEquals("Yellow",flowerToTest.getFlowerColor(),"Flower color should have not change");
    }
    // End of Update section

    /*
    Method name:deleteFlower_PositiveTesting
    Purpose: To test  that the delete method works using valid data.
    Arguments: none
    Return Value: void
     */
   @org.junit.jupiter.api.Test
    void deleteFlower_PositiveTesting() {
        Flower flowerToDelete = manager.addFlower("Rose","Red",10,19.99,"yes","yes");
        String idToDelete = flowerToDelete.getFlowerID();
        boolean wasDeleted = manager.deleteFlower(idToDelete);

        assertTrue(wasDeleted,"test failed flower was not deleted");
    }

    /*
    Method name:deleteFlower_NegativeTesting
    Purpose: This test that the method c=handles the invalid data correctly with return
    of false. as we try to delet a non-existing flower.
    Arguments: none
    Return Value: void
     */
    @Test
    void deleteFlower_NegativeTesting(){
        boolean wasDeleted = manager.deleteFlower("KP234567");
        assertFalse(wasDeleted,"Test failed should not have deleted a flower that doesn't exist");

    }
    //End of delete flower

    /*
    Method name:calculateTotalValue_PositiveTesting
    Purpose: test if the total value calculation adds up correctly using the data
    in the inventory.
    Arguments:none
    Return Value: void
     */
    @org.junit.jupiter.api.Test
    void calculateTotalValue_PositiveTesting() {
        manager.addFlower("Rose","Red",10,2.00,"yes","yes");
        manager.addFlower("Lily","Pink",5,3.00,"yes","no");
        double totalValue = manager.calculateTotalValue();

        assertEquals(35.00,totalValue,"test failed it gave me the wrong total");
    }

    /*
    Method name:CalculateTotalValue_NegativeTesting
    Purpose:to check that if there is an empty inventory it should calculate 0.0.
    Arguments:none
    Return Value:void
     */
    @Test
    void calculateTotalValue_NegativeTesting(){
        double totalValue = manager.calculateTotalValue();
        assertEquals(0.0,totalValue,"Total should be 0.0 as inventory is empty");
    }
    // End of calculate total value

    /*
    Method name:getCatSafeFlower_PositiveTesting
    Purpose: it checks if the cat safe filter works correctly. using a inventory that has mix
    of safe and non-safe flowers for cats. it should only return a list of safe flowers
    Arguments: none
    Return Value: void
     */
    @org.junit.jupiter.api.Test
    void getCatSafeFlower_PositiveTesting() {
    manager.addFlower("Rose","red",10,12.99,"yes","yes");
    manager.addFlower("Gerba Daisy","white",8,9.99,"yes","yes");
    manager.addFlower("Lily","orange",5,19.99,"yes","no");

    List<Flower> safeFlowers = manager.getCatSafeFlower();
    assertEquals(2,safeFlowers.size(),"test failed list return all flowers");
    }

    /*
    Method name: getCatSafeFLower_NegativeTesting
    Purpose: To test the cat filter if there is not any safe flower in the inventory
    it should return empty list.
    Arguments:none
    Return Value:void
     */
    @Test
    void getCatSafeFLower_NegativeTesting(){
        manager.addFlower("Lily","red",10,12.99,"yes","no");
        manager.addFlower("Hydrangea","white",8,10.99,"no","no");

        List<Flower> safeFlowers = manager.getCatSafeFlower();
        assertEquals(0,safeFlowers.size(),"List should be empty");
    }


}