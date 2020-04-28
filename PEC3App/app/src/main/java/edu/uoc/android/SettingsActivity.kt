package edu.uoc.android

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import kotlinx.android.synthetic.main.activity_settings.*
import java.io.*


class SettingsActivity : AppCompatActivity() {

    var REQUEST_IMAGE_CAPTURE = 1
    lateinit var currentPhotoPath: String
    val REQUEST_TAKE_PHOTO = 1
    private val filepath = "UOCImageApp"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        //Check if a photo exists
        val filepath = "UOCImageApp"
        var fileName = "imageapp.jpg"
        var baseDir: Array<out File>? = getExternalFilesDirs(null)
        var sBaseDir = baseDir?.get(0)?.path?:""
        val myExternalFile = File(sBaseDir,filepath
                + File.separator + fileName
        )
        //If exist load
        if (myExternalFile != null)  {
            val bit = BitmapFactory.decodeFile(myExternalFile.toString())

            imageView?.setImageBitmap(bit)
            if (bit != null){
                textView.text = ""
            }

        }

        take_photo.setOnClickListener {
            checkExternalStoragePermission()

        }


    }


    //Open Camera, when photo is taken, go onActivityResult
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {

                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            try {
                val imageBitmap = data!!.extras?.get("data") as Bitmap
                imageView.setImageBitmap(imageBitmap)
                textView.text = ""

                val filepath = "UOCImageApp"
                var fileName = "imageapp.jpg"

                var baseDir: Array<out File>? = getExternalFilesDirs(null)
                var sBaseDir = baseDir?.get(0)?.path?:""

                val storageDir = sBaseDir + File.separator + filepath
                val storageDirFile = File(storageDir.toString())
                //Create storage Directory in case it doesnt exist
                storageDirFile.mkdirs()
                //Create file
                val myExternalFile = File(sBaseDir,filepath + File.separator + fileName)
                try {
                    val stream = ByteArrayOutputStream()
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG,100,stream)
                    val fileOutPutStream = FileOutputStream(myExternalFile)
                    fileOutPutStream.write(stream.toByteArray())
                    fileOutPutStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                Toast.makeText(applicationContext,"data saved in" + myExternalFile.path, Toast.LENGTH_SHORT).show()
            } catch (ex: Error) {}

        }



    }




    private fun checkExternalStoragePermission() {
        //Check permissions, if don't have, ask for permission
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            Log.e("Permisos", "Permission not granted WRITE_EXTERNAL_STORAGE.")
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
            } else {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1888
                )
            }
        }
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            Log.e("Permisos", "Permission not granted CAMERA.")
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.CAMERA
                )
            ) {
            } else {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.CAMERA),
                    226
                )
            }
        }
        //If permission granted, continue with photo
        if ((ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE
            )  == PackageManager.PERMISSION_GRANTED)
                &&
            (ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED
                ))
        {
            dispatchTakePictureIntent()
        }
    }




}
