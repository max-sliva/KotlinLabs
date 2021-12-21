package com.example.kotlinlabs

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class LangsDbHelper (context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION){
    companion object{// here we have defined variables for our database
        private val DATABASE_NAME = "LANGS" //variable for database name
        private val DATABASE_VERSION = 1 // variable for database version
        val TABLE_NAME = "langs_table" // below is the variable for table name
        val ID_COL = "id" // variable for id column
        val NAME_COl = "lang_name" // variable for name column
        val YEAR_COL = "year" // variable for age column
        val PICTURE_COL = "picture"
    }

    override fun onCreate(db: SQLiteDatabase) { //method for creating a database by a sqlite query
        // below is a sqlite query, where column names along with their data types is given
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY autoincrement, " +
                NAME_COl + " TEXT," +
                YEAR_COL + " INTEGER," +
                PICTURE_COL + " TEXT" + ")")
        db.execSQL(query) // we are calling sqlite method for executing our query
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun addName(lang: ProgrLang){ // This method is for adding data in our database
        val values = ContentValues() // below we are creating a content values variable
        values.put(NAME_COl, lang.name) // we are inserting our values in the form of key-value pair
        values.put(YEAR_COL, lang.year)
        values.put(PICTURE_COL, lang.picture)

        // here we are creating a writable variable of
        // our database as we want to insert value in our database
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values) // all values are inserted into database
        db.close() // at last we are closing our database
    }

    fun getLang(): Cursor? { // method is to get all data from our database
        val db = this.readableDatabase // a readable variable of our database
        //returns a cursor to read data from the database
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null)

    }
//    object LangsContract {
//        // Table contents are grouped together in an anonymous object.
//        object LangsEntry : BaseColumns {
//            const val TABLE_NAME = "langs"
//            const val COLUMN_LANG_NAME = "lang_name"
//            const val COLUMN_YEAR = "lang_year"
//            const val COLUMN_PICTURE = "lang_picture"
//        }
//    }
//    private val SQL_CREATE_ENTRIES =
//        "CREATE TABLE ${LangsContract.LangsEntry.TABLE_NAME} (" +
//                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
//                "${LangsContract.LangsEntry.COLUMN_LANG_NAME} TEXT," +
//                "${LangsContract.LangsEntry.COLUMN_YEAR} TEXT," +
//                "${LangsContract.LangsEntry.COLUMN_PICTURE} TEXT)"
//
//    private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${LangsContract.LangsEntry.TABLE_NAME}"
}