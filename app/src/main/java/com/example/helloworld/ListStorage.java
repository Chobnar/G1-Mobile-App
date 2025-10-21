package com.example.helloworld;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ListStorage {
    private static final String FILENAME = "lists.json";

    public static List<ShoppingList> load(Context c) {
        try (FileInputStream fis = c.openFileInput(FILENAME)) {
            byte[] b = new byte[fis.available()];
            fis.read(b);
            JSONArray a = new JSONArray(new String(b));
            List<ShoppingList> lists = new ArrayList<>();
            for (int i = 0; i < a.length(); i++) {
                JSONObject o = a.getJSONObject(i);
                ShoppingList s = new ShoppingList(o.getString("name"));
                JSONArray ia = o.optJSONArray("items");
                if (ia != null) for (int j = 0; j < ia.length(); j++) s.items.add(ia.getString(j));
                lists.add(s);
            }
            return lists;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static void save(Context c, List<ShoppingList> lists) {
        try (FileOutputStream fos = c.openFileOutput(FILENAME, Context.MODE_PRIVATE)) {
            JSONArray a = new JSONArray();
            for (ShoppingList sl : lists) {
                JSONObject o = new JSONObject();
                o.put("name", sl.name);
                JSONArray ia = new JSONArray();
                for (String it : sl.items) ia.put(it);
                o.put("items", ia);
                a.put(o);
            }
            fos.write(a.toString().getBytes());
        } catch (Exception ignored) {}
    }
}
