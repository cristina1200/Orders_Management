package org.example.Model;

/**
 * A class representing a Product with an id, name, quantity, and price.
 */
public class Product {
    private int id;
    private String name;
    private int quantityPro;
    private int price;
    /**
     * Default constructor for Product.
     */
    public Product() {
    }

    //-----------------GETTERS AND SETTERS-------------
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

    public int getQuantityPro() {
        return quantityPro;
    }

    public void setQuantityPro(int quantityPro) {
        this.quantityPro = quantityPro;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    //-----------------CONSTRUCTOR----------

    /**
     * Constructs a Product object with the specified id, name, quantity, and price.
     * @param id The product ID.
     * @param name The product name.
     * @param quantityPro The product quantity.
     * @param price The product price.
     */

    public Product(int id, String name, int quantityPro, int price)
    {
        super();
        this.id = id;
        this.name = name;
        this.quantityPro = quantityPro;
        this.price = price;
    }

}
