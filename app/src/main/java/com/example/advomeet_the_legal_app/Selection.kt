package com.example.advomeet_the_legal_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.ArrayAdapter
import android.widget.Spinner

class Selection : AppCompatActivity(),AdapterView.OnItemSelectedListener {

    val opt1 = arrayOf("Criminal","Property","Family","Civil","Digital Media","Medical")
    val opt2 = arrayOf("Delhi","Mumbai","Kolkata","Chennai","Chandigarh","Bangalore","Bhopal","Mukerian")
    val opt3 = arrayOf("<10,000","<20,000","<40,000","<80,000",">80,000")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection)

        val button=findViewById<Button>(R.id.SendToLawyerButton)

        val sp1 = findViewById<Spinner>(R.id.categoryspinner1)
        val sp2 = findViewById<Spinner>(R.id.categoryspinner2)
        val sp3 = findViewById<Spinner>(R.id.categoryspinner3)

        sp1.onItemSelectedListener=this
        sp2.onItemSelectedListener=this
        sp3.onItemSelectedListener=this

        val ad1: ArrayAdapter<*> = ArrayAdapter<Any?>(this,android.R.layout.simple_spinner_item,opt1)
        val ad2: ArrayAdapter<*> = ArrayAdapter<Any?>(this,android.R.layout.simple_spinner_item,opt2)
        val ad3: ArrayAdapter<*> = ArrayAdapter<Any?>(this,android.R.layout.simple_spinner_item,opt3)

        ad1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        ad2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        ad3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        sp1.adapter=ad1
        sp2.adapter=ad2
        sp3.adapter=ad3

        button.setOnClickListener {

            val category = sp1.selectedItem.toString()
            val location = sp2.selectedItem.toString()
            val budget = sp3.selectedItem.toString()


            val intent=Intent(this,Lawyer_list::class.java)
            intent.putExtra("category",category)
            intent.putExtra("location",location)
            intent.putExtra("budget",budget)
            startActivity(intent)
        }
    }
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        //Toast.makeText(applicationContext, opt1[position], Toast.LENGTH_SHORT).show()
    }
    override fun onNothingSelected(parent: AdapterView<*>?) { }
}