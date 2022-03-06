//----------------------------------------------------------------------------
//     PROJ 207 Threaded Project #3
//     Group: 5
//     Class: OOSD May 21
// 	   Author: Adolphus Cox
//     Description: Class for static methods to display customized toast messages
//----------------------------------------------------------------------------

package com.example.travelexpertscustomerbookingview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Alerts {
    public static void customToast(Context context, String msg) {
        GradientDrawable shape =  new GradientDrawable();
        shape.setCornerRadius( 80 );
        shape.setColor(Color.argb(185, 0, 0, 0));

        Toast t = new Toast(context);
        t.setGravity(Gravity.CENTER, 0, 1000);
        t.setDuration(Toast.LENGTH_LONG);

        TextView tv = new TextView(context);
        tv.setTextColor(Color.WHITE);
        tv.setPadding(60, 40, 60, 40);
        tv.setBackground(shape);
        tv.setTextSize(18);
        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv.setText(msg);

        t.setView(tv);
        t.show();
    }

    public static void customToast_s(Context context, String msg) {
        GradientDrawable shape =  new GradientDrawable();
        shape.setCornerRadius( 80 );
        shape.setColor(Color.argb(200, 0, 0, 0));

        Toast t = new Toast(context);
        t.setGravity(Gravity.CENTER, 0, 1000);
        t.setDuration(Toast.LENGTH_SHORT);

        TextView tv = new TextView(context);
        tv.setTextColor(Color.WHITE);
        tv.setPadding(60, 40, 60, 40);
        tv.setBackground(shape);
        tv.setTextSize(18);
        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv.setText(msg);

        t.setView(tv);
        t.show();
    }
}
