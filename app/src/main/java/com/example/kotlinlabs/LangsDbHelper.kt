package com.example.kotlinlabs

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class LangsDbHelper (context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    companion object{ // here we have defined variables for our database
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME) //method to check if table already exists
        onCreate(db)
    }

    fun isEmpty(): Boolean {
        val cursor = getCurcor()
        return !cursor!!.moveToFirst()
    }

    fun printDB(){
        val cursor = getCurcor()
        if (!isEmpty()) {
            cursor!!.moveToFirst()
            val nameColIndex = cursor.getColumnIndex(NAME_COl)
            val yearColIndex = cursor.getColumnIndex(YEAR_COL)
            val pictureColIndex = cursor.getColumnIndex(PICTURE_COL)
            do {
                print("${cursor.getString(nameColIndex)} ")
                print("${cursor.getString(yearColIndex)} ")
                println("${cursor.getString(pictureColIndex)} ")
            } while (cursor.moveToNext())
        } else println("DB is empty")
    }

    fun addArrayToDB(progLangs: ArrayList<ProgrLang>){
        progLangs.forEach {
            addLang(it)
        }
    }

    fun addLang(lang: ProgrLang){ // This method is for adding data in our database
        val values = ContentValues() // below we are creating a content values variable
        values.put(NAME_COl, lang.name) // we are inserting our values in the form of key-value pair
        values.put(YEAR_COL, lang.year)
        values.put(PICTURE_COL, lang.picture)
        val db = this.writableDatabase //writable variable of our database as we want to insert value in our database
        db.insert(TABLE_NAME, null, values) // all values are inserted into database
        db.close() // at last we are closing our database
    }

    fun getCurcor(): Cursor? { // method is to get all data from our database
        val db = this.readableDatabase // a readable variable of our database
        //returns a cursor to read data from the database
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null)
    }

    fun changeImgForLang(name: String, img: String){
        val db = this.writableDatabase //writable variable of our database as we want to insert value in our database
        val values = ContentValues()
        values.put(PICTURE_COL, img)
        db.update(TABLE_NAME, values, NAME_COl+" = '$name'", null)
//        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $NAME_COl = '$name'", null)
        db.close()
//        cursor.moveToFirst()
//        val nameColIndex = cursor.getColumnIndex(NAME_COl)
//        val yearColIndex = cursor.getColumnIndex(YEAR_COL)
//        val pictureColIndex = cursor.getColumnIndex(PICTURE_COL)
//        println("Change in langs: ")
//        print("${cursor.getString(nameColIndex)} ")
//        print("${cursor.getString(yearColIndex)} ")
//        println("${cursor.getString(pictureColIndex)} ")
        //"UPDATE orders SET order_price=:price WHERE order_id = :id"
    }

    fun getLangsArray(): ArrayList<ProgrLang>{
        var progsArray = ArrayList<ProgrLang>()
        val cursor = getCurcor()
        if (!isEmpty()) {
            cursor!!.moveToFirst()
            val nameColIndex = cursor.getColumnIndex(NAME_COl)
            val yearColIndex = cursor.getColumnIndex(YEAR_COL)
            val pictureColIndex = cursor.getColumnIndex(PICTURE_COL)
            do {
                val name = cursor.getString(nameColIndex)
                val year = cursor.getString(yearColIndex).toInt()
                val picture = cursor.getString(pictureColIndex)
                progsArray.add(ProgrLang(name, year, picture))
            } while (cursor.moveToNext())
        } else println("DB is empty")
        return progsArray
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