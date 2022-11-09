package com.example.advomeet_the_legal_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.advomeet_the_legal_app.databinding.ActivityLawyerRegisterationBinding
import com.example.advomeet_the_legal_app.databinding.ActivityRegisterinfoBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Lawyer_registeration : AppCompatActivity() {

    private lateinit var binding: ActivityLawyerRegisterationBinding
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lawyer_registeration)

        binding = ActivityLawyerRegisterationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle:Bundle?=intent.extras
        val uid=bundle!!.getString("uid")
        val isnew=bundle.getBoolean("isnew")

        if(!isnew){
            if(uid!!.isNotEmpty()){
                readData(uid)
            }
            else{
                // toast k nahi huya
            }
        }


        binding.register.setOnClickListener {
            try {
                val fname = binding.firstname.text.toString()
                val lname = binding.lastname.text.toString()
                val phoneno1 = binding.phonenumber1.text.toString()
                val phoneno2 = binding.phonenumber2.text.toString()
                val city = binding.city.text.toString()
                val district = binding.district.text.toString()
                val state = binding.state.text.toString()
                val pincode = binding.pincode.text.toString().toInt()

                val selectid = binding.genderRadiogrp.checkedRadioButtonId
                val radbtn=findViewById<RadioButton>(selectid)
                val gender = radbtn.text.toString()

                val about = binding.about.text.toString()
                val barid = binding.barid.text.toString()
                val since = binding.since.text.toString()
                val education = binding.editTexteducation.text.toString()
                val experience = binding.editTextTextExperience.text.toString()
                val property = binding.property.isChecked
                val media = binding.media.isChecked
                val criminal = binding.criminal.isChecked
                val medical = binding.medical.isChecked

                if((pincode<100000)||(pincode>999999)){
                    Toast.makeText(this,"Invalid pincode",Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }



                database = FirebaseDatabase.getInstance().getReference("Lawyer_data")
                val lawyerData = Lawyer_database(fname,lname,phoneno1,phoneno2,city,district,state,pincode,gender,about,property,criminal,medical,media,barid,since,education,experience,)
                if (uid != null) {
                    database.child(uid).setValue(lawyerData).addOnSuccessListener {
                        //create a intent
                        if(isnew) Toast.makeText(this,"Welcome to Advomeet $fname",Toast.LENGTH_SHORT).show()

                        val intent= Intent(this,Lawyer_center::class.java)
                        intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        intent.putExtra("uid",uid)
                        startActivity(intent)
                    }.addOnFailureListener {
                        Toast.makeText(this,"Sorry,something went wrong!!!",Toast.LENGTH_SHORT).show()
                    }
                }
            }
            catch (e:Exception){
                Toast.makeText(this,"Data not entered",Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun readData(uid: String) {
        database=FirebaseDatabase.getInstance().getReference("Lawyer_data")
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
                val about = it.child("about").value
                val barid = it.child("barid").value
                val since = it.child("since").value
                val education = it.child("education").value
                val experience = it.child("experience").value
                val iscriminal = it.child("iscriminal").value
                val isdigital = it.child("isdigital").value
                val ismedical = it.child("ismedical").value
                val isproperty = it.child("isproperty").value


                binding.firstname.setText(fname.toString())
                binding.lastname.setText(lname.toString())
                binding.phonenumber1.setText(phone1.toString())
                binding.phonenumber2.setText(phone2.toString())
                binding.state.setText(state.toString())
                binding.district.setText(district.toString())
                binding.pincode.setText(pincode.toString())
                binding.city.setText(city.toString())
                binding.about.setText(about.toString())
                binding.barid.setText(barid.toString())
                binding.since.setText(since.toString())
                binding.editTexteducation.setText(education.toString())
                binding.editTextTextExperience.setText(experience.toString())

                if(binding.radioButton1.text==gender.toString()) binding.radioButton1.isChecked=true
                if(binding.radioButton2.text==gender.toString()) binding.radioButton2.isChecked=true
                if(binding.radioButton3.text==gender.toString()) binding.radioButton3.isChecked=true

                if(iscriminal.toString()=="true") binding.criminal.isChecked=true
                if(isdigital.toString()=="true") binding.media.isChecked=true
                if(ismedical.toString()=="true") binding.medical.isChecked=true
                if(isproperty.toString()=="true") binding.property.isChecked=true

            }
            else{
                // toast data does not exist
            }
        }.addOnFailureListener {
            // toast data cannot be fetched
        }
    }
}