package com.example.kotlinlabs

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import com.example.kotlinlabs.databinding.ActivityMainBinding
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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

        val langName = findViewById<EditText>(R.id.editName)
        val langYear = findViewById<EditText>(R.id.editYear)
        val button = findViewById<Button>(R.id.button)
        val TAG = this.javaClass.getSimpleName()
        button.setOnClickListener {
            val newLang = ProgrLang(langName.text.toString(), Integer.parseInt(langYear.text.toString()))
            Log.i(TAG,""+newLang)
            langList.add(0,newLang)
            progLangsAdapter.notifyDataSetChanged()
            langName.setText("")
            langYear.setText("")
        }

//        progLangsAdapter.onItemClick = { lang ->
////            Toast.makeText (recyclerView.context, "Selected: " + lang + "pos in list = "+langList.indexOf(lang), Toast.LENGTH_LONG).show()
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}