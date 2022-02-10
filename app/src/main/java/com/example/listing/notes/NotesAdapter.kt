package com.example.listing.notes


import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView

import com.example.listing.R
import com.example.listing.databinding.NoteCellBinding
import com.fasterxml.jackson.core.Base64Variants
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


class NotesAdapter2(val supFM: FragmentManager, val context: Context, notes: ArrayList<Notes>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder?>() {

    companion object {
        private const val TYPE_TEXT = 0
        private const val TYPE_Audio = 1
        private const val TYPE_Image = 2
    }


    //  var historyList = mutableListOf<Notes>()

    var historyList = notes

    override fun getItemViewType(position: Int): Int {
        return if (historyList[position].type == "AUD") {
            TYPE_Audio
        } else if (historyList[position].type == "IMG") {
            TYPE_Image
        } else {
            TYPE_TEXT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val textViewBinding = NoteCellBinding.inflate(LayoutInflater.from(context), parent, false)
        return TextViewHolder(textViewBinding)


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is TextViewHolder)
            holder.binding(position)


    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    inner class TextViewHolder(itemView: NoteCellBinding) : RecyclerView.ViewHolder(itemView.root) {
        private val creator: TextView = itemView.creatorName
        private val message: TextView = itemView.noteMessage
        private val image: ImageView = itemView.noteImage
        private val time: TextView = itemView.timestamp

        private val audioPlayer = itemView.audionote2

        var player: MediaPlayer? = null

        var isPlaying: Boolean = false
        var fileName: String = ""


        fun onPlay(start: Boolean) = if (start) {
            startPlaying()
        } else {
            stopPlaying()
        }


        //Playing
        fun startPlaying() {
            player = MediaPlayer().apply {
                try {
                    setDataSource(fileName)
                    prepare()
                    start()
                } catch (e: IOException) {


                    onPlay(false)
                }
                audioPlayer.setImageResource(R.drawable.ic_stop_button)
                audioPlayer.background = null
            }
        }

        fun stopPlaying() {
            audioPlayer.setImageResource(R.drawable.ic_play_audio)
            audioPlayer.background = null
            player?.release()
            player = null

        }


        fun binding(position: Int) {
            val type = getItemViewType(position)
            if (type == TYPE_Image) {
                audioPlayer.visibility = View.GONE
                message.visibility = View.GONE
                image.visibility = View.VISIBLE
                image.background = BitmapDrawable.createFromStream(
                    Base64Variants.getDefaultVariant().decode(historyList[position].noteText)
                        .inputStream(), ""
                )
                image.setOnClickListener {
                    val fragment = pictureMode(image.background)
                    fragment.show(supFM, "Image")
                }
            } else if (type == TYPE_TEXT) {
                audioPlayer.visibility = View.GONE
                message.visibility = View.VISIBLE
                message.text = historyList[position].noteText

            } else if (type == TYPE_Audio) {
                audioPlayer.visibility = View.VISIBLE
                message.visibility = View.GONE
                var base64Aud = historyList[position].noteText
                var decoded = Base64Variants.getDefaultVariant().decode(base64Aud)
                val file2 =
                    File(context.cacheDir.toString() + "/${UUID.randomUUID()}.3gp")
                val os = FileOutputStream(file2, true)
                os.write(decoded)
                os.close()
                fileName = file2.absolutePath
            }

            creator.text = historyList[position].creator
            time.text = historyList[position].time + "  " + historyList[position].date



            audioPlayer.setOnClickListener {
                if (isPlaying == false) {
                    isPlaying = true
                    onPlay(isPlaying)

                } else {
                    isPlaying = false
                    onPlay(isPlaying)

                }
            }


        }
    }


}
