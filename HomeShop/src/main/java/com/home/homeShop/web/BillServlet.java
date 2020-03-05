package com.home.homeShop.web;

import com.home.homeShop.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BillServlet extends HttpServlet {
    List<Product> products = new ArrayList<Product>();

    @Override
    public void init() throws ServletException {
        Product cafe = new Product("Philips HD7866/61", "PHilips SENSEO Quadrante, Noir - a ou 2 tasses", 79.99);
        Television tv = new Television("TV Samsung UE49MU62920", "Smart TV LED incurvée 49", 599, 49, "LED");
        Fridge fridge = new Fridge("BEKO TSE 1042 F","Réfrigérateur BEKO 130L - Classe A+ - blanc", 189, 130, false);
        Customers customer = new Customers("Juste Leblanc", "19 rue Germain Pilon, Paris");
        products.add(cafe);
        products.add(tv);
        products.add(fridge);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        if (req.getQueryString() == null)
            displayForm(resp);
        else
            displayBill(req, resp);
    }


    private void displayForm(HttpServletResponse resp) throws IOException {
        for (int i = 0; i < products.size(); i++){
            Product product = products.get(i);
            resp.getWriter().println("<b>" + i + " - " + product.getName() + "</b> : " + product.getPrice() +
                        "<br/>" + product.getDescription() + "<br/><br/>");

        }
        String form = "<form action=\"bill\">" +
                "<b>nom complet :</b> <input name=\"fullname\"/><br/>" +
                "<b>adresse :</b> <input name=\"address\"/><br/><br/>" +
                "<b>livraison :</b> <br/>" +
                "à domicile : <input type=\"radio\" name=\"deliveryMode\" value=\"direct\"/><br/>" +
                "express : <input type=\"radio\" name=\"deliveryMode\" value=\"express\"/><br/>" +
                "point relais : <input type=\"radio\" name=\"deliveryMode\" value=\"relay\"/><br/>" +
                "à retirer : <input type=\"radio\" name=\"deliveryMode\" value=\"takeAway\"/><br/>" +
                "<b>Informations livraison</b> (relay et express) : <input name=\"deliveryInfo\"/><br/><br/>" +
                "<b>liste produits </b> (produit:quantité, un produit par ligne) : <br/>" +
                "<textarea name=\"products\"></textarea><br/>" +
                "<input type=\"submit\"/>" +
                "</form>";
        resp.getWriter().println(form);
    }


    private void displayBill(HttpServletRequest req, HttpServletResponse resp) {
        Map<String, String> params = splitParameters(req.getQueryString());
        Customers customer = new Customers(params.get("fullname"), params.get("address"));
        Delivery delivery = null;
        switch (params.get("deliveryMode")) {
            case "direct" :
                delivery = new DirectDelivery();
                break;
            case "express" :
                delivery = new ExpressDelivery(params.get("deliveryInfo"));
                break;
            case "relay" :
                delivery = new RelayDelivery(Integer.parseInt(params.get("deliveryInfo")));
                break;
            case "takeAway" :
                delivery = new TakeAwayDelivery();
                break;

        }
        Bill bill = new Bill(customer, delivery);
        String[] productsParams = params.get("products").split("%0D%0A");
        for (String productLine : productsParams){
            String[] productAndQuantity = productLine.split("%3A");
            Product product = products.get(Integer.parseInt(productAndQuantity[0]));
            Integer quantity = Integer.parseInt(productAndQuantity[1]);
            bill.addProduct(product, quantity);
        }
        bill.generate(new Writer() {
            @Override
            public void start() {

            }

            @Override
            public void writeLine(String line) {
                try{
                    resp.getWriter().println("<br/>" + line);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void stop() {

            }
        });





    }

    public Map<String, String> splitParameters(String queryString) {
        String[] brutParams = queryString.split("&");
        Map<String, String> params = new HashMap<>();
        for (String brutParam : brutParams) {
            String[] keyAndValue = brutParam.split("=");
            if (keyAndValue.length == 2)
                params.put(keyAndValue[0], keyAndValue[1]);
        }
        return params;
    }
}
