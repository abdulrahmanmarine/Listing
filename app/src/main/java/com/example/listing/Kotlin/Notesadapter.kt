package com.example.listing


import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.media.MediaPlayer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listing.Kotlin.Notes
import com.example.listing.Kotlin.pictureMode
import com.example.listing.databinding.NoteCellBinding
import com.fasterxml.jackson.core.Base64Variants
import java.io.IOException



class NotesAdapter(val supFM: FragmentManager,val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder?>() {

    companion object {
        private const val TYPE_TEXT = 0
        private const val TYPE_Audio = 1
        private const val TYPE_Image = 2
    }


     var historyList = mutableListOf<Notes>()

    private var flage = false


    override fun getItemViewType(position: Int): Int {
        return if (historyList[position].type == 1) {
            println("Audio Note")
            TYPE_Audio
        } else if(historyList[position].type == 2) {
            println("Image Note")
            TYPE_Image
        }
        else{
            println("Text Note")
            TYPE_TEXT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val textViewBinding = NoteCellBinding.inflate(LayoutInflater.from(context), parent, false)
//        val audioViewBinding =
//            NoteSoundCellBinding.inflate(LayoutInflater.from(context), parent, false)

        return TextViewHolder(textViewBinding)
//        return when (viewType) {
//            TYPE_TEXT ->  TextViewHolder(textViewBinding)
//            else ->  AudioViewHolder(audioViewBinding)
//
//        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        println("The count of notes entered i ${historyList.size}")

        if (holder is TextViewHolder)
        holder.binding(position)

//        when (holder) {
//            is TextViewHolder -> holder.binding(position)
//            else -> (holder as AudioViewHolder).binding(position)
//        }

    }

    override fun getItemCount(): Int {
        println("The history size is ${historyList.size}")
        return historyList.size
    }

    inner class TextViewHolder(itemView: NoteCellBinding) : RecyclerView.ViewHolder(itemView.root) {
        private val creator: TextView = itemView.creatorName
        private val message: TextView = itemView.noteMessage
        private val image: ImageView = itemView.noteImage

        private val audioPlayer = itemView.audionote2

        var player: MediaPlayer? = null

        var isPlaying:Boolean = false
        var fileName:String = ""

//        private fun finishaudio() {
//            player.release()
//            player.reset()
//            flage = false
//            Log.i("stoppedplaying", "stopped playing")
//            audioPlayer.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_play_audio))
//        }


        fun onPlay(start: Boolean) =  if (start){
            startPlaying()
        }else{
            stopPlaying()
        }


        //Playing
        fun startPlaying(){
            player = MediaPlayer().apply {
                try {
                    setDataSource(fileName)
                    prepare()
                    start()
                    Log.e("AudioPlayingTest", "duration() $duration")
                }catch (e: IOException){

                    Log.e("AudioPlayingTest", "prepare() failed")

                    onPlay(false)
                }
                audioPlayer.setImageResource(R.drawable.ic_stop_button)
                audioPlayer.background = null
            }
        }

        fun stopPlaying(){
            audioPlayer.setImageResource(R.drawable.ic_play_audio)
            audioPlayer.background = null
            player?.release()
            player = null

        }


        fun binding(position: Int) {
            println("The note text entered  ${historyList[position].noteText}")
            val type = getItemViewType(position)
            if(type == TYPE_Image){
                audioPlayer.visibility = View.GONE
                message.visibility = View.GONE
                image.visibility = View.VISIBLE
                image.background = BitmapDrawable.createFromStream(
                    Base64Variants.getDefaultVariant().decode(historyList[position].noteText).inputStream(), "")
                image.setOnClickListener {
                    val fragment = pictureMode(image.background)
                    fragment.show(supFM, "Image")
                }
            }
           else if (type == TYPE_TEXT){
                audioPlayer.visibility = View.GONE
                message.visibility = View.VISIBLE
                message.text = historyList[position].noteText

            }else {
                audioPlayer.visibility = View.VISIBLE
                message.visibility = View.GONE
                fileName = historyList[position].noteText
            }

            creator.text = historyList[position].creator



            audioPlayer.setOnClickListener {
                if (isPlaying == false){
                    isPlaying = true
                    onPlay(isPlaying)

                }else{
                    isPlaying = false
                    onPlay(isPlaying)

                }
            }


        }
    }

//    inner class AudioViewHolder(itemView: NoteSoundCellBinding) :
//        RecyclerView.ViewHolder(itemView.root) {
//
//
////        private val creator = itemView.creatorName
////        private val audioPlayer = itemView.audionote
////
////        var player: MediaPlayer? = null
////
////        var isPlaying:Boolean = false
////        var fileName:String = ""
////
////
//////        private fun finishaudio() {
//////            player.release()
//////            player.reset()
//////            flage = false
//////            Log.i("stoppedplaying", "stopped playing")
//////            audioPlayer.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_play_audio))
//////        }
////
////
////        fun onPlay(start: Boolean) =  if (start){
////            startPlaying()
////        }else{
////            stopPlaying()
////        }
////
////
////        //Playing
////        fun startPlaying(){
////            player = MediaPlayer().apply {
////                try {
////                    setDataSource(fileName)
////                    prepare()
////                    start()
////                    Log.e("AudioPlayingTest", "duration() $duration")
////                }catch (e: IOException){
////
////                    Log.e("AudioPlayingTest", "prepare() failed")
////
////                    onPlay(false)
////                }
////                audioPlayer.setImageResource(R.drawable.ic_speaker)
////            }
////        }
////
////        fun stopPlaying(){
////            audioPlayer.setImageResource(R.drawable.ic_speaker)
////            player?.release()
////            player = null
////
////        }
//
//
//        fun binding(position: Int) {
//            val tempNote = historyList[position]
//            creator.text = tempNote.creator
//            println("The audio file name is ${tempNote.record}")
////            player = MediaPlayer().apply{
////                Log.e("AudioPlayingTest", "duration() $duration")
////            }
//
//
//
//
//            audioPlayer.setOnClickListener {
//                if (isPlaying == false){
//                    isPlaying = true
//                    onPlay(isPlaying)
//
//                }else{
//                    isPlaying = false
//                    onPlay(isPlaying)
//
//                }
//            }
//
//
//        }



//    }

}
