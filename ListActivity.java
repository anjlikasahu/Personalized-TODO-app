package com.example.mainproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mainproject.DatabaseHandler;
import com.example.mainproject.Item;
import com.example.mainproject.RecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    private List<Item> items;
    private DatabaseHandler handler;
    private Button saveButton;
    private EditText itemQuantity;
    private EditText itemColor;
    private EditText babyItem;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView=findViewById(R.id.recyclerview);

        handler=new DatabaseHandler(this);
        fab=findViewById(R.id.fab);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        items=new ArrayList<>();

        //get all baby items
        items=handler.getAllItems();

        recyclerViewAdapter=new RecyclerViewAdapter(this,items);
        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerViewAdapter.notifyDataSetChanged();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPopupDialog();
            }
        });

    }
    private void saveItem(View view) {

        //saving baby item---todo
        Item item=new Item();

        item.setColor(itemColor.getText().toString().trim());
        item.setItem(babyItem.getText().toString().trim());
        item.setQuantity(Integer.parseInt(itemQuantity.getText().toString().trim()));

        handler.insert(item);

        Snackbar.make(view,"Task added",Snackbar.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();

                startActivity(new Intent(ListActivity.this, ListActivity.class));
                finish();

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
    public void logout(View view)
    {
        Intent in = new Intent(this, LoginActivity.class);
        startActivity(in);


    }
}
