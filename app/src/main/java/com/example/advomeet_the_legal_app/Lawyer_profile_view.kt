package com.example.advomeet_the_legal_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.advomeet_the_legal_app.databinding.ActivityLawyerProfileViewBinding
import com.example.advomeet_the_legal_app.databinding.ActivityRegisterinfoBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlin.math.sin

class Lawyer_profile_view : AppCompatActivity() {

    private lateinit var binding: ActivityLawyerProfileViewBinding
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lawyer_profile_view)

        val bundle:Bundle?=intent.extras
        val uid=bundle!!.getString("uid")

        binding = ActivityLawyerProfileViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(uid!=null){
            readData(uid)
        }
        binding.message.setOnClickListener {
            val intent=Intent(this,ChatActivity::class.java)
            intent.putExtra("lid",uid)
            startActivity(intent)
        }
//        Toast.makeText(this,"$uid",Toast.LENGTH_LONG).show()
    }
    private fun readData(uid: String) {
        database= FirebaseDatabase.getInstance().getReference("Lawyer_data")
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
                val experience = it.child("experience").value
                val education = it.child("education").value
                val iscriminal = it.child("iscriminal").value
                val isdigital = it.child("isdigital").value
                val ismedical = it.child("ismedical").value
                val isproperty = it.child("isproperty").value
                val since = it.child("since").value
                val barid = it.child("barid").value

                var category=""

                val fnametxt=fname.toString()
                val lnametxt=lname.toString()

                val name= "$fnametxt $lnametxt"

                binding.firstname.text=name
                binding.phonenumber1.text=phone1.toString()
                binding.phonenumber2.text=phone2.toString()
                binding.state.text=state.toString()
                binding.district.text=district.toString()
                binding.pincode.text=pincode.toString()
                binding.city.text=city.toString()
                binding.about.text=about.toString()
                binding.experience.text=experience.toString()
                binding.education.text=education.toString()
                binding.since.text= since.toString()
                binding.barid.text=barid.toString()
                binding.gender.text=gender.toString()

                if(iscriminal.toString()=="true") category="criminal"
                if(isdigital.toString()=="true") category="$category digital"
                if(ismedical.toString()=="true") category="$category medical"
                if(isproperty.toString()=="true") category="$category property"

                binding.category.text=category


//                Toast.makeText(this,"$fname $lname",Toast.LENGTH_SHORT).show()
            }
            else{
                // toast data does not exist
            }
        }.addOnFailureListener {
            // toast data cannot be fetched
        }
    }
}