package com.geekbrains.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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
        Goods potato = new Goods("potato",12f);
        customerHo.addProductToPurchaseHistory(potato,session);
        Goods rice = new Goods("rice",42f);
        customerHo.addProductToPurchaseHistory(rice,session);
        Goods chickenFillet = new Goods("chickenFillet",120f);
        customerHo.addProductToPurchaseHistory(chickenFillet,session);

        session.beginTransaction();
        session.save(potato);
        session.save(rice);
        session.save(chickenFillet);
        session.save(customerXi);
        session.save(customerLi);
        session.save(customerHo);
        session.getTransaction().commit();
    }
}
