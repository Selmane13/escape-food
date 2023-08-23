package com.example.fadlou_restaurant;

import java.io.Serializable;

public class Element implements Serializable {
    private String name;
    private Type type;
    private double prixAchat;

    private double prixVente;


    public Element(String name, Type type, double prixAchat, double prixVente) {
        this.name = name;
        this.type = type;
        this.prixAchat = prixAchat;
        this.prixVente = prixVente;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public double getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(double prixAchat) {
        this.prixAchat = prixAchat;
    }

    public double getPrixVente() {
        return prixVente;
    }

    public double getProfit() {
        return prixVente - prixAchat;
    }
}
