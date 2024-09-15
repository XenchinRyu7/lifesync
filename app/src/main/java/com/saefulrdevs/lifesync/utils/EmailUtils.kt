package com.saefulrdevs.lifesync.utils

import android.content.Context
import com.saefulrdevs.lifesync.BuildConfig

object EmailUtils {

    fun generateVerificationCode(): String {
        return (1000..9999).random().toString()
    }

    fun saveVerificationCodeToSharedPreferences(context: Context, code: String) {
        val sharedPreferences =
            context.getSharedPreferences("VerificationPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("verificationCode", code)
        editor.putLong("expiryTime", System.currentTimeMillis() + 5 * 60 * 1000) // 5 menit
        editor.apply()
    }

    fun sendVerificationEmail(
        context: Context,
        email: String,
        verificationCode: String,
        listener: SendMail.OnMailSendListener
    ) {
        SendMail(
            username = "growyourgames@gmail.com",
            password = BuildConfig.SMTP_PASSWORD,
            recipient = email,
            subject = "Verification Code",
            messageBody = "Your verification code is $verificationCode",
            listener = listener
        ).execute()
    }
}
