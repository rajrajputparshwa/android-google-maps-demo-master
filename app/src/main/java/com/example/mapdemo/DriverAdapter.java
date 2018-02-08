package com.example.mapdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;

/**
 * Created by admin on 12/5/2017.
 */
public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.ViewHolder> {
    Context context;
    ArrayList<Chat> array_list = new ArrayList<>();
    Pref_Master pref_master;

    public DriverAdapter(Context context, ArrayList<Chat> array_list) {
        this.context = context;
        this.array_list = array_list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat_mine, parent, false);


        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        pref_master = new Pref_Master(context);


        if (array_list.get(position).message != null) {
            holder.car.setText(array_list.get(position).message);
        }

        String senderid = array_list.get(position).sender;

        if (senderid.equals(pref_master.getUID())) {
            holder.text_view_chat_message.setText(array_list.get(position).message);
            holder.text_view_chat_message2.setVisibility(View.GONE);
        } else {
            holder.text_view_chat_message2.setText(array_list.get(position).message);
            holder.text_view_chat_message.setVisibility(View.GONE);
        }


      /*  if (array_list.get(position).getDetail() != null) {
            holder.detail.setText(array_list.get(position).getDetail());
        }

        if (array_list.get(position).getCarModel() != null) {
            holder.carmodel.setText(array_list.get(position).getCarModel());
        }*/
    }

    @Override
    public int getItemCount() {


        return array_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView car, carmodel, detail, text_view_chat_message, text_view_user_alphabet, text_view_chat_message2, text_view_user_alphabet2;

        public ViewHolder(View itemView) {
            super(itemView);

            car = itemView.findViewById(R.id.text_view_chat_message);
            text_view_chat_message = itemView.findViewById(R.id.text_view_chat_message);
            text_view_user_alphabet = itemView.findViewById(R.id.text_view_user_alphabet);

            text_view_chat_message2 = itemView.findViewById(R.id.text_view_chat_message2);
            text_view_user_alphabet2 = itemView.findViewById(R.id.text_view_user_alphabet2);


        }
    }
}
