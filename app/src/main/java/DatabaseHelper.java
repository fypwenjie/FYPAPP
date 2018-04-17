/**
 * Created by VINTEDGE on 17/4/2018.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import dev.fypwenjie.fypapp.Domain.Cart;

/**
 * Created by ravi on 15/03/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "notes_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(Cart.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Cart.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public Cart getNote(long id) {
        // get readable database as we are not inserting anything
   /*     SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Cart.TABLE_NAME,
                new String[]{Cart.COLUMN_ID, Cart.COLUMN_NOTE, Note.COLUMN_TIMESTAMP},
                Note.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Note note = new Note(
                cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_NOTE)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_TIMESTAMP)));

        // close the db connection
        cursor.close();

        */return null;
    }

    public List<Cart> getNotes(String token) {
        List<Cart> carts = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + Cart.TABLE_NAME + " WHERE c_token = "+ token + " ORDER BY " + Cart.COLUMN_CREATETIME + "ASC";                ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Cart cart = new Cart();
                cart.setCart_id(cursor.getString(cursor.getColumnIndex(Cart.COLUMN_ID)));
                cart.setFood_id(cursor.getString(cursor.getColumnIndex(Cart.COLUMN_FOODID)));
                cart.setQuantity(cursor.getString(cursor.getColumnIndex(Cart.COLUMN_QUANTITY)));
                cart.setPrice(cursor.getString(cursor.getColumnIndex(Cart.COLUMN_PRICE)));
                cart.setRemark(cursor.getString(cursor.getColumnIndex(Cart.COLUMN_REMARK)));
                cart.setToken(cursor.getString(cursor.getColumnIndex(Cart.COLUMN_TOKEN)));
                cart.setStatus(cursor.getString(cursor.getColumnIndex(Cart.COLUMN_STATUS)));
                cart.setCreatetime(cursor.getString(cursor.getColumnIndex(Cart.COLUMN_CREATETIME)));

                carts.add(cart);
            } while (cursor.moveToNext());
        }
        // close db connection
        db.close();

        // return notes list
        return carts;
    }

    public int getNotesCount() {
        String countQuery = "SELECT  * FROM " + Cart.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }
}