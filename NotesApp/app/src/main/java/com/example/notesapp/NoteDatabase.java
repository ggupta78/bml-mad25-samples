package com.example.notesapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

  private static NoteDatabase instance;

  public abstract NoteDao noteDao();

  public static synchronized NoteDatabase getInstance(Context context) {
    if (instance == null) {
      instance = Room.databaseBuilder(context.getApplicationContext(),
        NoteDatabase.class, "note_database")
        .fallbackToDestructiveMigration()
        .addCallback(roomCallback)
        .build();
    }

    return instance;
  }

  private static RoomDatabase.Callback roomCallback = new Callback() {
    @Override
    public void onCreate(@NonNull SupportSQLiteDatabase db) {
      super.onCreate(db);

      NoteDao noteDao = instance.noteDao();

      ExecutorService executor = Executors.newSingleThreadExecutor();
      executor.execute(new Runnable() {
        @Override
        public void run() {
          noteDao.insert(new Note("Note 1", "Desc 1"));
          noteDao.insert(new Note("Note 2", "Desc 2"));
          noteDao.insert(new Note("Note 3", "Desc 3"));
        }
      });
    }
  };
}
