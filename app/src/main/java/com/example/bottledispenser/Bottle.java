package com.example.bottledispenser;

public class Bottle {
    private final String name;
    private final String manufacturer;
    private final double total_energy;
    private final double size;
    private final double prize;
    private double amount;

    public Bottle() {
        name = "Pepsi Max";
        manufacturer = "Pepsi";
        total_energy = 0.3;
        size = 0.5;
        prize = 1.80;
    }

    public Bottle(String n, String m, double t, double s, double p, double a) {
        name = n;
        manufacturer = m;
        total_energy = t;
        size = s;
        prize = p;
        amount = a;

    }
    @Override
    public String toString() {
        return name + " " + size;
    }
    public String getName() {
        return name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public double getEnergy() {
        return total_energy;
    }

    public double getSize() {
        return size;
    }

    public double getPrize() {
        return prize;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amnt) {
        amount = amnt;
    }
}
