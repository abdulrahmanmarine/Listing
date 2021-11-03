package com.example.listing.notes

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.*
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Rect
import android.location.LocationManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listing.*
import com.example.listing.DataViewModel.PlansDataModel
import com.example.listing.Utils.RestApiClient
import com.example.listing.models.Material
import com.example.listing.models.Plan
import com.example.listing.models.SAPNote
import com.fasterxml.jackson.core.Base64Variants
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.coroutines.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.io.*
import javax.security.auth.callback.Callback


@RequiresApi(Build.VERSION_CODES.M)


class RedesignedNotesFragment(
    noteType: String, material: Material,
    notes: java.util.ArrayList<Notes>, id2: String?,
    id3: String?,
    noteMjahr: String?
) : DialogFragment() {
    private lateinit var mViewModel: PlansDataModel
    private lateinit var Token: String
    var notes = notes
    var recording: Boolean = false
    var recorder: MediaRecorder? = null
    var player: MediaPlayer? = null
    private var vmJob = Job()
    private var uiScopre = CoroutineScope(Dispatchers.IO + vmJob)
    var noteType = noteType
    var id1 = material.zuphrLpid
    var id2 = id2
    var id3 = id3
    var material=material;
    var noteMjahr = noteMjahr
    lateinit var saveTextNote: ImageButton
    lateinit var saveAudioNote: ImageButton
    lateinit var noteTextView: EditText
    lateinit var noteRV: RecyclerView
    lateinit var noteAdapter: NotesAdapter2
    lateinit var cameraBtn: ImageButton
    var recordCount = 0
    var outputDir: String = ""
    lateinit var infview: View
    lateinit var binding : View
    var long: Double? = 0.0
    var lat :  Double? = 0.0
    var subSAPNote : SAPNote = SAPNote()
    lateinit var lManager : LocationManager
    var dummyDate = "/Date(1622592000000)/"
    var dummyTime = "PT00H00M00S"
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setView(infview)
            var mets = DisplayMetrics()
            requireContext().display?.getRealMetrics(mets)
            noteRV.layoutParams.width = (mets.widthPixels * 0.71).toInt()
            noteRV.layoutParams.height =  (mets.heightPixels * 0.65).toInt()
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = requireActivity().layoutInflater;
        infview = inflater.inflate(R.layout.note_view, null)
        binding = infview.rootView
        saveTextNote = infview.findViewById(R.id.submit_note)
        saveAudioNote = infview.findViewById(R.id.record_button)
        cameraBtn = infview.findViewById(R.id.camera_button)
        noteTextView = infview.findViewById(R.id.note_text)
        noteRV = infview.findViewById(R.id.notes_list)
        noteAdapter = NotesAdapter2(requireActivity().supportFragmentManager, requireContext(),notes)
        noteRV.adapter = noteAdapter
        Manifest.permission()
        mViewModel=ViewModelProviders.of(requireActivity()).get(PlansDataModel::class.java)
        lManager = requireContext().getSystemService(LOCATION_SERVICE) as LocationManager
        outputDir = "${requireActivity().filesDir?.path}/ggg.3gp"
//        getNotes()

        getNotesOffline()
        subSAPNote.ZuphrFpName = "DummyUser"
        subSAPNote.ZuphrFpDate = dummyDate
        subSAPNote.ZuphrFpTime = dummyTime
        subSAPNote.ZuphrId1 = id1
        subSAPNote.ZuphrId2 = id2
        subSAPNote.ZuphrId3 = id3
        subSAPNote.ZuphrType = noteType
        subSAPNote.ZuphrMjahr = noteMjahr
        subSAPNote.ZuphrNoteId = "1"
        subSAPNote.Lat = lat.toString()
        subSAPNote.Lon = long.toString()
        noteRV.layoutManager = LinearLayoutManager(requireActivity())
        noteRV.scrollToPosition(noteAdapter.itemCount-1)

        cameraBtn.setOnClickListener {
            dispatchTakePictureIntent()
        }

        saveAudioNote.setOnClickListener(this::saveAudioClickListener)
        //FOR ONLINE
        /*
                saveTextNote.setOnClickListener(this::saveTextClickListner)

         */

        //FOR OFFLINE
        saveTextNote.setOnClickListener(this::saveTextClickListnerOffline)
        noteRV.scrollToPosition(noteAdapter.itemCount-1)


        val preferences: SharedPreferences = requireContext().getSharedPreferences(
                requireContext().getResources().getString(R.string.SharedPrefName), Activity.MODE_PRIVATE
        )
        Token = preferences.getString("x-csrf-token", "")+""


    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
      binding = infview.rootView
        return binding
    }
    var screenFlag = 0
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onResume() {
        super.onResume()

        var mets = DisplayMetrics()
        requireContext().display?.getRealMetrics(mets)
        if (dialog != null) {
        val window = dialog!!.window
            window!!.setGravity(Gravity.CENTER)
        window!!.setLayout((mets.widthPixels * 0.75).toInt(), WindowManager.LayoutParams.WRAP_CONTENT)
        }


        binding.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val v: View? = v.findFocus()
                if (v is EditText) {
                    val outRect = Rect()
                    v.getGlobalVisibleRect(outRect)
                    if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                        Log.d("focus", "touchevent")
                        v.clearFocus()
                       // window!!.setLayout((mets.widthPixels * 0.75).toInt(), (mets.heightPixels * 0.75).toInt())
                        val imm: InputMethodManager =
                            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                    }
                }
            }
            false
        }






    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun permission(): Boolean {

        return if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.INTERNET
            ) != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val permissions = arrayOf(
                android.Manifest.permission.RECORD_AUDIO,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.INTERNET
            )
            requireActivity().requestPermissions(permissions, 0)

            false
        } else {
            true
        }


    }


    fun onRecord(start: Boolean) = if (start) {
        startRecording()
    } else {
        stopRecoding()
    }

    //Recording
    fun startRecording() {

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


    fun stopRecoding() {
        recorder?.apply {
            stop()
            release()
            recording = false
        }
        recorder = null
    }



    private val REQUEST_IMAGE_CAPTURE = 1

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            requireActivity().startActivityFromFragment(
                this,
                takePictureIntent,
                REQUEST_IMAGE_CAPTURE
            )
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
            var picString = Base64Variants.getDefaultVariant().encode(byteArray)
            subSAPNote.ZuphrContent = picString
            subSAPNote.ZuphrContentType = "IMG"
         
                sendNote()

        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun saveAudioClickListener(v: View) {
            if (!this.permission())
                Toast.makeText(
                    requireContext(),
                    "You Have Not Granted Permission To Record",
                    Toast.LENGTH_LONG
                ).show()
            else if (!recording) {
                recording = true
                onRecord(recording)
                saveAudioNote.setImageResource(R.drawable.ic_stop_button)
                saveAudioNote.background = null
            } else {
                recording = false

                onRecord(recording)
                saveAudioNote.setImageResource(R.drawable.ic_speaker)
                saveAudioNote.background = null

                val file = File(outputDir)
                val FileBytes: ByteArray = file.readBytes()
                var encoded = Base64Variants.getDefaultVariant().encode(FileBytes)

                subSAPNote.ZuphrContent = encoded.toString()
                subSAPNote.ZuphrContentType = "AUD"
                recordCount++
            
                    sendNote()
            }

    }


    fun sendNote(){
        if(RestApiClient.getInstance()!=null)
        RestApiClient.getInstance().retrofitInterface.submitNote(subSAPNote, Token)
                .enqueue(object : Callback,
                        retrofit2.Callback<ResponseBody> {
                    override fun onResponse(
                            call: Call<ResponseBody>,
                            response: Response<ResponseBody>
                    ) {
                        if (response.isSuccessful) {
                            var note = SharefPref.mapper.readValue<dVariableJSON<SAPNote>>(response.body()?.string()!!).d
                            notes.add(
                                    Notes(
                                            note!!.ZuphrFpName!!.lowercase(),
                                            note.ZuphrContent!!,
                                            note!!.ZuphrContentType,
                                            SharefPref.parseTime(note.ZuphrFpTime!!)!!,
                                            SharefPref.parseDate(note.ZuphrFpDate!!)!!
                                    )
                            )
                            noteAdapter.notifyDataSetChanged()
                            noteRV.scrollToPosition(noteAdapter.itemCount - 1)

                        }

                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.i("ApiConnection", "Failed: ${t.localizedMessage}")
                    }


                })

    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun saveTextClickListner(v: View){
        var textBoxValue = noteTextView.text.toString()
        if (textBoxValue.isNotEmpty() && textBoxValue.trim()!= "") {
                subSAPNote.ZuphrContent = noteTextView.text.toString()
                subSAPNote.ZuphrContentType = "TXT"
                notes.add(
                    Notes(
                        subSAPNote!!.ZuphrFpName!!.lowercase(),
                        subSAPNote.ZuphrContent!!,
                        subSAPNote!!.ZuphrContentType,
                        SharefPref.parseTime(subSAPNote.ZuphrFpTime!!)!!,
                        SharefPref.parseDate(subSAPNote.ZuphrFpDate!!)!!
                    )
                )
                noteAdapter.notifyDataSetChanged()
                noteRV.scrollToPosition(noteAdapter.itemCount - 1)
                noteTextView.text.clear()
                sendNote()

            }
    }

    fun saveTextClickListnerOffline(v: View){
        subSAPNote.ZuphrContent = noteTextView.text.toString()
        subSAPNote.ZuphrContentType = "TXT"
        notes.add(
            Notes(
                subSAPNote!!.ZuphrFpName!!.lowercase(),
                subSAPNote.ZuphrContent!!,
                subSAPNote!!.ZuphrContentType,
                SharefPref.parseTime(subSAPNote.ZuphrFpTime!!)!!,
                SharefPref.parseDate(subSAPNote.ZuphrFpDate!!)!!
            )
        )
        noteAdapter.notifyDataSetChanged()
        noteRV.scrollToPosition(noteAdapter.itemCount - 1)
        noteTextView.text.clear()
        noteAdapter.historyList = notes


        val materials: MutableList<Material> = mViewModel.plan.getValue()!!.getPlanToItems()
        val plan: Plan? = mViewModel.plan.getValue()
        for (i in materials.indices) {
            if ("" === materials[i].zuphrShortxt) {
                material.setNotes(notes)
                materials[i] = material
                plan!!.planToItems = materials
                mViewModel.plan.setValue(plan)
            }
            break
        }

        val plans: MutableList<Plan> = mViewModel.Plans.getValue()!!
        for (i in mViewModel.Plans.getValue()!!.indices) {
            if (mViewModel.plan.getValue()!!.getZuphrLpid() == mViewModel.Plans.getValue()!!.get(i)
                    .getZuphrLpid()
            ) {
                plans[i] = mViewModel.plan.getValue()!!
                mViewModel.Plans.setValue(plans)
            }
        }


    }

    fun getNotesOffline(){
        var SAPnotesList : ArrayList<SAPNote>?

        notes.forEach {
            Log.i("looping", it.noteText + " ")
        }
    }

    fun getNotes(){
        var filterstr = "NoteSet?\$filter=ZuphrType eq '$noteType' and ZuphrId1 eq '$id1' and ZuphrId2 eq '$id2' and ZuphrId3 eq '$id3' "

        if(RestApiClient.getInstance()!=null)
        RestApiClient.getInstance().retrofitInterface.retrieveNotes(filterstr)
                .enqueue(object : Callback,
                        retrofit2.Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            var sAPnotesList : ArrayList<SAPNote>? = SharefPref.mapper.readValue<dVariableJSON<resultVariableJSON<ArrayList<SAPNote>>>>(response.body()?.string()!!).d!!.results
                            notes.clear()
                            sAPnotesList?.forEach {
                                notes.add(Notes(it!!.ZuphrFpName!!.lowercase(),it!!.ZuphrContent!! , it.ZuphrContentType,
                                        SharefPref.parseTime(it.ZuphrFpTime!!)!!, SharefPref.parseDate(it.ZuphrFpDate!!)!!
                                )
                                )
                            }
                            notes.sortWith(compareBy( {x->x.date},{x->x.time}))
                            noteAdapter.historyList = notes
                            noteRV.scrollToPosition(notes.size-1)
                        }

                    }
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.i("ApiConnection","Failed: ${t.localizedMessage}")
                    }


                })
    }


}