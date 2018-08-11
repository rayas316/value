package com.example.raysakakibara.memocamp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;

public class FloatActivity extends AppCompatActivity {
    public Realm realm;
    EditText TitleEditText;
    EditText ContentEditText;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float);
        realm = Realm.getDefaultInstance();
        ContentEditText = findViewById(R.id.editText2);
        TitleEditText = findViewById(R.id.editText);






    }

    public void update(View view) {

        String titles = TitleEditText.getText().toString();

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm", Locale.JAPANESE);
        String updateDate = sdf.format(date);


        String contents = ContentEditText.getText().toString();

        save(titles, updateDate, contents);

        finish();
    }

    private void save(final String titles, final String updateDate, final String contents) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Memo memo = realm.createObject(Memo.class);
                memo.titles = titles;
                memo.updateDate = updateDate;
                memo.contents = contents;
            }
        });

    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        realm.close();

    }

}
