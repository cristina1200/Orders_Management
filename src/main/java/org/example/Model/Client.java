package org.example.Model;

/**
 * A class representing a Client with an id, name, email, and address.
 */
public class Client {
    private int id;
    private String name;
    private String email;
    private String address;

    //-----------GETTERS AND SETTERS---------
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    //--------CONSTRUCTOR------

    /**
     * Constructs a Client object with the specified id, name, address, and email.
     * @param id The client's ID.
     * @param name The client's name.
     * @param address The client's address.
     * @param email The client's email.
     */
    public Client(int id, String name, String address, String email) {
        super();
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
    }

    /**
     * Default constructor for Client.
     */
    public Client() {
    }

    //------------TO STRING-----------

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
