package com.smarttoolfactory.phonenumbercheck

import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.i18n.phonenumbers.PhoneNumberUtil

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val phoneNumberUtil = PhoneNumberUtil.getInstance()

        var countryCode: Int? = 90

        val editTextCountryCode = findViewById<EditText>(R.id.editTextCountryCode)
        val editTextPhoneNumber = findViewById<PhoneNumberGlobabalEditText>(R.id.editTextPhoneNumber)
        val button = findViewById<Button>(R.id.button)
        val textView = findViewById<TextView>(R.id.textView)

        button.setOnClickListener {


            countryCode = editTextCountryCode.text.toString().toIntOrNull()?:90


            countryCode?.let {
//                val regionCode = phoneNumberUtil.getRegionCodeForCountryCode(countryCode!!)

                editTextPhoneNumber.setPhoneTextChangeListener("$countryCode")

//                val exampleNumber = phoneNumberUtil.getExampleNumber(regionCode)
//
//
//                val num =   phoneNumberUtil.getExampleNumberForType("TR", PhoneNumberUtil.PhoneNumberType.MOBILE)
//
//                val formatter = phoneNumberUtil.getAsYouTypeFormatter(regionCode)
//
//                val phoneNumberString = editTextPhoneNumber.text.toString()
//
//                if (!phoneNumberString.isNullOrEmpty()) {
//
//                    try {
//                        val phoneNumber = phoneNumberUtil.parse(phoneNumberString, regionCode)
//
//                        textView.text =
//                            phoneNumberUtil.formatOutOfCountryCallingNumber(phoneNumber, regionCode)
//
//                    } catch (e: Exception) {
//                        e.printStackTrace()
//                    }
//                }
            }
        }
    }
}
