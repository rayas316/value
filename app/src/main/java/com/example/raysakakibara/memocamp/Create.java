package com.example.raysakakibara.memocamp;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;

public class Create extends AppCompatActivity {
    public Realm realm;
    TextInputEditText titleEditText;
    TextInputEditText contentEditText;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        realm = Realm.getDefaultInstance();
        titleEditText = findViewById(R.id.titleEditText);
        contentEditText = findViewById(R.id.contentEditText);
    }


    private void save(final String title, final String updateDate, final String content, final Date date) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Memo memo = realm.createObject(Memo.class);
                memo.title = title;
                memo.updateDate = updateDate;
                memo.content = content;
                memo.date = date;
            }
        });

    }

    public void create(View view) {

        String title = titleEditText.getText().toString();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH時mm分", Locale.JAPANESE);
        String updateDate = sdf.format(date);
        String content = contentEditText.getText().toString();
        if (title.matches("") && content.matches("")) {
            Toast.makeText(this, "商品名と値段が入力されていません", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (title.matches("")) {
            Toast.makeText(this, "商品名が入力されていません。", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (content.matches("")) {
            Toast.makeText(this, "値段が入力されていません。", Toast.LENGTH_SHORT).show();
            return;
        }


        save(title, updateDate, content, date);

        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();

    }

}
