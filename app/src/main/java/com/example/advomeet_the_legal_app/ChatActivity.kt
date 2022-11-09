package com.example.advomeet_the_legal_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.Toast
import com.example.advomeet_the_legal_app.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*
import kotlin.collections.ArrayList

class ChatActivity : AppCompatActivity() {

    private lateinit var binding:ActivityChatBinding
    private lateinit var database:FirebaseDatabase

    private lateinit var senderuid:String
    private lateinit var receiveruid:String

    private lateinit var senderRoom:String
    private lateinit var receiverRoom:String

    private lateinit var list : ArrayList<MessageModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        senderuid=FirebaseAuth.getInstance().uid.toString()
        receiveruid=intent.getStringExtra("lid")!!  //alternative of bundle

        list= ArrayList()

        senderRoom=senderuid+receiveruid
        receiverRoom=receiveruid+senderuid

        binding= ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database= FirebaseDatabase.getInstance()

        binding.imageView4.setOnClickListener {
            if(binding.messagebox.text.isEmpty()){
                Toast.makeText(this,"No message entered",Toast.LENGTH_SHORT).show()
            }
            else{
                val message=MessageModel(binding.messagebox.text.toString(),senderuid, Date().time)

                val randomkey=database.reference.push().key

                database.reference.child("Chats")
                    .child(senderRoom).child("message").child(randomkey!!).setValue(message).addOnSuccessListener {

                        database.reference.child("Chats").child(receiverRoom).child("message").child(randomkey).setValue(message).addOnSuccessListener {
                            // message sent
                            binding.messagebox.text=null
                        }
                    }
            }
        }

        database.reference.child("Chats").child(senderRoom).child("message")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()

                    for(snapshot1 in snapshot.children){
                        val data = snapshot1.getValue(MessageModel::class.java)
                        list.add(data!!)
                    }

                    binding.chatrecyclerview.adapter=MessageAdapter(this@ChatActivity,list)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@ChatActivity,"Error :$error",Toast.LENGTH_SHORT).show()
                }

            })
    }
}