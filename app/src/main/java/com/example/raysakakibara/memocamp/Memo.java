package com.example.raysakakibara.memocamp;

import io.realm.Realm;
import io.realm.RealmObject;

public class Memo extends RealmObject {
    public String titles;

    public String updateDate;

    public String contents;
}
