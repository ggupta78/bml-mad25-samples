package com.example.androidfeatures;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EmailActivity extends AppCompatActivity {

  EditText editEmail, editSubject, editBody;
  Button btnSendEmail;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_email);

    editEmail = findViewById(R.id.edit_email);
    editSubject = findViewById(R.id.edit_subject);
    editBody = findViewById(R.id.edit_body);
    btnSendEmail = findViewById(R.id.btn_send_email);

    btnSendEmail.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String userAddress = editEmail.getText().toString();
        String userSubject = editSubject.getText().toString();
        String userBody = editBody.getText().toString();

        sendEmail(userAddress, userSubject, userBody);
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

  public void sendEmail(String userAddress, String userSubject, String userBody) {
    String[] userAddresses = {userAddress};

    Intent emailIntent = new Intent(Intent.ACTION_SEND);

    emailIntent.setData(Uri.parse("mailto:"));
    emailIntent.setType("text/plain");

    emailIntent.putExtra(Intent.EXTRA_EMAIL, userAddresses);
    emailIntent.putExtra(Intent.EXTRA_SUBJECT, userSubject);
    emailIntent.putExtra(Intent.EXTRA_TEXT, userBody);

    startActivity(Intent.createChooser(emailIntent, "Select an Email App"));

  }
}