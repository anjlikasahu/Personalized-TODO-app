package com.example.mainproject;


import android.content.Intent;
import android.os.Bundle;

import com.example.mainproject.DatabaseHandler;
import com.example.mainproject.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Button saveButton;
    private EditText itemQuantity;
    private EditText itemColor;
    private EditText babyItem;
    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databaseHandler=new DatabaseHandler(HomeActivity.this);

        List<Item> items=databaseHandler.getAllItems();

        for (Item item:items){
            Log.d("Task name with date ",item.getItem()+"----"+item.getDate());
        }



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                createPopupDialog();
            }
        });
        byPassActivity();
    }

    private void byPassActivity() {
        if(databaseHandler.getCount()>0){
            startActivity(new Intent(HomeActivity.this,ListActivity.class));
            finish();
        }
    }

    private void saveItem(View view) {

        //saving baby item---todo
        Item item=new Item();

        item.setColor(itemColor.getText().toString().trim());
        item.setItem(babyItem.getText().toString().trim());
        item.setQuantity(Integer.parseInt(itemQuantity.getText().toString().trim()));


        databaseHandler.insert(item);

        Snackbar.make(view,"Task added",Snackbar.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();

                startActivity(new Intent(HomeActivity.this, ListActivity.class));

            }
        },1200);

    }

    //**for creating popup dialog**
    public void createPopupDialog(){

        builder=new AlertDialog.Builder(this);
        View view=getLayoutInflater().inflate(R.layout.popup,null);
        babyItem=view.findViewById(R.id.babyItem);
        itemColor=view.findViewById(R.id.itemColor);
        itemQuantity=view.findViewById(R.id.itemQuantity);
        saveButton=view.findViewById(R.id.saveButton);

        builder.setView(view);
        dialog=builder.create();
        dialog.show();


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!itemColor.getText().toString().trim().isEmpty() &&
                        !babyItem.getText().toString().trim().isEmpty() &&
                        !itemQuantity.getText().toString().trim().isEmpty())
                {
                    saveItem(v);
                } else {
                    Snackbar.make(v,"All fields are mandatory",Snackbar.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
