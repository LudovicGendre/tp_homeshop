package com.home.homeShop;

public class Main {

    public static void main(String[] args){
        Product cafe = new Product("Philips HD7866/61", "PHilips SENSEO Quadrante, Noir - a ou 2 tasses", 79.99);
        Television tv = new Television("TV Samsung UE49MU62920", "Smart TV LED incurvée 49", 599, 49, "LED");
        Fridge fridge = new Fridge("BEKO TSE 1042 F","Réfrigérateur BEKO 130L - Classe A+ - blanc", 189, 130, false);
        Customers customer = new Customers("Juste Leblanc", "19 rue Germain Pilon, Paris");

        Bill bill = new Bill(customer, new RelayDelivery(27));

        bill.addProduct(cafe, 1);
        bill.addProduct(tv, 1);
        bill.addProduct(fridge, 1);

        bill.generate(new Writer() {
            @Override
            public void start() {

            }

            @Override
            public void writeLine(String line) {
                System.out.println(line);
            }

            @Override
            public void stop() {

            }
        });
        bill.generate(new FileWriter("Facture_Leblanc"));


    }
}
