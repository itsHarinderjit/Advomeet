package com.example.advomeet_the_legal_app

import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.text.buildSpannedString
import com.example.advomeet_the_legal_app.databinding.ActivityMainBinding
import com.example.advomeet_the_legal_app.databinding.ActivityRegisterinfoBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Registerinfo : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterinfoBinding
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registerinfo)

        val bundle:Bundle?=intent.extras
        val isnew = bundle!!.getBoolean("isnew")
        val uid = bundle.getString("uid")

        binding = ActivityRegisterinfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(!isnew){
            if(uid!!.isNotEmpty()){
                readData(uid)
            }
            else{
                // toast k nahi huya
            }
        }


        binding.register.setOnClickListener {
            try{
                val fname = binding.firstname.text.toString()
                val lname = binding.lastname.text.toString()
                val ckphoneno1 = binding.phonenumber1.text.toString()
                val ckphoneno2 = binding.phonenumber2.text.toString()
                val phoneno1 = binding.phonenumber1.text.toString()
                val phoneno2 = binding.phonenumber2.text.toString()
                val city = binding.city.text.toString()
                val district = binding.district.text.toString()
                val state = binding.state.text.toString()
                val pincode = binding.pincode.text.toString().toInt()

                val selectid = binding.radiogrpid.checkedRadioButtonId
                val radbtn=findViewById<RadioButton>(selectid)
                val gender = radbtn.text.toString()

                if((pincode<100000)||(pincode>999999)){
                    Toast.makeText(this,"Invalid pincode",Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                database = FirebaseDatabase.getInstance().getReference("Client_data")
                val clientData = Client_data(fname,lname,phoneno1,phoneno2, city, district, state, pincode,gender)
                if (uid != null) {
                    database.child(uid).setValue(clientData).addOnSuccessListener {
                        //create a intent
                        Toast.makeText(this,"Welcome to Advomeet $fname",Toast.LENGTH_SHORT).show()
                        val intent=Intent(this,Client_center::class.java)
                        intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        intent.putExtra("uid",uid)
                        startActivity(intent)
                    }.addOnFailureListener {
                        Toast.makeText(this,"Sorry,something went wrong!!!",Toast.LENGTH_SHORT).show()
                    }
                }
            }
            catch (e:Exception){
                Toast.makeText(this,"$e",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun readData(uid: String) {
        database=FirebaseDatabase.getInstance().getReference("Client_data")
        database.child(uid).get().addOnSuccessListener {
            if(it.exists()){
                val fname=it.child("fname").value
                val lname=it.child("lname").value
                val gender = it.child("gender").value
                val phone1 = it.child("phoneno1").value
                val phone2 = it.child("phoneno2").value
                val state = it.child("state").value
                val district = it.child("district").value
                val pincode = it.child("pincode").value
                val city = it.child("city").value

                binding.firstname.setText(fname.toString())
                binding.lastname.setText(lname.toString())
                binding.phonenumber1.setText(phone1.toString())
                binding.phonenumber2.setText(phone2.toString())
                binding.state.setText(state.toString())
                binding.district.setText(district.toString())
                binding.pincode.setText(pincode.toString())
                binding.city.setText(city.toString())

                if(binding.radioButton1.text==gender.toString()) binding.radioButton1.isChecked=true
                if(binding.radioButton2.text==gender.toString()) binding.radioButton2.isChecked=true
                if(binding.radioButton3.text==gender.toString()) binding.radioButton3.isChecked=true

                Toast.makeText(this,"$fname $lname",Toast.LENGTH_SHORT).show()
            }
            else{
                // toast data does not exist
            }
        }.addOnFailureListener {
            // toast data cannot be fetched
        }
    }
}