package com.example.todoapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

  EditText txtAdd;
  Button btnAdd;
  ListView listView;
  ArrayList<String> items;
  ArrayAdapter<String> adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_main);

    txtAdd = findViewById(R.id.txt_add);
    btnAdd = findViewById(R.id.btn_add);
    listView = findViewById(R.id.list_view);

//    items = new ArrayList<String>();
    items = FileHelper.readList(this);
    adapter = new ArrayAdapter<String>(this,
      android.R.layout.simple_list_item_1, android.R.id.text1, items);
    listView.setAdapter(adapter);

    btnAdd.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String task = txtAdd.getText().toString();
        items.add(task);
        txtAdd.setText("");
        FileHelper.writeList(items, MainActivity.this);
        adapter.notifyDataSetChanged();
      }
    });

    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position,
                              long id) {
        //Create alert
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("Delete");
        alert.setMessage("Do you want to delete this item?");
        alert.setCancelable(false);

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
          }
        });

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            items.remove(position);
            adapter.notifyDataSetChanged();
            FileHelper.writeList(items, MainActivity.this);
          }
        });

        AlertDialog alertDialog = alert.create();
        alertDialog.show();
      }
    });

    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main),
      (v, insets) -> {
        Insets systemBars = insets.getInsets(
          WindowInsetsCompat.Type.systemBars());
        v.setPadding(systemBars.left, systemBars.top, systemBars.right,
          systemBars.bottom);
        return insets;
      });
  }
}