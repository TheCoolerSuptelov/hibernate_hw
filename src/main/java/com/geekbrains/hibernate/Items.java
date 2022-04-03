package com.geekbrains.hibernate;

import javax.persistence.*;

@Entity
@Table(name="Items")
public class Items {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private long id;

    @Column(name = "counter")
    private int counter = 0;

    public Items() {
    }
    public void increaseCounter(){
        ++this.counter;
    }

}
