package com.jjosft.android.lottovillage.activities

import android.Manifest
import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsManager
import android.view.View
import android.widget.Toast
import com.jjosft.android.lottovillage.R
import com.jjosft.android.lottovillage.base.BaseActivity
import com.jjosft.android.lottovillage.base.BaseApplication
import com.yarolegovich.lovelydialog.LovelyStandardDialog
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.content_register.*
import java.util.regex.Pattern


class RegisterActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setSupportActionBar(toolbar_register)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val certifiedNumber = intent.getStringExtra("certified_number")
        edit_confirm_certified.setText(certifiedNumber)
    }

    fun customOnClick(view: View) {
        when (view.id) {
            R.id.button_certified -> {
                LovelyStandardDialog(this)
                        .setTopColorRes(R.color.colorPrimary)
                        .setButtonsColorRes(R.color.colorPrimaryDark)
                        .setIcon(R.drawable.ic_warning_24dp)
                        .setTitle(getString(R.string.send_certified_number))
                        .setMessage(getString(R.string.request_permission_certified_number))
                        .setPositiveButton(R.string.okay, {
                            if (super.checkPermissionAndSetDisplayData(Manifest.permission.SEND_SMS)) {
                                if (super.checkPermissionAndSetDisplayData(Manifest.permission.READ_PHONE_STATE)) {
                                    if (super.checkPermissionAndSetDisplayData(Manifest.permission.RECEIVE_SMS)) {
                                        sendSms()
                                    }
                                }
                            }
                        })
                        .setNegativeButton(R.string.cancel, null)
                        .show()
            }
            R.id.button_confirm_certified -> {
                validateCertified()
            }
            R.id.checked_text_service -> {
                checked_text_service.isChecked = !checked_text_service.isChecked
            }
            R.id.checked_text_personal_information -> {
                checked_text_personal_information.isChecked = !checked_text_personal_information.isChecked
            }
            R.id.button_registered -> {
                validateRegister()
            }
        }
    }

    private fun validateCertified() {
        val sharedPreferences = applicationContext.getSharedPreferences(BaseApplication.PREF_ID, Activity.MODE_PRIVATE)
        val certifiedNumber = sharedPreferences.getString(BaseApplication.CERTIFIED_NUMBER, null)
        if (certifiedNumber == null || edit_confirm_certified.text.toString() != certifiedNumber) {
            edit_confirm_certified.requestFocus()
            edit_confirm_certified.error = getString(R.string.unmatched_certified_number)
        } else {
            edit_phone_number.isEnabled = false
            button_certified.isEnabled = false
            edit_confirm_certified.isEnabled = false
            button_confirm_certified.isEnabled = false
            button_registered.isEnabled = true
            Toast.makeText(applicationContext, getString(R.string.matched_certified_number), Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendSms() {
        val smsManager = SmsManager.getDefault()
        val sentIntent = PendingIntent.getBroadcast(this, 0, Intent("SMS_SENT_ACTION"), 0);
        val deliveredIntent = PendingIntent.getBroadcast(this, 0, Intent("SMS_DELIVERED_ACTION"), 0)

        val editTextPhoneNumber = edit_phone_number.text.toString()
        val isValidate = Pattern.matches("^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}\$", editTextPhoneNumber)

        if (isValidate) {
            val certifiedNumber = randomRange(10000, 99999).toString()
            val sharedPreferencesEditors = applicationContext.getSharedPreferences(BaseApplication.PREF_ID, Activity.MODE_PRIVATE).edit()
            sharedPreferencesEditors.putString(BaseApplication.CERTIFIED_NUMBER, certifiedNumber)
            sharedPreferencesEditors.apply()
            smsManager.sendTextMessage(editTextPhoneNumber.replace("-", ""), null, getString(R.string.message_front) + certifiedNumber + getString(R.string.message_back), sentIntent, deliveredIntent)
        } else {
            edit_phone_number.requestFocus()
            edit_phone_number.error = getString(R.string.unmatched_phone_number)
        }
    }

    private fun randomRange(from: Int, to: Int): Int {
        return ((Math.random() * (to - from + 1)) + from).toInt()
    }
}

private fun validateRegister() {
    /*val editTextName = edit_name.text
    val editTextPassword = edit_password.text
    val editTextConfirmPassword = edit_confirm_password.text
    val editTextPhoneNumber = edit_phone_number.text
    val editTextConfirmCertified = edit_confirm_certified.text*/
}
