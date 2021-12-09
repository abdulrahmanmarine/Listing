package com.example.listing.notes

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Application
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
import java.util.*
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = requireActivity().layoutInflater;


    }
}