package com.indialone.indiepdfreader

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.indialone.indiepdfreader.databinding.ActivityDocumentViewerBinding
import java.io.File

class DocumentViewerActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityDocumentViewerBinding
    private var pdfFilePath = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityDocumentViewerBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        if (intent.hasExtra("path")) {
            pdfFilePath = intent.getStringExtra("path")!!
        }

        val file = File(pdfFilePath)
        val path: Uri = Uri.fromFile(file)
        mBinding.pdfView.fromUri(path).load()

    }
}