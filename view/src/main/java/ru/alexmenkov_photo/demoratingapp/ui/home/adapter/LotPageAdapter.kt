package ru.alexmenkov_photo.demoratingapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnLayout
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.alexmenkov_photo.demoratingapp.R
import ru.alexmenkov_photo.demoratingapp.databinding.ItemLotBinding
import ru.alexmenkov_photo.demoratingapp.service.room.entity.Lot

const val HEADER_VIEW = 1

class LotPageAdapter(
    private val onClick: (Lot) -> Unit,
    private val headerHolderFactoryProducer: (ViewGroup) -> RecyclerView.ViewHolder,
    diffCallback: DiffUtil.ItemCallback<Lot>
) : PagingDataAdapter<Lot, RecyclerView.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == HEADER_VIEW) {
            return headerHolderFactoryProducer.invoke(parent)
        }
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.item_lot,
            parent,
            false
        )
        return LotGroupViewHolder(view, this)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position).let {
            try {
                /*if (holder is DirectoryHeaderViewHolder) {
                    val vh: DirectoryHeaderViewHolder = holder
                } else */if (holder is LotGroupViewHolder) {
                    val vh: LotGroupViewHolder = holder
                    vh.bind(it)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        if (position == -1) {
            return HEADER_VIEW
        }
        return super.getItemViewType(position)
    }


    class LotGroupViewHolder(itemView: View, val adapter: LotPageAdapter) :
        RecyclerView.ViewHolder(itemView) {

        private var _binding: ItemLotBinding? = ItemLotBinding.bind(itemView)
        private val binding get() = _binding!!

        fun bind(lot: Lot?) {
            if (lot != null) {
                binding.tvLotTitle.text = lot.lotTitle
                val img = binding.imgLotPreview
                img.doOnLayout {
                    Picasso.get()
                        .load(lot.imageUrl)
                        .placeholder(R.color.colorCard)
                        .error(R.color.design_default_color_error)
                        .resize(img.width, 0)
                        .into(img)
                }
                binding.cardItemLot.setOnClickListener {
                    adapter.onClick.invoke(lot)
                }
            }
        }
    }
}