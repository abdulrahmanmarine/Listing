import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.util.Log
import java.io.IOException

class AudioNote(context: Context){
    var outputDir: String = ""
    var state: Boolean = false
    var recording: Boolean = false
    var recorder : MediaRecorder? = null
    var player: MediaPlayer? = null

    init {
        if ( 0 < outputDir.length){
//         outputDir = "${context.getExternalFilesDir(null)?.absoluteFile}/audiorecordtest.3gp"
            outputDir  = "${context.externalCacheDir?.absolutePath}/audiorecordtest.3gp"
        }
    }

    fun onRecord(start: Boolean) = if(start){
        startRecording()
    }else{
        stopRecoding()
    }

    fun onPlay(start: Boolean) =  if (start){
        startPlaying()
    }else{
        stopPlaying()
    }

    //Recording
    fun startRecording(){
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setOutputFile(outputDir)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

            try {
                prepare()
            } catch (e: IOException) {
                Log.e("AudioRecordTest", "prepare() failed")
            }

            start()
        }

    }


    fun stopRecoding(){
        recorder?.apply {
            stop()
            release()
            recording = false
        }
        recorder = null
    }


    //Playing
    fun startPlaying(){
        player = MediaPlayer().apply {
            try {
                setDataSource(outputDir)
                prepare()
                start()
            }catch (e: IOException){
                Log.e("AudioPlayingText", "prepare() failed")
            }
        }
    }

    fun stopPlaying(){
        player?.release()
        player = null
    }

    fun stopping(){
        recorder?.release()
        recorder = null
        player?.release()
        player = null
    }

}