package com.example.helloworld;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<ShoppingList> lists;
    ArrayAdapter<ShoppingList> adapter;

    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ListView lv = findViewById(R.id.listView);
        FloatingActionButton fab = findViewById(R.id.fabAdd);

        fab.setOnClickListener(v -> {
            EditText et = new EditText(this);
            new AlertDialog.Builder(this)
                    .setTitle("Create list")
                    .setView(et)
                    .setPositiveButton("OK", (d, w) -> {
                        lists.add(new ShoppingList(et.getText().toString()));
                        adapter.notifyDataSetChanged();
                        ListStorage.save(this, lists);
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        lists = ListStorage.load(this);
        adapter = new ArrayAdapter<ShoppingList>(this, R.layout.shopping_list_row, R.id.listName, lists) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = convertView;
                if (v == null) v = getLayoutInflater().inflate(R.layout.shopping_list_row, parent, false);
                TextView name = v.findViewById(R.id.listName);
                ImageButton del = v.findViewById(R.id.btnDelete);
                final int pos = position;
                ShoppingList sl = getItem(pos);
                name.setText(sl.name);
                del.setOnClickListener(btn -> {
                    lists.remove(pos);
                    notifyDataSetChanged();
                    ListStorage.save(MainActivity.this, lists);
                });
                return v;
            }
        };
        lv.setAdapter(adapter);
    }
}
