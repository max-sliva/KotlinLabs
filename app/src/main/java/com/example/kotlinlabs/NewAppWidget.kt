package com.example.kotlinlabs

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import android.widget.Toast
import android.content.Intent
import android.view.View
import android.app.PendingIntent
import android.database.Cursor
import android.net.Uri
import java.lang.NumberFormatException
import android.content.ComponentName

class NewAppWidget : AppWidgetProvider() {
    var remoteViews : RemoteViews? = null//объект для доступа к интерфейсу нашего виджета
    var ACTION_NEXT = "next" //строки для обозначения действий в интентах
    var ACTION_PREV = "prev"
    var dbHelper : LangsDbHelper? = null // объект класса DBHelper
    var c: Cursor? = null
    var nextEnabled = false //для обозначения доступности кнопки Next
    var prevEnabled  = false //для обозначения доступности кнопки Prev
    companion object {
        var pos = 0 // статическая переменная текущей позиции в списке языков
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        remoteViews = RemoteViews(context.packageName, R.layout.new_app_widget)
        //Подготавливаем Intent для работы кнопки Next
        //Подготавливаем Intent для работы кнопки Next
        val next = Intent(context, NewAppWidget::class.java)
        next.action = ACTION_NEXT //устанавливаем действие – свою строку ACTION_NEXT
//создаем наше событие с объектом класса Intent
        val actionIntentNext = PendingIntent.getBroadcast(context, 0, next, 0)
        val prev = Intent(context, NewAppWidget::class.java) //Подготавливаем Intent для работы кнопки Prev
        prev.action = ACTION_PREV
        //создаем наше событие с объектом класса Intent
        val actionIntentPrev = PendingIntent.getBroadcast(context, 0, prev, 0)
        //регистрируем наши события (их обработка будет ниже в методе onReceive)
        remoteViews!!.setOnClickPendingIntent(R.id.buttonNext, actionIntentNext)
        remoteViews!!.setOnClickPendingIntent(R.id.buttonPrev, actionIntentPrev)
        //делаем неактивной кнопку Prev
        remoteViews!!.setViewVisibility(R.id.buttonPrev, View.GONE)
        remoteViews!!.setViewVisibility(R.id.buttonNext, View.VISIBLE)

        prevEnabled = false //соответствующим переменным присваиваем нужные логические значения
        nextEnabled = true

        dbHelper = LangsDbHelper(context)
        c = dbHelper?.getCurcor()
        c?.moveToFirst()
        updateMyWidget(remoteViews!!,c!!,context)
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews) //обновляем виджет

