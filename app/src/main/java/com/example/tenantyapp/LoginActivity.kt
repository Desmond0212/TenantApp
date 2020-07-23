package com.example.tenantyapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val window: Window = this.window
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        // finally change the color
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorBackground)

        layoutAnimation.visibility = View.GONE
        animLoginLoading.visibility = View.GONE

        btnSignIn.setOnClickListener {
            /*val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)*/
            performAnimation(true)
            performLogin()
        }
    }

    private fun performAnimation(isPerform: Boolean)
    {
        if (isPerform)
        {
            layoutAnimation.visibility = View.VISIBLE
            animLoginLoading.visibility = View.VISIBLE
            btnGoogleSignIn.isClickable = false
            btnSignIn.isClickable = false
            btnGoogleSignIn.isEnabled = false
            btnSignIn.isEnabled = false
            btnForgotPassword.isEnabled = false

            animLoginLoading.setAnimation("loading_animation.json")
            animLoginLoading.playAnimation()
            animLoginLoading.loop(true)
        }
        else
        {
            layoutAnimation.visibility = View.GONE
            animLoginLoading.visibility = View.GONE
            btnGoogleSignIn.isClickable = true
            btnSignIn.isClickable = true
            btnGoogleSignIn.isEnabled = true
            btnSignIn.isEnabled = true
            btnForgotPassword.isEnabled = true

            animLoginLoading.setAnimation("loading_animation.json")
            animLoginLoading.cancelAnimation()
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        //dont do anything
    }

    private fun performLogin()
    {
        val email = txtEmail.text.toString()
        val password = txtPassword.text.toString()

        if (email.isEmpty() || password.isEmpty())
        {
            Toast.makeText(this, "Please fill out email/pw.", Toast.LENGTH_SHORT).show()
            return
        }

        val handler = Handler()
        val r = Runnable {
            //what ever you do here will be done after 3 seconds delay.
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (!it.isSuccessful) return@addOnCompleteListener

                    Log.d("Login", "Successfully logged in: ${it.result?.user?.uid}")

                    performAnimation(false)

                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                .addOnFailureListener {
                    performAnimation(false)
                    Toast.makeText(this, "Failed to log in: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }
        handler.postDelayed(r, 1000) //Desmond Temp Changes


    }
}