package com.indialone.indiepdfreader

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.indialone.indiepdfreader.databinding.ActivityMainBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import java.io.File
import java.util.jar.Manifest

class MainActivity : AppCompatActivity(), OnPdfFileSelectListener {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mPdfAdapter: PdfRvAdapter
    private lateinit var mPdfList: ArrayList<File>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        runtimePermission()

    }

    private fun runtimePermission() {
        Dexter.withContext(this)
            .withPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    displayPdf()
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    Toast.makeText(this@MainActivity, "Permission is required", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onPermissionRationaleShouldBeShown(
                    request: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    token!!.continuePermissionRequest()
                }

            }).check()

    }

    private fun findPdfs(file: File): ArrayList<File> {
        val arrayList = ArrayList<File>()

        val pdfFiles = file.listFiles()

        if (pdfFiles != null) {

            for (pdfFile in pdfFiles) {
                if (pdfFile.isDirectory && !pdfFile.isHidden) {
                    arrayList.addAll(findPdfs(pdfFile))
                } else {
                    if (pdfFile.name.endsWith(".pdf")) {
                        arrayList.add(pdfFile)
                    }
                }
            }
        }
        return arrayList
    }

    private fun displayPdf() {
        mBinding.recyclerView.setHasFixedSize(true)
        mBinding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        mPdfList = ArrayList()
        mPdfList.addAll(findPdfs(Environment.getExternalStorageDirectory()))
        mPdfAdapter = PdfRvAdapter(this, mPdfList, this)
        mBinding.recyclerView.adapter = mPdfAdapter
    }

    override fun onPdfSelected(file: File) {
        val intent = Intent(this, DocumentViewerActivity::class.java)
        intent.putExtra("path", file.absolutePath)
        startActivity(intent)
    }

}