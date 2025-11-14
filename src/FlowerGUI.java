import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.RowFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.regex.PatternSyntaxException;
import javax.swing.table.TableRowSorter;

/**
 * Name: Kimberly Colector
 * Course: Software development 1 CEN 3024C
 * Date: 10/27/2025
 * Class Name: FlowerGUI
 * Class Function: The class the mein window for the flower invcentory program.
 * Its the user interface,showing buttons, flower data,amd pop up boxes for input.
 * When the user clisck the button the class tells the DatabaseManager what action to perform
 * and then updates the screen to show the results.
 */

public class FlowerGUI extends JFrame{
    private JPanel buttonPanel;
    private JButton viewAllButton;
    private DatabaseManager dbManager;
    private JTable inventoryTable;
    private DefaultTableModel tableModel;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton totalValueButton;
    private JButton loadButton;
    private JCheckBox catSafeBox;
    private JTextField forSearch;
    private JButton searchButton;
    private TableRowSorter<DefaultTableModel> sorter;

    /**
     * Method Name: FlowerGUI
     * Purpose: Creates the main window it accepts the DatabaseManager
     * from the loginFrame and saves it then builds all the components such as button ,table and add all the ActionListeners.
     * @param manager manager the DatabaseManager created by Login window.
     */

    public FlowerGUI(DatabaseManager manager){
        super("Ophelia Garden Inventory");
               setSize(1000,500);
               setLayout(new BorderLayout());


            //Table
            String[] columnHeaders = {"ID","Name","Color","Qty","Price","In Season","Cat Safe"};
            tableModel = new DefaultTableModel(columnHeaders,0);
            inventoryTable = new JTable(tableModel);
            sorter = new TableRowSorter<>(tableModel);
            inventoryTable.setRowSorter(sorter);
            inventoryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            // Scroll thing
            JScrollPane scrollPane = new JScrollPane(inventoryTable);
            javax.swing.table.JTableHeader tableHeader = inventoryTable.getTableHeader();




                // Create buttons panel
                buttonPanel = new JPanel();
                buttonPanel.setBorder(new EmptyBorder(10,10,10,10));
                // button panel color
                buttonPanel.setBackground(new Color(0,100,0));
                // Create buttons
                viewAllButton = new JButton("View ALL");
                addButton = new JButton("Add");
                updateButton = new JButton("Update");
                deleteButton = new JButton("Delete");
                totalValueButton = new JButton("Calculate Total");
                loadButton = new JButton("Load Custom File");
                searchButton = new JButton("Search");
                // check box
                catSafeBox = new JCheckBox("Show only Cat Safe");
                // search box input
                forSearch = new JTextField(8);
        //Button Colors
        Color beige = new Color(245,245,220);
        Color lPink = new Color(255,182,193);
        Color lGreen = new Color(144,238,144);
        Color lDarkGreen = new Color(0,100,0);
        // setting button colors
        viewAllButton.setBackground(beige);
        viewAllButton.setForeground(lDarkGreen);
        viewAllButton.setOpaque(true);
        viewAllButton.setBorderPainted(false);

        addButton.setBackground(beige);
        addButton.setForeground(lDarkGreen);
        addButton.setOpaque(true);
        addButton.setBorderPainted(false);

        updateButton.setBackground(beige);
        updateButton.setForeground(lDarkGreen);
        updateButton.setOpaque(true);
        updateButton.setBorderPainted(false);

        deleteButton.setBackground(beige);
        deleteButton.setForeground(lDarkGreen);
        deleteButton.setOpaque(true);
        deleteButton.setBorderPainted(false);

        totalValueButton.setBackground(beige);
        totalValueButton.setForeground(lDarkGreen);
        totalValueButton.setOpaque(true);
        totalValueButton.setBorderPainted(false);

        loadButton.setBackground(beige);
        loadButton.setForeground(lDarkGreen);
        loadButton.setOpaque(true);
        loadButton.setBorderPainted(false);

        searchButton.setBackground(beige);
        searchButton.setForeground(lDarkGreen);
        searchButton.setOpaque(true);
        searchButton.setBorderPainted(false);

        catSafeBox.setBackground(beige);
        catSafeBox.setForeground(lDarkGreen);
        catSafeBox.setOpaque(true);
    //table color
        tableHeader.setBackground(lGreen);
        tableHeader.setForeground(lDarkGreen);
        inventoryTable.setBackground(beige);
        tableHeader.setFont(new Font("Tahoma",Font.BOLD, 13));
        // background color
        getContentPane().setBackground(new Color(245,245,220));
        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setForeground(lPink);
                // add buttons to gui to see
                 buttonPanel.add(catSafeBox);
                 buttonPanel.add(loadButton);
                buttonPanel.add(addButton);
                buttonPanel.add(viewAllButton);
                buttonPanel.add(updateButton);
                buttonPanel.add(deleteButton);
                buttonPanel.add(totalValueButton);
                buttonPanel.add(searchLabel);
                buttonPanel.add(searchButton);
                buttonPanel.add(forSearch);





                // add panel to frame
                add(buttonPanel,BorderLayout.NORTH);
                add(scrollPane,BorderLayout.CENTER);

                this.dbManager = manager;
                refreshTableData();
            try {
                ImageIcon logoIcon = new ImageIcon("flowershoplogoInventory.png");
                setIconImage(logoIcon.getImage());
            } catch (Exception e) {
                System.out.println("error loading image");
            }



        // making the button function
            viewAllButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                 System.out.println(" view all button was click");
                 refreshTableData();
                 applyFilter();
                }
            });

            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //gets name
                    String name = null;
                    boolean validName = false;
                    while(!validName){
                        name = JOptionPane.showInputDialog(FlowerGUI.this,"Enter flower name:");
                        if (name == null){
                            return;
                        }
                        name = name.trim();
                        if (name.isEmpty()){
                            JOptionPane.showMessageDialog(FlowerGUI.this,"Name cannot be empty","Invaild input",JOptionPane.ERROR_MESSAGE);
                            continue;
                        }
                        validName = true;
                        for (int i = 0; i<name.length();i++){
                            char currentChar = name.charAt(i);
                            if (!Character.isLetter(currentChar)&& !Character.isSpaceChar(currentChar) ){
                                validName = false;
                                JOptionPane.showMessageDialog(FlowerGUI.this,"Invalid Name. only letters allowed.","Invalid Input",JOptionPane.ERROR_MESSAGE);
                                break;
                            }

                        }
                    }

                // gets color
                    String color = null;
                    boolean validColor = false;
                    while(!validColor){
                    color = JOptionPane.showInputDialog(FlowerGUI.this,"Enter flower color:");
                    if (color == null){
                        return;
                    }
                    color = color.trim();
                    if (color.isEmpty()){
                        JOptionPane.showMessageDialog(FlowerGUI.this,"Color cannot be empty","Invalid Input",JOptionPane.ERROR_MESSAGE);
                    }
                    validColor = true;
                    for (int i =0; i <color.length();i++){
                        char currentChar = color.charAt(i);
                        if (!Character.isLetter(currentChar)&& !Character.isSpaceChar(currentChar)){
                            validColor = false;
                            JOptionPane.showMessageDialog(FlowerGUI.this,"Invalid color.only letters please","Invalid Input",JOptionPane.ERROR_MESSAGE);
                            break;

                        }
                    }
                }

                // gets quantity
                    int quantity = -1;
                while (quantity < 0){
                    String quantityInput = JOptionPane.showInputDialog(FlowerGUI.this,"Enter quantity: ");
                    if (quantityInput == null) {
                        return;
                    }
                    try {
                        quantity = Integer.parseInt(quantityInput.trim());
                        if (quantity < 0){
                            JOptionPane.showMessageDialog(FlowerGUI.this,"Cannot be a negative number. try again","Invaild Input",JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(FlowerGUI.this, "Invalid input.please type in whole numbers");

                    }
                }

                // get price
                double price = -1.0;
                while(price < 0.0){
                    String priceInput = JOptionPane.showInputDialog(FlowerGUI.this,"Enter price: ");
                    if (priceInput == null){
                        return;
                    }
                    try{
                        price = Double.parseDouble(priceInput);
                        if (price < 0.0){
                            JOptionPane.showMessageDialog(FlowerGUI.this," Price cannot be negative number try again.","Invalid Input",JOptionPane.ERROR_MESSAGE);
                        }
                    }catch(NumberFormatException ex){
                        JOptionPane.showMessageDialog(FlowerGUI.this, "Invalid input. Please enter a number for price(ex: 5.99)","Invalid Input",JOptionPane.ERROR_MESSAGE);

                    }
                }

                // for the In Season Section
                String isInSeason;
                while(true){
                    isInSeason = JOptionPane.showInputDialog(FlowerGUI.this,"Is it in season? (yes or no): ");
                    if (isInSeason == null){
                        return;
                    }
                    isInSeason = isInSeason.trim();
                    if (isInSeason.equalsIgnoreCase("yes")|| isInSeason.equalsIgnoreCase("no")){
                        break;
                    }else {
                        JOptionPane.showMessageDialog(FlowerGUI.this,"Invalid input.please type in yes or no");
                    }
                }
                // Flowers that are safe for cats
              String safe;
                 while (true){
                     safe = JOptionPane.showInputDialog(FlowerGUI.this,"Is its safe for cats?(yes/no): ");
                     if (safe == null){
                         return;
                     }
                     safe = safe.trim();
                     if (safe.equalsIgnoreCase("yes") || safe.equalsIgnoreCase("no")){
                         break;
                     }else{
                         JOptionPane.showMessageDialog(FlowerGUI.this,"Invalid input.Please type in yes or no","Invalid Input",JOptionPane.ERROR_MESSAGE);
                     }

                 }
                 Flower addedFlower = dbManager.addFlower(name,color,quantity,price,isInSeason,safe);

                 // messages
                    if(addedFlower != null ){
                        JOptionPane.showMessageDialog(FlowerGUI.this,
                                "Success!"+ addedFlower.getFlowerName() + "(ID:"+ addedFlower.getFlowerID()+") was added.", "Flower added",JOptionPane.INFORMATION_MESSAGE);
                        refreshTableData();
                        applyFilter();
                    }else {
                        JOptionPane.showMessageDialog(FlowerGUI.this,"Failed to add flower","Error",JOptionPane.ERROR_MESSAGE);

                    }

                }
            });// end of add button function

            updateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    int viewRow = inventoryTable.getSelectedRow();
                    if (viewRow == -1) {
                        JOptionPane.showMessageDialog(FlowerGUI.this, "Please click on a flower to update.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    int modelRow = inventoryTable.convertRowIndexToModel(viewRow);
                    String idToUpdate = (String) tableModel.getValueAt(modelRow, 0);
                    String flowerName = (String) tableModel.getValueAt(modelRow, 1);


                    String updateChoice = JOptionPane.showInputDialog(FlowerGUI.this, "Found: " + flowerName + " ID: " + idToUpdate +
                            "\n What do you want to update?\n1. Color\n2.Quantity\n3. Price");

                    // dealing with choice inputs
                    if (updateChoice == null) return;
                    updateChoice = updateChoice.trim();
                    boolean success = false;

                    if (updateChoice.equals("1")) {
                        while (true){
                            String newColor = JOptionPane.showInputDialog(FlowerGUI.this, "Enter new color:");
                            if (newColor == null){
                                success = false;
                                break;
                            }
                            newColor = newColor .trim();
                            if (newColor.isEmpty()){
                                JOptionPane.showMessageDialog(FlowerGUI.this,"Color can not be left empty.","Invalid Input",JOptionPane.ERROR_MESSAGE);
                                continue;
                            }
                            boolean isValid = true;
                            for (int i = 0; i<newColor.length(); i++){
                                char c = newColor.charAt(i);
                                if (!Character.isLetter(c) && !Character.isSpaceChar(c)){
                                    isValid = false;
                                    break;
                                }
                            }
                            if (isValid){
                                success = dbManager.updateFlowerColor(idToUpdate, newColor.trim());
                                break;
                            }else{
                                JOptionPane.showMessageDialog(FlowerGUI.this,"Invalid color.Only letters allowed try again.","Invalid Input",JOptionPane.ERROR_MESSAGE);

                            }


                        }
                        //End of choice 1

                    } else if (updateChoice.equals("2")) {
                        while (true) {
                            String newQtyString = JOptionPane.showInputDialog(FlowerGUI.this, "Enter new quantity.Whole number please: ");
                            if (newQtyString == null) {
                                success = false;
                                break;
                            }
                            try {
                                int newQuantity = Integer.parseInt(newQtyString.trim());
                                if (newQuantity < 0) {
                                    JOptionPane.showMessageDialog(FlowerGUI.this, "Quantity can not be negative.Try again", "Invalid input", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    success = dbManager.updateFlowerQuantity(idToUpdate, newQuantity);
                                    break;
                                }
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(FlowerGUI.this, "Invalid number for quantity.", "Input Error", JOptionPane.ERROR_MESSAGE);
                            }

                        }
                        // End of choice 2

                    } else if (updateChoice.equals("3")) {
                        while (true) {
                            String newPriceString = JOptionPane.showInputDialog(FlowerGUI.this, "Enter new price (Ex: 5.99):");
                            if (newPriceString == null) {
                                success = false;
                                break;
                            }
                            try {
                                double newPrice = Double.parseDouble(newPriceString.trim());

                                if (newPrice < 0.0) {
                                    JOptionPane.showMessageDialog(FlowerGUI.this, "Price cannot be negative please try again.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    success = dbManager.updateFlowerPrice(idToUpdate, newPrice);
                                    break;
                                }
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(FlowerGUI.this, "Invalid input.Please enter a number(ex: 5.99)", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                            }

                        }
                    }if (success){
                        JOptionPane.showMessageDialog(FlowerGUI.this,"Update is ssuccessful");
                        refreshTableData();
                        applyFilter();
                    }else {
                        if (updateChoice != null){
                            JOptionPane.showMessageDialog(FlowerGUI.this,"Update failed","Error",JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }

            }); // End of Update section

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int viewRow = inventoryTable.getSelectedRow();
                if (viewRow == -1){
                    JOptionPane.showMessageDialog(FlowerGUI.this,"Please click on a flower","Error",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int modelRow = inventoryTable.convertRowIndexToModel(viewRow);
                String idToDelete = (String) tableModel.getValueAt(modelRow,0);
                String flowerName = (String) tableModel.getValueAt(modelRow,1);

                int choice = JOptionPane.showConfirmDialog(FlowerGUI.this, "Confirming you want to delete "+flowerName+"'(ID:"+ idToDelete+")?","Confirm Deletion",JOptionPane.YES_NO_OPTION);
                if (choice != JOptionPane.YES_OPTION){
                    return;
                }
                dbManager.deleteFlower(idToDelete);
                JOptionPane.showMessageDialog(FlowerGUI.this,"Flower deleted successfully.","Success",JOptionPane.INFORMATION_MESSAGE);
                tableModel.removeRow(modelRow);
            }
        });//End of delete section

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchTerm = forSearch.getText().trim();
                if (searchTerm.isEmpty()){
                    JOptionPane.showMessageDialog(FlowerGUI.this,"please enter a name or color to search for.","Missing Input",JOptionPane.WARNING_MESSAGE);
                    return;
                }
                List<Flower> foundFlowers = dbManager.searchBYNameorColor(searchTerm);

                tableModel.setRowCount(0);
                if (!foundFlowers.isEmpty()){

                    for (Flower foundFlower : foundFlowers) {
                        Object[] rowData = {
                                foundFlower.getFlowerID(),
                                foundFlower.getFlowerName(),
                                foundFlower.getFlowerColor(),
                                foundFlower.getFlowerQuantity(),
                                foundFlower.getFlowerPrice(),
                                foundFlower.isInSeason(),
                                foundFlower.isSafeForCats()
                        };
                        tableModel.addRow(rowData);
                    }
                    applyFilter();
                }else {
                    JOptionPane.showMessageDialog(FlowerGUI.this,"No flower found matching:"+ searchTerm,"Search Failed",JOptionPane.INFORMATION_MESSAGE);

                }
            }
        });// End of Search Section

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
           JFileChooser fileChooser = new JFileChooser();
           fileChooser.setDialogTitle("Select New SQLite Database");
           fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("SQLite Database(*.db)","db"));
            int result = fileChooser.showOpenDialog(FlowerGUI.this);
            
            if (result == JFileChooser.APPROVE_OPTION){ 
                java.io.File selectedFile = fileChooser.getSelectedFile();
                String newPath = selectedFile.getAbsolutePath();
                try{
                    String newURL = "jdbc:sqlite:"+ newPath;
                    DatabaseManager newDbManager = new DatabaseManager(newURL);
                    FlowerGUI.this.dbManager = newDbManager;
                    refreshTableData();
                    JOptionPane.showMessageDialog(FlowerGUI.this,"Successfully loaded new database:\n"+ newPath,"Load Successful",JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(FlowerGUI.this,"Failed to load database:\n"+ newPath,"Load Failed",JOptionPane.ERROR_MESSAGE);
                }
            }
                //refreshTableData(true);
                // JOptionPane.showMessageDialog(FlowerGUI.this,"data reloaded from database.","Load successful",JOptionPane.INFORMATION_MESSAGE);


            }
        });// End of loadButton section
        totalValueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            double total = dbManager.calculateTotalValue();
            String formattedTotal = String.format("%.2f",total);
            JOptionPane.showMessageDialog(FlowerGUI.this," Total Inventory vale: $"+formattedTotal,"Inventory Value",JOptionPane.INFORMATION_MESSAGE);

            }
        });// end of total Value section

        catSafeBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyFilter();

            }
        });


                // Don't touch  closes and shows the gui
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setVisible(true);
    }

    /**
     * Method Name: refreshedTableDate
     * Purpose: It updates the JTable.It clears the table, calls the database Manager
     * to get fresh list of all flowers and then add them one by one to the table.
     * It converts the booleans into Strings so the filter can read them.At the end it calls the apply filter()
     * to make sure the view is correct.
     *  Arguments: none
     *  return value : void
     */



    private void refreshTableData(){
        tableModel.setRowCount(0);
        List<Flower> flowersToShow = dbManager.getAllFlowers();
        for (Flower flower : flowersToShow){
            String inSesonString = flower.isInSeason()? "true":"false";
            String catSafeString = flower.isSafeForCats()? "true":"false";
            Object[] rowData = {
                    flower.getFlowerID(),
                    flower.getFlowerName(),
                    flower.getFlowerColor(),
                    flower.getFlowerQuantity(),
                    flower.getFlowerPrice(),
                    inSesonString,
                    catSafeString
            };
            tableModel.addRow(rowData);
        }
        applyFilter();

    }


    /**
     *Method Name : applyFilter
     * Purpose: Is the cat safe filter. It checks  catSafeBox is ticked. then tells the sorter to only show flowers with true in
     * cat safe column. if it's not ticked  the sorter shows everything.
     */

    private  void applyFilter(){
        RowFilter<DefaultTableModel,Object> ct = null;
        if (catSafeBox.isSelected()){
            try{
                ct = RowFilter.regexFilter("(?i)true",6);
            } catch (PatternSyntaxException e) {
                System.err.println("Bad"+ e.getMessage());
            }
        }
        sorter.setRowFilter(ct);
    }

   //private  String forValidating()




}
