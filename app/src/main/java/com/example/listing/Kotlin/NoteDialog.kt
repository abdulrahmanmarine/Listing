package com.example.listing

import AudioNote
import android.Manifest
import android.app.Activity
import android.content.*
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listing.Kotlin.Notes
import com.example.listing.databinding.NoteViewBinding
import com.fasterxml.jackson.core.Base64Variants
import java.io.*


@RequiresApi(Build.VERSION_CODES.M)

private const val REQUEST_RECORD_AUDIO_PERMISSION = 200




class NotesFragment(LPid : Int, LP_item_id: Int?) : DialogFragment() {

    var notes = ArrayList<Notes>()
    var creator: String = "User1"
    lateinit var audioNote: AudioNote

    private  val REQUEST_RECORD_AUDIO_PERMISSION = 200
    var recording: Boolean = false
    var recorder : MediaRecorder? = null
    var player: MediaPlayer? = null

    var LP_item_id = LP_item_id
    var LPid = LPid



    private lateinit var binding: NoteViewBinding
    private var permissionToRecordAccepted = false
    private var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)

    lateinit var saveTextNote: ImageButton
    lateinit var saveAudioNote: ImageButton
    lateinit var noteTextView: EditText
    lateinit var noteRV: RecyclerView
    lateinit var  noteAdapter: NotesAdapter

    lateinit var cameraBtn : ImageButton

    var recordCount = 0
    var outputDir: String = ""

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, 0)


        outputDir  = "${requireActivity().filesDir?.path}/ggg.3gp"
        var icursor : Cursor = DBManager(requireContext()).dbsql.rawQuery("select * from Notes where LPid = $LPid and LP_item_id = $LP_item_id" ,null)
        icursor.moveToFirst()
        if(icursor.count > 0) {
            do {
                if (icursor.getInt(6) == 2) {
                    notes.add(
                        Notes(
                            icursor.getString(5),
                            icursor.getString(4), 2
                        )
                    )
                }
                else if (icursor.getInt(6) == 1) {
                    var recordPath = icursor.getString(4)
                    var decoded = Base64Variants.getDefaultVariant().decode(recordPath)
                    val file2 =
                        File(requireActivity().cacheDir.toString() + "/${icursor.getInt(2)}")
                    val os = FileOutputStream(file2, true)
                    os.write(decoded)
                    os.close()

                    notes.add(
                        Notes(
                            icursor.getString(5),
                            file2.absolutePath, 1
                        )
                    )
                } else if (icursor.getInt(6) == 0){
                    notes.add(
                        Notes(
                            icursor.getString(5),
                            icursor.getString(3),
                            0
                        )
                    )
                }
                else{
                    var recordPath = icursor.getString(4)
                    var decoded = Base64Variants.getDefaultVariant().decode(recordPath)
                    val file2 =
                        File(requireActivity().cacheDir.toString() + "/${icursor.getInt(2)}")
                    val os = FileOutputStream(file2, true)
                    os.write(decoded)
                    os.close()
                }

            }while(icursor.moveToNext())
        }




    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onStart() {
        super.onStart()

        var mets : DisplayMetrics = DisplayMetrics()
        requireContext().display?.getRealMetrics(mets)
        //var wM = (requireContext().getSystemService(Context.WINDOW_SERVICE)as WindowManager)
        dialog?.setTitle("Notes")
        dialog?.window?.setLayout(((mets.widthPixels)*3)/4,((mets.heightPixels)*3)/4)
        Log.d("METRICS HEIGHT", mets.heightPixels.toString())
        Log.d("METRICS WIDTH", mets.widthPixels.toString())
        Log.d("METRICS ", mets.density.toString())
        Log.d("METRICS ", mets.densityDpi.toString())
        Log.d("METRICS ", mets.xdpi.toString())
        Log.d("METRICS ", mets.ydpi.toString())
        Log.d("METRICS ", mets.scaledDensity.toString())

        dialog?.setCanceledOnTouchOutside(true)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        //TODO: Save the entered notes

//        stopping()
    }



    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        binding = NoteViewBinding.inflate(inflater, container, false)
        saveTextNote = binding.submitNote
        saveAudioNote = binding.recordButton
        noteTextView = binding.noteText
        noteRV = binding.notesList
        noteAdapter = NotesAdapter(requireActivity().supportFragmentManager,requireContext())
        noteRV.adapter = noteAdapter
        noteRV.layoutManager = LinearLayoutManager(requireActivity())
        cameraBtn = binding.cameraButton
        cameraBtn.setOnClickListener {
            dispatchTakePictureIntent()
        }
