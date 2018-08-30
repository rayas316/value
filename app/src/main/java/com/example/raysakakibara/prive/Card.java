package com.example.raysakakibara.prive;

import java.util.Date;

import io.realm.RealmObject;

public class Card extends RealmObject {
    public String title;

    public String updateDate;

    public String content;

    public Date date;
}
