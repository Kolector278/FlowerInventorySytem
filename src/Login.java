import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    private JTextField usernameField;
    private  JPasswordField passwordField;
    private  JButton loginButton;
    private  JLabel logoLabel;

    public Login(){
        super("Ophelia Garden Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10,10));

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

        JPanel inputpanel = new JPanel(new GridLayout(2,2,5,5));
        inputpanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        inputpanel.add(new JLabel("User Name:"));
        usernameField = new JTextField();
        inputpanel.add(usernameField);

        inputpanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        inputpanel.add(passwordField);

        add(inputpanel,BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        loginButton = new JButton("Login");
        buttonPanel.add(loginButton);
        add(buttonPanel,BorderLayout.SOUTH);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });

        setVisible(true);
    }


    // Method : performLogin
    // Purpose: checks that username and password are correct.
    // Arguments: None
    //Return Value: void
    public void performLogin(){
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.equals("Manager278242")&& password.equals("Admin")){

           String databasePath = JOptionPane.showInputDialog(
                   this,"Enter full file path to the inventory.db database:",
                   "Database Connection",JOptionPane.QUESTION_MESSAGE
           );
           if (databasePath == null|| databasePath.trim().isEmpty()){
               JOptionPane.showMessageDialog(this,"No database path provided.","Error",JOptionPane.ERROR_MESSAGE);
               System.exit(0);
           }
           String databaseURL = "jdbc:sqlite:"+ databasePath;
           final  DatabaseManager dbManager = new DatabaseManager(databaseURL);
            dispose();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new FlowerGUI(dbManager).setVisible(true);
                }
            });
        } else{
            JOptionPane.showMessageDialog(this,"Invalid username or password","Login Failed",JOptionPane.ERROR_MESSAGE);
        }
    }


}
