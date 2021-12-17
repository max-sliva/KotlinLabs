package com.example.kotlinlabs

import android.app.AlertDialog
import android.content.Context
import android.view.*
import android.widget.*
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView

 class MyAdapter_old(private var langList: ArrayList<ProgrLang>,
                     private var context: Context) :
    RecyclerView.Adapter<MyAdapter_old.MyViewHolder>() , View.OnCreateContextMenuListener{
     var myInterface: MyInterface? = null
     class MyViewHolder(view: View, onCreateContextMenuListener: View.OnCreateContextMenuListener) : RecyclerView.ViewHolder(view)
        //, View.OnCreateContextMenuListener //, PopupMenu.OnMenuItemClickListener
    {
        var langName: TextView = view.findViewById(R.id.name)
        var langYear: TextView = view.findViewById(R.id.year)
        var langAuthor: ImageView = view.findViewById(R.id.imageView1)

        //var activity = context as Activity
        init {
            itemView.setOnClickListener {
                // onItemClick?.invoke(langList[adapterPosition])
                Toast.makeText(view.context, "pos = " + adapterPosition, Toast.LENGTH_LONG).show()
                showMessage(langName.text.toString(), view)
            }
//            val act = itemView.context as Activity
//            view.setOnCreateContextMenuListener{ menu: ContextMenu, view: View, contextMenuInfo: ContextMenu.ContextMenuInfo ->
//                val Edit = menu.add(this.adapterPosition, 1, 1, "Edit")
//                val Delete = menu.add(this.adapterPosition, 2, 2, "Delete")
//            }
            itemView.setOnCreateContextMenuListener(onCreateContextMenuListener)
        }

//        override fun onCreateContextMenu(menu: ContextMenu, view: View, menuInfo: ContextMenu.ContextMenuInfo?) {
////            var popup =  PopupMenu(view.context, view)
////            popup.menuInflater.inflate(R.menu.context_menu, popup.menu)
////            popup.show()
////            popup.setOnMenuItemClickListener(this)
//            val edit = menu.add(Menu.NONE, 1, 1, "Edit")
//            edit.setOnMenuItemClickListener { myInterface?.callback(langName.toString(), langAuthor);  false }
//            val delete = menu.add(Menu.NONE, 2, 2, "Delete")
//
//        }

//        override fun onMenuItemClick(item: MenuItem?): Boolean {
//            return when (item?.itemId) {
////                R.id.change_picture -> onChangePicture(item)
//                else -> {false}
//            }
//        }
//        private val pickImages = (context as MainActivity).registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
//            uri?.let {uri ->
//                langAuthor.setImageURI(uri)
//            }
//        }
//
//        private fun onChangePicture(item: MenuItem): Boolean {
//            Toast.makeText(itemView.context,"name = ${this.langName.text}", Toast.LENGTH_SHORT).show()
//            pickImages.launch("image/*")
//            return false
//        }

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
//            var popup =  PopupMenu(view.context, view)
//            popup.menuInflater.inflate(R.menu.context_menu, popup.menu)
//            popup.show()
//            popup.setOnMenuItemClickListener(this)
         val edit = menu.add(Menu.NONE, 1, 1, "Edit")
         edit.setOnMenuItemClickListener {
//             myInterface?.callback(langName.toString(), langAuthor);
             var pic = (view as LinearLayout).getChildAt(1) as ImageView
             println("Edit "+pic)
//             myInterface?.callback(pic)
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
        holder.langName.text = lang.name
        holder.langYear.text = lang.year.toString()
        holder.langAuthor.setImageResource(lang.picture as Int)
//        holder.itemView.setOnLongClickListener {
//            var popup =  PopupMenu(it.context, it)
//            popup.menuInflater.inflate(R.menu.context_menu, popup.menu)
//            val m = popup.menu
//            popup.setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener,
//                PopupMenu.OnMenuItemClickListener {
//                override fun onMenuItemClick(item: MenuItem): Boolean {
//                    Toast.makeText(it.context, item.title, Toast.LENGTH_SHORT).show()
//                    return true
//                }
//            })
//            popup.show()
//            Toast.makeText(it.context, "name = " + lang.name, Toast.LENGTH_LONG).show()
//            return@setOnLongClickListener false
//        }
    }

    override fun getItemCount() = langList.size
}