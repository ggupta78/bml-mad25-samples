package com.example.firebasesampleproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

  EditText txtName;
  Button btnAddName;
  TextView txtDisplayUsername;

  FirebaseDatabase database = FirebaseDatabase.getInstance();
  DatabaseReference referenceUsers = database.getReference().child("employees");

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_main);

    txtName = findViewById(R.id.txt_name);
    btnAddName = findViewById(R.id.btn_add_name);
    txtDisplayUsername = findViewById(R.id.txt_display_username);

    btnAddName.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String username = txtName.getText().toString();
        referenceUsers.child("100").child("username").setValue(username);
      }
    });

    referenceUsers.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        String updatedUsername = (String) snapshot.child("100").child("username").getValue();
        txtDisplayUsername.setText(updatedUsername);
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {

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