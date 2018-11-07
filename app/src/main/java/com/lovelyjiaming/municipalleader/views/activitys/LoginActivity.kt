package com.lovelyjiaming.municipalleader.views.activitys

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.AutoUtils
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil
import kotlinx.android.synthetic.main.activity_login.*

data class LoginResult(var result: String)

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AutoUtils.setSize(this, false, 1080, 1920)
        setContentView(R.layout.activity_login)
        AutoUtils.auto(this)
        //
        cv_login_invoke.setOnClickListener {
            XCNetWorkUtil.invokeGetRequest(this, XCNetWorkUtil.NETWORK_BASIC_CHECK_ADDRESS + "getLogin", {
                val result = Gson().fromJson(it, LoginResult::class.java)
                if (result.result == "1") {
                    startActivity(Intent(this, MainActivity::class.java))
                    Toast.makeText(this, "登录成功", Toast.LENGTH_LONG).show()
                    finish()
                }
            }, hashMapOf("phonenumber" to login_edt_username.text.toString(), "password" to login_edt_password.text.toString()))
        }
    }
}
