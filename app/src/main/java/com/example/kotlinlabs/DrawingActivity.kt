package com.example.kotlinlabs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import android.graphics.Bitmap
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.lang.Exception


class DrawingActivity : AppCompatActivity() {
    var layout1: LinearLayout? =
        null //этот id должен быть у самой верхней компоновки, иногда нужно прописать

    //вручную
    var myGraphView: MyGraphView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawing)
        myGraphView = MyGraphView(this) //создаем объект нашего графического класса
        layout1 = findViewById(R.id.layout1) //находим объект-компоновку
        layout1!!.addView(myGraphView) //добавляем на компоновку наш объект
    }

    fun onCircleClick(view: View) {
        Toast.makeText(this, "Circle", Toast.LENGTH_SHORT).show()
        myGraphView!!.drawCircle() //вызываем функцию для рисования окружности
    }

    fun onSquareClick(view: View?) {
        Toast.makeText(this, "Square", Toast.LENGTH_SHORT).show()
        myGraphView!!.drawSquare() //вызываем функцию для рисования квадрата
    }

    fun onFaceClick(view: View?) {
        Toast.makeText(this, "Face", Toast.LENGTH_SHORT).show()
        myGraphView!!.drawFace() //вызываем функцию для рисования лица
    }

    fun onSaveClick(view: View?) {
        Toast.makeText(this, "Save", Toast.LENGTH_SHORT).show()
        val saveBitMap = myGraphView!!.getBitMap() //получаем картинку с myGraphView
        //получаем путь к каталогу программы на карте памяти (для этого проекта это)
        ///storage/emulated/0/Android/data/com.example.kotlinlabs/files
        val destPath: String = applicationContext.getExternalFilesDir(null)!!.absolutePath
        var outStream: OutputStream? = null //объявляем поток вывода
        val file = File(destPath, "my.PNG") //создаем файл с нужным путем и названием
        println("path = $destPath") //вывод в консоль для отладки
        outStream = FileOutputStream(file) //создаем объект потока и связываем его с файлом
        //у нашего битмапа вызываем функцию для записи его с нужными параметрами (тип графического файла,
        //качество в процентах и поток для записи)
        saveBitMap!!.compress(Bitmap.CompressFormat.PNG, 100, outStream)
        outStream.flush() //для прохождения данных вызываем функцию flush у потока
        outStream.close() //закрываем поток
    }

}