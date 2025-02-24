package com.example.sampleviews2;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.CheckBox;
import android.widget.ToggleButton;

import androidx.appcompat.widget.ScrollingTabContainerView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

  SwitchCompat switchCompat;
  RadioGroup radioGroup;
  RadioButton radio1;
  RadioButton radio2;
  Spinner tvSpinner;
  ArrayAdapter tvAdapter;
  ToggleButton toggleButton;
  Button btnClickMe;
  DatePicker datePicker;
  TimePicker timePicker;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_main);

    switchCompat = findViewById(R.id.switch1);
    radioGroup = findViewById(R.id.radio_group);
    radio1 = findViewById(R.id.radioButton1);
    radio2 = findViewById(R.id.radioButton2);
    tvSpinner = findViewById(R.id.spinner_tv);
    toggleButton = findViewById(R.id.toggleButton);
    btnClickMe = findViewById(R.id.btn_shape);
    timePicker = findViewById(R.id.time_picker);
    datePicker = findViewById(R.id.date_picker);

    btnClickMe.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        displayTimeAndDate();
      }
    });

    tvAdapter = ArrayAdapter.createFromResource(this, R.array.tv_shows,
      android.R.layout.simple_spinner_item);
    tvAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    tvSpinner.setAdapter(tvAdapter);

    tvSpinner.setSelection(0,true);
    tvSpinner.setOnItemSelectedListener(
      new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
          String show = parent.getItemAtPosition(position).toString();
          Toast.makeText(MainActivity.this, "Show selected: " + show,
            Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
      });

    toggleButton.setOnCheckedChangeListener(
      new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
          Toast.makeText(MainActivity.this, "ToggleButton status: " + toggleButton.getText().toString(),
            Toast.LENGTH_SHORT).show();
        }
      });

    radioGroup.setOnCheckedChangeListener(
      new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
          if (checkedId == radio1.getId()) {
            Toast.makeText(MainActivity.this, "Radio 1 checked",
              Toast.LENGTH_SHORT).show();
          } else if (checkedId == radio2.getId()) {
            Toast.makeText(MainActivity.this, "Radio 2 checked",
              Toast.LENGTH_SHORT).show();
          }
        }
      });

    switchCompat.setOnCheckedChangeListener(
      new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
          if (isChecked) {
            Toast.makeText(MainActivity.this, "Switch is ON",
              Toast.LENGTH_SHORT).show();
          } else {
            Toast.makeText(MainActivity.this, "Switch is OFF",
              Toast.LENGTH_SHORT).show();
          }
        }
      });

//    switchCompat.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        if (switchCompat.isChecked()) {
//          Toast.makeText(MainActivity.this, "Switch is ON",
//            Toast.LENGTH_SHORT).show();
//        } else {
//          Toast.makeText(MainActivity.this, "Switch is OFF",
//            Toast.LENGTH_SHORT).show();
//        }
//      }
//    });

    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main),
      (v, insets) -> {
        Insets systemBars = insets.getInsets(
          WindowInsetsCompat.Type.systemBars());
        v.setPadding(systemBars.left, systemBars.top, systemBars.right,
          systemBars.bottom);
        return insets;
      });
  }

  private void displayTimeAndDate() {
    int hour, minute;
    hour = timePicker.getHour();
    minute = timePicker.getMinute();

    String time = String.format(Locale.getDefault(), "%02d:%02d",
      hour, minute);
    String date = String.format(Locale.getDefault(), "%02d/%02d/%04d",
      datePicker.getDayOfMonth(),
      datePicker.getMonth() + 1, //Month is zero based so adding 1
      datePicker.getYear());

    Toast.makeText(MainActivity.this,
      "time: " + time + ", date: " + date,
      Toast.LENGTH_SHORT).show();
  }
}