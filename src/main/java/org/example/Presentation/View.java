package org.example.Presentation;


import org.example.BusinessLogic.ClientBLL;
import org.example.BusinessLogic.OrdersBLL;
import org.example.BusinessLogic.ProductBLL;
import org.example.Model.Bill;
import org.example.Model.Client;
import org.example.Model.Orders;
import org.example.Model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.util.List;

/**
 * View class that creates the GUI for the application
 */

public class View extends JFrame {
    public View() throws IllegalAccessException {

        setTitle(" Management");
        setSize(800, 800);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();
        /**
         * Creates the first tab for the client data
         * Creates the table for the client data
         * Creates the labels and text fields for the client data
         * Creates the buttons for the client data
         * Adds action listeners for the buttons
         */

        ///////////////////////////////////////////////////////////////// First tab - Client Data
        // Client Data Tab
        JPanel clientPanel = new JPanel();
        tabbedPane.addTab("Client Data", clientPanel);


        clientPanel.setLayout(null);
        DefaultTableModel model = new DefaultTableModel(){
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };

        loadColumnNames(model, new Client());

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(300, 50, 400, 500);
        clientPanel.add(scrollPane);
        ClientBLL clientDataService = new ClientBLL();
        List<Client> clients = clientDataService.findAllClients();

        for (Client client : clients) {
            model.addRow(new Object[]{client.getId(), client.getName(), client.getAddress(), client.getEmail()});
        }

        JLabel idLabel = new JLabel("Id");
        idLabel.setBounds(50, 50, 100, 30);
        clientPanel.add(idLabel);
        JTextField idField = new JTextField();
        idField.setBounds(100, 50, 100, 30);
        clientPanel.add(idField);

        JLabel nameLabel = new JLabel("Name");
        nameLabel.setBounds(50, 75, 100, 30);
        clientPanel.add(nameLabel);
        JTextField nameField = new JTextField();
        nameField.setBounds(100, 75, 100, 30);
        clientPanel.add(nameField);

        JLabel addressLabel = new JLabel("Address");
        addressLabel.setBounds(50, 100, 100, 30);
        clientPanel.add(addressLabel);
        JTextField addressField = new JTextField();
        addressField.setBounds(100, 100, 100, 30);
        clientPanel.add(addressField);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(50, 125, 100, 30);
        clientPanel.add(emailLabel);
        JTextField emailField = new JTextField();
        emailField.setBounds(100, 125, 100, 30);
        clientPanel.add(emailField);

        JButton insertButton = new JButton("Insert");
        insertButton.setBounds(100, 150, 100, 30);
        //add action listener
        insertButton.addActionListener(e -> {
            ClientBLL clientBL = new ClientBLL();
            try {
                clientBL.insertClient(new Client(Integer.parseInt(idField.getText()), nameField.getText(), addressField.getText(), emailField.getText()));
                model.addRow(new Object[]{Integer.parseInt(idField.getText()), nameField.getText(), addressField.getText(), emailField.getText()});
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "The client already exists!");

            }
        });
        clientPanel.add(insertButton);

        JLabel idUpdateLabel = new JLabel("Id");
        idUpdateLabel.setBounds(50, 200, 100, 30);
        clientPanel.add(idUpdateLabel);
        JTextField idUpdateField = new JTextField();
        idUpdateField.setBounds(100, 200, 100, 30);
        clientPanel.add(idUpdateField);

        JLabel nameLabelUpdate = new JLabel("Name");
        nameLabelUpdate.setBounds(50, 225, 100, 30);
        clientPanel.add(nameLabelUpdate);
        JTextField nameUpdateField = new JTextField();
        nameUpdateField.setBounds(100, 225, 100, 30);
        clientPanel.add(nameUpdateField);

        JLabel addressLabelUpdate = new JLabel("Address");
        addressLabelUpdate.setBounds(50, 250, 100, 30);
        clientPanel.add(addressLabelUpdate);
        JTextField addressUpdateField = new JTextField();
        addressUpdateField.setBounds(100, 250, 100, 30);
        clientPanel.add(addressUpdateField);

