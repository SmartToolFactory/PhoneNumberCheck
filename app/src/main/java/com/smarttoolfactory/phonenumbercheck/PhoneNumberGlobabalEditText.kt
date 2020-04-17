package com.smarttoolfactory.phonenumbercheck

import android.content.Context
import android.os.Build
import android.telephony.PhoneNumberFormattingTextWatcher
import android.util.AttributeSet
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatEditText
import com.google.i18n.phonenumbers.PhoneNumberUtil

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class PhoneNumberGlobabalEditText(context: Context, attributeSet: AttributeSet) :
    AppCompatEditText(context, attributeSet) {

    companion object {
        private const val TR_REGION_CODE = "TR"
        private const val TR_COUNTRY_CODE = "90"
        private const val TR_PHONE_PREFIX = "5"
        private const val TR_MAX_PHONE_NUMBER_LENGTH = 13
        private const val EMPTY = ""
    }

    private val phoneNumberUtil: PhoneNumberUtil = PhoneNumberUtil.getInstance()

    private var regionCode = TR_REGION_CODE

    private var phoneTextWatcher: PhoneNumberFormattingTextWatcher? = null
    var rawText: String? = null
    var currentText: String? = null

    private var maxLengthReached = false

    init {

        setPhoneTextChangeListener(TR_COUNTRY_CODE)

    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun setPhoneTextChangeListener(countryCode: String) {

        countryCode.toIntOrNull()?.let {
            regionCode = phoneNumberUtil.getRegionCodeForCountryCode(it)
        } ?: TR_REGION_CODE

        phoneTextWatcher?.let {
            removeTextChangedListener(it)
        }

        phoneTextWatcher =
            PhoneNumberFormattingTextWatcher(regionCode)

        addTextChangedListener(phoneTextWatcher)

        if (regionCode == TR_REGION_CODE)
            setText(TR_PHONE_PREFIX) else setText(EMPTY)

    }

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        if (regionCode == TR_REGION_CODE) {

            if (text?.length == 1) {
                setSelection(1)
            } else if (text?.length == TR_MAX_PHONE_NUMBER_LENGTH) {
                setSelection(TR_MAX_PHONE_NUMBER_LENGTH)
            }
        } else {
            super.onSelectionChanged(selStart, selEnd)
        }
    }


    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {

        if (!checkIfMaxLengthReached(text)) {
            currentText = text.toString()
            super.onTextChanged(text, start, lengthBefore, lengthAfter)
        } else {
            currentText?.let {
                setText(currentText)
            }
        }

        setPhonePrefix(text, lengthAfter, lengthBefore)

        getRawPhoneNumber(text)

        println("Text $text, currentText: $currentText, rawText: $rawText")

    }

    private fun setPhonePrefix(
        text: CharSequence?,
        lengthAfter: Int,
        lengthBefore: Int
    ) {
        if (text.isNullOrEmpty() && regionCode == TR_REGION_CODE && lengthAfter < lengthBefore) {
            setText(TR_PHONE_PREFIX)
        }
    }

    private fun checkIfMaxLengthReached(text: CharSequence?): Boolean {

        return if (regionCode == TR_REGION_CODE) {
            text?.let {
                it.length == TR_MAX_PHONE_NUMBER_LENGTH + 1
            } ?: false
        } else {
            false
        }

    }

    private fun getRawPhoneNumber(text: CharSequence?) {
        rawText = text?.toString()
            ?.replace(" ", "")
            ?.replace("-", "")
            ?.replace("(", "")
            ?.replace(")", "")
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        phoneTextWatcher?.let {
            removeTextChangedListener(it)
        }

    }

}