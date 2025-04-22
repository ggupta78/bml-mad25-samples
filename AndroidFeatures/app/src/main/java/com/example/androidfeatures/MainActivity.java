package com.example.androidfeatures;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

  EditText editMessage, editNumber;
  Button btnSend;

  String userMessage, userNumber;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_main);

    editMessage = findViewById(R.id.edit_message);
    editNumber = findViewById(R.id.edit_number);
    btnSend = findViewById(R.id.btn_send);

    btnSend.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        userMessage = editMessage.getText().toString();
        userNumber = editNumber.getText().toString();

        checkPermissions();
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

  public void checkPermissions() {
    if (ContextCompat.checkSelfPermission(this,
      Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(this,
        new String[] {Manifest.permission.SEND_SMS}, 1);
    } else {
      sendSMS();
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode,
                                         @NonNull String[] permissions,
                                         @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    if (requestCode == 1 && grantResults.length > 0
      && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
      sendSMS();
    }
  }

  public void sendSMS() {
    SmsManager smsManager = SmsManager.getDefault();
    smsManager.sendTextMessage(userNumber, null, userMessage, null, null);

    Toast.makeText(this, "SMS sent!", Toast.LENGTH_SHORT).show();
  }
}