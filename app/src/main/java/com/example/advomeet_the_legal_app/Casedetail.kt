package com.example.advomeet_the_legal_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class Casedetail : AppCompatActivity() ,AdapterView.OnItemSelectedListener{

    val opt1 = arrayOf("Criminal","Property","Digital Media","Medical")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_casedetail)

        val bundle:Bundle?=intent.extras
        val uid=bundle!!.getString("lid")

        val sp1=findViewById<Spinner>(R.id.categoryspinner)
        val details=findViewById<EditText>(R.id.EnterDetailsOfMatter)
        val button=findViewById<Button>(R.id.SendToLawyerButton)

        sp1.onItemSelectedListener=this

        val ad1: ArrayAdapter<*> = ArrayAdapter<Any?>(this,android.R.layout.simple_spinner_item,opt1)
        ad1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp1.adapter=ad1

        button.setOnClickListener {
            val category = sp1.selectedItem.toString()
            val detailstxt=details.text.toString()
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        TODO("Not yet implemented")
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}