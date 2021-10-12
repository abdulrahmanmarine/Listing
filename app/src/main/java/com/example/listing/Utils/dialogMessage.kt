package com.example.listing.Utils


import android.content.Context
import android.content.DialogInterface
import android.view.View
import androidx.appcompat.app.AlertDialog


fun dialogMessage(context: Context,
                  title:String,
                  message:String,
                  confirmMessage:Boolean,
                  view: View?=null,
                  confirmBtn:MessageDialogInterface?=null){

    val dialog = AlertDialog.Builder(context).create()
    var dismissButtonStr = "OK"
    if (confirmMessage) {
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Confirm") { dialog, which ->
            confirmBtn?.onConfirmClick(dialog)
        }
        dismissButtonStr = "Cancel"
    }

    dialog.setButton(
        AlertDialog.BUTTON_NEGATIVE,dismissButtonStr) { dialog, which ->
        dialog.dismiss()
    }
    dialog.setMessage(message)
    dialog.setView(view)
    dialog.setTitle(title)
    dialog.show()

}

interface MessageDialogInterface{
    fun onConfirmClick(dialog: DialogInterface)
}