        c?.close() //закрываем все соединения с БД
        dbHelper?.close()
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    override fun onEnabled(context: Context) {
        pos = 0 //зануляем позицию в списке языков
        //создаем объект класса RemoteViews, через него будем менять значения нужных параметров в интерфейсе
        remoteViews = RemoteViews(context.packageName, R.layout.new_app_widget)
        remoteViews!!.setViewVisibility(R.id.buttonPrev, View.GONE) //делаем неактивной кнопку Prev,

//т.е. у объекта с идентификатором R.id.buttonPrev устанавливаем параметр setEnabled в значение false
        //т.е. у объекта с идентификатором R.id.buttonPrev устанавливаем параметр setEnabled в значение false
        remoteViews!!.setViewVisibility(R.id.buttonNext, View.VISIBLE)
        Toast.makeText(context, "onEnabled", Toast.LENGTH_SHORT).show()
        super.onEnabled(context) //вызов родительского метода
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private fun updateMyWidget(remoteViews: RemoteViews, c2: Cursor, context: Context) {
        val nameColIndex = c2!!.getColumnIndex("lang_name") //получаем номера столбцов с нужными данными
        val yearColIndex = c2!!.getColumnIndex("year")
        val authorColIndex = c2!!.getColumnIndex("picture")
        //получаем строковые данные из БД
        val langName = c2!!.getString(nameColIndex)
        val langYear = c2!!.getString(yearColIndex)
        val authorInDB = c2!!.getString(authorColIndex)
        //выводим в интерфейс полученные данные
        remoteViews!!.setTextViewText(R.id.widgetLangName, langName)
//        Toast.makeText(context, "text=: $langName", Toast.LENGTH_SHORT).show()
        remoteViews!!.setTextViewText(R.id.widgetLangYear, langYear)
        try { //для картинки автора пробуем организуем try … catch
            //пробуем преобразовать его в число, если получается, значит взято из внутренних ресурсов
            //т.к. все ресурсы хранятся в виде числовых констант, и выводим на ImageView
            remoteViews!!.setImageViewResource(R.id.widgetAuthImg, authorInDB.toInt())
        } catch (e: NumberFormatException) { // если сработало исключение, значит, там путь к файлу
            val uri: Uri = Uri.parse(authorInDB) //делаем из него URI (см. в лаб.раб.№5 )
            remoteViews!!.setImageViewUri(R.id.widgetAuthImg, uri) // и выводим на ImageView
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        //Ловим наш интент и берем его действие, прописанное нами
        val action = intent.action
        val watchWidget = ComponentName(context, NewAppWidget::class.java) //объект для связи с виджетом, связываем его с нашим классом

//создаем объект класса AppWidgetManager для обновления через него нашего виджета
        val appWidgetManager = AppWidgetManager.getInstance(context)

//создаем объект класса RemoteViews, через него будем менять значения нужных параметров в интерфейсе
        remoteViews = RemoteViews(context.packageName, R.layout.new_app_widget)
        //создаем объекты для работы с БД
        dbHelper = LangsDbHelper(context)
        c = dbHelper?.getCurcor()
        if (ACTION_NEXT == action) { //если действие в интенте является «next»
            pos++ //увеличиваем позицию в списке на 1
            Toast.makeText(context, "after next pos=: $pos", Toast.LENGTH_SHORT).show()

            if (c!!.moveToPosition(pos)) { //если такая позиция есть,
                //то вызываем нашу функцию для заполнения виджета данными из БД (см. ниже)
                updateMyWidget(remoteViews!!, c!!, context)
            }
            if (c!!.isLast) { //если текущая запись последняя
                //делаем неактивной кнопку Next
                remoteViews!!.setViewVisibility(R.id.buttonNext, View.GONE)
                remoteViews!!.setViewVisibility(R.id.buttonPrev, View.VISIBLE)
                nextEnabled = false //присваиваем соответствующее значение логической переменной
            }
            if (!prevEnabled) { //если кнопка Prev не активна
                remoteViews!!.setViewVisibility(R.id.buttonPrev, View.VISIBLE) //делаем ее активной
                prevEnabled = true //присваиваем соответствующее значение логической переменной
            }
        }
        if (ACTION_PREV == action) { //если действие в интенте является «prev»
            pos-- //уменьшаем позицию в списке на 1
            Toast.makeText(context, "after prev pos=: $pos", Toast.LENGTH_SHORT).show()

            if (c!!.moveToPosition(pos)) { //если такая позиция есть,
                //то вызываем нашу функцию для заполнения виджета данными из БД (см. ниже)
                updateMyWidget(remoteViews!!, c!!, context)
            }
            if (!nextEnabled) { //если кнопка Next не активна
                remoteViews!!.setViewVisibility(R.id.buttonNext, View.VISIBLE) //делаем ее активной
                nextEnabled = true //присваиваем соответствующее значение логической переменной
            }
            if (c!!.isFirst) { //если текущая запись первая
                //делаем неактивной кнопку Prev
                remoteViews!!.setViewVisibility(R.id.buttonPrev, View.GONE)
                remoteViews!!.setViewVisibility(R.id.buttonNext, View.VISIBLE)
                prevEnabled = false //присваиваем соответствующее значение логической переменной
            }
        }
        appWidgetManager.updateAppWidget(watchWidget, remoteViews) //обновляем виджет
        c!!.close() // закрываем все соединения с БД
        dbHelper!!.close()
        super.onReceive(context, intent) //вызываем метод родительского класса
    }
}

//internal fun updateAppWidget(
//    context: Context,
//    appWidgetManager: AppWidgetManager,
//    appWidgetId: Int
//) {
////    val widgetText = context.getString(R.string.appwidget_text)
//    // Construct the RemoteViews object
////    val views = RemoteViews(context.packageName, R.layout.new_app_widget)
////    views.setTextViewText(R.id.appwidget_text, widgetText)
//
//    // Instruct the widget manager to update the widget
////    appWidgetManager.updateAppWidget(appWidgetId, views)
//}

