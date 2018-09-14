package ray.io.raysakakibara.prive;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    public ListView listView;
    public Realm realm;
    CardAdapter adapter;
    List<Card> items2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ray.io.raysakakibara.prive.R.layout.activity_main);

        realm = Realm.getDefaultInstance();
        listView = (ListView) findViewById(ray.io.raysakakibara.prive.R.id.listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Card card = (Card) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("updateDate", card.updateDate);
                startActivity(intent);
            }

        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final String[] items = {"item_0", "item_1", "item_2"};
                int defaultItem = 0; // デフォルトでチェックされているアイテム
                final List<Integer> checkedItems = new ArrayList<>();
                checkedItems.add(defaultItem);
                new AlertDialog.Builder(getActivity())
                        .setTitle("使用終了")
                        .setSingleChoiceItems(items, defaultItem, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int idx) {
                                if (idx == 0) {

                                } else if (idx == 1) {

                                } else if (idx == 2) {
                                    RealmResults<Card> results = realm.where(Card.class)
                                            .equalTo("title", adapter.getItem(position).title)
                                            .equalTo("updateDate", adapter.getItem(position).updateDate)
                                            .equalTo("content", adapter.getItem(position).content)
                                            .findAll();
                                    realm.beginTransaction();
                                    results.deleteAllFromRealm();
                                    realm.commitTransaction();
                                    items2.remove(position);
                                    adapter.notifyDataSetChanged();
                                    return false;
                                }
                                checkedItems.clear();
                                checkedItems.add(idx);
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!checkedItems.isEmpty()) {
                                    Log.d("checkedItem:", "" + checkedItems.get(0));
                                }
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();

            }

        });


    }

    public void setMemoList() {
        RealmResults<Card> results = realm.where(Card.class).findAll();
        items2 = realm.copyFromRealm(results);

        adapter = new CardAdapter(this, ray.io.raysakakibara.prive.R.layout.layout_item_memo, items2);

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
