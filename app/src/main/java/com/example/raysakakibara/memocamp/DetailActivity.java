package com.example.raysakakibara.memocamp;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import io.realm.Realm;

public class DetailActivity extends AppCompatActivity {
    Realm realm;
    TextInputEditText titleText;
    TextInputEditText contentText;
    TextInputLayout titleEditTextTextInputLayout2;
    TextInputLayout contentEditTextTextInputLayout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_activity);
        realm = Realm.getDefaultInstance();

        titleText = (TextInputEditText) findViewById(R.id.titleEditText2);
        contentText = (TextInputEditText) findViewById(R.id.contentEditText2);
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
        String title=titleText.getText().toString();
        String content=contentText.getText().toString();
        titleEditTextTextInputLayout2=findViewById(R.id.titleEditTextTextInputLayout2);
        contentEditTextTextInputLayout2=findViewById(R.id.contentEditTextTextInputLayout2);
        if (title.matches("") && content.matches("")) {

            contentEditTextTextInputLayout2.setError("商品名と値段が入力されていません");
            return;
        }
        else if (title.matches("")) {
            titleEditTextTextInputLayout2.setError("商品名が入力されていません");
            return;
        }
        else if (content.matches("")) {
            contentEditTextTextInputLayout2.setError("値段が入力されていません");
            return;
        }else if (title.length()>10){
            titleEditTextTextInputLayout2.setError("入力できるのは10文字までです");
            return;
        }else if (content.length()>10){
            contentEditTextTextInputLayout2.setError("入力できるのは10桁までです");
            return;
        }
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