package uz.hamroev.imagebywavelet.model

import com.mukesh.imageproccessing.filters.Filter


data class ImageFilter(
    var filterName: String,
    var filterFunctionName: Filter,
    var filteredImage: Int
): java.io.Serializable
