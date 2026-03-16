package com.teleconta.pas.activities

import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.os.Looper
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.teleconta.pas.R
import org.apache.commons.net.ftp.FTP
import org.apache.commons.net.ftp.FTPClient
import java.io.File
import java.io.FileOutputStream

class AdFragment : DialogFragment() {

    private lateinit var imageView: ImageView
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var updateImageRunnable: Runnable

    override fun onCreateDialog(savedInstanceState: Bundle?): AlertDialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_ad, null)

        imageView = view.findViewById(R.id.adImage)

        builder.setView(view)
            .setPositiveButton("Fechar") { dialog, _ ->
                dialog.dismiss()
            }

        isCancelable = false
        return builder.create()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_ad, container, false)
    }

    override fun onStart() {
        super.onStart()
        startImageUpdate()
    }

    private fun startImageUpdate() {
        val server = "ftp.seculoteleconta.com.br"
        val port = 21
        val user = "rtlt1i2v"
        val pass = "m0S2xv80hH"
        val remoteFilePath = "/public_html/pas_app_image/pas_ad2.jpg"
        val localFilePath = requireContext().filesDir.absolutePath + "/image.jpg"

        Thread {
            val success = downloadImageFromFTP(server, port, user, pass, remoteFilePath, localFilePath)
            if (success) {
                requireActivity().runOnUiThread {
                    val imgFile = File(localFilePath)
                    if (imgFile.exists()) {
                        Log.d("AdFragment", "Image file exists: ${imgFile.absolutePath}")
                        val myBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
                        if (myBitmap != null) {
                            imageView.setImageBitmap(myBitmap)
                            Log.d("AdFragment", "Image set in ImageView")
                        } else {
                            Log.e("AdFragment", "Failed to decode Bitmap from image file")
                        }
                    } else {
                        Log.e("AdFragment", "Image file does not exist: ${imgFile.absolutePath}")
                    }
                }
            } else {
                Log.e("AdFragment", "Failed to download image from FTP server")
            }
        }.start()
    }

    private fun downloadImageFromFTP(server: String, port: Int, user: String, pass: String, remoteFilePath: String, localFilePath: String): Boolean {
        val ftpClient = FTPClient()
        return try {
            ftpClient.connect(server, port)
            ftpClient.login(user, pass)
            ftpClient.enterLocalPassiveMode()
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE)

            val outputStream = FileOutputStream(File(localFilePath))
            val success = ftpClient.retrieveFile(remoteFilePath, outputStream)
            outputStream.close()
            ftpClient.logout()
            if (success) {
                Log.d("AdFragment", "Image downloaded successfully from FTP server")
            } else {
                Log.e("AdFragment", "Failed to download image from FTP server")
            }
            success
        } catch (ex: Exception) {
            ex.printStackTrace()
            Log.e("AdFragment", "Exception during FTP download: ${ex.message}")
            false
        } finally {
            try {
                if (ftpClient.isConnected) {
                    ftpClient.disconnect()
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                Log.e("AdFragment", "Exception during FTP disconnect: ${ex.message}")
            }
        }
    }
}