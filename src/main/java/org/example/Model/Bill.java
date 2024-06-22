package org.example.Model;

import org.example.Connection.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A record representing a Bill with an id, idOrder, and total amount.
 */
public record Bill(int id, int idOrder, int total) {

    /**
     * Inserts a Bill record into the database.
     * @param b The Bill object to be inserted.
     * @return The inserted Bill object.
     */
    public Bill insertBill(Bill b) {

        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement statement = null;

        String query = "INSERT INTO Bill (id, idOrder, total) VALUES(?, ?, ?)";

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, b.id());
            statement.setInt(2, b.idOrder());
            statement.setInt(3, b.total());
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error inserting bill: " + e.getMessage());
        }
        finally
        {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return b;
    }

    /**
     * Retrieves all Bill records from the database.
     * @return A list of Bill objects.
     */
    public List<Bill> findAll() {
        List<Bill> bills = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM Bill";

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                bills.add(new Bill(resultSet.getInt("id"), resultSet.getInt("idOrder"), resultSet.getInt("total")));
            }
        }
        catch (SQLException e)
        {
            System.out.println("Bill:findAll" + e.getMessage());
        }
        finally
        {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return bills;
    }

    /**
     * Finds a Bill by its associated order ID.
     * @param idOrder The order ID to search for.
     * @return The Bill object corresponding to the order ID, or null if not found.
     */
    public Bill findByIdOrder(int idOrder)
    {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM Bill WHERE idOrder = ?";
        try
        {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, idOrder);
            resultSet = statement.executeQuery();
            if (resultSet.next())
            {
                return new Bill(resultSet.getInt("id"), resultSet.getInt("idOrder"), resultSet.getInt("total"));
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        finally
        {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Deletes a Bill record by its ID.
     * @param id The ID of the Bill to be deleted.
     */
    public void delete(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = "DELETE FROM Bill WHERE id = ?";
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }
}
