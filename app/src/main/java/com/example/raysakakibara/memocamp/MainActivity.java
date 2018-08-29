package com.example.raysakakibara.memocamp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    public ListView listView;
    public Realm realm;
    MemoAdapter adapter;
    List<Memo> items;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();
        listView = (ListView) findViewById(R.id.listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Memo memo = (Memo) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("updateDate", memo.updateDate);
                startActivity(intent);
            }

        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {//int positionは何番目のデータを押したかを管理している
                //adapter.getItem(position)で押したデータのMemoオブジェクトを取得できる
                //Memo.titleとやれば押したやつのtitleが取得できる、この要領でtitle、updateDate、contentが押したやつと同じものをrealmの中から探してくる
                RealmResults<Memo> results = realm.where(Memo.class)
                        .equalTo("title", adapter.getItem(position).title)
                        .equalTo("updateDate", adapter.getItem(position).updateDate)
                        .equalTo("content", adapter.getItem(position).content)
                        .findAll();//該当するもの全てを見つける
                realm.beginTransaction();
                results.deleteAllFromRealm();//全てを削除する
                realm.commitTransaction();//変更を更新
                items.remove(position);
                adapter.notifyDataSetChanged();//realmのデータが変更されたことを通知してリストに表示される内容を更新する
                return false;
            }

        });


    }

    public void setMemoList() {
        RealmResults<Memo> results = realm.where(Memo.class).findAll();
        items = realm.copyFromRealm(results);

        adapter = new MemoAdapter(this, R.layout.layout_item_memo, items);

        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setMemoList();
    }

    public void FloatingActionButton(View v) {
        Intent intent = new Intent(this, Create.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();

    }

}
