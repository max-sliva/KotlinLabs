package com.example.kotlinlabs

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import com.example.kotlinlabs.databinding.ActivityMainBinding
import android.widget.EditText
import android.widget.TextView
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
        langList = arrayListOf(ProgrLang("Basic", 1964), ProgrLang("Pascal", 1975), ProgrLang("C", 1972),
                               ProgrLang("C++", 1983), ProgrLang("C#", 2000), ProgrLang("Java", 1995),
                               ProgrLang("Python", 1991), ProgrLang("JavaScript", 1995), ProgrLang("Kotlin", 2011))
        recyclerView = findViewById(R.id.recyclerView)
        progLangsAdapter = MyAdapter(langList)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = progLangsAdapter

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