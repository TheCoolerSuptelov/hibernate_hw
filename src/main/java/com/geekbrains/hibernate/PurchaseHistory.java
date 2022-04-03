package com.geekbrains.hibernate;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "PurchaseHistory")
public class PurchaseHistory {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @ManyToOne()
    @JoinColumn(name = "customer_id")
    private Customers Customer;

    @ManyToOne(mappedBy = "id")
    private Goods product;

    @Column(name = "salesAmount")
    private Float salesAmount;

    public static PurchaseHistory createPurchaseHistoryRecord(Customers customer, Goods product, Float salesAmount) {
        return new PurchaseHistory(customer,product,salesAmount);
    }

    public PurchaseHistory(Customers customer, Goods product, Float salesAmount) {
        this.Customer = customer;
        this.product = product;
        this.salesAmount = salesAmount;
    }

    public Goods getProduct() {
        return product;
    }

    public PurchaseHistory() {
    }

    public Customers getCustomer() {
        return Customer;
    }


}
