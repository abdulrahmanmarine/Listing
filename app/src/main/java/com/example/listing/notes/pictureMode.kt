package com.example.listing.notes
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.example.listing.R


class pictureMode(image: Drawable) : DialogFragment() {
     var images = image
    override fun onCreateDialog(savedInstanceState: Bundle?) : Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater;
            val infview = inflater.inflate(R.layout.activity_picture_mode, null)
            var imageEx : ImageView = infview.findViewById(R.id.expanded_image)
           // imageEx.setImageBitmap(images)
            imageEx.background = images
            imageEx

            builder.setView(infview)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}