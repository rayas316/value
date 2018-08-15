package com.example.raysakakibara.memocamp;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.util.Log;

import java.util.List;

import android.app.AlertDialog.Builder;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.widget.ArrayAdapter;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    private ArrayAdapter<String> adapter;
    private ListView listView;
    public Realm realm;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1);

        realm = Realm.getDefaultInstance();
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Memo memo = (Memo) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("updateDate", memo.updateDate);
                startActivity(intent);
            }

        });
        listView.setOnItemLongClickListener
                (new AdapterView.OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent,
                                                   View view, int position, long id) {
                        ListView list = (ListView) parent;
                        String selectedItem = (String) list
                                .getItemAtPosition(position);

                        showDialogFragment(selectedItem);
                        return false;
                    }
                });

    }

    private void showDialogFragment(String selectedItem) {
        FragmentManager manager = getFragmentManager();
        DeleteDialog dialog = new DeleteDialog();
        dialog.setSelectedItem(selectedItem);

        dialog.show(manager, "dialog");
    }

    public static class DeleteDialog extends DialogFragment {


        private String selectedItem = null;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Delete entry.");
            builder.setMessage("Are you really?");
            builder.setPositiveButton("Yes I'm serious.",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity activity = (MainActivity) getActivity();
                            activity.removeItem(selectedItem);
                        }
                    });
            AlertDialog dialog = builder.create();
            return dialog;
        }

        public void setSelectedItem(String selectedItem) {
            this.selectedItem = selectedItem;
        }
    }

    private void removeItem(String selectedItem) {
        adapter.remove(selectedItem);
    }


    public void setMemoList() {
        RealmResults<Memo> results = realm.where(Memo.class).findAll();
        List<Memo> items = realm.copyFromRealm(results);

        MemoAdapter adapter = new MemoAdapter(this, R.layout.layout_item_memo, items);

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


