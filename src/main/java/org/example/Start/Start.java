package org.example.Start;

import org.example.BusinessLogic.ClientBLL;
import org.example.BusinessLogic.ProductBLL;
import org.example.Model.Product;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Start {
    protected static final Logger LOGGER = Logger.getLogger(Start.class.getName());

    public static void main(String[] args) throws SQLException {

        ProductBLL productsBLL = new ProductBLL();
        Product p1=null;
        productsBLL.deleteProduct(100);
        ClientBLL clientBLL = new ClientBLL();
        try {
            //p1=productsBLL.findProductById(2);
        } catch (Exception ex) {
            LOGGER.log(Level.INFO, ex.getMessage());
        }

        //obtain field-value pairs for object through reflection
        ReflectionExample.retrieveProperties(p1);

    }


}
