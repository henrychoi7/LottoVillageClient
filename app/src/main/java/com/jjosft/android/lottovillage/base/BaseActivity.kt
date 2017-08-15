package com.jjosft.android.lottovillage.base

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.jjosft.android.lottovillage.R


/**
 * Created by JJSOFT-DESKTOP on 2017-08-13.
 */

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    fun progressOn(message: String) {
        BaseApplication.getInstance().progressOn(this, message)
    }

    fun progressSet(message: String) {
        BaseApplication.getInstance().progressSet(message)
    }

    fun progressOff() {
        BaseApplication.getInstance().progressOff()
    }

    // 권한이 있는지 확인하는 작업
    protected fun checkPermissionAndSetDisplayData(requestPermission : String): Boolean {
        if (!checkAndExplainPermission(requestPermission)) {
            ActivityCompat.requestPermissions(this, arrayOf(requestPermission), 1)
            return false
        }
        return true
    }

    private fun checkAndExplainPermission(requestPermission: String): Boolean {
        /* 권한이 이미 부여되어 있을 수도 있으므로 checkSelfPermission() 메서드를 호출하여
           권한이 부여되어 있는지 체크
           - 현재 앱이 특정 권한을 갖고 있는지 확인 가능
         */
        val permissionCheck = ContextCompat.checkSelfPermission(this, requestPermission)
        return permissionCheck == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //DisplayCustomToast(getApplicationContext(), "엇 내부로직이다, 인터넷 권한은 얻음");
                } else {
                    //DisplayCustomToast(getApplicationContext(), getString(R.string.explain_permission));
                    Toast.makeText(applicationContext, getString(R.string.explain_permission_sms), Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}