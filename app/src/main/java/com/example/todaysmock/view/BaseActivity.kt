package com.example.todaysmock.view

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar


abstract class BaseActivity:AppCompatActivity() {

    private var dialog: AlertDialog? = null

    fun snackBar(view: View, msg: String) {
        val snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
        snackbar.show()
    }
    fun toast(msg:String,context: Context){
        val toastmsg=Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
    }
     fun startLoadingdialog(context: Context) {
        // adding ALERT Dialog builder object and passing activity as parameter
        runOnUiThread {
            if (dialog==null){
                val builder = AlertDialog.Builder(context)
                // layoutinflater object and use activity to get layout inflater
                val inflater: LayoutInflater = layoutInflater
                builder.setView(inflater.inflate(com.example.todaysmock.R.layout.progresscustom,null))
                builder.setCancelable(false)
                dialog = builder.create()
            }
            dialog?.show()
        }
    }
     fun dismissdialog() {
        runOnUiThread {
            dialog?.dismiss()
            dialog=null
        }
    }
}