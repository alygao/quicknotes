package com.example.a4_basic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.View
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {
    private val model: Model by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        model.status.observe(this) {
            supportActionBar?.subtitle = it
        }
        supportActionBar?.subtitle = "(0 notes)"

        model.actionTaken.observe(this) {

            val view: View = findViewById(R.id.myCoordinatorLayout)
            val snackBar = Snackbar.make(view, it, Snackbar.LENGTH_SHORT)
            snackBar.show()
        }
    }




}