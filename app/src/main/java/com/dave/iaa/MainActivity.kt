package com.dave.iaa

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dropdown: Spinner = findViewById(R.id.spinner)
        val items = resources.getStringArray(R.array.locations)
        val adapter = ArrayAdapter(this, R.layout.simple_dropdown_item, items)
        dropdown.adapter = adapter
    }
}