package com.example.kotlinlabs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

internal class MyAdapter(private var langList: ArrayList<ProgrLang>, private var context: Context) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    //    var onItemClick: ((ProgrLang) -> Unit)? = null
//    private var context: Context? = null
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var langName: TextView = view.findViewById(R.id.name)
        var langYear: TextView = view.findViewById(R.id.year)
        var langAuthor: ImageView = view.findViewById(R.id.imageView1)

        init {
            itemView.setOnClickListener {
                // onItemClick?.invoke(langList[adapterPosition])
                Toast.makeText(view.context, "pos =" + adapterPosition, Toast.LENGTH_LONG).show()
                showMessage(langName.text.toString(), view)
            }
        }

        fun showMessage(str: String?, view: View) {
            //создаем диалоговое окно, параметр – контекст, который берем у view
            val builder = AlertDialog.Builder(view.context)
            builder.setTitle(str) //заголовок диалогового окна
            //создаем переменную для нахождения строкового ресурса (см. текст после примера)
            //ищем в строковых ресурсах строку с именем, которое совпадает с значением str
            //и берем её идентификатор
            val strId: Int = context.resources.getIdentifier(str, "string", context.packageName)
            var strValue: String? = ""
            //если ресурс был найден, т.е. strId!=0, то по найденному идентификатору получаем значение строки
            if (strId != 0) strValue =
                context.getString(strId)
            builder.setMessage(strValue) //задаем содержимое окна
            //создаем в окне кнопку ОК и задаем ее функционал
            builder.setPositiveButton(android.R.string.ok) { dialog, which ->
                dialog.dismiss() // Закрывает диалоговое окно
            }
            val dialog: AlertDialog = builder.create() //создаем диалоговое окно через построитель
            dialog.show() //показываем диалоговое окно
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.my_list, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val lang = langList[position]
        holder.langName.text = lang.name
        holder.langYear.text = lang.year.toString()
        holder.langAuthor.setImageResource(lang.picture)
    }

    override fun getItemCount() = langList.size
}