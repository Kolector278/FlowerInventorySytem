


import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;
/**
 * Name: Kimberly Colector
 *  Course: Software development 1 CEN 3024C
 *  Date: 10/13/2025
 * Class Name : Main
 *Class Function:
 * This is the main entry point of Inventory application.
 * The class is to run the Login Gui and set font for all windows.
 * It makes sure that the window runs and show properly on the screen.
 */
public class Main {
    /**
     * Method Name: Main
     * Purpose: It set the default font and then creates and shows the Login Frame.
     * @param args none
     */
    public static void main(String[] args) {
// set the default font
try{
    Font modernFont = new Font("Sego UI",Font.BOLD,14);
    Enumeration<Object> keys = UIManager.getDefaults().keys();
    while (keys.hasMoreElements()){
        Object key = keys.nextElement();
        Object value = UIManager.getDefaults().keys();
        if (value instanceof FontUIResource){
            UIManager.put(key,new FontUIResource(modernFont));
        }
    }

} catch (Exception e) {
    System.err.println("Error setting font");
}
// Starts the login window
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Login();
            }
        });
        }
    }
