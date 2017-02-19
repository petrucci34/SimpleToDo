package com.example.ftq194.simpletodo;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by Korhan on 2/5/17.
 */

public class PersistenceHelper {
    private final String LIST_NAME = "ToDo";
    private final String FILENAME = LIST_NAME + ".txt";
    private DatabaseReference mDatabaseReference = null;
    private ArrayList<String> mLoadedList = new ArrayList<String>();
    private ArrayAdapter<String> mArrayAdapter = null;

    public enum Type {
        FILE,
        DATABASE
    }
    public Type type = PersistenceHelper.Type.FILE;
    public Context context;

    public PersistenceHelper(Context context, ArrayAdapter<String> arrayAdapter) {
        this.context = context;
        mArrayAdapter = arrayAdapter;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabaseReference = database.getReference();

        mDatabaseReference.child(LIST_NAME).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mLoadedList = (ArrayList<String>) dataSnapshot.getValue();
                        mArrayAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    public ArrayList<String> loadItems() {
        switch (type) {
            case FILE:
                return loadItemsFromFile();
            case DATABASE:
                return mLoadedList;
            default:
                break;
        }
        return new ArrayList<String>();
    }

    public void persistItems(ArrayList<String> items) {
        switch (type) {
            case FILE:
                writeItems(items);
                break;
            case DATABASE:
                writeToDatabase(items);
                break;
            default:
                break;
        }
    }

    private ArrayList<String> loadItemsFromFile() {
        File toDoFile = getToDoFile();
        ArrayList<String> items = null;

        try {
            items = new ArrayList<String>(FileUtils.readLines(toDoFile));
        } catch (IOException exception) {
            items = new ArrayList<String>();
            exception.printStackTrace();
        }

        return items;
    }

    private void writeToDatabase(ArrayList<String> items) {
        mDatabaseReference.child(LIST_NAME).setValue(items);
    }

    private void writeItems(ArrayList<String> items) {
        File toDoFile = getToDoFile();

        try {
            FileUtils.writeLines(toDoFile, items);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private File getToDoFile() {
        File filesDirectory = context.getFilesDir();
        File toDoFile = new File(filesDirectory, FILENAME);
        return toDoFile;
    }
}
