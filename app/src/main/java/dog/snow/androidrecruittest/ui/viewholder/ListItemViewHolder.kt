package dog.snow.androidrecruittest.ui.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.databinding.ListItemBinding
import dog.snow.androidrecruittest.extension.loadUrl
import dog.snow.androidrecruittest.model.ListItem

class ListItemViewHolder(private val binding: ListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(listItem: ListItem?, listener: (ListItem) -> Unit) {
        with(binding) {
            if (listItem != null) {
                imgThumb.loadUrl(listItem.thumbnailUrl)
                txtAlbumTitle.text = listItem.albumTitle
                txtPhotoTitle.text = listItem.title
                root.setOnClickListener { listener.invoke(listItem) }
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): ListItemViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false)
            val binding = ListItemBinding.bind(view)
            return ListItemViewHolder(binding)
        }
    }
}