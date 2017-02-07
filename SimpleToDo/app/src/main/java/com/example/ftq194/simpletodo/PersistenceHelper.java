package com.example.ftq194.simpletodo;

import android.content.Context;

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

    public Type type = PersistenceHelper.Type.FILE;
    public Context context;

    public PersistenceHelper(Context context) {
        this.context = context;
    }

    public ArrayList<String> loadItems() {
        switch (type) {
            case FILE:
                return loadItemsFromFile();
            case DATABASE:
                return null;
            default:
                break;
        }
        return null;
    }

    public void persistItems(ArrayList<String> items) {
        switch (type) {
            case FILE:
                writeItems(items);
            case DATABASE:
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
        File toDoFile = new File(filesDirectory, "ToDo.txt");
        return toDoFile;
    }
}
