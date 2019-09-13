package com.esi.pharmacie.models;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Locale;

public class StaticObjects {

    private final static int timeOut = 3;
    private static String lng = "en";
    private static User user;



    public static Object getSavedData(String file, Context c) {
        Object o = null;
        try {
            FileInputStream fis = c.openFileInput(file);
            ObjectInputStream is = new ObjectInputStream(fis);
            o = is.readObject();
            is.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return o;
    }

    public static void saveData(Object object, String file, Context c) {
        try {
            FileOutputStream fos = c.openFileOutput(file, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(object);
            os.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        StaticObjects.user = user;
    }
}