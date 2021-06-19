package com.example.mainproject;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainproject.R;
import com.example.mainproject.DatabaseHandler;
import com.example.mainproject.Item;
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.text.MessageFormat;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {


    private Context context;
    private List<Item> items;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private LayoutInflater inflater;

    public RecyclerViewAdapter(Context context, List<Item> items){
        this.context=context;
        this.items=items;
    }
    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row,parent,false);

        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {

        Item item=items.get(position);
        holder.item_name.setText(MessageFormat.format("TASK: {0}", item.getItem()));
        holder.item_quantity.setText(MessageFormat.format("DURATION: {0}", String.valueOf(item.getQuantity())));
        holder.item_color.setText(MessageFormat.format("CONTACT PERSON: {0}", item.getColor()));
        holder.item_date.setText(MessageFormat.format("ADDED ON: {0}", item.getDate()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView item_name;
        public TextView item_size;
        public TextView item_quantity;
        public TextView item_color;
        public TextView item_date;
        public Button editButton;
        public Button deleteButton;
        public int id;
        public ViewHolder(@NonNull View itemView,Context ctx) {
            super(itemView);
            context=ctx;

            item_name=itemView.findViewById(R.id.item_name);
            item_color=itemView.findViewById(R.id.item_color);
            item_quantity=itemView.findViewById(R.id.item_quantity);
            item_date=itemView.findViewById(R.id.item_date);
            item_size=itemView.findViewById(R.id.item_size);

            editButton=itemView.findViewById(R.id.editbtn);
            deleteButton=itemView.findViewById(R.id.deletebtn);

            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            switch (v.getId()){
                case R.id.editbtn:
                    position=getAdapterPosition();
                    updateItem(items.get(position));
                    break;
                case R.id.deletebtn:
                    position=getAdapterPosition();
                    deleteItem(items.get(position).getId());
                    break;

            }
        }

        private void updateItem(final Item item) {

            builder=new AlertDialog.Builder(context);
            inflater=LayoutInflater.from(context);
            final View view=inflater.inflate(R.layout.popup,null);

            final EditText babyItem=view.findViewById(R.id.babyItem);
            final EditText itemQuantity=view.findViewById(R.id.itemQuantity);
            final EditText itemColor=view.findViewById(R.id.itemColor);
            TextView title=view.findViewById(R.id.title);
            Button update=view.findViewById(R.id.saveButton);
            final DatabaseHandler handler=new DatabaseHandler(context);

            title.setText("Update item");
            babyItem.setText(item.getItem());
            itemQuantity.setText(String.valueOf(item.getQuantity()));
            itemColor.setText(item.getColor());
            update.setText("Update");
            builder.setView(view);
            dialog=builder.create();
            dialog.show();

            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Item updatedItem=new Item();
                    item.setQuantity(Integer.parseInt(itemQuantity.getText().toString().trim()));
                    item.setItem(babyItem.getText().toString().trim());
                    item.setColor(itemColor.getText().toString().trim());


                    if(!babyItem.getText().toString().isEmpty() &&
                            itemQuantity.getText().toString()!=""&&
                            itemColor.getText().toString()!=""){

                        handler.updateItem(item);
                        notifyItemChanged(getAdapterPosition(),item);
                        dialog.dismiss();

                    } else{
                        Snackbar.make(view,"All fields are mandatory",Snackbar.LENGTH_SHORT).show();
                    }
                }
            });


        }

        public void deleteItem(final int id){

            builder=new AlertDialog.Builder(context);
            inflater=LayoutInflater.from(context);
            View view=inflater.inflate(R.layout.confirmation_popup,null);


            builder.setView(view);

            dialog=builder.create();
            dialog.show();

            Button no=view.findViewById(R.id.no_btn);
            Button yes=view.findViewById(R.id.yes_btn);

            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();
                }
            });
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHandler handler=new DatabaseHandler(context);
                    handler.deleteItem(id);
                    items.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    dialog.dismiss();
                }
            });

        }
    }
}
