package com.example.raysakakibara.prive;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;

public class Create extends AppCompatActivity {
    public Realm realm;
    TextInputEditText titleEditText;
    TextInputEditText contentEditText;
    TextInputLayout titleEditTextTextInputLayout;
    TextInputLayout contentEditTextTextInputLayout;


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
                Card card = realm.createObject(Card.class);
                card.title = title;
                card.updateDate = updateDate;
                card.content = content;
                card.date = date;
            }
        });

    }

    public void create(View view) {
        titleEditTextTextInputLayout=findViewById(R.id.titleEditTextTextInputLayout);
        contentEditTextTextInputLayout=findViewById(R.id.contentEditTextTextInputLayout);
        String title = titleEditText.getText().toString();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH時mm分", Locale.JAPANESE);
        String updateDate = sdf.format(date);
        String content = contentEditText.getText().toString();
        if (title.matches("") && content.matches("")) {

            contentEditTextTextInputLayout.setError("商品名と値段が入力されていません");
            return;
        }
        else if (title.matches("")) {
            titleEditTextTextInputLayout.setError("商品名が入力されていません");
            return;
        }
        else if (content.matches("")) {
            contentEditTextTextInputLayout.setError("値段が入力されていません");
            return;
        }else if (title.length()>10){
            titleEditTextTextInputLayout.setError("入力できるのは10文字までです");
            return;
        }else if (content.length()>10){
            contentEditTextTextInputLayout.setError("入力できるのは10桁までです");
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