        JLabel emailLabelUpdate = new JLabel("Email");
        emailLabelUpdate.setBounds(50, 275, 100, 30);
        clientPanel.add(emailLabelUpdate);
        JTextField emailUpdateField = new JTextField();
        emailUpdateField.setBounds(100, 275, 100, 30);
        clientPanel.add(emailUpdateField);

        JButton updateButton = new JButton("Update");
        updateButton.setBounds(100, 300, 100, 30);

        updateButton.addActionListener(e -> {
            ClientBLL clientBLL = new ClientBLL();
            try {
                if (!nameUpdateField.getText().equals("")) {
                    clientBLL.update(Integer.parseInt(idUpdateField.getText()), "name", nameUpdateField.getText());
                }
                if (!addressUpdateField.getText().equals("")) {
                    clientBLL.update(Integer.parseInt(idUpdateField.getText()), "address", addressUpdateField.getText());
                }
                if (!emailUpdateField.getText().equals("")) {
                    clientBLL.update(Integer.parseInt(idUpdateField.getText()), "email", emailUpdateField.getText());
                }
                //refreshes table
                model.setRowCount(0);
                List<Client> newClients = clientDataService.findAllClients();
                //adds new data to the table
                for (Client client : newClients) {
                    model.addRow(new Object[]{client.getId(), client.getName(), client.getAddress(), client.getEmail()});
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        clientPanel.add(updateButton);

        JLabel idFindLabel = new JLabel("Find Id");
        idFindLabel.setBounds(50, 350, 100, 30);
        clientPanel.add(idFindLabel);
        JTextField idFindField = new JTextField();
        idFindField.setBounds(100, 350, 100, 30);
        clientPanel.add(idFindField);

        JButton findButton = new JButton("Find");
        findButton.setBounds(100, 370, 100, 30);

        JLabel resultLabel = new JLabel("Result");
        JTextPane resultField = new JTextPane();
        resultField.setBounds(100, 400, 200, 50);
        clientPanel.add(resultField);

        findButton.addActionListener(e -> {
            ClientBLL clientBLL = new ClientBLL();
            try {
                Client client = clientBLL.findClientById(Integer.parseInt(idFindField.getText()));
                resultField.setText(" Name: " + client.getName() + " Address: " + client.getAddress() + "  Email: " + client.getEmail());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        clientPanel.add(findButton);


        JLabel idDeleteLabel = new JLabel("Delte Id");
        idDeleteLabel.setBounds(50, 450, 100, 30);
        clientPanel.add(idDeleteLabel);

        JTextField idDeleteField = new JTextField();
        idDeleteField.setBounds(100, 450, 100, 30);
        clientPanel.add(idDeleteField);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(100, 475, 100, 30);
        deleteButton.addActionListener(e -> {
            ClientBLL clientBLL = new ClientBLL();
            try {

                int id=Integer.parseInt(idDeleteField.getText());
                OrdersBLL ordersBLL = new OrdersBLL();
                //find the orders that have the client id and delete them
                List<Orders> orders = ordersBLL.findAllOrders();
                for(int i=0; i<orders.size();i++){
                    if(orders.get(i).getClient()==id){
                        ordersBLL.deleteOrders(orders.get(i).getId());
                    }
                }
                clientBLL.deleteClient(id);
                model.setRowCount(0);
                List<Client> newClients = clientDataService.findAllClients();
                for (Client client : newClients) {
                    model.addRow(new Object[]{client.getId(), client.getName(), client.getAddress(), client.getEmail()});
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        clientPanel.add(deleteButton);

        /**
         * Creates the second tab for the product data
         * Creates the table for the product data
         * Creates the labels and text fields for the product data
         * Creates the buttons for the product data
         * Adds action listeners for the buttons
         */
        ///////////////////////////////////////////////////////////////// Second tab - Product Data
        JPanel productsPanel = new JPanel();
        productsPanel.setLayout(null);
        productsPanel.add(new JLabel("Date Produse"));
        tabbedPane.addTab("Date Produse", productsPanel);

        DefaultTableModel prodModel = new DefaultTableModel();
        loadColumnNames(prodModel, new Product());
        JTable productTable = new JTable(prodModel);
        JScrollPane productScrollPane = new JScrollPane(productTable);
        productScrollPane.setBounds(300, 50, 400, 500);
        productsPanel.add(productScrollPane);

        ProductBLL productDataService = new ProductBLL();
        List<Product> products = productDataService.findAllProducts();


        for (int i=0; i< products.size(); i++) {
            prodModel.addRow(new Object[]{products.get(i).getId(), products.get(i).getName(), products.get(i).getQuantityPro(), products.get(i).getPrice()});
        }

        JLabel PidLabel = new JLabel("Id");
        PidLabel.setBounds(50, 50, 100, 30);
        productsPanel.add(PidLabel);
        JTextField PidField = new JTextField();
        PidField.setBounds(100, 50, 100, 30);
        productsPanel.add(PidField);

        JLabel PnameLabel = new JLabel("Name");
        PnameLabel.setBounds(50, 75, 100, 30);
        productsPanel.add(PnameLabel);
        JTextField PnameField = new JTextField();
        PnameField.setBounds(100, 75, 100, 30);
        productsPanel.add(PnameField);

        JLabel PquantityLabel = new JLabel("Quantity");
        PquantityLabel.setBounds(50, 100, 100, 30);
        productsPanel.add(PquantityLabel);
        JTextField PquantityField = new JTextField();
        PquantityField.setBounds(100, 100, 100, 30);
        productsPanel.add(PquantityField);

        JLabel PpriceLabel = new JLabel("Price");
        PpriceLabel.setBounds(50, 125, 100, 30);
        productsPanel.add(PpriceLabel);
        JTextField PpriceField = new JTextField();
        PpriceField.setBounds(100, 125, 100, 30);
        productsPanel.add(PpriceField);

        JButton PinsertButton = new JButton("Insert");
        PinsertButton.setBounds(100, 150, 100, 30);
        PinsertButton.addActionListener(e -> {
            ProductBLL productBLL = new ProductBLL();
            try {
                productBLL.insertProduct(new Product(Integer.parseInt(PidField.getText()), PnameField.getText(), Integer.parseInt(PquantityField.getText()), Integer.parseInt(PpriceField.getText())));
                prodModel.addRow(new Object[]{Integer.parseInt(PidField.getText()), PnameField.getText(), Integer.parseInt(PquantityField.getText()), Integer.parseInt(PpriceField.getText())});
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "The product already exists!");
            }
        });
        productsPanel.add(PinsertButton);

        JLabel PidUpdateLabel = new JLabel("Id");
        PidUpdateLabel.setBounds(50, 200, 100, 30);
        productsPanel.add(PidUpdateLabel);
        JTextField PidUpdateField = new JTextField();
        PidUpdateField.setBounds(100, 200, 100, 30);
        productsPanel.add(PidUpdateField);

        JLabel PnameUpdateLabel = new JLabel("Name");
        PnameUpdateLabel.setBounds(50, 225, 100, 30);
        productsPanel.add(PnameUpdateLabel);
        JTextField PnameUpdateField = new JTextField();
        PnameUpdateField.setBounds(100, 225, 100, 30);
        productsPanel.add(PnameUpdateField);

        JLabel PquantityUpdateLabel = new JLabel("Quantity");
        PquantityUpdateLabel.setBounds(50, 250, 100, 30);
        productsPanel.add(PquantityUpdateLabel);
        JTextField PquantityUpdateField = new JTextField();
        PquantityUpdateField.setBounds(100, 250, 100, 30);
        productsPanel.add(PquantityUpdateField);

        JLabel PpriceUpdateLabel = new JLabel("Price");
        PpriceUpdateLabel.setBounds(50, 275, 100, 30);
        productsPanel.add(PpriceUpdateLabel);
        JTextField PpriceUpdateField = new JTextField();
        PpriceUpdateField.setBounds(100, 275, 100, 30);
        productsPanel.add(PpriceUpdateField);

        JButton PupdateButton = new JButton("Update");
        PupdateButton.setBounds(100, 300, 100, 30);
        PupdateButton.addActionListener(e -> {
            ProductBLL productBLL = new ProductBLL();
            try {
                if (!PnameUpdateField.getText().equals("")) {
                    productBLL.update(Integer.parseInt(PidUpdateField.getText()), "name", PnameUpdateField.getText());
                }
                if (!PquantityUpdateField.getText().equals("")) {
                    productBLL.update(Integer.parseInt(PidUpdateField.getText()), "quantityPro", PquantityUpdateField.getText());
                }
                if (!PpriceUpdateField.getText().equals("")) {
                    productBLL.update(Integer.parseInt(PidUpdateField.getText()), "price", PpriceUpdateField.getText());
                }
                prodModel.setRowCount(0);
                List<Product> newProducts = productDataService.findAllProducts();
                for (Product product : newProducts) {
                    prodModel.addRow(new Object[]{product.getId(), product.getName(), product.getQuantityPro(), product.getPrice()});
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        productsPanel.add(PupdateButton);

        JLabel PidFindLabel = new JLabel("Find Id");
        PidFindLabel.setBounds(50, 350, 100, 30);
        productsPanel.add(PidFindLabel);
        JTextField PidFindField = new JTextField();
        PidFindField.setBounds(100, 350, 100, 30);
        productsPanel.add(PidFindField);

        JLabel PresultLabel = new JLabel("Result");
        JTextPane PresultField = new JTextPane();
        PresultField.setBounds(100, 400, 200, 50);
        productsPanel.add(PresultField);

        JButton PfindButton = new JButton("Find");
        PfindButton.setBounds(100, 375, 100, 30);
        PfindButton.addActionListener(e -> {
            ProductBLL productBLL = new ProductBLL();
            try {
                Product product = productBLL.findProductById(Integer.parseInt(PidFindField.getText()));
                PresultField.setText("Name: " + product.getName() + " Quantity: " + product.getQuantityPro() + " Price: " + product.getPrice());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        productsPanel.add(PfindButton);


        JLabel PidDeleteLabel = new JLabel("Delte Id");
        PidDeleteLabel.setBounds(50, 450, 100, 30);
        productsPanel.add(PidDeleteLabel);
        JTextField PidDeleteField = new JTextField();
        PidDeleteField.setBounds(100, 450, 100, 30);
        productsPanel.add(PidDeleteField);

        JButton PdeleteButton = new JButton("Delete");
        PdeleteButton.setBounds(100, 475, 100, 30);
        PdeleteButton.addActionListener(e -> {
            ProductBLL productBLL = new ProductBLL();
            try {
                productBLL.deleteProduct(Integer.parseInt(PidDeleteField.getText()));
                prodModel.setRowCount(0);
                List<Product> newProducts = productDataService.findAllProducts();
                for (Product product : newProducts) {
                    prodModel.addRow(new Object[]{product.getId(), product.getName(), product.getQuantityPro(), product.getPrice()});
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        productsPanel.add(PdeleteButton);
        ////////////////////////////////////////////////////////////////////////- THIRD Tab- Bill Data
        /**
         * Creates the third tab for the bill data
         * Creates the table for the bill data
         * Adds the data from the database to the table
         * Adds the table to the tab
         */
        JPanel billPanel = new JPanel();
        billPanel.setLayout(null);
        billPanel.add(new JLabel("Bill"));
        tabbedPane.addTab("Bill", billPanel);

        DefaultTableModel billModel = new DefaultTableModel(){
            public boolean isCellEditable(int row, int column)
            {
                return false;//This causes all cells to not be editable
            }
        };

        JTable billTable = new JTable(billModel);
        JScrollPane billScrollPane = new JScrollPane(billTable);
        loadColumnNames(billModel,new Bill(0,0,0));
        billScrollPane.setBounds(50, 50, 700, 700);
        billPanel.add(billScrollPane);
        billModel.setRowCount(0);
        Bill billDataService = new Bill(0,0,0);
        List<Bill> bills = billDataService.findAll();
        for (Bill bill : bills) {
            billModel.addRow(new Object[]{bill.id(), bill.idOrder(), bill.total()});
        }
        add(tabbedPane);

        ///////////////////////////////////////////////////////////////// FOURTH tab - Orders Data
        /**
         * Creates the fourth tab for the orders data
         * Creates the table for the orders data
         * Creates the labels and text fields for the orders data
         * Creates the buttons for the orders data
         */
        JPanel ordersPanel = new JPanel();
        ordersPanel.setLayout(null);
        ordersPanel.add(new JLabel("Orders"));
        tabbedPane.addTab("Orders", ordersPanel);

        DefaultTableModel orderModel = new DefaultTableModel(){
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
        loadColumnNames(orderModel,new Orders());

        JTable orderTable = new JTable(orderModel);
        JScrollPane orderScrollPane = new JScrollPane(orderTable);
        orderScrollPane.setBounds(300, 50, 400, 500);
        ordersPanel.add(orderScrollPane);
        OrdersBLL ordersDataService = new OrdersBLL();
        List<Orders> orders = ordersDataService.findAllOrders();
        for (Orders order : orders) {
            orderModel.addRow(new Object[]{order.getId(), order.getClient(), order.getProduct(), order.getQuantityOrd()});
        }

        JLabel OidLabel = new JLabel("Id");
        OidLabel.setBounds(50, 50, 100, 30);
        ordersPanel.add(OidLabel);
        JTextField OidField = new JTextField();
        OidField.setBounds(100, 50, 100, 30);
        ordersPanel.add(OidField);

        JLabel OclientLabel = new JLabel("Cl ID");
        OclientLabel.setBounds(50, 75, 100, 30);
        ordersPanel.add(OclientLabel);

        JComboBox<String> OclientField = new JComboBox<>();
        OclientField.setBounds(100, 75, 100, 30);
        ordersPanel.add(OclientField);

        ClientBLL OclientDataService = new ClientBLL();
        List<Integer> Oclients = OclientDataService.getClientsId();
        for (Integer client : Oclients) {
            OclientField.addItem(client.toString());
        }

        JLabel OproductLabel = new JLabel("Prod ID");
        OproductLabel.setBounds(50, 100, 100, 30);
        ordersPanel.add(OproductLabel);

        JComboBox<String> OproductField = new JComboBox<>();
        OproductField.setBounds(100, 100, 100, 30);
        ordersPanel.add(OproductField);

        ProductBLL OproductDataService = new ProductBLL();
        List<Integer> Oproducts = OproductDataService.getProductsId();
        for (Integer product : Oproducts) {
            OproductField.addItem(product.toString());
        }

        JLabel OquantityLabel = new JLabel("Quantity");
        OquantityLabel.setBounds(50, 125, 100, 30);
        ordersPanel.add(OquantityLabel);
        JTextField OquantityField = new JTextField();
        OquantityField.setBounds(100, 125, 100, 30);
        ordersPanel.add(OquantityField);

        JButton OinsertButton = new JButton("Insert");
        OinsertButton.setBounds(100, 150, 100, 30);
        OinsertButton.addActionListener(e -> {
            OrdersBLL ordersBLL = new OrdersBLL();
            ProductBLL prod=new ProductBLL();
            try {
                //checking if the quantity is enough for the product to order
                if(Integer.parseInt(OquantityField.getText())>prod.findProductById(Integer.parseInt(OproductField.getSelectedItem().toString())).getQuantityPro()){
                    JOptionPane.showMessageDialog(null, "The quantity of the product is not enough!");
                }
                ordersBLL.insertOrders(new Orders(Integer.parseInt(OidField.getText()), Integer.parseInt(OclientField.getSelectedItem().toString()), Integer.parseInt(OproductField.getSelectedItem().toString()), Integer.parseInt(OquantityField.getText())));
                orderModel.addRow(new Object[]{Integer.parseInt(OidField.getText()), Integer.parseInt(OclientField.getSelectedItem().toString()), Integer.parseInt(OproductField.getSelectedItem().toString()), Integer.parseInt(OquantityField.getText())});
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "The order already exists!");
            }
            prodModel.setRowCount(0);
            List<Product> newProducts = productDataService.findAllProducts();
            for (Product product : newProducts) {
                prodModel.addRow(new Object[]{product.getId(), product.getName(), product.getQuantityPro(), product.getPrice()});
            }
            billModel.setRowCount(0);
            Bill ObillDataService = new Bill(0,0,0);
            List<Bill> Obills = billDataService.findAll();
            for (Bill bill : Obills) {
                billModel.addRow(new Object[]{bill.id(), bill.idOrder(), bill.total()});
            }
        });
        ordersPanel.add(OinsertButton);

        JLabel OidUpdateLabel = new JLabel("Id");
        OidUpdateLabel.setBounds(50, 200, 100, 30);
        ordersPanel.add(OidUpdateLabel);
        JTextField OidUpdateField = new JTextField();
        OidUpdateField.setBounds(100, 200, 100, 30);
        ordersPanel.add(OidUpdateField);

        JLabel OclientUpdateLabel = new JLabel("Cl ID");
        OclientUpdateLabel.setBounds(50, 225, 100, 30);
        ordersPanel.add(OclientUpdateLabel);

        JComboBox<String> OclientUpdateField = new JComboBox<>();
        OclientUpdateField.setBounds(100, 225, 100, 30);
        ordersPanel.add(OclientUpdateField);

        for (Integer client : Oclients) {
            OclientUpdateField.addItem(client.toString());
        }

        JLabel OproductUpdateLabel = new JLabel("Prod ID");
        OproductUpdateLabel.setBounds(50, 250, 100, 30);
        ordersPanel.add(OproductUpdateLabel);

        JComboBox<String> OproductUpdateField = new JComboBox<>();
        OproductUpdateField.setBounds(100, 250, 100, 30);
        ordersPanel.add(OproductUpdateField);

        for (Integer product : Oproducts) {
            OproductUpdateField.addItem(product.toString());
        }

        JLabel OquantityUpdateLabel = new JLabel("Quantity");
        OquantityUpdateLabel.setBounds(50, 275, 100, 30);
        ordersPanel.add(OquantityUpdateLabel);
        JTextField OquantityUpdateField = new JTextField();
        OquantityUpdateField.setBounds(100, 275, 100, 30);
        ordersPanel.add(OquantityUpdateField);

        JButton OupdateButton = new JButton("Update");
        OupdateButton.setBounds(100, 300, 100, 30);
        OupdateButton.addActionListener(e -> {
            OrdersBLL ordersBLL = new OrdersBLL();
            try {
                int ok=1;
                if (!OquantityUpdateField.getText().equals("")) {
                    ordersBLL.update(Integer.parseInt(OidUpdateField.getText()), "quantityOrd", OquantityUpdateField.getText());
                    ProductBLL productBL = new ProductBLL();
                    int id=Integer.parseInt(OidUpdateField.getText());
                    Orders o=ordersBLL.findOrdersById(id);
                    //update the quantity of the product by adding the quantity ordered before the update to the quantity
                    if(Integer.parseInt(OquantityUpdateField.getText())>OproductDataService.findProductById(Integer.parseInt(OproductField.getSelectedItem().toString())).getQuantityPro()){
                        ok=0;
                        JOptionPane.showMessageDialog(null, "The quantity of the product is not enough!");
                    }else{
                        productBL.update(o.getProduct(), "quantityPro", productBL.findProductById(o.getProduct()).getQuantityPro() - Integer.parseInt(OquantityUpdateField.getText()));
                    }
                }
                if (!OclientUpdateField.getSelectedItem().toString().equals("") && ok==1) {
                    ordersBLL.update(Integer.parseInt(OidUpdateField.getText()), "client", OclientUpdateField.getSelectedItem().toString());
                }

                if (!OproductUpdateField.getSelectedItem().toString().equals("") && ok==1) {
                    ordersBLL.update(Integer.parseInt(OidUpdateField.getText()), "product", OproductUpdateField.getSelectedItem().toString());
                }
                //checking if the quantity is enough for the product to order


                orderModel.setRowCount(0);
                List<Orders> newOrders = ordersDataService.findAllOrders();
                for (Orders order : newOrders) {
                    orderModel.addRow(new Object[]{order.getId(), order.getClient(), order.getProduct(), order.getQuantityOrd()});
                }

                billModel.setRowCount(0);
                Bill ObillDataService = new Bill(0,0,0);
                List<Bill> Obills = billDataService.findAll();
                for (Bill bill : Obills) {
                    billModel.addRow(new Object[]{bill.id(), bill.idOrder(), bill.total()});
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        ordersPanel.add(OupdateButton);

        JLabel OidFindLabel = new JLabel("Find Id");
        OidFindLabel.setBounds(50, 350, 100, 30);
        ordersPanel.add(OidFindLabel);
        JTextField OidFindField = new JTextField();
        OidFindField.setBounds(100, 350, 100, 30);
        ordersPanel.add(OidFindField);

        JButton OfindButton = new JButton("Find");
        OfindButton.setBounds(100, 375, 100, 30);
        JLabel OresultLabel = new JLabel("Result");
        JTextPane OresultField = new JTextPane();
        OresultField.setBounds(100, 400, 200, 50);
        ordersPanel.add(OresultField);
        OfindButton.addActionListener(e -> {
            OrdersBLL ordersBLL = new OrdersBLL();
            try {
                Orders order = ordersBLL.findOrdersById(Integer.parseInt(OidFindField.getText()));
                OresultField.setText("Client: " + order.getClient() + " Product: " + order.getProduct() + " Quantity: " + order.getQuantityOrd());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        ordersPanel.add(OfindButton);

        JLabel OidDeleteLabel = new JLabel("Delte Id");
        OidDeleteLabel.setBounds(50, 450, 100, 30);
        ordersPanel.add(OidDeleteLabel);
        JTextField OidDeleteField = new JTextField();
        OidDeleteField.setBounds(100, 450, 100, 30);
        ordersPanel.add(OidDeleteField);


        JButton OdeleteButton = new JButton("Delete");
        OdeleteButton.setBounds(100, 475, 100, 30);
        OdeleteButton.addActionListener(e -> {
            OrdersBLL ordersBLL = new OrdersBLL();
            try {
                if (!OidDeleteField.getText().equals("")) {
                    int idToDelete = Integer.parseInt(OidDeleteField.getText());
                    List<Bill> billz = billDataService.findAll();
                    for (Bill b : billz) {
                        if (b.idOrder() == idToDelete) {
                            b.delete(idToDelete);
                        }
                    }
                    ordersBLL.deleteOrders(idToDelete);
                    orderModel.setRowCount(0);
                    List<Orders> newOrders = ordersDataService.findAllOrders();
                    for (Orders order : newOrders) {
                        orderModel.addRow(new Object[]{order.getId(), order.getClient(), order.getProduct(), order.getQuantityOrd()});
                    }
                    //update BILL view as well
                    Bill billDataService2 = new Bill(0,0,0);
                    List<Bill> bills2 = billDataService2.findAll();
                    for (Bill bill : bills2) {
                        billModel.addRow(new Object[]{bill.id(), bill.idOrder(), bill.total()});
                    }

                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        ordersPanel.add(OdeleteButton);
        add(tabbedPane);
    }
    public void loadColumnNames(DefaultTableModel tableModel, Object obj) throws IllegalAccessException {
        Object[] properties = new Object[obj.getClass().getDeclaredFields().length];
        int index = 0;
        for (Field field : obj.getClass().getDeclaredFields()) {
            tableModel.addColumn(field.getName());
        }
    }
    public static void main(String[] args) throws IllegalAccessException {
        View view = new View();
        view.setVisible(true);
    }
}