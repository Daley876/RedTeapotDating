package com.example.redteapotdating.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.redteapotdating.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.main_content,ProfileViewFragment())
            .commit()
    }
}