//        notes = if (Common.noteFor == NOTE_FOR_ITEM)
//            Common.item.notes
//        else
//            Common.plan.notes

        noteAdapter.historyList = notes
        actions()
        setupMediaRecording()
        return binding.root

    }


    fun actions(){
        saveTextNote.setOnClickListener{
//            println("The note is ${noteTextView.text}")
//            onPlay(true)
            if(0 < noteTextView.text.length ){
                println("Note is ${noteTextView.text}")

                notes.add(Notes(creator, noteTextView.text.toString(), 0))
                var contNote = ContentValues()
                contNote.put("user",creator)
                contNote.put("noteText",noteTextView.text.toString())
                contNote.put("noteVoice", "")
                contNote.put("LP_item_id",LP_item_id)
                contNote.put("voice",0)
                //contNote.put("NID",0)
                contNote.put("LPid",LPid)
                DBManager(requireContext()).dbsql.insert("Notes",null,contNote)
//                if (Common.noteFor== NOTE_FOR_ITEM)
//                    Common.item.notes = notes
//                else
//                    Common.plan.notes = notes
                println("adapter Note size before ${noteAdapter.historyList.size}")
                noteTextView.text.clear()
                noteAdapter.notifyDataSetChanged()
                noteRV.scrollToPosition(noteAdapter.itemCount -1)
                println("adapter Note size after ${noteAdapter.historyList.size}")

            }
        }




    }


    //TODO: Set media recorder
    //region Media recorder
    @RequiresApi(Build.VERSION_CODES.M)
    fun setupMediaRecording(){

        saveAudioNote.setOnClickListener {
            if(!this.permission())
//            if(!permissionToRecordAccepted)
                Toast.makeText(
                    requireContext(),
                    "You Have Not Granted Permission To Record",
                    Toast.LENGTH_LONG
                ).show()
            else if (!recording){
                recording = true
                onRecord(recording)
                saveAudioNote.setImageResource(R.drawable.ic_stop_button)
                saveAudioNote.background = null
//                Toast.makeText(requireActivity(), "Cannot record. You do not have permission", Toast.LENGTH_LONG).show()
            }else{
                recording = false
                onRecord(recording)
//                audioPlayer.setImageDrawable(ContextCompat.getDrawable(context, com.example.services.R.drawable.ic_play_audio))
                saveAudioNote.setImageResource(R.drawable.ic_speaker)
                saveAudioNote.background = null
                notes.add(Notes("User1",  outputDir, 1))

                val file = File(outputDir)
                val FileBytes: ByteArray = file.readBytes()
                var encoded = Base64Variants.getDefaultVariant().encode(FileBytes)

                var contNote = ContentValues()
                contNote.put("user","Hussain")
                contNote.put("noteText","")
                contNote.put("noteVoice", encoded.toString())
                contNote.put("LP_item_id",LP_item_id)
                contNote.put("LPid",LPid)
                contNote.put("voice",1)
                DBManager(requireContext()).dbsql.insert("Notes",null,contNote)
//                if (Common.noteFor== NOTE_FOR_ITEM)
//                    Common.item.notes = notes
//                else
//                    Common.plan.notes = notes
                noteAdapter.notifyDataSetChanged()
                recordCount++
//                recorder.apply {
//                    Log.e("AudioRecordTest", "duration() $")
//                }

            }
//           onRecord(true)
//            saveAudioNote.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.ic_media_ff))
//            Toast.makeText(requireActivity(), "Cannot record. You do not have permission", Toast.LENGTH_LONG)
//            if (permission()){
//                audioNote.onRecord(true)
//                saveAudioNote.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.ic_media_ff))
//            }else{
//                Toast.makeText(requireActivity(), "Cannot record. You do not have permission", Toast.LENGTH_LONG)
//            }
        }

    }

//    @RequiresApi(Build.VERSION_CODES.M)
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        permissionToRecordAccepted = if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION ) {
//            println("Permission granted")
//            grantResults[0] == PackageManager.PERMISSION_GRANTED
//
//
//
//
//        } else {
//            println("does not have permission ")
//            false
//        }
//        if (!permissionToRecordAccepted){ dismiss() }
//    }

    //endregion

    @RequiresApi(Build.VERSION_CODES.M)
    fun permission():Boolean{

        return if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            val permissions = arrayOf(android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                requireActivity().requestPermissions(permissions,0)

            false
        }else{
            true
        }




    }


    fun onRecord(start: Boolean) = if(start){
        startRecording()
    }else{
        stopRecoding()
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

//
//    fun onPlay(start: Boolean) =  if (start){
//        startPlaying()
//    }else{
//        stopPlaying()
//    }


    //Playing
//    fun startPlaying(){
//        player = MediaPlayer().apply {
//            try {
//                setDataSource(outputDir)
//                prepare()
//                start()
//            }catch (e: IOException){
//                Log.e("AudioPlayingText", "prepare() failed")
//            }
//        }
//    }
//
//    fun stopPlaying(){
//        player?.release()
//        player = null
//    }

    fun stopping(){
        recorder?.release()
        recorder = null

    }
    private val REQUEST_IMAGE_CAPTURE = 1

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
           // requireActivity().startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
           requireActivity().startActivityFromFragment(this,takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val bmp = data?.extras?.get("data") as Bitmap
            val stream = ByteArrayOutputStream()
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray = stream.toByteArray()
            var picString =  Base64Variants.getDefaultVariant().encode(byteArray)
            //DBManager(requireContext()).dbsql.execSQL("Update Items set image = $picString where LPid=99004 and LP_item_id = 11 ")

            var contNote = ContentValues()
            contNote.put("user","Hussain")
            contNote.put("noteText","")
            contNote.put("noteVoice", picString)
            contNote.put("LP_item_id",LP_item_id)
            contNote.put("LPid",LPid)
            contNote.put("voice",2)
            DBManager(requireContext()).dbsql.insert("Notes",null,contNote)
            noteAdapter.historyList.add(Notes("User1",  picString, 2))
            noteAdapter.notifyDataSetChanged()
           // Log.d("PICTURE TAKEN", picString)
        }
    }

}