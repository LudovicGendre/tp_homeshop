package com.home.homeShop;

public class RelayDelivery implements Delivery {

    private int relayNumber;

    public RelayDelivery(int relayNumber) {
        this.relayNumber = relayNumber;
    }

    @Override
    public double getPrice() {
       if (relayNumber >= 1 && relayNumber <= 22)
           return 0;
       else if (relayNumber >= 23 && relayNumber <= 47 )
           return 2.99;
       else
           return 4.99;
    }

    @Override
    public String getInfo() {
        return "livraison point relais " + getPrice();
    }
}
