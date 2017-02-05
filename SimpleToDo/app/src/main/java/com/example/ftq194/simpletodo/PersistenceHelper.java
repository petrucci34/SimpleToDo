package com.example.ftq194.simpletodo;

import android.widget.ArrayAdapter;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by Korhan on 2/5/17.
 */

public class PersistenceHelper {
    public enum Type {
        FILE,
        DATABASE
    }

    public Type type = PersistenceHelper.Type.DATABASE;
    private static PersistenceHelper ourInstance = new PersistenceHelper();

    public static PersistenceHelper getInstance() {
        return ourInstance;
    }

    private PersistenceHelper() {
    }

    public void loadItems() {
        switch (type) {
            case FILE:
                loadItemsFromFile();
                break;
            case DATABASE:
                break;
            default:
                break;
        }
    }

    private ArrayList<String> loadItemsFromFile() {
        File toDoFile = getToDoFile();
        ArrayList<String> items = nil;

        try {
            items = new ArrayList<String>(FileUtils.readLines(toDoFile));
        } catch (IOException exception) {
            items = new ArrayList<String>();
            exception.printStackTrace();
        }

        return items;
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
        File filesDirectory = getFilesDir();
        File toDoFile = new File(filesDirectory, "ToDo.txt");
        return toDoFile;
    }
}
