package com.example.recyclerviewsample;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

  RecyclerView recyclerView;

  ArrayList<String> gameList = new ArrayList<String>();
  ArrayList<String> gameDetails = new ArrayList<String>();
  ArrayList<Integer> gameImages = new ArrayList<Integer>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_main);

    recyclerView = findViewById(R.id.recycler_view);
    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

    gameList.add("Candy Crush");
    gameList.add("Subway Surfer");
    gameList.add("Dota 2");
    gameList.add("Candy Crush");
    gameList.add("Subway Surfer");
    gameList.add("Dota 2");

    gameDetails.add("A free-to-play Tile Matching game");
    gameDetails.add("A free-to-play endless runner");
    gameDetails.add("A free multi-player online battle arena");
    gameDetails.add("A free-to-play Tile Matching game");
    gameDetails.add("A free-to-play endless runner");
    gameDetails.add("A free multi-player online battle arena");

    gameImages.add(R.drawable.candy_crush);
    gameImages.add(R.drawable.subway_surfer);
    gameImages.add(R.drawable.dota2);
    gameImages.add(R.drawable.candy_crush);
    gameImages.add(R.drawable.subway_surfer);
    gameImages.add(R.drawable.dota2);

    RecyclerAdapter adapter = new RecyclerAdapter(gameList, gameDetails,
      gameImages, this);
    recyclerView.setAdapter(adapter);

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