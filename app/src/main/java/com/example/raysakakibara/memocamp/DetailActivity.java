package com.example.raysakakibara.memocamp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.Date;

import io.realm.Realm;

public class DetailActivity extends AppCompatActivity {
    Realm realm;
    EditText titleEditText;
    EditText contentEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_activity);
        realm = Realm.getDefaultInstance();

        titleEditText = (EditText) findViewById(R.id.titleEditText);
        contentEditText = (EditText) findViewById(R.id.contentEditText);
        showData();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();

        realm.close();

    }


    public void showData() {
        final Memo memo = realm.where(Memo.class).equalTo("updateDate", getIntent().getStringExtra("updateDate")).findFirst();
        titleEditText.setText(memo.title);
        contentEditText.setText(memo.content);

    }

    public void update(View view) {
        final Memo memo = realm.where(Memo.class).equalTo("updateDate", getIntent().getStringExtra("updateDate")).findFirst();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                memo.title = titleEditText.getText().toString();
                memo.content = contentEditText.getText().toString();
            }
        });
        finish();
    }

}