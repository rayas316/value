package com.example.raysakakibara.memocamp;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import io.realm.Realm;

public class DetailActivity extends AppCompatActivity {
    Realm realm;
    TextInputEditText titleText;
    TextInputEditText contentText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_activity);
        realm = Realm.getDefaultInstance();

        titleText = (TextInputEditText) findViewById(R.id.titleEditText);
        contentText = (TextInputEditText) findViewById(R.id.contentEditText);
        showData();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();

        realm.close();

    }


    public void showData() {
        final Memo memo = realm.where(Memo.class).equalTo("updateDate", getIntent().getStringExtra("updateDate")).findFirst();
        titleText.setText(memo.title);
        contentText.setText(memo.content);

    }

    public void update(View view) {
        final Memo memo = realm.where(Memo.class).equalTo("updateDate", getIntent().getStringExtra("updateDate")).findFirst();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                memo.title = titleText.getText().toString();
                memo.content = contentText.getText().toString();
            }
        });
        finish();
    }

}