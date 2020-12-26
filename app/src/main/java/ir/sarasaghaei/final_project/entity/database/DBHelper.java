package ir.sarasaghaei.final_project.entity.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import ir.sarasaghaei.final_project.Const;
import ir.sarasaghaei.final_project.entity.Biyab;

class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        super(context, Const.DB_NAME, null, Const.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        CreateTable_Biyab(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void CreateTable_Biyab(SQLiteDatabase db){

        String query = "CREATE TABLE "+ Biyab.class.getSimpleName() +" (\n" +
                "    id  integer primary key ,\n" +
                "    title nvarchar(200),\n" +
                "    description text,\n" +
                "    category nvarchar(50) ,\n" +
                "    sub_category nvarchar(50),\n" +
                "    Tell nvarchar(50),\n" +
                "    price nvarchar(50),\n" +
                "    image1 nvarchar(200),\n" +
                "    image2 nvarchar(200),\n" +
                "    image3 nvarchar(200),\n" +
                "    offer nvarchar(10),\n" +
                "    rank nvarchar(10)); ";


        try {
            db.execSQL(query);
        }
        catch (Exception ex){

        }
    }
}
