package com.example.mapdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class DialogBox extends Activity {

    public static void setConfirm(final Context context) {
        LayoutInflater li = LayoutInflater.from(context);
        View v = li.inflate(R.layout.dialog, null);
        final android.support.v7.app.AlertDialog alert = new android.support.v7.app.AlertDialog.Builder(context).setView(v).show();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView cancel = (TextView) v.findViewById(R.id.con_ok);
        final EditText edittext = v.findViewById(R.id.edittext);




        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Pref_Master pref_master = new Pref_Master(context);

                if (edittext.getText().toString().trim().length() == 0 || edittext.getText().toString().equals(" ")){

                    alert.dismiss();

                } else {

                    pref_master.setUID(edittext.getText().toString());

                    Intent i = new Intent(context, MapDemoActivity.class);
                    context.startActivity(i);


                    alert.dismiss();
                }

            }
        });

    }
}
