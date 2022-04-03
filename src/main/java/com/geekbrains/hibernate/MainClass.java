package com.geekbrains.hibernate;

import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MainClass {
    public static void main(String[] args) {
        //hw11();
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(Items.class);
        SessionFactory factory = configuration.buildSessionFactory();
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        for (int i = 0; i < 40; i++) {
            Items newItem = new Items();
            session.save(newItem);
        }
        session.getTransaction().commit();

        //debugThreadMove(factory);
        threadMove(factory);
    }

    private static void debugThreadMove(SessionFactory factory) {
        AtomicInteger globalCounter = new AtomicInteger(160_000);
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        Items item = (Items) session.get(Items.class, getRandomNumberUsingInts(), LockMode.PESSIMISTIC_WRITE);
        item.increaseCounter();
        session.save(item);
        session.getTransaction().commit();
        globalCounter.decrementAndGet();
    }

    private static void threadMove(SessionFactory factory) {
        AtomicInteger globalCounter = new AtomicInteger(160_000);
        ExecutorService executorService = Executors.newFixedThreadPool(8);

        for (int i = 0; i < 8; i++) {
            new Thread(() -> {
                while (globalCounter.get() > 0) {
                    try {
                        Session session = factory.getCurrentSession();
                        session.beginTransaction();
                        Items item = (Items) session.get(Items.class, getRandomNumberUsingInts(), LockMode.PESSIMISTIC_WRITE);
                        item.increaseCounter();
                        session.save(item);
                        session.getTransaction().commit();
                        globalCounter.decrementAndGet();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
            );
        }
    }

    public static long getRandomNumberUsingInts() {
        Random random = new Random();
        return (long) random.ints(0, 39)
                .findFirst()
                .getAsInt();
    }

    private static void hw11() {
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
        System.out.println(chickenFillet.getCustomersList().stream().map(customer -> customer.getName()).collect(Collectors.joining(", ")));
        System.out.println(customerHo.showPurchaseHistory());
        curSession.getTransaction().commit();
    }
}


