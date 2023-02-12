package uz.hamroev.imagebywavelet.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mukesh.imageproccessing.PhotoFilter
import uz.hamroev.imagebywavelet.databinding.ItemImageFilterBinding
import uz.hamroev.imagebywavelet.model.ImageFilter

class ImageFilterAdapter(
    var context: Context,
    var list: ArrayList<ImageFilter>,
    var onClickListerImageFilterItem: OnClickListerImageFilterItem
) :
    RecyclerView.Adapter<ImageFilterAdapter.VhImageFilter>() {


    inner class VhImageFilter(private var binding: ItemImageFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {



        fun onBind(imageFilter: ImageFilter, position: Int) {
            binding.filterName.text = imageFilter.filterName
            binding.imageFilter.setImageResource(imageFilter.filteredImage)
            binding.main.setOnClickListener {
                onClickListerImageFilterItem.onClick(imageFilter, position)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VhImageFilter {
        return VhImageFilter(
            ItemImageFilterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: VhImageFilter, position: Int) {
        return holder.onBind(list[position], position)
    }

    interface OnClickListerImageFilterItem {
        fun onClick(imageFilter: ImageFilter, position: Int)
    }

}


