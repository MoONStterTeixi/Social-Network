package com.moonstterinc.epidemicgames.epidemicgames;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private List<ListItem> listItems;
    private Context context;

    public MyAdapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;

        if(DataClass.myAdapter == 0){
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent,false);

        }else{
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item2, parent,false);
        }

        return new ViewHolder(v);
        //return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ListItem listItem = listItems.get(position);

            //Añadir texto en los Textview de list_item
            holder.textViewHead.setText(listItem.getHead());
            holder.textViewAbout.setText(listItem.getAbout());
            holder.textViewDate.setText(listItem.getDate());

            //Obtener la imagen vía url
            Picasso.get()
                    .load(listItem.getImageUrl())
                    .into(holder.imageView);

            //Obtener el onClick de las tarjetas
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Pasar datos de un intent a otro
                    Intent intent = new Intent(context, newsComplet.class);
                    intent.putExtra("head",  listItem.getHead());
                    intent.putExtra("desc",   listItem.getDesc());
                    intent.putExtra("tag",   listItem.getTag());
                    intent.putExtra("date",   listItem.getDate());
                    intent.putExtra("text",   listItem.getText());
                    intent.putExtra("img",   listItem.getImageUrl());
                    context.startActivity(intent);
                    //Toast.makeText(context, "Has Clicado en " + listItem.getHead(), Toast.LENGTH_LONG).show();
                }
            });

    }


    @Override
    public int getItemCount() {
        return listItems.size();
        //return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        //Declrarar las variables para uso posterios
        public TextView textViewHead;
        public TextView textViewAbout;
        public TextView textViewTag;
        public TextView textViewDate;
        public TextView textViewText;
        public ImageView imageView;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewHead = itemView.findViewById(R.id.textViewHead);
            textViewAbout = itemView.findViewById(R.id.textViewAbout);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            imageView = itemView.findViewById(R.id.imageView);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }

}
