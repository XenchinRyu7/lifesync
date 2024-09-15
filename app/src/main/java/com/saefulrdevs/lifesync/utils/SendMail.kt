package com.saefulrdevs.lifesync.utils

import android.os.AsyncTask
import android.util.Log
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class SendMail(
    private val username: String,
    private val password: String,
    private val recipient: String,
    private val subject: String,
    private val messageBody: String,
    private val listener: OnMailSendListener
) : AsyncTask<Void, Void, Boolean>() { // Mengembalikan Boolean

    interface OnMailSendListener {
        fun onSuccess() // Dipanggil jika berhasil
        fun onFailure() // Dipanggil jika gagal
    }

    override fun doInBackground(vararg params: Void?): Boolean {
        return try {
            val properties = Properties().apply {
                put("mail.smtp.auth", "true")
                put("mail.smtp.starttls.enable", "true")
                put("mail.smtp.host", "smtp.gmail.com")
                put("mail.smtp.port", "587")
            }

            val session = Session.getInstance(properties, object : Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(username, password)
                }
            })
            session.debug = true
            val message = MimeMessage(session).apply {
                setFrom(InternetAddress(username))
                setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient))
                subject = this@SendMail.subject
                setText(messageBody)
            }

            Transport.send(message)
            return true
        } catch (e: MessagingException) {
            e.printStackTrace()
            println("Error sending email: ${e.message}")
            Log.e("EmailError", "Gagal mengirim email: ${e.message}", e)
            return false
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onPostExecute(result: Boolean) {
        super.onPostExecute(result)
        if (result) {
            listener.onSuccess() // Email berhasil dikirim
        } else {
            listener.onFailure() // Email gagal dikirim
        }
    }
}

