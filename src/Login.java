import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Name: Kimberly Colector
 * Course: Software development 1 CEN 3024C
 * Date: 10/13/2025
 * Class Name: Login
 * Class Function: The class creates the Login window when the program starts.
 * It asks the user for a username and password. If the login is correct it closes itself, then ask for
 * the database path , and then opens the main FlowerGUI window.
 */
public class Login extends JFrame {
    // The components
    private JTextField usernameField;
    private  JPasswordField passwordField;
    private  JButton loginButton;
    private  JLabel logoLabel;

    /**
     * Method Name: Login -the constructor
     * Purpose: Builds the login window.It sets up all the visual components
     * examples : Logo,buttons,text fields
     * and add the action Listener.
     */

    public Login(){
        // window setup
        super("Ophelia Garden Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10,10));
        // Logo Panel
        JPanel logoPanel = new JPanel();
        try{
            ImageIcon logoIcon = new ImageIcon("flowershoplogoInventory.png");
            Image image = logoIcon.getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH);
            logoLabel = new JLabel(new ImageIcon(image));
            logoPanel.add(logoLabel);
        } catch (Exception e) {
            logoPanel.add(new JLabel("Logo Error"));
            System.err.println("Error loading logo: "+e.getMessage());
        }
           add(logoPanel,BorderLayout.NORTH);

        // Input fields Panel
        JPanel inputpanel = new JPanel(new GridLayout(2,2,5,5));
        inputpanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        inputpanel.add(new JLabel("User Name:"));
        usernameField = new JTextField();
        inputpanel.add(usernameField);

        inputpanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        inputpanel.add(passwordField);

        add(inputpanel,BorderLayout.CENTER);

        //  Login button
        JPanel buttonPanel = new JPanel();
        loginButton = new JButton("Login");
        buttonPanel.add(loginButton);
        add(buttonPanel,BorderLayout.SOUTH);

        // Add ActionListener  to login button
        loginButton.addActionListener(new ActionListener() {
            /**
             * This is the action that runs when the login button is click.
             * @param e the click event.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();// Call the login check method
            }
        });

        setVisible(true);
    }

    /**
     *  Method : performLogin
     *   Purpose: The method is to get the text from the input fields and check if
     *   the username and password are correct.If they are it asks for the database path, creates the DatabaseManager
     *   and launches the FlowerGUI.
     *
     */

    public void performLogin(){
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        // Checks for specific username and password
        if (username.equals("Manager278242")&& password.equals("Admin")){
        //  1. Ask for the database path
           String databasePath = JOptionPane.showInputDialog(
                   this,"Enter full file path to the inventory.db database:",
                   "Database Connection",JOptionPane.QUESTION_MESSAGE
           );

           // 2. Check if the user cancelled
           if (databasePath == null|| databasePath.trim().isEmpty()){
               JOptionPane.showMessageDialog(this,"No database path provided.","Error",JOptionPane.ERROR_MESSAGE);
               System.exit(0);
           }

           // 3. Create the DB URL and manager
           String databaseURL = "jdbc:sqlite:"+ databasePath;
           final  DatabaseManager dbManager = new DatabaseManager(databaseURL);

          // 4. close the window and open the main gui
            dispose();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new FlowerGUI(dbManager).setVisible(true);
                }
            });

        } else{
            // Login Failed
            JOptionPane.showMessageDialog(this,"Invalid username or password","Login Failed",JOptionPane.ERROR_MESSAGE);
        }
    }


}
