package com;

import java.util.Enumeration;
import java.util.Vector;

public class Customer {
    private final String name;
    private final Vector<Rental> rentals = new Vector<>();
    private int frequentRenterPoints;
    private double totalAmount;

    public Customer(String newName) {
        name = newName;
    }

    public void addRental(Rental arg) {
        rentals.addElement(arg);
    }

    public String getName() {
        return name;
    }

    public String statement() {
        totalAmount = 0;
        frequentRenterPoints = 0;
        Enumeration<Rental> enum_rentals = rentals.elements();
        StringBuilder result = new StringBuilder();
        while (enum_rentals.hasMoreElements()) {
            result.append(calculateTotalAmount(enum_rentals));
        }
        return createOutputString(result.toString());
    }

    private String createOutputString(String variableString) {
        return "Rental Record for " + this.getName() + "\n" + "Title" + "\t" + "Days" + "\t" + "Amount" + "\n" +
                variableString +
                "Amount owed is " + totalAmount + "\n" +
                "You earned " + frequentRenterPoints + " frequent renter points";
    }

    private String calculateTotalAmount(Enumeration<Rental> enum_rentals) {
        Rental each = enum_rentals.nextElement();
        //determine amounts for each line
        double thisAmount = each.amountFor();
        // add frequent renter points
        frequentRenterPoints++;
        // add bonus for a two day new release rental
        if ((each.getMovie().getPriceCode() == Movie.NEW_RELEASE) && each.getDaysRented() > 1)
            frequentRenterPoints++;
        totalAmount += thisAmount;
        return each.getMovie().getTitle() + "\t" + each.getDaysRented() + "\t" + thisAmount + "\n";
    }
}
    