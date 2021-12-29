package com.example.kotlinlabs

import android.content.Context
import android.graphics.*
import android.view.View
import android.widget.Toast

class MyGraphView(context: Context?) : View(context) {
    private var mPaint: Paint? = null //объект для параметров рисования графических примитивов
    private var mBitmapPaint: Paint? = null //объект для параметров вывода битмапа на холст
    private var mBitmap : Bitmap? = null //сам битмап
    private var mCanvas: Canvas? = null //холст

    init {
        mBitmapPaint = Paint(Paint.DITHER_FLAG) // Paint.DITHER_FLAG – для эффекта сглаживания
        mPaint = Paint()//создаем объект класса Paint для параметров рисования графических примитивов
        mPaint!!.setAntiAlias(true) //устанавливаем антиалиасинг (сглаживание)
        mPaint?.setColor(Color.GREEN) //цвет рисования
        mPaint?.setStyle(Paint.Style.STROKE) //стиль рисования (Paint.Style.STROKE – без заполнения)
        mPaint?.setStrokeJoin(Paint.Join.ROUND) //стиль соединения линий (ROUND - скруглённый)
        mPaint?.setStrokeCap(Paint.Cap.ROUND) //стиль концов линий (ROUND - скруглённый)
        mPaint?.setStrokeWidth(12F) //толщина линии рисования
    }        //создаем объект класса Paint для параметров вывода битмапа на холст


    //метод onSizeChanged вызывается первый раз при создании объекта,
    //далее – при изменении размера объекта, нам он нужен для выяснения первичных размеров битмапа
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        //создаем битмап с высотой и шириной как у текущего объекта и с параметром Bitmap.Config.ARGB_8888 –
//четырехканальный RGB (прозрачность и 3 цвета)
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mBitmap!!) //создаем канвас и связываем его с битмапом
        Toast.makeText(this.context, "onSizeChanged ", Toast.LENGTH_SHORT).show() //для отладки
    }

    //метод перерисовки объекта, он будет срабатывать каждый раз
    override fun onDraw(canvas: Canvas) { //при вызове функции invalidate() текущего объекта
        super.onDraw(canvas)
        //отрисовываем на канвасе текущего объекта (не путать с созданным нами канвасом) наш битмап
        canvas.drawBitmap(mBitmap!!, 0f, 0f, mBitmapPaint!!)
    }

    fun drawCircle() { //метод для рисования круга
        mCanvas!!.drawCircle(100f, 100f, 50f, mPaint!!)
        invalidate() //для срабатывания метода onDraw
    }

    fun drawSquare() { //метод для рисования квадрата
        mCanvas!!.drawRect(200f, 200f, 300f, 300f, mPaint!!)
        invalidate()
    }

    fun drawFace() { //метод для рисования картинки из файла
        //создаем временный битмап из файла
        val mBitmapFromSdcard = BitmapFactory.decodeFile("/mnt/sdcard/face.png")
        mCanvas!!.drawBitmap(mBitmapFromSdcard, 100f, 100f, mPaint) //рисуем его на нашем канвасе
        invalidate()
    }

}