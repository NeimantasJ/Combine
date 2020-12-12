package lt.neimantasjocius.combine.adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import lt.neimantasjocius.combine.R
import lt.neimantasjocius.combine.activities.MagicActivity
import lt.neimantasjocius.combine.sql.Image
import java.io.File

class ImageListAdapter(private val data: MutableList<Image>) : RecyclerView.Adapter<ImageListAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var imageView: ImageView = v.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageListAdapter.ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.about_image, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ImageListAdapter.ViewHolder, position: Int) {
        val image = data[position]

        try {
            val file = File(image.path!!)

            if (file.exists()) {
                Glide.with(holder.itemView.context)
                    .asBitmap()
                    .load(image.path)
                    .into(holder.imageView)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }
}
