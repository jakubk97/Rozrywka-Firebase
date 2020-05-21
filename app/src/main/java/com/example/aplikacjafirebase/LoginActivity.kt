package com.example.aplikacjafirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        btn_sign_up.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }

        btn_log_in.setOnClickListener {
            login()
        }

    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    fun login() {
        if (tv_username.text.toString().trim().isEmpty()) {
            tv_username.error = "Please enter email"
            tv_username.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(tv_username.text.toString()).matches()) {
            tv_username.error = "Please enter valid email"
            tv_username.requestFocus()
            return
        }

        if (tv_password.text.toString().trim().isEmpty()) {
            tv_password.error = "Please enter password"
            tv_password.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(
            tv_username.text.trim().toString(),
            tv_password.text.trim().toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("Username",tv_username.text.trim().toString().replace(",","").replace(".","").replace("#","").replace("@",""))
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }

    fun updateUI(currentUser: FirebaseUser?) {

    }

}
