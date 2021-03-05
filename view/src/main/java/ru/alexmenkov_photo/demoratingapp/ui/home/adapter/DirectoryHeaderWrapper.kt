package ru.alexmenkov_photo.demoratingapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.alexmenkov_photo.demoratingapp.R

class DirectoryHeaderWrapper(private val onItemClick: (String?) -> Unit) {

    private var viewHolder: DirectoryHeaderViewHolder? = null

    fun createViewHolder(parent: ViewGroup) = DirectoryHeaderViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.header_lot_page, parent, false), onItemClick
    ).also {
        this.viewHolder = it
        it.bind()
    }

    class DirectoryHeaderViewHolder(
        itemView: View,
        private val onItemClick: (String?) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind() {

        }
    }
}