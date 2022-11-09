package com.example.advomeet_the_legal_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class Lawyer_center : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lawyer_center)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        val bundle:Bundle?=intent.extras
        val uid = bundle!!.getString("uid")
        // will use firebase to write users name

        val messages = findViewById<ImageButton>(R.id.messages)
        val logout = findViewById<ImageButton>(R.id.logout)
        val editprofile = findViewById<ImageButton>(R.id.editProfile)

        messages.setOnClickListener {
            val intent=Intent(this,Lawyer_chat_fragment::class.java)
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
            val intent= Intent(this,Lawyer_registeration::class.java)
            intent.putExtra("uid",uid)
            startActivity(intent)
        }
    }
}