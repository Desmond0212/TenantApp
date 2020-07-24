package com.example.tenantyapp

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
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
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_new_tenant_rental_info.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


class NewTenantRentalInfoActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener
{
    private var btnDoneAddAccount: Button? = null
    private var selectedUnit: String? = null
    private var unitAddress: String? = null
    private var fullName: String? = null
    private var emailAddress: String? = null
    private var phoneNumber: String? = null
    private var numberOfOccupant: String? = null
    private var spinner : Spinner? = null
    private var contractPeriod = arrayOf("1 month contract", "2 months contract", "3 months contract", "4 months contract", "5 months contract", "6 months contract", "7 months contract", "8 months contract", "9 months contract", "10 months contract", "11 months contract", "1 year contract", "2 years contract", "3 years contract")
    private var contractPeriodValue: String? = null
    private val myCalendar: Calendar = Calendar.getInstance()

    //Signature
    var ConvertedBitmap: String? = null
    var mClear: Button? = null
    var mCancel: ImageButton? = null
    var builder: AlertDialog.Builder? = null

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
        var mGetSign: Button? = null
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_tenant_rental_info)

        val window: Window = this.window
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        // finally change the color
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorBackground)

        getExtraStringValue()

        /*spinner = findViewById(R.id.spinnerTenantRentalInfo)
        spinner!!.onItemSelectedListener = this
        // Create an ArrayAdapter using a simple spinner layout and languages array
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, contractPeriod)
        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner!!.adapter = aa

        spinner?.setOnTouchListener {_, _ ->
            hideSoftKeyboard(this)
            txtStartDate.clearFocus()
            txtEndDate.clearFocus()
            txtRentalPrice.clearFocus()
            txtSecurityDeposit.clearFocus()
            txtDayPaymentDue.clearFocus()
            txtLateCharges.clearFocus()
            txtBillsPeriod.clearFocus()

            false
        }*/

        layoutNewTenantRentalInfo.setOnTouchListener { _, _ ->
            hideSoftKeyboard(this)
            txtStartDate.clearFocus()
            txtEndDate.clearFocus()
            txtRentalPrice.clearFocus()
            txtSecurityDeposit.clearFocus()
            txtDayPaymentDue.clearFocus()
            txtLateCharges.clearFocus()
            txtBillsPeriod.clearFocus()

            false
        }

        btnBackNewTenantRentalInfo.setOnClickListener {
            finish()
        }

        btnNextNewTenantRentalInfo.setOnClickListener {
            if (txtStartDate.text != null && txtStartDate.text.toString() != "" &&
                txtEndDate.text != null && txtEndDate.text.toString() != "" &&
                txtRentalPrice.text != null && txtRentalPrice.text.toString() != "" &&
                txtSecurityDeposit.text != null && txtSecurityDeposit.text.toString() != "" &&
                txtDayPaymentDue.text != null && txtDayPaymentDue.text.toString() != "" &&
                txtLateCharges.text != null && txtLateCharges.text.toString() != "" &&
                txtBillsPeriod.text != null && txtStartDate.text.toString() != "" &&
                lblBankName.text != null && lblBankName.text.toString() != "" &&
                lblAccountNumber.text != null && lblAccountNumber.text.toString() != "")
            {
                /*ConvertedBitmap = ""
                dialogAction()*/

                val intent = Intent(this, NewTenantCreatingActivity::class.java)
                intent.putExtra("SelectedUnit", selectedUnit)
                intent.putExtra("UnitAddress", unitAddress)
                intent.putExtra("FullName", fullName)
                intent.putExtra("EmailAddress", emailAddress)
                intent.putExtra("PhoneNumber", phoneNumber)
                intent.putExtra("NumberOfOccupant", numberOfOccupant)
                intent.putExtra("StartDate", txtStartDate.text.toString())
                intent.putExtra("EndDate", txtEndDate.text.toString())
                intent.putExtra("RentalPrice", txtRentalPrice.text.toString())
                intent.putExtra("SecurityDeposit", txtSecurityDeposit.text.toString())
                intent.putExtra("DayPaymentDue", txtDayPaymentDue.text.toString())
                intent.putExtra("LateCharges", txtLateCharges.text.toString())
                intent.putExtra("BillsPeriod", txtBillsPeriod.text.toString())
                intent.putExtra("BankName", lblBankName.text.toString())
                intent.putExtra("ContractPeriod", "2 year contract")
                intent.putExtra("BankAccountNumber", lblAccountNumber.text.toString())
                startActivity(intent)
            }
            else
            {
                Toast.makeText(this, "Please complete the form before proceed.", Toast.LENGTH_SHORT).show()
            }
        }

        btnCloseNewTenantRentalInfo.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        btnAddNewAccount.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
            val bottomSheetView = LayoutInflater.from(applicationContext).inflate(R.layout.bottom_sheet_new_account, findViewById<View>(R.id.bottomSheetContainerNewAccount) as? LinearLayout)

            btnDoneAddAccount = bottomSheetView.findViewById(R.id.btnDoneNewAccount)

            btnDoneAddAccount?.setOnClickListener {
                bottomSheetDialog.dismiss()
            }

            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
        }

        val date = OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                myCalendar[Calendar.YEAR] = year
                myCalendar[Calendar.MONTH] = monthOfYear
                myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                updateLabel()
            }

        val date2 = OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            myCalendar[Calendar.YEAR] = year
            myCalendar[Calendar.MONTH] = monthOfYear
            myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
            updateLabel2()
        }

        txtStartDate.setOnClickListener {
            DatePickerDialog(this, date, myCalendar[Calendar.YEAR], myCalendar[Calendar.MONTH], myCalendar[Calendar.DAY_OF_MONTH]).show()
        }

        txtEndDate.setOnClickListener {
            DatePickerDialog(this, date2, myCalendar[Calendar.YEAR], myCalendar[Calendar.MONTH], myCalendar[Calendar.DAY_OF_MONTH]).show()
        }

        Storagepermission()
        //Contactpermission()
        dialog = Dialog(this)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.dialog_signature2)
        dialog!!.setCancelable(true)
    }

    private fun updateLabel()
    {
        val myFormat = "dd MMM yyyy" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        txtStartDate.setText(sdf.format(myCalendar.time))
    }

    private fun updateLabel2()
    {
        val myFormat = "dd MMM yyyy" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        txtEndDate.setText(sdf.format(myCalendar.time))
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        contractPeriodValue = contractPeriod[position]
    }

    override fun onNothingSelected(arg0: AdapterView<*>) {}

    private fun getExtraStringValue()
    {
        selectedUnit = intent.getStringExtra("SelectedUnit")
        unitAddress = intent.getStringExtra("UnitAddress")
        fullName = intent.getStringExtra("FullName")
        emailAddress = intent.getStringExtra("EmailAddress")
        phoneNumber = intent.getStringExtra("PhoneNumber")
        numberOfOccupant = intent.getStringExtra("NumberOfOccupant")
    }

    private fun hideSoftKeyboard(activity: Activity)
    {
        val inputMethodManager: InputMethodManager = activity.getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            activity.currentFocus?.windowToken, 0
        )
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
            //img_sig!!.setImageDrawable(null)
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
            }
            Log.v("log_tag", "Panel Saved")
            view!!.isDrawingCacheEnabled = true
            mSignature?.save(view!!, StoredPath)
            dialog!!.dismiss()

        }
        mCancel!!.setOnClickListener {
            Log.v("log_tag", "Panel Canceled")

            mSignature?.clear()
            dialog!!.dismiss()
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
}