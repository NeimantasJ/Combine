package lt.neimantasjocius.combine.adapters

import android.graphics.Bitmap
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.view.LayoutInflater
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import lt.neimantasjocius.combine.R
import lt.neimantasjocius.combine.data.Image

class ImageListAdapter : ListAdapter<Image, ImageListAdapter.ImageViewHolder>(ImageComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val current = getItem(position)
        // TODO Pakeisti type
        holder.bind(current)
    }

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(filename: String) {
            // TODO Konvertuoti failą iš image path į bitmap
            val bitmap =
            Glide.with(itemView).asBitmap().load(bitmap).into(image)
        }

        companion object {
            fun create(parent: ViewGroup): ImageViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.about_image, parent, false)
                return ImageViewHolder(view)
            }
        }
    }

    class ImageComparator : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem.path == newItem.path
        }
    }
}