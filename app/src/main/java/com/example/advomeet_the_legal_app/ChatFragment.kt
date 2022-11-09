package com.example.advomeet_the_legal_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.*
import java.lang.Exception

class ChatFragment : AppCompatActivity() {

    private lateinit var newRecyclerView: RecyclerView
    private var database:FirebaseDatabase?=null
    private var dbref : FirebaseDatabase?=null
    lateinit var userList : ArrayList<Lawyer_database>
    lateinit var userid : ArrayList<String>
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_fragment)
//        return super.onCreateView(inflater, container, savedInstanceState)
//        return inflater.inflate(R.layout.activity_chat_fragment,container,false)
            newRecyclerView = findViewById(R.id.userlistrecyclerview)
            newRecyclerView.layoutManager = LinearLayoutManager(this)
            newRecyclerView.setHasFixedSize(true)

            database= FirebaseDatabase.getInstance()
            dbref= FirebaseDatabase.getInstance()
            userList= ArrayList()
            userid= ArrayList()
            auth = FirebaseAuth.getInstance()
            val firebaseUser = auth.currentUser!!
            val uid = firebaseUser.uid


        database!!.reference.child("Lawyer_data")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshotout: DataSnapshot) {
                    userList.clear() // yaha bhi dekhna hai

                    for(snapshot1 in snapshotout.children){

                        val user = snapshot1.getValue(Lawyer_database::class.java)
//                        if(user.uid!=)
                        val key=snapshot1.key.toString()
                        val id=uid+key
                        dbref!!.reference.child("Chats")
                            .addValueEventListener(object : ValueEventListener{
                                override fun onDataChange(snapshotin: DataSnapshot) {
                                    for(snapshot2 in snapshotin.children){
                                        val chatid = snapshot2.key.toString()
                                        if (chatid==id){
                                            if(user!=null){
                                                userList.add(user)
                                                userid.add(key)
                                            }
                                        }
                                    }
                                    userList.distinct().toList()
//                                    Toast.makeText(this@ChatFragment,"${userList.size}",Toast.LENGTH_SHORT).show()
                                    val adapter=Lawyer_adapter(userList)
                                    newRecyclerView.adapter = adapter
                                    adapter.setOnItemClickListner(object : Lawyer_adapter.onItemClickListner{
                                        override fun onItemClick(position: Int) {
                                            val intent = Intent(this@ChatFragment,ChatActivity::class.java)
                                            intent.putExtra("lid",userid[position])
                                            startActivity(intent)
                                        }

                                    })
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    TODO("Not yet implemented")
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