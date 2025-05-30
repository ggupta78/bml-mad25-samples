package com.example.notesapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {
  @Insert
  void insert(Note note);

  @Update
  void update(Note note);

  @Delete
  void delete(Note note);

  @Query("Select * from note_table order by id asc")
  LiveData<List<Note>> getAllNotes();
}
