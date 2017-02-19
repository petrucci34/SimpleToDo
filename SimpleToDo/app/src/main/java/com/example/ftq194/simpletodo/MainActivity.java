package com.example.ftq194.simpletodo;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements EditItemDialog.EditItemDialogListener {
    ArrayList<String> items;
    ArrayAdapter<String> mItemsAdapter;
    ListView listView;

    private int mSelectedItemPosition = 0;
    private final int REQUEST_CODE = 20;
    private PersistenceHelper mPersistenceHelper;
    private final String LOG_TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.lvItems);
        mPersistenceHelper = new PersistenceHelper(getApplicationContext());
        items = mPersistenceHelper.loadItems();
        mItemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(mItemsAdapter);

        setUpListViewListener();
    }

    public void onAddItem(View view) {
        EditText editText = (EditText)findViewById(R.id.etNewItem);
        String itemText = editText.getText().toString();
        mItemsAdapter.add(itemText);
        editText.setText("");
        mPersistenceHelper.persistItems(items);
    }

    private void setUpListViewListener() {
        listView.setOnItemClickListener(
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mSelectedItemPosition = position;
                    showEditDialog(items.get(position));
                }
            }
        );

        listView.setOnItemLongClickListener(
            new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapter, View item, int position, long id) {
                    items.remove(position);
                    mItemsAdapter.notifyDataSetChanged();
                    mPersistenceHelper.persistItems(items);

                    return true;
                }
            }
        );
    }

    @Override
    public void onFinishEditDialog(String inputText) {
        items.set(mSelectedItemPosition, inputText);
        mPersistenceHelper.persistItems(items);
        mItemsAdapter.notifyDataSetChanged();
    }

    private void showEditDialog(String item) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        EditItemDialog editItemDialog = EditItemDialog.newInstance(item);
        editItemDialog.show(fragmentManager, "fragment_edit_item_dialog");
    }
}
