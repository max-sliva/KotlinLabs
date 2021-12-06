package com.example.kotlinlabs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

internal class MyAdapter(private var langList: ArrayList<ProgrLang>): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var langName: TextView = view.findViewById(R.id.name)
        var langYear: TextView = view.findViewById(R.id.year)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_list, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = langList[position]
        holder.langName.text = movie.name
        holder.langYear.text = movie.year.toString()
    }

    override fun getItemCount(): Int {
        return langList.size
    }
}