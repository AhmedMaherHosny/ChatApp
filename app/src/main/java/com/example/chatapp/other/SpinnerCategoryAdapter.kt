package com.example.chatapp.other

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.chatapp.R
import com.example.chatapp.model.SpinnerCategory

class SpinnerCategoryAdapter(private val items: List<SpinnerCategory>) : BaseAdapter() {

    override fun getCount(): Int = items.size

    override fun getItem(index: Int): Any = items[index]

    override fun getItemId(index: Int): Long = index.toLong()

    override fun getView(index: Int, view: View?, container: ViewGroup?): View {
//        val binding = ItemSpinnerCategoryBinding.inflate(
//            LayoutInflater.from(container!!.context),
//            container,
//            false
//        )
//        val viewHolder = ViewHolder(binding)
//        viewHolder.bind(item)
//        return ViewHolder(binding)
        val item = items[index]
        var myView = view
        val viewHolder: ViewHolder
        when (myView) {
            null -> {
                myView = LayoutInflater.from(container!!.context)
                    .inflate(R.layout.item_spinner_category, container, false)
                viewHolder = ViewHolder(myView)
                myView.tag = viewHolder
            }
            else -> {
                viewHolder = myView.tag as ViewHolder
            }
        }
        viewHolder.title.text = item.name
        viewHolder.image.setImageResource(item.imageId)
        return myView!!
    }

    class ViewHolder(view: View) {
        //        fun bind(item: SpinnerCategory) {
//            binding.titleSpinner.text = item.name
//            binding.iconSpinner.setImageResource(item.imageId)
//        }
        val title: TextView = view.findViewById(R.id.titleSpinner)
        val image: ImageView = view.findViewById(R.id.iconSpinner)
    }
}