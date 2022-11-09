package com.example.advomeet_the_legal_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class Lawyer_list : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<Lawyer_database>
    private lateinit var idArray: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lawyer_list)

        val bundle:Bundle?=intent.extras
        val category=bundle!!.getString("category")
        val location=bundle.getString("location")
        val budget=bundle.getString("budget")


        newRecyclerView = findViewById(R.id.recyclerview)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)

        newArrayList= arrayListOf<Lawyer_database>()
        idArray = arrayListOf<String>()
        getUserData(location,category,budget)
    }
    private fun getUserData(location:String?,category:String?,budget:String?){

        dbref = FirebaseDatabase.getInstance().getReference("Lawyer_data")

        dbref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val lawyer = userSnapshot.getValue(Lawyer_database::class.java)
                        val id = userSnapshot.key.toString()

//                        Toast.makeText(this@Lawyer_list,"$id",Toast.LENGTH_LONG).show()


                        if(lawyer?.city==location){
                            if((lawyer?.iscriminal==true) && (category=="Criminal")){
                                newArrayList.add(lawyer)
                                idArray.add(id)
                            }
                            else if((lawyer?.ismedical==true) && (category=="Medical")){
                                newArrayList.add(lawyer)
                                idArray.add(id)
                            }
                            else if((lawyer?.isproperty==true) && (category=="Property")){
                                newArrayList.add(lawyer)
                                idArray.add(id)
                            }
                            else if((lawyer?.isdigital==true) && (category=="Digital")){
                                newArrayList.add(lawyer)
                                idArray.add(id)
                            }
                            else{
                                Toast.makeText(this@Lawyer_list,"no lawyers found",Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
//                    Toast.makeText(this@Lawyer_list,"${}",Toast.LENGTH_SHORT).show()

                    val adapter=Lawyer_adapter(newArrayList)
                    newRecyclerView.adapter = adapter
                    adapter.setOnItemClickListner(object : Lawyer_adapter.onItemClickListner{
                        override fun onItemClick(position: Int) {
                            // here we will do what we want to do after user click on the list

                            val intent = Intent(this@Lawyer_list,Lawyer_profile_view::class.java)
                            intent.putExtra("uid",idArray[position])
                            startActivity(intent)
                        }

                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}