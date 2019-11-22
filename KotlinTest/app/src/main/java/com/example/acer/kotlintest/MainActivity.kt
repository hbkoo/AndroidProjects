package com.example.acer.kotlintest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val li = listOf(Animal("dog"),Animal("Monkey",8))

        val l = 0


    }

    data class Animal(val name: String, val age: Int? = null)


}
