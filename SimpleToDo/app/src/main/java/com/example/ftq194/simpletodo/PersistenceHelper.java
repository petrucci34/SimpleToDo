package com.example.ftq194.simpletodo;

import android.content.Context;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;


/**
 * Created by Korhan on 2/5/17.
 */

public class PersistenceHelper {
    private final String filename = "ToDo.txt";
    private DatabaseReference mDatabaseReference = null;

    public enum Type {
        FILE,
        DATABASE
    }
    public Type type = PersistenceHelper.Type.DATABASE;
    public Context context;

    public PersistenceHelper(Context context) {
        this.context = context;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabaseReference = database.getReference();
    }

    public ArrayList<String> loadItems() {
        switch (type) {
            case FILE:
                return loadItemsFromFile();
            case DATABASE:
                return new ArrayList<String>();
            default:
                break;
        }
        return new ArrayList<String>();
    }

    public void persistItems(ArrayList<String> items) {
        switch (type) {
            case FILE:
                writeItems(items);
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
//        for (for int index = 0; index < items.size(); ++index) {
            mDatabaseReference.setValue(items);
//        }
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
        File toDoFile = new File(filesDirectory, filename);
        return toDoFile;
    }
}
