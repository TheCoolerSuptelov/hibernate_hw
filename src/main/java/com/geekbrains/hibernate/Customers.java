package com.geekbrains.hibernate;

import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="Customer")
public class Customers {
    @Id
    @GeneratedValue (strategy= GenerationType.IDENTITY)
    private long id;
    @Column(name="name")
    private String name;

    @ManyToMany
    @JoinTable(name="purchaseHistoryByCustomer",
    joinColumns = @JoinColumn(name="customer_id"),
    inverseJoinColumns = @JoinColumn(name = "PurchaseHistory.customer_id"))
    private List<Goods> purchaseHistoryByCustomer;

    @ManyToMany
    @JoinTable(name = "PurchaseHistory",
    joinColumns = @JoinColumn(name = "customer_id"),
    inverseJoinColumns = @JoinColumn(name = "PurchaseHistory.customer_id"))
    private List<PurchaseHistory> purchaseHistoryRecords;

    public Customers() {

    }

    public Customers(String name) {
        this.name = name;
        this.purchaseHistoryRecords = new ArrayList<PurchaseHistory>();
        this.purchaseHistoryByCustomer = new ArrayList<Goods>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addProductToPurchaseHistory(Goods product, Session session){
        //public PurchaseHistory(Customers customer, Goods product, Float salesAmount) {
        PurchaseHistory pH = PurchaseHistory.createPurchaseHistoryRecord(this, product, product.getPrice());
        session.save(pH);
        this.purchaseHistoryRecords.add(pH);
        this.purchaseHistoryByCustomer.add(product);
    }
}
