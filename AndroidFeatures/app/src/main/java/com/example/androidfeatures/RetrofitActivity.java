package com.example.androidfeatures;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitActivity extends AppCompatActivity {

  TextView txtUserId, txtId, txtTitle, txtBody;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_retrofit);

    txtUserId = findViewById(R.id.txt_user_id);
    txtId = findViewById(R.id.txt_id);
    txtTitle = findViewById(R.id.txt_title);
    txtBody = findViewById(R.id.txt_body);

    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/")
      .addConverterFactory(GsonConverterFactory.create())
      .build();

    RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
    Call<List<Post>> call = retrofitApi.getPosts();

    call.enqueue(new Callback<List<Post>>() {
      @Override
      public void onResponse(Call<List<Post>> call,
                             Response<List<Post>> response) {
        if (!response.isSuccessful()) {
          txtUserId.setText("Error");
          txtId.setText("Error");
          txtTitle.setText("Error");
          txtBody.setText("Error");
        } else {
          List<Post> data = response.body();
          txtUserId.setText("" + data.get(2).getUserId());
          txtId.setText("" + data.get(2).getId());
          txtTitle.setText("" + data.get(2).getTitle());
          txtBody.setText("" + data.get(2).getBody());
        }
      }

      @Override
      public void onFailure(Call<List<Post>> call, Throwable throwable) {
        Toast.makeText(RetrofitActivity.this, "Retrofit API call failed!!", Toast.LENGTH_SHORT).show();
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