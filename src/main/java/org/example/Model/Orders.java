package org.example.Model;

/**
 * A class representing an Order with an id, client id, product id, and quantity ordered.
 */
public class Orders {
    private int id;
    private int client;
    private int product;
    private int quantityOrd;

    /**
     * Default constructor for Orders.
     */
    public Orders() {
    }

    //-------------GETTERS AND SETTERS-------

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClient() {
        return client;
    }

    public void setClient(int client) {
        this.client = client;
    }

    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
    }

    public int getQuantityOrd() {
        return quantityOrd;
    }

    public void setQuantityOrd(int quantityOrd) {
        this.quantityOrd = quantityOrd;
    }

    //------------CONSTRUCTOR--------------

    /**
     * Constructs an Orders object with the specified id, client id, product id, and quantity ordered.
     * @param id The order ID.
     * @param client The client ID.
     * @param product The product ID.
     * @param quantity The quantity ordered.
     */
    public Orders(int id, int client, int product, int quantity) {
        super();
        this.id = id;
        this.client = client;
        this.product = product;
        this.quantityOrd = quantity;
    }
}
