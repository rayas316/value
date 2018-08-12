package com.example.raysakakibara.memocamp;

import java.util.Date;

import io.realm.RealmObject;

public class Memo extends RealmObject {
    public String title;

    public String updateDate;

    public String content;

    public Date date;
}
