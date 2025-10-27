import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.plaf.FontUIResource;


public class FlowerGUI extends JFrame{
    private JPanel buttonPanel;
    private JButton viewAllButton;
    private InventoryManager manager;
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
    //Name: Kimberly Colector
//Course: Software development 1 CEN 3024C
//Date: 10/27/2025
//Class Name: FlowerGUI
// Class Function : This class creates the interface/Window for the flower Inventory.
//Showing the buttons,table with the flower data and getting input from the user.
// it is able to get the action of the user and pass it along to the Inventory manager to handle it what its asking for.
//Then updates the screen to show the results.
    public FlowerGUI(){
        super("Ophelia Garden Inventory");
               setSize(1000,500);
               setLayout(new BorderLayout());


            //Table
            String[] columnHeaders = {"ID","Name","Color","Qty","Price","In Season","Cat Safe"};
            tableModel = new DefaultTableModel(columnHeaders,0);
            inventoryTable = new JTable(tableModel);
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
        JLabel searchLabel = new JLabel("Search ID:");
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

                manager = new InventoryManager();
                refreshTableData(false);
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
                 refreshTableData(false);
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
                 Flower addedFlower = manager.addFlower(name,color,quantity,price,isInSeason,safe);

                 // messages
                    if(addedFlower != null ){
                        JOptionPane.showMessageDialog(FlowerGUI.this,
                                "Success!"+ addedFlower.getFlowerName() + "(ID:"+ addedFlower.getFlowerID()+") was added.", "Flower added",JOptionPane.INFORMATION_MESSAGE);
                        refreshTableData(false);
                    }else {
                        JOptionPane.showMessageDialog(FlowerGUI.this,"Failed to add flower","Error",JOptionPane.ERROR_MESSAGE);

                    }

                }
            });// end of add button function

            updateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                   String idToUpdate = JOptionPane.showInputDialog(FlowerGUI.this,"Enter ID of flower: ");
                   if (idToUpdate == null|| idToUpdate.trim().isEmpty()){
                       return;
                   }
                   Flower flowerToUpdate = manager.searchByID(idToUpdate.trim());
                   if (flowerToUpdate != null){
                       String updateChoice = JOptionPane.showInputDialog(FlowerGUI.this,
                               "Found:"+ flowerToUpdate.getFlowerName()+
                               "\n What do you want to update?\n1. Color\n2.Quantity\n3. Price");

                      // dealing with choice inputs
                       if (updateChoice == null) return;
                       updateChoice = updateChoice.trim();



                       if (updateChoice.equals("1")){
                           boolean updateSuccessful = false;
                           while (!updateSuccessful){
                           String newColor =JOptionPane.showInputDialog(FlowerGUI.this,"Enter new color:");
                           if (newColor == null){
                               JOptionPane.showMessageDialog(FlowerGUI.this,"Update canceled");
                               return;
                           }
                           updateSuccessful = flowerToUpdate.setFlowerColor(newColor);

                           if (updateSuccessful){
                               JOptionPane.showMessageDialog(FlowerGUI.this,"Color Update complete");
                           }else {
                               JOptionPane.showMessageDialog(FlowerGUI.this,"Invalid color: only enter characters","Invalid Input",JOptionPane.ERROR_MESSAGE);
                           }
                           }
                           //End of choice 1



                       } else if (updateChoice.equals("2")) {
                          boolean updateTotest = false;
                          while (!updateTotest) {
                              String quantityInput = JOptionPane.showInputDialog(FlowerGUI.this, "Enter new quantity.Whole number please: ");
                              if (quantityInput == null) {
                                  JOptionPane.showMessageDialog(FlowerGUI.this, "Update cancelled");
                                  return;
                              }
                              try {
                                  int newQuantitiy = Integer.parseInt(quantityInput.trim());
                                  boolean success = flowerToUpdate.setFlowerQuantity(newQuantitiy);
                                  if (success){
                                      JOptionPane.showMessageDialog(FlowerGUI.this,"Quantity updated successfully.");
                                      updateTotest = true;
                                  }else {
                                      JOptionPane.showMessageDialog(FlowerGUI.this,"Invalid quantity. Quantity cannot be negative please enter correct value","Invalid Input",JOptionPane.ERROR_MESSAGE);
                                  }
                              } catch (Exception ex) {
                                 JOptionPane.showMessageDialog(FlowerGUI.this,"Invaild input.Please enter a whole number for quantity.","Invalid input",JOptionPane.ERROR_MESSAGE);
                              }

                          }// End of choice 2

                       } else if (updateChoice.equals("3")) {
                           double newPrice = -1.0;
                           while(newPrice < 0.0){
                               String priceInput = JOptionPane.showInputDialog(FlowerGUI.this,"Enter new price (Ex: 5.99):");
                               if (priceInput == null)return;
                               try{
                                    newPrice = Double.parseDouble(priceInput);
                                    if (newPrice < 0.0){
                                        JOptionPane.showMessageDialog(FlowerGUI.this,"Price cannot be negative try again.(Ex:5.99)","Invalid input",JOptionPane.ERROR_MESSAGE);
                                    }
                                }catch (NumberFormatException ex){
                                    JOptionPane.showMessageDialog(FlowerGUI.this,"Invalid Input try again (Ex: 5.99)","Invalid input",JOptionPane.ERROR_MESSAGE);
                                    newPrice = -1.0;
                                }
                           }
                           flowerToUpdate.setFlowerPrice(newPrice);
                           JOptionPane.showMessageDialog(FlowerGUI.this,"Price has been Updated!");
                       }else {
                           JOptionPane.showMessageDialog(FlowerGUI.this,"Invalid update choice");
                       }
                       refreshTableData(false);
                   }else {
                       JOptionPane.showMessageDialog(FlowerGUI.this,"Flower with ID "+ idToUpdate +" was not found.","search failed",JOptionPane.ERROR_MESSAGE);
                   }
                }
            });// End of Update section

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idToDelete = JOptionPane.showInputDialog(FlowerGUI.this,"Enter the ID of the flower to delete:");
                if (idToDelete == null || idToDelete.isEmpty()){
                    return;
                }
                boolean wasDeleted = manager.deleteFlower(idToDelete);
                // Flower flowerToDelete = manager.searchByID(idToDelete.trim());
                if (wasDeleted){
                    JOptionPane.showMessageDialog(FlowerGUI.this,"Flower was deleted successfully.","Success",JOptionPane.INFORMATION_MESSAGE);
                    refreshTableData( false);

                }else {
                    JOptionPane.showMessageDialog(FlowerGUI.this,"Flower wit ID:" + idToDelete+" was not found.","Delete Failed",JOptionPane.ERROR_MESSAGE);
                }
            }
        });//End of delete section

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idToSearch = forSearch.getText().trim();
                Flower foundFlower = manager.searchByID(idToSearch);
                if (idToSearch.isEmpty()){
                    JOptionPane.showMessageDialog(FlowerGUI.this,"please enter an ID to search for.","Missing Input",JOptionPane.WARNING_MESSAGE);
                    return;
                }


                tableModel.setRowCount(0);
                if (foundFlower != null){
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
                }else {
                    JOptionPane.showMessageDialog(FlowerGUI.this,"No flower found with ID:"+ idToSearch,"Search Failed",JOptionPane.INFORMATION_MESSAGE);

                }
            }
        });// End of Search Section

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new java.io.File("."));
            fileChooser.setDialogTitle("Select Inventory File");
            int result = fileChooser.showOpenDialog(FlowerGUI.this);

            if (result == JFileChooser.APPROVE_OPTION){
                java.io.File selectedFile = fileChooser.getSelectedFile();
                String filePath = selectedFile.getAbsolutePath();

                boolean didLoad = manager.loadFromFile(filePath);

                if (didLoad){
                    manager.resetIDCounter();
                    refreshTableData(false);
                    JOptionPane.showMessageDialog(FlowerGUI.this,"Successfully loaded new from:\n"+filePath,"Load Successful", JOptionPane.INFORMATION_MESSAGE);

                }else {
                    JOptionPane.showMessageDialog(FlowerGUI.this,"Failed to load inventory from:\n"+filePath+"\nCheck console for details.",
                            "Load Failed",JOptionPane.ERROR_MESSAGE);
                }
            }else{
                System.out.println("File selection cancelled.");
            }

            }
        });// End of loadButton section
        totalValueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            double total = manager.calculateTotalValue();
            String formattedTotal = String.format("%.2f",total);
            JOptionPane.showMessageDialog(FlowerGUI.this," Total Inventory vale: $"+formattedTotal,"Inventory Value",JOptionPane.INFORMATION_MESSAGE);

            }
        });// end of total Value section

        catSafeBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isChecked = catSafeBox.isSelected();
                refreshTableData(isChecked);

            }
        });


                // Don't touch  closes and shows the gui
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setVisible(true);
    }
    // Method Name: refreshTableData
    // Purpose: the method updates what shown in the table .it clears out any old rows.
    // Then gets the updated list of flowers from the InventoryManager either all or just cats safe.
    // Arguments: onlyCatSafe is a boolean value. if true it only shows cat safe flowers.
    // when false it shows all the flowers in the Inventory.
    //Return Value:void
    private void refreshTableData(boolean onlyCatSafe){
        tableModel.setRowCount(0);
        List<Flower> flowersToShow;
        if (onlyCatSafe){
             flowersToShow = manager.getCatSafeFlower();
        }else {
            flowersToShow = manager.getInventory();
        }
        System.out.println("refresh table data found "+ flowersToShow.size()+" flower to show");

        for (Flower flower : flowersToShow){
            Object[] rowData = {
                    flower.getFlowerID(),
                    flower.getFlowerName(),
                    flower.getFlowerColor(),
                    flower.getFlowerQuantity(),
                    flower.getFlowerPrice(),
                    flower.isInSeason(),
                    flower.isSafeForCats()
            };
            tableModel.addRow(rowData);
        }

    }




}
