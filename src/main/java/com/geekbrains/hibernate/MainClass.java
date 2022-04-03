package com.geekbrains.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.stream.Collectors;

public class MainClass {
    public static void main(String[] args) {

        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(Customers.class);
        configuration.addAnnotatedClass(Goods.class);

        SessionFactory factory = configuration.buildSessionFactory();
        Session session = factory.getCurrentSession();
        Customers customerXi = new Customers("Xi");
        Customers customerLi = new Customers("Li");
        Customers customerHo = new Customers("Ho");
        Goods potato = new Goods("potato", 12f);
        Goods chickenFillet = new Goods("chickenFillet", 120f);
        Goods rice = new Goods("rice", 42f);

        customerLi.addProductToPurchaseHistory(chickenFillet, session);

        customerHo.addProductToPurchaseHistory(chickenFillet, session);
        customerHo.addProductToPurchaseHistory(potato, session);
        customerHo.addProductToPurchaseHistory(rice, session);

        session.beginTransaction();
        session.save(potato);
        session.save(rice);
        session.save(chickenFillet);
        session.save(customerXi);
        session.save(customerLi);
        session.save(customerHo);
        session.getTransaction().commit();

        Session curSession = factory.getCurrentSession();
        curSession.beginTransaction();
        // System.out.println(chickenFillet.whoBoughtProduct(curSession));
        System.out.println(chickenFillet.getCustomersList().stream().map(customer->customer.getName()).collect(Collectors.joining(", ")));
        System.out.println(customerHo.showPurchaseHistory());
        curSession.getTransaction().commit();
    }
}


