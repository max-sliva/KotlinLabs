package com.example.kotlinlabs

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
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var langList: ArrayList<ProgrLang>
    private lateinit var recyclerView: RecyclerView
    private lateinit var progLangsAdapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        langList = arrayListOf(ProgrLang("Basic", 1964, R.drawable.basic), ProgrLang("Pascal", 1975, R.drawable.pascal),
                               ProgrLang("C", 1972, R.drawable.c), ProgrLang("C++", 1983, R.drawable.cpp),
                               ProgrLang("C#", 2000, R.drawable.c_sharp), ProgrLang("Java", 1995, R.drawable.java),
                               ProgrLang("Python", 1991, R.drawable.python), ProgrLang("JavaScript", 1995), ProgrLang("Kotlin", 2011))
        recyclerView = findViewById(R.id.recyclerView)
        progLangsAdapter = MyAdapter(langList, applicationContext)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = progLangsAdapter


//        progLangsAdapter.onItemClick = { lang ->
////            Toast.makeText (recyclerView.context, "Selected: " + lang + "pos in list = "+langList.indexOf(lang), Toast.LENGTH_LONG).show()
//        }
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
//            if (result.data?.hasExtra(RESULT_TEXT)!!) {
//
//            }
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
}