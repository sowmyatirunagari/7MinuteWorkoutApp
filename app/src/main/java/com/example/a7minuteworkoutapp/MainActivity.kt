package com.example.a7minuteworkoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import com.example.a7minuteworkoutapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding:ActivityMainBinding?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)



        binding?.flstart?.setOnClickListener {

            val intent=Intent(this,excerciseactivity::class.java)
            startActivity(intent)

        }
        binding?.flBmi?.setOnClickListener {

            val intent=Intent(this,bmiactivity::class.java)
            startActivity(intent)

        }
        binding?.flHistory?.setOnClickListener {

            val intent=Intent(this,historyactivity::class.java)
            startActivity(intent)

        }


    }

    override fun onDestroy() {
        super.onDestroy()

        binding=null
    }
}