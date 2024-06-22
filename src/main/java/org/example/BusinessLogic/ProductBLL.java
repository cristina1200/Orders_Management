package org.example.BusinessLogic;

import org.example.BusinessLogic.Validators.Validator;
import org.example.DataAccess.ProductDAO;
import org.example.Model.Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ProductBLL {
    private List<Validator<Product>> validators;
    private ProductDAO productDA;

    /**
     * Initializes the validators and the ProductDAO.
     */
    public ProductBLL() {
        validators = new ArrayList<Validator<Product>>();
        productDA = new ProductDAO();
    }

    /**
     * Finds a product by its ID.
     * @param id the ID of the product to find
     * @return the found product
     * @throws NoSuchElementException if the product with the specified ID is not found
     */
    public Product findProductById(int id) {
        Product st = productDA.findById(id);
        if (st == null) {
            throw new NoSuchElementException("The product with id= " + id + " was not found!");
        }
        return st;
    }

    /**
     * Inserts a new product into the database.
     * @param product the product to insert
     * @return the inserted product
     */
    public Product insertProduct(Product product) {
        for (Validator<Product> v : validators) {
            v.validate(product);
        }
        return productDA.insert(product);
    }

    /**
     * Updates a product's column with a new value.
     * @param id the ID of the product to update
     * @param column the column to update
     * @param value the new value for the column
     * @return the updated product
     */
    public Product update(int id, String column, Object value) {
        return productDA.update(column, value, id);
    }

    /**
     * Deletes a product by its ID.
     * @param id the ID of the product to delete
     */
    public void deleteProduct(int id) {
        productDA.delete(id);
        System.out.println("Deleted!");
    }

    /**
     * Retrieves all column names from the product table.
     * @return a list of column names
     * @throws SQLException if a database access error occurs
     */
    public List<String> getColumns() throws SQLException {
        return productDA.getColumnNames(Product.class);
    }

    /**
     * Retrieves all product IDs.
     * @return a list of product IDs
     */
    public List<Integer> getProductsId() {
        return productDA.getIds(Product.class);
    }

    /**
     * Retrieves all products from the product table.
     * @return a list of all products
     */
    public List<Product> findAllProducts() {
        return productDA.findAll();
    }
}
