package com.kursovaya;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Timer;

public class UpdateActivity extends AppCompatActivity {

    EditText title_input, distance_input, time_input;
    Button update_button, delete_button, timer_button;

    String id, title, distance, time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        title_input = findViewById(R.id.title_input2);
        distance_input = findViewById(R.id.distance_input2);
        time_input = findViewById(R.id.time_input2);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);
        timer_button = findViewById(R.id.timer_button);

        //Сначала вызываем это
        getAndSetIntentData();

        ActionBar ab = getSupportActionBar();
        assert ab != null; {
            ab.setTitle(title);
        }

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Потом вызываем это
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                title = title_input.getText().toString().trim();
                distance = distance_input.getText().toString().trim();
                time = time_input.getText().toString().trim();
                myDB.updateData(id, title, distance, time);
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

        timer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateActivity.this, TimerActivity.class);
                startActivity(intent);
            }
        });

    }

    void getAndSetIntentData(){
        if (getIntent().hasExtra("id") && getIntent().hasExtra("title") &&
                getIntent().hasExtra("distance") && getIntent().hasExtra("time")){
            //Получение данных из Intent
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            distance = getIntent().getStringExtra("distance");
            time = getIntent().getStringExtra("time");

            //Настройка данных из Intent
            title_input.setText(title);
            distance_input.setText(distance);
            time_input.setText(time);
    }else {
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Удалить " + title + " ?");
        builder.setMessage("Вы уверены, что хотите удалить " + title + " ?");
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteOneRow(id);
                finish();
            }
        });
        builder.setNegativeButton("Нет", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

}