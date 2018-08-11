package com.example.raysakakibara.memocamp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import io.realm.Realm;

public class DetailActivity extends AppCompatActivity {
    Realm realm;
    EditText titleText2;
    EditText contenttext2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_activity);
        realm = Realm.getDefaultInstance();

        titleText2 = (EditText) findViewById(R.id.title);
        contenttext2 = (EditText) findViewById(R.id.content);
        showData();
}

    public void showData() {

        final Memo memo = realm.where(Memo.class).equalTo("updateDate", getIntent().getStringExtra("updateDate")).findFirst();
        titleText2.setText(memo.titles);
        contenttext2.setText(memo.contents);

    }


    public void update2(View view) {
        final Memo memo = realm.where(Memo.class).equalTo("updateDate",getIntent().getStringExtra("updateDate")).findFirst();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                memo.titles = titleText2.getText().toString();
                memo.contents = contenttext2.getText().toString();
            }
        });
        finish();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();

        realm.close();

    }


}

