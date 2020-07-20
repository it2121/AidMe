package com.example.AidMe;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all quotes from the database.
     *
     * @return a List of quotes
     */
    public  double[][] getnodes() {
        // List<String> list = new ArrayList<>();
        double[][] nodeid= new double[3][7313];
        Cursor cursor = database.rawQuery("SELECT * FROM p", null);
        cursor.moveToFirst();
        int i = 0;
        while (!cursor.isAfterLast()) {
            nodeid[0][i] = (cursor.getDouble(0));
            nodeid[1][i] = (cursor.getDouble(3));
            nodeid[2][i] =  (cursor.getDouble(4));

            //list.add(cursor.getString(1));

            cursor.moveToNext();
            i++;
        }
        cursor.close();
        return nodeid;
    }




    public     double[][] getLines() {
        //   List<String> list = new ArrayList<>();
        double[][] nodeid= new double [9][10869];
        Cursor cursor = database.rawQuery("SELECT * FROM l", null);
        cursor.moveToFirst();
        int i = 0;
        while (!cursor.isAfterLast()) {
            nodeid[0][i] = (cursor.getDouble(0));
            nodeid[1][i] = (cursor.getDouble(6));
            nodeid[2][i] = (cursor.getDouble(17));
            nodeid[3][i] = (cursor.getDouble(18));
            nodeid[4][i] = (cursor.getDouble(7));
            nodeid[5][i] = (cursor.getDouble(19));
            nodeid[6][i] = (cursor.getDouble(20));
            nodeid[7][i] = (cursor.getDouble(4));
            nodeid[8][i] = (cursor.getDouble(15));


            //list.add(cursor.getString(1));

            cursor.moveToNext();
            i++;
        }
        cursor.close();
        return nodeid;
    }
    public     double[] getlineto() {
        // List<String> list = new ArrayList<>();
        double[] nodeid= new double[10869];
        Cursor cursor = database.rawQuery("SELECT * FROM l", null);
        cursor.moveToFirst();
        int i = 0;
        while (!cursor.isAfterLast()) {
            nodeid[i] = (cursor.getInt(7));
            //list.add(cursor.getString(1));

            cursor.moveToNext();
            i++;
        }
        cursor.close();
        return nodeid;
    }
    public     double[] getlinecost() {
        // List<String> list = new ArrayList<>();
        double[] nodeid= new double[10869];
        Cursor cursor = database.rawQuery("SELECT * FROM l", null);
        cursor.moveToFirst();
        int i = 0;
        while (!cursor.isAfterLast()) {
            nodeid[i] = (cursor.getInt(4));
            //list.add(cursor.getString(1));

            cursor.moveToNext();
            i++;
        }
        cursor.close();
        return nodeid;
    }
    public     double [] getnodelonlat(int idd) {
        // List<String> list = new ArrayList<>();
        int id = idd;
        double[] nodeid = new double[2];
        Cursor cursor = database.rawQuery("SELECT * FROM p where id ='"+id+"'", null);
        cursor.moveToFirst();
        int i = 0;
        while (!cursor.isAfterLast()) {
            nodeid[0] = (cursor.getDouble(3));
            nodeid[1] =  (cursor.getDouble(4));
            //list.add(cursor.getString(1));

            cursor.moveToNext();
            i++;
        }
        cursor.close();
        return nodeid;
    }
//    public     double getnodelat(int idd) {
//        // List<String> list = new ArrayList<>();
//        int id = idd;
//        double nodeid =0;
//        Cursor cursor = database.rawQuery("SELECT * FROM p where id ='"+id+"'", null);
//        cursor.moveToFirst();
//        int i = 0;
//        while (!cursor.isAfterLast()) {
//            nodeid = (cursor.getDouble(4));
//            //list.add(cursor.getString(1));
//
//            cursor.moveToNext();
//            i++;
//        }
//        cursor.close();
//        return nodeid;
//    }
    public     int getdis(int node1,int node2) {
        // List<String> list = new ArrayList<>();
        int ns = node1;
        int nt = node2;
        int nodeid =0;
        Cursor cursor = database.rawQuery("SELECT * FROM l where source ='"+ns+"' and target = '"+nt+"'   ", null);
        cursor.moveToFirst();
        int i = 0;
        while (!cursor.isAfterLast()) {
            nodeid = (cursor.getInt(4));
            //list.add(cursor.getString(1));

            cursor.moveToNext();
            //  i++;
        }
        cursor.close();
        return nodeid;
    }


}


