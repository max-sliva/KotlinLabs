package com.example.kotlinlabs

import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.view.*
import android.widget.*
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView

 class MyAdapter(private var langList: ArrayList<ProgrLang>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() , View.OnCreateContextMenuListener{
     var myInterface: MyInterface? = null
     class MyViewHolder(view: View, onCreateContextMenuListener: View.OnCreateContextMenuListener) : RecyclerView.ViewHolder(view)
    {
        var langName: TextView = view.findViewById(R.id.name)
        var langYear: TextView = view.findViewById(R.id.year)
        var langAuthor: ImageView = view.findViewById(R.id.imageView1)

        init {
            itemView.setOnClickListener {
                Toast.makeText(view.context, "pos = " + adapterPosition, Toast.LENGTH_LONG).show()
                showMessage(langName.text.toString(), view)
            }
            itemView.setOnCreateContextMenuListener(onCreateContextMenuListener)
        }

        fun showMessage(str: String?, view: View) {
            //создаем диалоговое окно, параметр – контекст, который берем у view
            val builder = AlertDialog.Builder(view.context)
            builder.setTitle(str) //заголовок диалогового окна
//создаем переменную для нахождения строкового ресурса (см. текст после примера)
//ищем в строковых ресурсах строку с именем, равным значению str и берем её идентификатор
            val strId: Int = view.context.resources.getIdentifier(str, "string", view.context.packageName)
            var strValue: String? = ""
            //если ресурс был найден, т.е. strId!=0, то по найденному идентификатору получаем значение строки
            if (strId != 0) strValue = view.context.getString(strId)
            builder.setMessage(strValue) //задаем содержимое окна
            //создаем в окне кнопку ОК и задаем ее функционал
            builder.setPositiveButton(android.R.string.ok) { dialog, which ->
                dialog.dismiss() // Закрывает диалоговое окно
            }
            val dialog: AlertDialog = builder.create() //создаем диалоговое окно через построитель
            dialog.show() //показываем диалоговое окно
        }
    }

     override fun onCreateContextMenu(menu: ContextMenu, view: View, menuInfo: ContextMenu.ContextMenuInfo?) {
         val edit = menu.add(Menu.NONE, 1, 1, "Edit picture") //создаем пункт меню Edit picture
         edit.setOnMenuItemClickListener {
             var pic = (view as LinearLayout).getChildAt(1) as ImageView
             val pos = view.tag as Int
             myInterface?.callback(pic, pos)
             false
         }
         val delete = menu.add(Menu.NONE, 2, 2, "Delete")
     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.my_list, parent, false)
        return MyViewHolder(itemView, this)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val lang = langList[position]
        holder.itemView.tag = position
        holder.langName.text = lang.name
        holder.langYear.text = lang.year.toString()
        try {
            holder.langAuthor.setImageResource(lang.picture.toInt())
        }
        catch (e:ClassCastException) {
            holder.langAuthor.setImageURI(Uri.parse(lang.picture))
        }
        catch (e: NumberFormatException) {
            holder.langAuthor.setImageURI(Uri.parse(lang.picture))
        }
    }

    override fun getItemCount() = langList.size
}