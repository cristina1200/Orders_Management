package org.example.DataAccess;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.example.Connection.ConnectionFactory;

/**
 * Class that contains the basic CRUD operations. (Create, Read, Update, Delete)
 * @param <T> the type of the entity
 */
public class AbstractDAO<T>
{
    protected static final Logger LOGGER = Logger.getLogger(org.example.DataAccess.AbstractDAO.class.getName());

    private final Class<T> type;

    /**
     * Constructor that retrieves the class type parameter.
     */
    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Creates a SELECT query string based on a specified field.
     * @param field the field to use in the WHERE clause
     * @return the SQL SELECT query as a string
     */
    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        if (!field.equals("ALL")) {
            sb.append(" WHERE ").append(field).append(" =?");
        }
        return sb.toString();
    }

    /**
     * Retrieves all IDs from the table corresponding to the type T.
     * @param type the class type of the entity
     * @return a list of IDs
     */
    public List<Integer> getIds(Class<T> type) {
        List<Integer> ids = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT id FROM " + type.getSimpleName();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ids.add(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:getIds " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return ids;
    }

    /**
     * Retrieves all column names from the table corresponding to the type T.
     * @param type the class type of the entity
     * @return a list of column names
     */
    public List<String> getColumnNames(Class<T> type) {
        List<String> columns = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("ALL");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int count = rsmd.getColumnCount();
            for (int i = 1; i <= count; i++) {
                columns.add(rsmd.getColumnName(i));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:getColumnNames " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return columns;
    }

    /**
     * Retrieves all objects from the table corresponding to the type T.
     * @return a list of objects
     */
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("ALL");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Retrieves an object by its ID from the table corresponding to the type T.
     * @param id the ID of the object
     * @return the object with the specified ID or null if not found
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            List<T> objects = createObjects(resultSet);
            if (!objects.isEmpty()) {
                return objects.get(0);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Creates a list of objects from a ResultSet.
     * @param resultSet the ResultSet containing the data
     * @return a list of objects
     */
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (Constructor c : ctors) {
            if (c.getGenericParameterTypes().length == 0) {
                ctor = c;
                break;
            }
        }
        try {
            while (resultSet.next()) {
                assert ctor != null;
                ctor.setAccessible(true);
                T instance = (T) ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException | IllegalAccessException | SQLException | IntrospectionException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return list;
    }


    //------------------------------------------------------------------
    /**
     * Builds the SQL INSERT query string.
     * @return the SQL INSERT query as a StringBuilder
     */
    public StringBuilder insertQuery() {
        List<String> columns = getColumnNames(type);
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ").append(type.getSimpleName()).append(" (");
        for (int i = 0; i < columns.size(); i++) {
            if (i == columns.size() - 1) {
                sb.append(columns.get(i)).append(") VALUES (");
            } else {
                sb.append(columns.get(i)).append(",");
            }
        }
        for (int i = 0; i < columns.size(); i++) {
            if (i == columns.size() - 1) {
                sb.append("?)");
            } else {
                sb.append("?,");
            }
        }
        return sb;
    }

    /**
     * Inserts an object into the table corresponding to the type T.
     * @param t the object to insert
     * @return the inserted object
     */
    public T insert(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = insertQuery().toString();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            List<Integer> ids = getIds(type);
            int i = 1;
            for (Field field : type.getDeclaredFields()) {
                field.setAccessible(true);
                statement.setObject(i, field.get(t));
                if (field.getName().equals("id")) {
                    for (int idz : ids) {
                        if (idz == (int) field.get(t)) {
                            throw new IllegalArgumentException("The id is already in the database!");
                        }
                    }
                }
                i++;
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return t;
    }

    /**
     * Builds the SQL UPDATE query string.
     * @param column the column to update
     * @return the SQL UPDATE query as a StringBuilder
     */
    public StringBuilder updateQuery(String column) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ").append(type.getSimpleName()).append(" SET ");
        sb.append(column).append(" = ? ");
        sb.append("WHERE id = ?");
        return sb;
    }

    /**
     * Updates a specific column of an object in the table corresponding to the type T.
     * @param column the column to update
     * @param value the new value for the column
     * @param id the ID of the object to update
     * @return the updated object
     */
    public T  update(String column, Object value, int id ) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query =updateQuery(column).toString();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setObject(1, value);
            statement.setObject(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:UPDATE " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        T t=findById(id);
        return t;
    }

    /**
     * Delete an object by id by executing DELETE FROM table name WHERE id = ? and setting the id with the value
     * given by the user
     * @param id
     */
    public void delete(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "DELETE FROM " + type.getSimpleName() + " WHERE id = " +id;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:DELETE " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }
}
