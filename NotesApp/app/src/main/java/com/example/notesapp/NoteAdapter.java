package com.example.notesapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

  private List<Note> notes = new ArrayList<>();

  OnItemClickListener listener;

  @NonNull
  @Override
  public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                       int viewType) {
    View view = LayoutInflater.from(parent.getContext())
      .inflate(R.layout.note_item, parent, false);

    return new NoteHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
    Note currentNote = notes.get(position);
    holder.txtTitle.setText(currentNote.getTitle());
    holder.txtDesc.setText(currentNote.getDescription());
  }

  @Override
  public int getItemCount() {
    return notes.size();
  }

  public Note getNote(int position) {
    return notes.get(position);
  }

  public void setNotes(List<Note> notes) {
    this.notes = notes;
    notifyDataSetChanged();
  }

  class NoteHolder extends RecyclerView.ViewHolder {
    TextView txtTitle, txtDesc;

    public NoteHolder(@NonNull View itemView) {
      super(itemView);

      txtTitle = itemView.findViewById(R.id.txt_title);
      txtDesc = itemView.findViewById(R.id.txt_desc);

      itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          int position = getAdapterPosition();
          if (listener != null && position != RecyclerView.NO_POSITION) {
            listener.onItemClick(notes.get(position));
          }
        }
      });
    }
  }

  public interface OnItemClickListener {
    void onItemClick(Note note);
  }

  public void setOnItemClickListener(OnItemClickListener listener) {
    this.listener = listener;
  }
}
