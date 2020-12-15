package lt.neimantasjocius.combine.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import lt.neimantasjocius.combine.R
import lt.neimantasjocius.combine.sql.Image
import java.io.File

class ImageListAdapter(private val data: MutableList<Image>) : RecyclerView.Adapter<ImageListAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var imageView: ImageView = v.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageListAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.about_image, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ImageListAdapter.ViewHolder, position: Int) {
        val image = data[position]

        if (image.path != null){
            val file = File(image.path)

            if (file.exists()) {
                Glide.with(holder.itemView.context)
                    .asBitmap()
                    .load(image.path)
                    .into(holder.imageView)
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
