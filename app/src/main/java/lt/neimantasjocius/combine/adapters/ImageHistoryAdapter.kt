package lt.neimantasjocius.combine.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import lt.neimantasjocius.combine.R
import lt.neimantasjocius.combine.data.Image

class ImageHistoryAdapter(
    private val data: MutableList<Image>
) : RecyclerView.Adapter<ImageHistoryAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v)
    {
        var imageView = v.findViewById<ImageView>(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.about_image, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = data[position]

//        holder.imageView = ???? pagal path paskirti onClickListener
        holder.itemView.setOnClickListener(View.OnClickListener {

        })


        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return data.size
    }

}