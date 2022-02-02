package com.example.projetandroid4a;

import android.content.Context;
import android.widget.Toast;

public class Utils {
    public static void ShowInfo(String info, Context context){
        Toast toast;
        toast = Toast.makeText(context,info,Toast.LENGTH_SHORT);
        toast.show();
    }
}
