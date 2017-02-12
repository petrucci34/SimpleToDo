package com.example.ftq194.simpletodo;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements EditItemDialog.EditItemDialogListener {
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;

    private int mSelectedItemPosition = 0;
    private final int REQUEST_CODE = 20;
    private PersistenceHelper mPersistenceHelper;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPersistenceHelper = new PersistenceHelper(getApplicationContext());

        lvItems = (ListView)findViewById(R.id.lvItems);
        items = mPersistenceHelper.loadItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);

        setUpListViewListener();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabaseReference = database.getReference("items");
        mDatabaseReference.setValue("Hello, World!");
        mDatabaseReference.child("users").setValue("hello");
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText)findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        mPersistenceHelper.persistItems(items);
    }

    private void setUpListViewListener() {
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mSelectedItemPosition = position;
                        showEditDialog(items.get(position));
                    }
                }
        );

        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter, View item, int position, long id) {
                        items.remove(position);
                        itemsAdapter.notifyDataSetChanged();
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
        itemsAdapter.notifyDataSetChanged();
    }

    private void showEditDialog(String item) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        EditItemDialog editItemDialog = EditItemDialog.newInstance(item);
        editItemDialog.show(fragmentManager, "fragment_edit_item_dialog");
    }
}
