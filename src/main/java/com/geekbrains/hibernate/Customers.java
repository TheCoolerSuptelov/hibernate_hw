package com.geekbrains.hibernate;

import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name="Customer")
public class Customers {
    @Id
    @GeneratedValue (strategy= GenerationType.IDENTITY)
    private long id;
    @Column(name="name")
    private String name;

    @ManyToMany
    @JoinTable(schema = "hw11",name="purchaseHistoryByCustomer",
    joinColumns = @JoinColumn(name="customer_id"),
    inverseJoinColumns = @JoinColumn(name = "goods_id"))
    private List<Goods> purchaseHistoryByCustomer;

    public Customers() {

    }

    public Customers(String name) {
      this.name = name;
      this.purchaseHistoryByCustomer = new ArrayList<Goods>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addProductToPurchaseHistory(Goods product, Session session){
       this.purchaseHistoryByCustomer.add(product);
       product.getCustomersList().add(this);
    }

    public String showPurchaseHistory(){
        return purchaseHistoryByCustomer.stream().
                map(product->product.getTitle()).
                collect(Collectors.joining(", "));
    }
}
