// Name: Kimberly Colector
// Course: Software development 1 CEN 3024C
// Date: 10/13/2025
//Class Name : Main
// This class is to run the Login Gui.
// It makes sure that the window runs and show properly on the screen.


import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;

public class Main {
    public static void main(String[] args) {

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
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Login();
            }
        });
        }
    }
