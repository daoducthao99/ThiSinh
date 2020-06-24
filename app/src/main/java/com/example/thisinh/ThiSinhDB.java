package com.example.thisinh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class ThiSinhDB extends SQLiteOpenHelper {
    public static  final String TableName = "ThiSinhTable";
    public static  final String Sbd = "Sbd";
    public static  final String Hoten = "Hoten";
    public static final String Toan = "Toan";
    public static final String Ly = "Ly";
    public static final String Hoa = "Hoa";

    public ThiSinhDB(Context context,String name,SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreate = "Create table if not exists " + TableName + "("
                + Sbd + " Text Primary key, "
                + Hoten + " Text, "
                + Toan + " Double, "
                + Ly + " Double, "
                + Hoa + " Double)";
        //Chay cau truy van de tao bang
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Xoa bang TableDevice da co
        db.execSQL("Drop table if exists "+ TableName);
        //Tao lai
        onCreate(db);
    }

    public ArrayList<ThiSinh> getAllThiSinh(){
        ArrayList<ThiSinh> list = new ArrayList<>();
        //Cau truy van
        String sql ="Select * from " + TableName;
        //Lay doi tuong csdl sqlite
        SQLiteDatabase db = this.getReadableDatabase();
        //Chay cau truy van tra ve dang Cursor
        Cursor cursor = db.rawQuery(sql,null);
        //Tao ArrayList<Device> de tra ve
        if(cursor!=null)
            while (cursor.moveToNext()){
                ThiSinh thiSinh = new ThiSinh(cursor.getString(0),cursor.getString(1),
                        cursor.getDouble(2),cursor.getDouble(3),
                        cursor.getDouble(4));
                list.add(thiSinh);
            }
        return list;
    }

    //Ham them 1 device vao bang TableDevice
    public void addThiSinh(ThiSinh thiSinh)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(Sbd,thiSinh.getSbd());
        value.put(Hoten,thiSinh.getHoten());
        value.put(Toan,thiSinh.getToan());
        value.put(Ly,thiSinh.getLy());
        value.put(Hoa,thiSinh.getHoa());
        db.insert(TableName,null,value);
        db.close();
    }

    public void deleteThiSinh(String sbd){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "Delete from "+TableName+" Where Sbd= '"+sbd+"'";
        db.execSQL(sql);
        db.close();
    }
}
