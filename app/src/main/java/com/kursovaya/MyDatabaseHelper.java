package com.kursovaya;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Kursovaya.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "runs";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "run_title";
    private static final String COLUMN_DISTANCE = "run_distance";
    private static final String COLUMN_TIME = "run_time";

    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_DISTANCE + " TEXT, " +
                COLUMN_TIME + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addRun(String title, String distance, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_DISTANCE, distance);
        cv.put(COLUMN_TIME, time);
        long result = db.insert (TABLE_NAME, null, cv);
        if (result == -1){
            Toast.makeText(context, "Заполнены не все поля!", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Забег успешно добавлен!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateData(String row_id, String title, String distance, String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_DISTANCE, distance);
        cv.put(COLUMN_TIME, time);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Заполните все поля!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Успешно изменено!", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if (result == -1){
            Toast.makeText(context, "Ошибка удаления!", Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(context, "Успешно удалено!", Toast.LENGTH_SHORT).show();
    }

    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(" DELETE FROM " + TABLE_NAME);
    }
}
