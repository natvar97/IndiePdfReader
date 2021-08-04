package com.indialone.indiepdfreader

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.indialone.indiepdfreader.databinding.ElementHolderBinding
import java.io.File

class PdfRvAdapter(
    private val context: Context,
    private val pdfFiles: ArrayList<File>,
    private val listener: OnPdfFileSelectListener
) : RecyclerView.Adapter<PdfRvAdapter.PdfRvViewHolder>() {
    class PdfRvViewHolder(itemView: ElementHolderBinding) : RecyclerView.ViewHolder(itemView.root) {
        val fileName = itemView.tvPdfName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PdfRvViewHolder {
        val view = ElementHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PdfRvViewHolder(view)
    }

    override fun onBindViewHolder(holder: PdfRvViewHolder, position: Int) {
        holder.fileName.text = pdfFiles[position].name
        holder.fileName.isSelected = true
        holder.itemView.setOnClickListener {
            listener.onPdfSelected(pdfFiles[position])
        }
    }

    override fun getItemCount(): Int {
        return pdfFiles.size
    }
}