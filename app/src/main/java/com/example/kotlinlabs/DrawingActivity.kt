package com.example.kotlinlabs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast

class DrawingActivity : AppCompatActivity() {
    var layout1: LinearLayout? = null //этот id должен быть у самой верхней компоновки, иногда нужно прописать
                                        //вручную
    var myGraphView: MyGraphView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawing)
        myGraphView = MyGraphView(this) //создаем объект нашего графического класса
        layout1=findViewById(R.id.layout1) //находим объект-компоновку
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

}