package org.example.BusinessLogic;

import org.example.BusinessLogic.Validators.EmailValidator;
import org.example.BusinessLogic.Validators.Validator;
import org.example.DataAccess.ClientDAO;
import org.example.Model.Client;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Contains the business logic for handling clients.
 */
public class ClientBLL {
    private List<Validator<Client>> validators;
    private ClientDAO clientDAO;

    /**
     * Initializes the validators and the ClientDAO.
     */
    public ClientBLL() {
        validators = new ArrayList<Validator<Client>>();
        validators.add(new EmailValidator());
        clientDAO = new ClientDAO();
    }

    /**
     * Finds a client by its ID.
     * @param id the ID of the client to find
     * @return the found client
     * @throws NoSuchElementException if the client with the specified ID is not found
     */
    public Client findClientById(int id) {
        Client st = clientDAO.findById(id);
        if (st == null) {
            throw new NoSuchElementException("The client with id= " + id + " was not found!");
        }
        return st;
    }

    /**
     * Inserts a new client into the database.
     * @param client the client to insert
     * @return the inserted client
     */
    public Client insertClient(Client client) {
        for (Validator<Client> v : validators) {
            v.validate(client);
        }
        return clientDAO.insert(client);
    }

    /**
     * Updates a client's column with a new value. Validates the email if the column is email.
     * @param id the ID of the client to update
     * @param column the column to update
     * @param value the new value for the column
     * @return the updated client
     */
    public Client update(int id, String column, Object value) {
        if (column.equals("email")) {
            EmailValidator emailValidator = new EmailValidator();
            emailValidator.validate(findClientById(id));
        }
        return clientDAO.update(column, value, id);
    }

    /**
     * Deletes a client by its ID.
     * @param id the ID of the client to delete
     */
    public void deleteClient(int id) {
        clientDAO.delete(id);
    }

    /**
     * Retrieves all clients from the client table.
     * @return a list of all clients
     */
    public List<Client> findAllClients() {
        return clientDAO.findAll();
    }

    /**
     * Retrieves all client IDs.
     * @return a list of client IDs
     */
    public List<Integer> getClientsId() {
        return clientDAO.getIds(Client.class);
    }

    /**
     * Retrieves all column names from the client table.
     * @return a list of column names
     * @throws SQLException if a database access error occurs
     */
    public List<String> getColumns() throws SQLException {
        return clientDAO.getColumnNames(Client.class);
    }
}
