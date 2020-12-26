package ir.sarasaghaei.final_project.entity.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ir.sarasaghaei.final_project.Const;
import ir.sarasaghaei.final_project.R;
import ir.sarasaghaei.final_project.SplashActivity;
import ir.sarasaghaei.final_project.entity.Biyab;

public class BiyabDBHelper extends DBHelper {

    public Context context;
    private final String TABLE_NAME = Biyab.class.getSimpleName();
    private final String FILD_Id = "id", FILD_Title = "title",
            FILD_Description = "description", FILD_Category = "category",
            FILD_Subcategory = "sub_category", FILD_Tell = "Tell",
            FILD_Price = "price", FILD_Image1 = "image1", FILD_Image2 = "image2",
            FILD_Image3 = "image3", FILD_Offer = "offer", FILD_Rank = "rank";

    public BiyabDBHelper(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insert(Biyab biyab_record) {
        long result = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            // cv.put(FILD_Id,biyab_record.getId());
            cv.put(FILD_Title, biyab_record.getTitle());
            cv.put(FILD_Description, biyab_record.getDescription());
            cv.put(FILD_Category, biyab_record.getCategory());
            cv.put(FILD_Subcategory, biyab_record.getSub_category());
            cv.put(FILD_Tell, biyab_record.getTell());
            cv.put(FILD_Price, biyab_record.getPrice());
            cv.put(FILD_Image1, biyab_record.getImage1());
            cv.put(FILD_Image2, biyab_record.getImage2());
            cv.put(FILD_Image3, biyab_record.getImage3());
            cv.put(FILD_Offer, biyab_record.getOffer());
            cv.put(FILD_Rank, biyab_record.getRank());

            result = db.insert(TABLE_NAME, null, cv);
        } catch (Exception ex) {

        } finally {
            db.close();
        }
        return result;

    }

    public List<Biyab> select() {
        List<Biyab> result = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] Columns = {FILD_Id, FILD_Title, FILD_Description, FILD_Category, FILD_Subcategory,
                FILD_Tell, FILD_Price, FILD_Image1, FILD_Image2, FILD_Image3, FILD_Offer, FILD_Rank};

