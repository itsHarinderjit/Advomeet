package com.example.advomeet_the_legal_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Roleselection : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_roleselection)


        val bundle:Bundle?=intent.extras
        val uid=bundle!!.getString("uid")
        val clbutton=findViewById<Button>(R.id.clientRbutton)
        val lwbutton=findViewById<Button>(R.id.lawyerRbutton)

        clbutton.setOnClickListener {
            val intent=Intent(this,Registerinfo::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra("uid",uid)
            intent.putExtra("isnew",true)
            startActivity(intent)
        }
        lwbutton.setOnClickListener {
            val intent=Intent(this,Lawyer_registeration::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra("uid",uid)
            intent.putExtra("isnew",true)
            startActivity(intent)
        }
    }
}