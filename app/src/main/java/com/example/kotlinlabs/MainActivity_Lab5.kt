package com.example.kotlinlabs

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinlabs.databinding.ActivityMainBinding
import android.content.Intent
import android.util.Log
import android.view.ContextMenu
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat

import android.content.pm.PackageManager

import androidx.core.content.ContextCompat

class MainActivity_Lab5 : AppCompatActivity(), MyInterface {
    private lateinit var imageAuthor: ImageView
    private var curentPosInLangList: Int = -1

    private lateinit var binding: ActivityMainBinding
//    private var langList: ArrayList<ProgrLang> = arrayListOf(ProgrLang("Basic", 1964, R.drawable.basic as Object), ProgrLang("Pascal", 1975, R.drawable.pascal as Object),
//        ProgrLang("C", 1972, R.drawable.c as Object), ProgrLang("C++", 1983, R.drawable.cpp as Object),
//        ProgrLang("C#", 2000, R.drawable.c_sharp as Object), ProgrLang("Java", 1995, R.drawable.java as Object),
//        ProgrLang("Python", 1991, R.drawable.python as Object), ProgrLang("JavaScript", 1995),
//        ProgrLang("Kotlin", 2011))

    private var langList: ArrayList<ProgrLang> = arrayListOf(ProgrLang("Basic", 1964, R.drawable.basic.toString()), ProgrLang("Pascal", 1975, R.drawable.pascal.toString()),
        ProgrLang("C", 1972, R.drawable.c.toString()), ProgrLang("C++", 1983, R.drawable.c_sharp.toString()),
        ProgrLang("C#", 2000, R.drawable.c_sharp.toString()), ProgrLang("Java", 1995, R.drawable.java.toString()),
        ProgrLang("Python", 1991, R.drawable.python.toString()), ProgrLang("JavaScript", 1995),
        ProgrLang("Kotlin", 2011))
    private lateinit var recyclerView: RecyclerView
    private lateinit var progLangsAdapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        recyclerView = findViewById(R.id.recyclerView)
        if (savedInstanceState!=null && savedInstanceState.containsKey("langs")) {
            langList = savedInstanceState.getSerializable("langs") as ArrayList<ProgrLang>
            Toast.makeText(this, "From saved", Toast.LENGTH_SHORT).show()
        } else Toast.makeText(this, "From create", Toast.LENGTH_SHORT).show()

        progLangsAdapter = MyAdapter(langList).also { it.myInterface = this }
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
//        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = progLangsAdapter
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show() //сообщение для отслеживания
        outState.putSerializable("langs", langList) //помещаем наш основной массив в хранилище
        super.onSaveInstanceState(outState)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> onAboutClick(item)
            R.id.addNew -> onAddNewClick(item)
            else -> super.onOptionsItemSelected(item)
        }
    }

    private val secondActivityWithResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val newLang = result.data?.getSerializableExtra("newItem") as ProgrLang
            Toast.makeText (recyclerView.context, "New: " + newLang.name + " "+newLang.year, Toast.LENGTH_LONG).show()
            langList.add(0,newLang)
            progLangsAdapter.notifyDataSetChanged()
        }
    }

    private fun onAddNewClick(item: MenuItem): Boolean {
        val newAct = Intent(applicationContext, InputActivity::class.java)
        secondActivityWithResult.launch(newAct)
        return true
    }

    private fun onAboutClick(item: MenuItem): Boolean { //наш метод для показа диалогового окна с информацией
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(item.title)
        //ищем id строкового ресурса с именем about_content – в нем текст для диалогового окна
        val strId = resources.getIdentifier("about_content", "string", packageName)
        var strValue = "" //объявляем пустую строку
        if (strId != 0) strValue = getString(strId) //получаем строку с нужным id
        builder.setMessage(strValue)
        builder.setPositiveButton(android.R.string.ok) { dialog, which -> dialog.dismiss() }
        builder.show()
        return true
    }

    override fun callback(image: ImageView, pos: Int) {
        val permission: String = Manifest.permission.READ_EXTERNAL_STORAGE
        val grant = ContextCompat.checkSelfPermission(applicationContext, permission)
        if (grant != PackageManager.PERMISSION_GRANTED) {
            val permission_list = arrayOfNulls<String>(1)
            permission_list[0] = permission
            ActivityCompat.requestPermissions(this, permission_list, 1)
        }
        curentPosInLangList = pos
        println("image = $image")
        println("pos = $curentPosInLangList")
        imageAuthor = image
        pickImages.launch("image/*")
    }
    private val pickImages = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {uri ->
            imageAuthor.setImageURI(uri)
            println("image uri = $uri")
            langList[curentPosInLangList].picture = uri.toString()
        }
    }

}