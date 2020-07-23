package com.example.tenantyapp

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_defect_detail.*

class DefectDetailActivity : AppCompatActivity()
{
    private var limitedTimeItemLayout: LinearLayout? = null
    private val limitedItemImages: ArrayList<Int> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_defect_detail)

        val window: Window = this.window
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        // finally change the color
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorBackground)

        limitedTimeItemLayout = findViewById(R.id.linearLayoutDefectImage)

        val title = intent.getStringExtra("DefectTitle")
        val postDate = intent.getStringExtra("PostDate")
        val defectStatus = intent.getStringExtra("DefectStatus")
        val defectFrom = intent.getStringExtra("DefectFrom")
        val cardNumber = intent.getIntExtra("cardNumber", 0)
        val gotVideo = intent.getBooleanExtra("gotVideo", false)

        when(cardNumber)
        {
            0 ->
            {
                limitedItemImages.add(R.drawable.defect_image1)
                limitedItemImages.add(R.drawable.defect_image2)
                layoutVideoDefect.visibility = View.VISIBLE
                lblVideoTitle.visibility = View.VISIBLE
                imgVideoImage.setImageResource(R.drawable.defect_image3)
            }

            1 ->
            {
                limitedItemImages.add(R.drawable.defect_image3)
                limitedItemImages.add(R.drawable.defect_image4)
                limitedItemImages.add(R.drawable.defect_image5)
                limitedItemImages.add(R.drawable.defect_image6)
                limitedItemImages.add(R.drawable.defect_image7)
                limitedItemImages.add(R.drawable.defect_image8)
                layoutVideoDefect.visibility = View.GONE
                lblVideoTitle.visibility = View.GONE
            }

            2 ->
            {
                limitedItemImages.add(R.drawable.defect_image7)
                layoutVideoDefect.visibility = View.VISIBLE
                lblVideoTitle.visibility = View.VISIBLE
                imgVideoImage.setImageResource(R.drawable.defect_image8)
            }

            3 ->
            {
                limitedItemImages.add(R.drawable.defect_image9)
                limitedItemImages.add(R.drawable.defect_image10)
                limitedItemImages.add(R.drawable.defect_image11)
                limitedItemImages.add(R.drawable.defect_image1)
                limitedItemImages.add(R.drawable.defect_image3)
                limitedItemImages.add(R.drawable.defect_image5)
                layoutVideoDefect.visibility = View.VISIBLE
                lblVideoTitle.visibility = View.VISIBLE
                imgVideoImage.setImageResource(R.drawable.defect_image4)
            }
        }

        lblDefectTitle.text = title
        lblPostDateValue.text = postDate
        lblDefectFrom.text = defectFrom

        if (defectStatus == "Open")
        {
            lblDefectDetailStatus.text = defectStatus
            lblDefectDetailStatus.setTextColor(resources.getColor(R.color.colorYellow))
        }
        else
        {
            lblDefectDetailStatus.text = defectStatus
        }

        limitedTimeItemLayout!!.removeAllViews()
        for (i in 0 until limitedItemImages.size)
        {
            val layoutInflater:LayoutInflater = LayoutInflater.from(applicationContext)
            val limitedTimeView: View = layoutInflater.inflate(R.layout.item_list_defect_image, null)
            val imgLimitedItem: ImageView = limitedTimeView.findViewById(R.id.imgDefectItemList)

            imgLimitedItem.setImageResource(limitedItemImages[i])
            limitedTimeItemLayout!!.addView(limitedTimeView)
        }

        imgBackDefectDetail.setOnClickListener {
            finish()
        }

        imgNotificationBills.setOnClickListener {
            //Creating the instance of PopupMenu
            //Creating the instance of PopupMenu
            val popup = PopupMenu(this, imgNotificationBills)
            //Inflating the Popup using xml file
            //Inflating the Popup using xml file
            popup.menuInflater.inflate(R.menu.top_bar_defect_menu, popup.getMenu())

            //registering popup with OnMenuItemClickListener
            //registering popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem): Boolean {
                    Toast.makeText(applicationContext, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show()
                    return true
                }
            })

            popup.show() //showing popup menu
        }
    }
}