package com.example.myproducts.Database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.myproducts.Model.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {

    private static final String DB_NAME = "mydbproducts.db";
    private static final int DB_VERSION = 1;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @SuppressLint("Range")
    public List<Order> getCarts() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sql_selected = {"pro_id", "pro_name", "quantity", "price"};
        String sql_table = "order_details";

        qb.setTables(sql_table);

        Cursor cursor = qb.query(db, sql_selected, null, null, null, null, null);
        final List<Order> result = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {


                result.add(new Order(cursor.getString(cursor.getColumnIndex("pro_id")),
                        cursor.getString(cursor.getColumnIndex("pro_name")),
                        cursor.getString(cursor.getColumnIndex("quantity")),
                        cursor.getString(cursor.getColumnIndex("price")))
                );

            } while (cursor.moveToNext());


        }
        return result;
    }

    public void addCart(Order order) {
        SQLiteDatabase db = getWritableDatabase();
        String query = String.format("INSERT INTO order_details(pro_id , pro_name, quantity , price)" +
                        " VALUES('%s','%s','%s','%s');",order.getProduct_id(),
                order.getProduct_name(),order.getQuantity(),order.getPrice());

        db.execSQL(query);
    }

    public void cleanCart() {
        SQLiteDatabase db = getWritableDatabase();
        String query = String.format("DELETE FROM order_details");

        db.execSQL(query);
    }

}
