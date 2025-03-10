package com.example.recyclerviewsample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends
  RecyclerView.Adapter<RecyclerAdapter.GameViewHolder> {
  ArrayList<String> gameList;
  ArrayList<String> gameDetails;
  ArrayList<Integer> gameImages;
  Context context;

  public RecyclerAdapter(ArrayList<String> gameList,
                         ArrayList<String> gameDetails,
                         ArrayList<Integer> gameImages, Context context) {
    this.gameList = gameList;
    this.gameDetails = gameDetails;
    this.gameImages = gameImages;
    this.context = context;
  }

  @NonNull
  @Override
  public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                           int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(
      R.layout.card_design, parent, false);

    return new GameViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
    holder.txtGameName.setText(gameList.get(position));
    holder.txtGameDetails.setText(gameDetails.get(position));
    holder.imgGame.setImageResource(gameImages.get(position));
    holder.cardView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String mesg = "You clicked " + gameList.get(position) + " at position: " + position;
        Toast.makeText(context, mesg, Toast.LENGTH_SHORT).show();
      }
    });
  }

  @Override
  public int getItemCount() {
    return gameList.size();
  }

  public class GameViewHolder extends RecyclerView.ViewHolder {

    TextView txtGameName, txtGameDetails;
    ImageView imgGame;
    CardView cardView;

    public GameViewHolder(@NonNull View itemView) {
      super(itemView);

      txtGameName = itemView.findViewById(R.id.txt_title);
      txtGameDetails = itemView.findViewById(R.id.txt_desc);
      imgGame = itemView.findViewById(R.id.img_view);
      cardView = itemView.findViewById(R.id.card_view);
    }
  }
}
