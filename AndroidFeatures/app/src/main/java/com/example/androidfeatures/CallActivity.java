package com.example.androidfeatures;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CallActivity extends AppCompatActivity {
  EditText editCallNumber;
  Button btnCallNumber;
  String userNumber;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_call);

    editCallNumber = findViewById(R.id.edit_call_number);
    btnCallNumber = findViewById(R.id.btn_call);

    btnCallNumber.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        userNumber = editCallNumber.getText().toString();

        checkCallPermission();
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

  public void checkCallPermission() {
    if (ContextCompat.checkSelfPermission(CallActivity.this,
      Manifest.permission.CALL_PHONE)
      != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(CallActivity.this,
        new String[] {Manifest.permission.CALL_PHONE}, 100);
    } else {
      callPhoneNumber();
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode,
                                         @NonNull String[] permissions,
                                         @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    if (requestCode == 100 && grantResults.length > 0
      && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
      callPhoneNumber();
    }
  }

  public void callPhoneNumber() {
    Intent callIntent = new Intent(Intent.ACTION_CALL);
    callIntent.setData(Uri.parse("tel:" + userNumber));
    startActivity(callIntent);
  }
}