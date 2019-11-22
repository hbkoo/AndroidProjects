package com.example.acer.plugin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        add(1,2)
    }

    data class Animal (val name:String ,val age:Int ?= null)


    fun add(a: Int, b: Int): Int {
        val animal1 = Animal("dog")
        val animal2 = Animal("Monkey",2)
        Toast.makeText(this,"$animal1",Toast.LENGTH_SHORT).show();

        return a + b
    }

}
