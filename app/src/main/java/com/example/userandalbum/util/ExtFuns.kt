package com.example.userandalbum.util

import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.isVisible(): Boolean {
    return this.visibility == View.VISIBLE
}

fun View.disable() {
    this.isEnabled = false
}

fun View.enable() {
    this.isEnabled = true
}

fun EditText.showError(errorMessage: String) {
    error = errorMessage
    requestFocus()
}


fun Context.notifyUser(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}

fun Context.showError(message: String, clickListener: DialogInterface.OnClickListener? = null){
    AlertDialog.Builder(this)
        .setMessage(message)
        .setCancelable(false)
        .setPositiveButton("OK", clickListener)
        .show()
}

//for logs
fun logD(tag: String = "USER_DETAILS_APP", message: String) {
    Log.d(tag, message)
}

fun logE(tag: String = "USER_DETAILS_APP", message: String) {
    Log.e(tag, message)
}

fun logI(tag: String = "USER_DETAILS_APP", message: String) {
    Log.i(tag, message)
}