package com.thoughtworks.shikhargupta.databasebasics;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by shikhargupta on 06/07/15.
 */
public class Message {

    public static void message(Context context, String message){

        Toast.makeText(context , message , Toast.LENGTH_LONG).show();
    }
}