        Cursor cursor = db.query(TABLE_NAME, Columns, null, null, null, null, null);
        if (cursor.getCount() == 0) {
        }
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(FILD_Id));
            String title = cursor.getString(cursor.getColumnIndex(FILD_Title));
            String description = cursor.getString(cursor.getColumnIndex(FILD_Description));
            String category = cursor.getString(cursor.getColumnIndex(FILD_Category));
            String subcategory = cursor.getString(cursor.getColumnIndex(FILD_Subcategory));
            String Tell = cursor.getString(cursor.getColumnIndex(FILD_Tell));
            String price = cursor.getString(cursor.getColumnIndex(FILD_Price));
            String image1 = cursor.getString(cursor.getColumnIndex(FILD_Image1));
            String image2 = cursor.getString(cursor.getColumnIndex(FILD_Image2));
            String image3 = cursor.getString(cursor.getColumnIndex(FILD_Image3));
            String offer = cursor.getString(cursor.getColumnIndex(FILD_Offer));
            String rank = cursor.getString(cursor.getColumnIndex(FILD_Rank));

            Biyab biyabItem = new Biyab(id, title, description, category, subcategory,
                    Tell, price, image1, image2, image3, offer, rank);

            result.add(biyabItem);
        }
        cursor.close();
        db.close();
        return result;
    }

    public List<Biyab> select(String category) {
        List<Biyab> result = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] Columns = {FILD_Id, FILD_Title, FILD_Category, FILD_Price, FILD_Image1, FILD_Offer};

        Cursor cursor = db.query(TABLE_NAME, Columns, "category=?", new String[]{category}, null, null, null);
        if (cursor.getCount() == 0) {
        }
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(FILD_Id));
            String title = cursor.getString(cursor.getColumnIndex(FILD_Title));
            category = cursor.getString(cursor.getColumnIndex(FILD_Category));
            String price = cursor.getString(cursor.getColumnIndex(FILD_Price));
            String image1 = cursor.getString(cursor.getColumnIndex(FILD_Image1));
            String offer = cursor.getString(cursor.getColumnIndex(FILD_Offer));

            Biyab biyabItem = new Biyab(id, title, category, price, image1, offer);
            result.add(biyabItem);

        }
        cursor.close();
        db.close();
        return result;
    }

    public List<Biyab> select_subcat(String sub_category) {
        List<Biyab> result = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] Columns = {FILD_Id, FILD_Title, FILD_Category, FILD_Price, FILD_Image1, FILD_Offer};

        Cursor cursor = db.query(TABLE_NAME, Columns, "sub_category=?", new String[]{sub_category}, null, null, null);
        if (cursor.getCount() == 0) {
        }
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(FILD_Id));
            String title = cursor.getString(cursor.getColumnIndex(FILD_Title));
            String category = cursor.getString(cursor.getColumnIndex(FILD_Category));
            String price = cursor.getString(cursor.getColumnIndex(FILD_Price));
            String image1 = cursor.getString(cursor.getColumnIndex(FILD_Image1));
            String offer = cursor.getString(cursor.getColumnIndex(FILD_Offer));

            Biyab biyabItem = new Biyab(id, title, category, price, image1, offer);
            result.add(biyabItem);

        }
        cursor.close();
        db.close();
        return result;
    }

    public List<Biyab> select(String category, String offer) {
        List<Biyab> result = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] Columns = {FILD_Id, FILD_Title, FILD_Category, FILD_Price, FILD_Image1, FILD_Offer};

        Cursor cursor = db.query(TABLE_NAME, Columns, "category=? AND offer=?", new String[]{category, offer}, null, null, null);
        if (cursor.getCount() == 0) {
        }
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(FILD_Id));
            String title = cursor.getString(cursor.getColumnIndex(FILD_Title));
            category = cursor.getString(cursor.getColumnIndex(FILD_Category));
            String price = cursor.getString(cursor.getColumnIndex(FILD_Price));
            String image1 = cursor.getString(cursor.getColumnIndex(FILD_Image1));
            offer = cursor.getString(cursor.getColumnIndex(FILD_Offer));

            Biyab biyabItem = new Biyab(id, title, category, price, image1, offer);
            result.add(biyabItem);

        }
        cursor.close();
        db.close();
        return result;
    }

    public List<Biyab> select_search(String search) {
        List<Biyab> result = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] Columns = {FILD_Id, FILD_Title, FILD_Category, FILD_Price, FILD_Image1, FILD_Offer};

        Cursor cursor = db.query(TABLE_NAME, Columns, "title like ?", new String[]{"%" + search + "%"}, null, null, null);
        if (cursor.getCount() == 0) {
        }
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(FILD_Id));
            String title = cursor.getString(cursor.getColumnIndex(FILD_Title));
            String category = cursor.getString(cursor.getColumnIndex(FILD_Category));
            String price = cursor.getString(cursor.getColumnIndex(FILD_Price));
            String image1 = cursor.getString(cursor.getColumnIndex(FILD_Image1));
            String offer = cursor.getString(cursor.getColumnIndex(FILD_Offer));

            Biyab biyabItem = new Biyab(id, title, category, price, image1, offer);
            result.add(biyabItem);

        }
        cursor.close();
        db.close();
        return result;
    }

    public Biyab select(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] Columns = {FILD_Id, FILD_Title, FILD_Description, FILD_Category, FILD_Subcategory, FILD_Tell, FILD_Price,
                FILD_Image1, FILD_Image2, FILD_Image3, FILD_Offer, FILD_Rank};

        Cursor cursor = db.query(TABLE_NAME, Columns, "id=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor.getCount() == 0) {
            return null;
        }
        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex(FILD_Title));
            String description = cursor.getString(cursor.getColumnIndex(FILD_Description));
            String category = cursor.getString(cursor.getColumnIndex(FILD_Category));
            String subcategory = cursor.getString(cursor.getColumnIndex(FILD_Subcategory));
            String Tell = cursor.getString(cursor.getColumnIndex(FILD_Tell));
            String price = cursor.getString(cursor.getColumnIndex(FILD_Price));
            String image1 = cursor.getString(cursor.getColumnIndex(FILD_Image1));
            String image2 = cursor.getString(cursor.getColumnIndex(FILD_Image2));
            String image3 = cursor.getString(cursor.getColumnIndex(FILD_Image3));
            String offer = cursor.getString(cursor.getColumnIndex(FILD_Offer));
            String rank = cursor.getString(cursor.getColumnIndex(FILD_Rank));

            Biyab biyabItem = new Biyab(id, title, description, category, subcategory, Tell, price, image1, image2, image3, offer, rank);
            cursor.close();
            db.close();
            return biyabItem;
        }
        return null;
    }

    public int update_rank(Biyab biyab_update) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(FILD_Rank, biyab_update.getRank());

        int result = -1;
        try {
            result = db.update(TABLE_NAME, cv, "id=?", new String[]{String.valueOf(biyab_update.getId())});
        } catch (Exception ex) {
        }
        db.close();
        return result;
    }

    public boolean checkDataBase() {
        File dbFile = context.getDatabasePath(Const.DB_NAME);
        return dbFile.exists();
    }

}
