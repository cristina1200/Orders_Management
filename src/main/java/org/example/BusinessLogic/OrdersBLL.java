package org.example.BusinessLogic;

import org.example.BusinessLogic.Validators.Validator;
import org.example.DataAccess.OrdersDAO;
import org.example.Model.Orders;
import org.example.Model.Bill;
import org.example.Model.Product;

import java.util.List;
import java.util.NoSuchElementException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Contains the business logic for handling orders.
 */
public class OrdersBLL {
    private List<Validator<Orders>> validators;
    private OrdersDAO ordersDAO;

    /**
     * Initializes the validators and the OrdersDAO.
     */
    public OrdersBLL() {
        validators = new ArrayList<Validator<Orders>>();
        ordersDAO = new OrdersDAO();
    }

    /**
     * Finds an order by its ID.
     * @param id the ID of the order to find
     * @return the found order
     * @throws NoSuchElementException if the order with the specified ID is not found
     */
    public Orders findOrdersById(int id) {
        Orders st = ordersDAO.findById(id);
        if (st == null) {
            throw new NoSuchElementException("The order with id= " + id + " was not found!");
        }
        return st;
    }

    /**
     * Inserts a new order into the database. Validates the order and checks product quantity.
     * @param order the order to insert
     * @return the inserted order
     * @throws IllegalArgumentException if the product quantity is not sufficient
     */
    public Orders insertOrders(Orders order) {
        for (Validator<Orders> v : validators) {
            v.validate(order);
        }

        Product prod = new ProductBLL().findProductById(order.getProduct());
        if (prod.getQuantityPro() < order.getQuantityOrd()) {
            throw new IllegalArgumentException("The quantity of the product is not enough!");
        } else {
            prod.setQuantityPro(prod.getQuantityPro() - order.getQuantityOrd());
            new ProductBLL().update(prod.getId(), "quantityPro", prod.getQuantityPro());
        }
        Orders o = ordersDAO.insert(order);
        Bill b = calculateBill(o);
        b.insertBill(b);
        return o;
    }

    /**
     * Updates an order's column with a new value.
     * @param id the ID of the order to update
     * @param column the column to update
     * @param value the new value for the column
     * @return the updated order
     */
    public Orders update(int id, String column, Object value) {
        return ordersDAO.update(column, value, id);
    }

    /**
     * Deletes an order by its ID.
     * @param id the ID of the order to delete
     */
    public void deleteOrders(int id) {
        ordersDAO.delete(id);
    }

    /**
     * Calculates the bill for an order.
     * @param order the order for which to calculate the bill
     * @return the calculated bill
     */
    public Bill calculateBill(Orders order) {
        int bill = 0;
        Product prod = new ProductBLL().findProductById(order.getProduct());
        bill = order.getQuantityOrd() * prod.getPrice();
        Bill result = new Bill(order.getId(), order.getId(), bill);
        return result;
    }

    /**
     * Retrieves all column names from the orders table.
     * @return a list of column names
     * @throws SQLException if a database access error occurs
     */
    public List<String> getColumns() throws SQLException {
        return ordersDAO.getColumnNames(Orders.class);
    }

    /**
     * Retrieves all orders from the orders table.
     * @return a list of all orders
     */
    public List<Orders> findAllOrders() {
        return ordersDAO.findAll();
    }
}
