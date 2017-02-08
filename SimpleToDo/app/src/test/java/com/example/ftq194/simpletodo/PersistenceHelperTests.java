package com.example.ftq194.simpletodo;

/**
 * Created by ftq194 on 2/7/17.
 */

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PersistenceHelperTests {

    @Test
    public void writeAndLoadFileTest() {
        PersistenceHelper persistenceHelper = new PersistenceHelper(null);
        persistenceHelper.type = PersistenceHelper.Type.FILE;
        ArrayList<String> list = new ArrayList<String>();
        list.add("first");
        list.add("second");
        list.add("third");
        persistenceHelper.persistItems(list);

        ArrayList<String> loadedList = persistenceHelper.loadItems();
        assertNotNull(loadedList);
        assertEquals(loadedList.size(), list.size());

        for (int index = 0; index < list.size(); ++index) {
            String item = list.get(index);
            assertTrue(loadedList.contains(item));
        }
    }
}
