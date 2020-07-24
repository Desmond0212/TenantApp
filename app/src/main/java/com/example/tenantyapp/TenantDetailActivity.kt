package com.example.tenantyapp

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.os.Bundle
import android.os.Environment
import android.util.AttributeSet
import android.util.Base64
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_tenant_detail.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


class TenantDetailActivity : AppCompatActivity()
{
    //Signature
    var ConvertedBitmap: String? = null
    var btn_get_sign: Button? = null
    var mClear: Button? = null
    var mCancel: ImageButton? = null
    var img_sig: ImageView? = null
    var builder: AlertDialog.Builder? = null
    var layout: LinearLayout? = null

    private val STORAGE_PERMISSION_CODE = 1
    private val Contact_PERMISSION_CODE = 1

    companion object
    {
        var canvas: Canvas? = null
        var mContent: LinearLayout? = null
        var view: View? = null
        var mSignature: signature? = null
        var bitmap: Bitmap? = null
        var dialog: Dialog? = null
        var mGetSign: android.widget.Button? = null
    }
    //Signature

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tenant_detail)

        val window: Window = this.window
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        // finally change the color
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorBackground)

        img_sig = findViewById(R.id.img_sign) as ImageView
        layout = findViewById(R.id.layout) as LinearLayout
        btn_get_sign = findViewById(R.id.btnAddSignature) as Button
        dialog = Dialog(this)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.dialog_signature2)
        dialog!!.setCancelable(true)

        getExtraString()

        imgBackButton.setOnClickListener {
            finish()
        }

        imgButtonCopy.setOnClickListener {
            Toast.makeText(this, "Link Copied...", Toast.LENGTH_SHORT).show()
        }

        layoutCopyButton.setOnClickListener {
            Toast.makeText(this, "Link Copied...", Toast.LENGTH_SHORT).show()
        }

        btnDoneTenantDetail.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        topRightIcon.setOnClickListener {
            //Creating the instance of PopupMenu
            //Creating the instance of PopupMenu
            val popup = PopupMenu(this, topRightIcon)
            //Inflating the Popup using xml file
            //Inflating the Popup using xml file
            popup.menuInflater.inflate(R.menu.top_bar_menu, popup.getMenu())

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

        /*
        * Signature
        * */

        Storagepermission()
        //Contactpermission()

        btn_get_sign!!.setOnClickListener {
            ConvertedBitmap = ""
            dialogAction()
        }

        /*
        * Signature
        * */
    }

    // Function for Digital Signature
    private fun dialogAction()
    {
        mContent = dialog!!.findViewById<View>(R.id.linearLayout) as LinearLayout
        mSignature = signature(applicationContext, null)
        mSignature?.setBackgroundColor(Color.WHITE)
        // Dynamically generating Layout through java code
        mContent!!.addView(
            mSignature,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        mClear = dialog!!.findViewById<View>(R.id.clear) as Button
        mGetSign = dialog!!.findViewById<View>(R.id.getsign) as Button
        mGetSign!!.isEnabled = false
        mCancel = dialog!!.findViewById<View>(R.id.cancel) as ImageButton
        view = mContent

        mClear!!.setOnClickListener {
            mSignature?.clear()
            bitmap = null
            img_sig!!.setImageDrawable(null)
        }
        mGetSign!!.setOnClickListener {
            // Creating Separate Directory for saving Generated Images
            val DIRECTORY = Environment.getExternalStorageDirectory()
                .path + "/DCIM/TenantApp/" //CREAMLINE
            val pic_name =
                SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
                    .format(Date())
            val StoredPath = "$DIRECTORY$pic_name.png"
            //save to static string
            ConvertedBitmap = StoredPath
            // Method to create Directory, if the Directory doesn't exists
            val file = File(DIRECTORY)
            if (!file.exists()) {
                file.mkdir()
                /*Toast.makeText(getApplicationContext(), "Folder created", Toast.LENGTH_SHORT).show();*/
                val snackbar: Snackbar =
                    Snackbar.make(layout!!, "Folder created successfully!", Snackbar.LENGTH_LONG)
                snackbar.show()
            }
            Log.v("log_tag", "Panel Saved")
            view!!.isDrawingCacheEnabled = true
            mSignature?.save(view!!, StoredPath)
            dialog!!.dismiss()
            if (img_sig?.equals("")!!)
            {
                builder!!.setTitle("Reminder!")
                builder!!.setMessage("Please make sure all required fields are not empty. Before getting the driver's Signature")
                //builder.setIcon(R.drawable.ic_priority_high_black_24dp);
                builder!!.setPositiveButton(
                    "OK"
                ) { dialog, which -> }
                builder!!.show()
            }
            else
            {
                lblEmptySign.visibility = View.GONE
                val snackbar: Snackbar = Snackbar.make(layout!!, "Signature saved successfully!", Snackbar.LENGTH_LONG)
                snackbar.show()

                val imgFile = File(ConvertedBitmap)
                if (imgFile.exists())
                {
                    bitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
                    val myImage = findViewById(R.id.img_sign) as ImageView
                    myImage.setImageBitmap(bitmap)
                }
            }
        }
        mCancel!!.setOnClickListener {
            Log.v("log_tag", "Panel Canceled")
            if (img_sig == null)
            {
                mSignature?.clear()
                dialog!!.dismiss()
            }
            else
            {
                dialog!!.dismiss()
            }
        }

        dialog!!.show()
    }

    class signature(context: Context?, attrs: AttributeSet?) : View(context, attrs)
    {
        private val paint = Paint()
        private val path = Path()
        private var lastTouchX = 0f
        private var lastTouchY = 0f
        private val dirtyRect = RectF()

        fun save(v: View, StoredPath: String?)
        {
            Log.v("log_tag", "Width: " + v.width)
            Log.v("log_tag", "Height: " + v.height)
            if (bitmap == null)
            {
                bitmap = Bitmap.createBitmap(
                    mContent!!.getWidth(),
                    mContent!!.getHeight(),
                    Bitmap.Config.RGB_565
                )

                canvas = Canvas(bitmap!!)
            }
            /*val workingBitmap: Bitmap = Bitmap.createBitmap( mContent!!.getWidth(),
                mContent!!.getHeight(),
                Bitmap.Config.RGB_565)
            val mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888, true)
            canvas = Canvas(mutableBitmap)*/


            try
            {
                // Output the file
                val mFileOutStream = FileOutputStream(StoredPath)
                v.draw(canvas)

                // Convert the output file to Image such as .png
                bitmap?.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream)
                mFileOutStream.flush()
                mFileOutStream.close()
            }
            catch (e: Exception)
            {
                Log.v("log_tag", e.toString())
            }
        }

        fun clear()
        {
            path.reset()
            invalidate()
            mContent?.removeAllViews()
            mContent = dialog?.findViewById(R.id.linearLayout) as LinearLayout
            mSignature = signature(context, null)
            mSignature?.setBackgroundColor(Color.WHITE)
            // Dynamically generating Layout through java code
            mContent?.addView(
                mSignature,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }

        override fun onDraw(canvas: Canvas) {
            canvas.drawPath(path, paint)
        }

        override fun onTouchEvent(event: MotionEvent): Boolean {
            val eventX = event.x
            val eventY = event.y
            mGetSign?.setEnabled(true)
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    path.moveTo(eventX, eventY)
                    lastTouchX = eventX
                    lastTouchY = eventY
                    return true
                }
                MotionEvent.ACTION_MOVE, MotionEvent.ACTION_UP -> {
                    resetDirtyRect(eventX, eventY)
                    val historySize = event.historySize
                    var i = 0
                    while (i < historySize) {
                        val historicalX = event.getHistoricalX(i)
                        val historicalY = event.getHistoricalY(i)
                        expandDirtyRect(historicalX, historicalY)
                        path.lineTo(historicalX, historicalY)
                        i++
                    }
                    path.lineTo(eventX, eventY)
                }
                else -> {
                    debug("Ignored touch event: $event")
                    return false
                }
            }
            invalidate(
                (dirtyRect.left - HALF_STROKE_WIDTH).toInt(),
                (dirtyRect.top - HALF_STROKE_WIDTH).toInt(),
                (dirtyRect.right + HALF_STROKE_WIDTH).toInt(),
                (dirtyRect.bottom + HALF_STROKE_WIDTH).toInt()
            )
            lastTouchX = eventX
            lastTouchY = eventY
            return true
        }

        private fun debug(string: String) {
            Log.v("log_tag", string)
        }

        private fun expandDirtyRect(
            historicalX: Float,
            historicalY: Float
        ) {
            if (historicalX < dirtyRect.left) {
                dirtyRect.left = historicalX
            } else if (historicalX > dirtyRect.right) {
                dirtyRect.right = historicalX
            }
            if (historicalY < dirtyRect.top) {
                dirtyRect.top = historicalY
            } else if (historicalY > dirtyRect.bottom) {
                dirtyRect.bottom = historicalY
            }
        }

        private fun resetDirtyRect(eventX: Float, eventY: Float) {
            dirtyRect.left = Math.min(lastTouchX, eventX)
            dirtyRect.right = Math.max(lastTouchX, eventX)
            dirtyRect.top = Math.min(lastTouchY, eventY)
            dirtyRect.bottom = Math.max(lastTouchY, eventY)
        }

        companion object {
            private const val STROKE_WIDTH = 5f
            private const val HALF_STROKE_WIDTH = STROKE_WIDTH / 2
        }

        init {
            paint.isAntiAlias = true
            paint.color = Color.BLACK
            paint.style = Paint.Style.STROKE
            paint.strokeJoin = Paint.Join.ROUND
            paint.strokeWidth = STROKE_WIDTH
        }
    }

    private fun imageToString(bitmap: Bitmap): String? {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val imgBytes = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(imgBytes, Base64.DEFAULT)
    }

    //storage permission
    fun Storagepermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) === PackageManager.PERMISSION_GRANTED
        ) {

            //Toast.makeText(MainActivity.this, "storage permission granted",Toast.LENGTH_SHORT).show();
        } else {
            requestStoragePermission()
        }
    }


    fun requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        ) {
            AlertDialog.Builder(this).setTitle("Storage Permission needed!")
                .setMessage("This permission is needed")
                .setPositiveButton("OK",
                    DialogInterface.OnClickListener { dialog, which ->
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            STORAGE_PERMISSION_CODE
                        )
                    }).setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, which -> }).create().show()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                STORAGE_PERMISSION_CODE
            )
        }
    }

    //contact permision
    private fun Contactpermission()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)
        {
            // Toast.makeText(MainActivity.this, "Read Contact permission granted",Toast.LENGTH_SHORT).show();
        }
        else
        {
            requestContactPermission()
        }
    }

    private fun requestContactPermission()
    {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE))
        {
            AlertDialog.Builder(this).setTitle("Read Contact Permission needed!")
                .setMessage("This permission is needed")
                .setPositiveButton("OK",
                    DialogInterface.OnClickListener { dialog, which ->
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.READ_PHONE_STATE),
                            Contact_PERMISSION_CODE
                        )
                    }).setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, which -> }).create().show()
        }
        else
        {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_PHONE_STATE), Contact_PERMISSION_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
    {
        if (requestCode == STORAGE_PERMISSION_CODE)
        {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                // Toast.makeText(MainActivity.this, "storage permission is granted", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "storage permission is denied", Toast.LENGTH_SHORT).show()
            }
        } else if (requestCode == Contact_PERMISSION_CODE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                //Toast.makeText(MainActivity.this, "access contacts permission is granted", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "contact permission is denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getExtraString()
    {
        val username: String? = intent.getStringExtra("Username")
        val contactDate: String? = intent.getStringExtra("ContactDate")
        val dayLeft: String? = intent.getStringExtra("DayLeft")
        val rentalPrice: String? = intent.getStringExtra("RentalPrice")
        val billsStatus: String? = intent.getStringExtra("BillsStatus")
        val startDate: String? = intent.getStringExtra("StartDate")
        val endDate: String? = intent.getStringExtra("EndDate")
        val email: String? = intent.getStringExtra("Email")
        val phoneNumber: String? = intent.getStringExtra("PhoneNumber")
        val totalContactAmount: String? = intent.getStringExtra("TotalContactAmount")
        val paymentDue: String? = intent.getStringExtra("PaymentDue")
        val lateCharges: String? = intent.getStringExtra("LateCharges")
        val billsPeriod: String? = intent.getStringExtra("BillsPeriod")
        val accountReceive: String? = intent.getStringExtra("AccountReceive")
        val isFromNewTenant: Boolean? = intent.getBooleanExtra("isFromNewTenant", false)
        val accountNumber: String? = intent.getStringExtra("BankAccountNumber")

        lblUsername.text = username
        lblContact.text = contactDate
        lblStartDateValue.text = startDate
        lblEmailTitleValue.text = email
        lblEndDateValue.text = endDate
        lblPhoneNumberValue.text = phoneNumber
        lblRentalPriceValue.text = rentalPrice
        lblAfterPaymentDueValue.text = paymentDue
        lblBillPeriodValue.text = billsPeriod
        lblReceiveAccountValue.text = accountReceive
        lblContractAmountTitleValue.text = totalContactAmount
        lblLateChangesValue.text = lateCharges
        lblBankAccountValue.text = accountNumber

        if (dayLeft != null)
        {
            lblStatus.text = dayLeft

            if (dayLeft == "Pending Sign")
            {
                layoutTenantTitleCardView.visibility = View.VISIBLE
                layout?.visibility = View.VISIBLE
            }
            else
            {
                layoutTenantTitleCardView.visibility = View.GONE
                layout?.visibility = View.GONE
                btnAddSignature.visibility = View.GONE
                layoutSignatureDetail.visibility = View.GONE
            }
        }

        if (isFromNewTenant!!)
        {
            btnDoneTenantDetail.visibility = View.VISIBLE
            imgBackButton.visibility = View.GONE
        }
        else
        {
            btnDoneTenantDetail.visibility = View.GONE
            imgBackButton.visibility = View.VISIBLE
        }
    }
}