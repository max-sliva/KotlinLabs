package com.example.kotlinlabs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.content.Intent
import android.widget.Toast


class InputActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)
        val langName = findViewById<EditText>(R.id.editName)
        val langYear = findViewById<EditText>(R.id.editYear)
        val button = findViewById<Button>(R.id.button)
        val TAG = this.javaClass.getSimpleName()
        button.setOnClickListener {
            val newLang = ProgrLang(langName.text.toString(), Integer.parseInt(langYear.text.toString()))
            Log.i(TAG,""+newLang)
            val intent = Intent()
            intent.putExtra("newItem", newLang)
            setResult(RESULT_OK, intent); //задаем результат текущего активити
            finish()
        }
    }
}