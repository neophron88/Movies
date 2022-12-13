package com.rasulov.main.presentation.all_categories.setup.spinner_adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.res.ResourcesCompat
import com.rasulov.main.R
import com.rasulov.main.databinding.SpinnerSortbyItemBinding


class SpinnerSortByAdapter(
    context: Context
) : BaseAdapter() {

    private val data = context.resources.getStringArray(R.array.sort_by)
    private val font = ResourcesCompat.getFont(context, com.rasulov.feature.R.font.opensans_medium)

    private val inflate = LayoutInflater.from(context)

    override fun getCount(): Int = data.size

    override fun getItem(position: Int): Any = data[position]


    override fun getItemId(position: Int): Long = position.toLong()


    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val holder = if (convertView == null) {
            SpinnerSortByHolder(
                SpinnerSortbyItemBinding.inflate(inflate, parent, false),
                font
            ).also { it.binding.root.tag = it }

        } else convertView.tag as SpinnerSortByHolder

        holder.bind(data[position])
        return holder.binding.root
    }

    private class SpinnerSortByHolder(
        val binding: SpinnerSortbyItemBinding,
        typeface: Typeface?
    ) {
        init {
            binding.sortByText.typeface = typeface
        }

        fun bind(item: String) {
            binding.sortByText.text = item
        }

    }
}



