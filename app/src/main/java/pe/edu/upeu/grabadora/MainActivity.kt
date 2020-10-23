package pe.edu.upeu.grabadora

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var mr : MediaRecorder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var path = Environment.getExternalStorageDirectory().toString()+"/Grabacion.3gp"
        mr = MediaRecorder()

        button.isEnabled = false
        button2.isEnabled = false

        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO)!= PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO,
                                                                    Manifest.permission.WRITE_EXTERNAL_STORAGE),111)

            button.isEnabled = true

        button.setOnClickListener {
            mr.setAudioSource(MediaRecorder.AudioSource.MIC)
            mr.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            mr.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB)
            mr.setOutputFile(path)
            mr.prepare()
            mr.start()
            button2.isEnabled = true
            button.isEnabled = false

        }


        button2.setOnClickListener {
            mr.stop()
            button.isEnabled = true
            button2.isEnabled = false
        }

        button3.setOnClickListener {
            var mp = MediaPlayer()
            mp.setDataSource(path)
            mp.prepare()
            mp.start()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==111 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            button.isEnabled = true
    }
}