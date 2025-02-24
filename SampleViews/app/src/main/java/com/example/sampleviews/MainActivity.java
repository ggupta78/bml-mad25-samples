package com.example.sampleviews;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

  TextView txtView;
  EditText txtName;
  Button btnClick;
  ImageView imgView;
  ImageButton imgBtn;
  LinearLayout linearLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_main);

    txtView = findViewById(R.id.txt_view);
    txtName = findViewById(R.id.txt_name);
    btnClick = findViewById(R.id.btn_click);
    imgView = findViewById(R.id.img_view);
    imgBtn = findViewById(R.id.img_btn);
    linearLayout = findViewById(R.id.linear_layout);

    btnClick.setOnClickListener(v -> {
      String name = txtName.getText().toString();
      txtView.setText(name);
      Toast.makeText(this, "Click me got clicked", Toast.LENGTH_SHORT).show();
    });

    imgBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (linearLayout.getOrientation() == LinearLayout.HORIZONTAL) {
          linearLayout.setOrientation(LinearLayout.VERTICAL);
          Toast.makeText(MainActivity.this,
            "Linear Layout became Vertical", Toast.LENGTH_SHORT).show();
          Log.i("myTag", "LL became vertical");
        } else {
          linearLayout.setOrientation(LinearLayout.HORIZONTAL);
          Toast.makeText(MainActivity.this,
            "Linear Layout became Horizontal", Toast.LENGTH_SHORT).show();
          Log.i("myTag", "LL became horizontal");
        }

        Log.w("myTag", "Linear Layout Orientation Changed");
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