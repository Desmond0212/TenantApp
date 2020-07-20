package com.example.tenantyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_new_tenant_creating.*

class NewTenantCreatingActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_tenant_creating)

        val window: Window = this.window
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        // finally change the color
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorBackground)

        animCreatingNewTenant.setAnimation("animation_1.json")
        animCreatingNewTenant.playAnimation()
        animCreatingNewTenant.loop(true)

        val signInIntent = Intent(this, TenantDetailActivity::class.java)
        val timer = object : Thread()
        {
            override fun run()
            {
                try
                {
                    sleep(8000)
                } catch (e: InterruptedException)
                {
                    e.printStackTrace()
                } finally
                {
                    startActivity(signInIntent)
                    finish()
                }
            }
        }
        timer.start()
    }
}