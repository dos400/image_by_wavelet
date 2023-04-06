package uz.hamroev.imagebywavelet.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.hamroev.imagebywavelet.databinding.ItemSaveBinding
import java.io.File

class GalleryAdapter(
    context: Context,
    var imageList: List<File>,
    var onSavedImageClickListener: OnSavedImageClickListener,
) :
    RecyclerView.Adapter<GalleryAdapter.VhSaved>() {

    inner class VhSaved(var itemSaveBinding: ItemSaveBinding) :
        RecyclerView.ViewHolder(itemSaveBinding.root) {


        fun onBind(file: File) {
            val uri = Uri.fromFile(file)
            itemSaveBinding.savedImage.setImageURI(uri)
            itemSaveBinding.savedImage.setOnClickListener {
                onSavedImageClickListener.onClick(file)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VhSaved {
        return VhSaved(ItemSaveBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = imageList.size

    override fun onBindViewHolder(holder: VhSaved, position: Int) {
        return holder.onBind(imageList[position])
    }

    interface OnSavedImageClickListener {
        fun onClick(file: File)
    }
}