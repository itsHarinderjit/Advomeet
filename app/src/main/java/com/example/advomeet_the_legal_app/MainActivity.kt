package com.example.advomeet_the_legal_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.lang.Exception

class MainActivity : AppCompatActivity() {


    companion object {
        private const val RC_SIGN_IN = 120
    }

    //    val exe = findViewById<TextView>(R.id.exceptionH)
    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var database: DatabaseReference
    private lateinit var senderuid: String
//    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<ImageButton>(R.id.googlesignup)


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        mAuth = FirebaseAuth.getInstance()

        button.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if (task.isSuccessful) {
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {

                }
            } else {

            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
//        mAuth.signInWithCredential(credential)
//            .addOnCompleteListener(this){
//                if(it.additionalUserInfo!!.isNewUser)
//
//
//                if(it.isSuccessful){
//                    Toast.makeText(this,"ho gaya bhai",Toast.LENGTH_SHORT).show()
//                    val intent= Intent(this,Roleselection::class.java)
//                    startActivity(intent)
//                }
//                else{
//                    //some log message
//                }
//            }
        mAuth.signInWithCredential(credential)
            .addOnSuccessListener(this) {
                val firebaseUser = mAuth.currentUser!!
                val uid = firebaseUser.uid
                if (it.additionalUserInfo!!.isNewUser) {
                    val intent = Intent(this, Roleselection::class.java)
                    intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    intent.putExtra("uid", uid)
                    startActivity(intent)
                } else {
                    database = FirebaseDatabase.getInstance().getReference("Lawyer_data")
                    senderuid = FirebaseAuth.getInstance().uid.toString()     //senders uid
                    database.child(senderuid).get().addOnSuccessListener {
                        if (it.exists()) {
                            val intent = Intent(this, Lawyer_center::class.java)
                            intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            intent.putExtra("uid", uid)
                            startActivity(intent)
                        } else {
                            database = FirebaseDatabase.getInstance().getReference("Client_data")
                            senderuid = FirebaseAuth.getInstance().uid.toString() //senders uid
                            database.child(senderuid).get().addOnSuccessListener {
                                if (it.exists()) {
                                    val intent = Intent(this, Client_center::class.java)
                                    intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    intent.putExtra("uid", uid)
                                    startActivity(intent)
                                }
                            }
                        }
                    }
                }
            }
    }
}