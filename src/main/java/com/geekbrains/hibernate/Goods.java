package com.geekbrains.hibernate;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.loader.custom.sql.SQLCustomQuery;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Goods")
public class Goods {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(name="title")
    private String title;

    @Column(name="price")
    private Float price;

    public List<Customers> getCustomersList() {
        return customersList;
    }

    @ManyToMany(mappedBy = "purchaseHistoryByCustomer")
    List<Customers> customersList;

    public Goods() {
    }
    public Goods(String title, Float price) {
        this.title = title;
        this.price = price;
        this.customersList = new ArrayList<Customers>();
    }
    public String whoBoughtProduct(Session curSession){
        NativeQuery<Object[]> query = curSession.createNativeQuery("select purchasehistorybycustomer.customer_id, customer.name " +
                "from purchasehistorybycustomer " +
                "inner join customer on purchasehistorybycustomer.customer_id = customer.id " +
                "where purchasehistorybycustomer.goods_id=" +this.id +
                " group by purchasehistorybycustomer.customer_id, customer.name");
        //query.setParameter("goods_id", this.id);
        List<Object[]> queryDataList = query.getResultList();
        StringBuilder sB = new StringBuilder();
        sB.append("Product ");
        sB.append(this.getTitle());
        sB.append("was bought this customers:");
        sB.append("\n");
        for (Object[] row : queryDataList){
            sB.append(row[1].toString());
        }
        return sB.toString();
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
