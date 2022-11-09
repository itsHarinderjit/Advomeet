package com.example.advomeet_the_legal_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Client_center : AppCompatActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_center)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        val bundle: Bundle? = intent.extras
        val uid = bundle!!.getString("uid")

        // will use firebase to write users name

        val findlawyer = findViewById<ImageButton>(R.id.lawyerRbutton)
        val messages = findViewById<ImageButton>(R.id.messages)
        val logout = findViewById<ImageButton>(R.id.logout)
        val editprofile = findViewById<ImageButton>(R.id.editProfile)

        findlawyer.setOnClickListener {
            val intent = Intent(this, Selection::class.java)
            intent.putExtra("uid", uid)
            startActivity(intent)
        }

        messages.setOnClickListener {
            val intent = Intent(this, ChatFragment::class.java)
            startActivity(intent)
        }

        logout.setOnClickListener {
            googleSignInClient.signOut().addOnCompleteListener {
                val intent= Intent(this, MainActivity::class.java)
                intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        }

        editprofile.setOnClickListener {
            val intent = Intent(this, Registerinfo::class.java)
            intent.putExtra("uid", uid)
            intent.putExtra("isnew", false)
            startActivity(intent)
        }
    }
}