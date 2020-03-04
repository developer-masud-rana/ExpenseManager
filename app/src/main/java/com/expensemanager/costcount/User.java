package com.expensemanager.costcount;

/**
 * Created by navanee on 07-11-2016.
 */

public class User {

    public String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String fullName;
    public String email;
    public String password;
    public int groceries;
    public int invoice;
    public int transportation;
    public int shopping;
    public int rent;
    public int trips;
    public int utilities;
    public int other;

    public int getGroceries() {
        return groceries;
    }

    public void setGroceries(int groceries) {
        this.groceries = groceries;
    }

    public int getInvoice() {
        return invoice;
    }

    public void setInvoice(int invoice) {
        this.invoice = invoice;
    }

    public int getTransportation() {
        return transportation;
    }

    public void setTransportation(int transportation) {
        this.transportation = transportation;
    }

    public int getShopping() {
        return shopping;
    }

    public void setShopping(int shopping) {
        this.shopping = shopping;
    }

    public int getRent() {
        return rent;
    }

    public void setRent(int rent) {
        this.rent = rent;
    }

    public int getTrips() {
        return trips;
    }

    public void setTrips(int trips) {
        this.trips = trips;
    }

    public int getUtilities() {
        return utilities;
    }

    public void setUtilities(int utilities) {
        this.utilities = utilities;
    }

    public int getOther() {
        return other;
    }

    public void setOther(int other) {
        this.other = other;
    }

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}