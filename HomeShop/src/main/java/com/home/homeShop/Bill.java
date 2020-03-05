package com.home.homeShop;

import java.util.HashMap;
import java.util.Map;

public class Bill {
    private Customers customers;
    private Map<Product, Integer> products = new HashMap<Product, Integer>();
    private Delivery delivery;

    public Bill(Customers customers, Delivery delivery) {
        this.customers = customers;
        this.delivery = delivery;
    }

    public Bill(Customers customer) {
    }

    public void addProduct(Product product, int quantity) {
        this.products.put(product, quantity);
    }

    public Customers getCustomers() {
        return customers;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void generate(Writer writer){
        writer.start();
        writer.writeLine("Home Shop Compagnie");
        writer.writeLine("1 Place Charles de Gaulle, 75008 Paris");
        writer.writeLine("");
        writer.writeLine("Facture à l'attention de : ");
        writer.writeLine(customers.getFullname());
        writer.writeLine(customers.getAddress());
        writer.writeLine("");
        writer.writeLine("Mode de livraison : " + delivery.getInfo());
        writer.writeLine("");
        writer.writeLine("Produits");
        writer.writeLine("-------------------------------------------------");
        for (Map.Entry<Product,Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            Integer quantity = entry.getValue();
            writer.writeLine(product.getName() + " - " + product.getPrice() + " - " + quantity + " unité(s)");
            writer.writeLine(product.getDescription());
            writer.writeLine("");
        }
         writer.writeLine("Livraison : " + delivery.getPrice());
        writer.writeLine("--------------------------------------------------");
        writer.writeLine("Total : " + this.getTotal() );
        writer.stop();
        }


    /**
     * get the total price for the current bill, including products and delivery cost
     * @return total price
     */
    public double getTotal() {
        double total = delivery.getPrice();
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            Integer quantity = entry.getValue();
            total += product.getPrice() * quantity;
        }
        return total;
    }
}
