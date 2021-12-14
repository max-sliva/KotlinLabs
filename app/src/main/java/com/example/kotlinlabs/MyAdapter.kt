package com.example.kotlinlabs

import android.app.AlertDialog
import android.content.Context
import android.view.*
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

internal class MyAdapter(private var langList: ArrayList<ProgrLang>, private var context: Context) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    //    var onItemClick: ((ProgrLang) -> Unit)? = null
//    private var context: Context? = null
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
//        , View.OnCreateContextMenuListener //, PopupMenu.OnMenuItemClickListener
    {
        var langName: TextView = view.findViewById(R.id.name)
        var langYear: TextView = view.findViewById(R.id.year)
        var langAuthor: ImageView = view.findViewById(R.id.imageView1)

        init {

            itemView.setOnClickListener {
                // onItemClick?.invoke(langList[adapterPosition])
                Toast.makeText(view.context, "pos = " + adapterPosition, Toast.LENGTH_LONG).show()
                showMessage(langName.text.toString(), view)
            }
//            itemView.setOnLongClickListener {
//
//            }
//            view.setOnCreateContextMenuListener{ menu: ContextMenu, view: View, contextMenuInfo: ContextMenu.ContextMenuInfo ->
//                val Edit = menu.add(this.adapterPosition, 1, 1, "Edit")
//                val Delete = menu.add(this.adapterPosition, 2, 2, "Delete")
//            }
//            itemView.setOnCreateContextMenuListener(this)
        }

//        override fun onCreateContextMenu(menu: ContextMenu, view: View, menuInfo: ContextMenu.ContextMenuInfo) {
//            onCreateContextMenu(menu, view, menuInfo)
////            var popup =  PopupMenu(view.context, view)
////            popup.menuInflater.inflate(R.menu.context_menu, popup.menu)
////            popup.show()
//            val Edit = menu.add(Menu.NONE, 1, 1, "Edit")
//            val Delete = menu.add(Menu.NONE, 2, 2, "Delete")
//        }

//        override fun onMenuItemClick(p0: MenuItem?): Boolean {
//            TODO("Not yet implemented")
//        }

        fun showMessage(str: String?, view: View) {
            //создаем диалоговое окно, параметр – контекст, который берем у view
            val builder = AlertDialog.Builder(view.context)
            builder.setTitle(str) //заголовок диалогового окна
//создаем переменную для нахождения строкового ресурса (см. текст после примера)
//ищем в строковых ресурсах строку с именем, равным значению str и берем её идентификатор
            val strId: Int = context.resources.getIdentifier(str, "string", context.packageName)
            var strValue: String? = ""
            //если ресурс был найден, т.е. strId!=0, то по найденному идентификатору получаем значение строки
            if (strId != 0) strValue = context.getString(strId)
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

        holder.itemView.setOnLongClickListener {
            var popup =  PopupMenu(it.context, it)
            popup.menuInflater.inflate(R.menu.context_menu, popup.menu)
            val m = popup.menu
            popup.setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener,
                PopupMenu.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem): Boolean {
                    Toast.makeText(it.context, item.title, Toast.LENGTH_SHORT).show()
                    return true
                }
            })
            popup.show()
            Toast.makeText(it.context, "name = " + lang.name, Toast.LENGTH_LONG).show()
            return@setOnLongClickListener false
        }
    }

    override fun getItemCount() = langList.size
}