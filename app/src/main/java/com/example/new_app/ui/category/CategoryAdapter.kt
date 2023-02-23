package com.example.new_app.ui.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.new_app.databinding.CategoryItemRecyclerBinding

class CategoryAdapter(var item: List<CategoryDataClass>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {


    class ViewHolder(var viewBinding: CategoryItemRecyclerBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var viewBinding = CategoryItemRecyclerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //here >  but data on the view > (category -item-xml )
        // by the data class > item :List<CategoryDataClass>
        holder.viewBinding.name
            .text = item[position].name
        holder.viewBinding.image
            .setImageResource(item[position].imageId)
        holder.viewBinding.layoutItem
            .setCardBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, item[position].backgroundColorId)
            )

        //it > if-- true >
        onItemClickListener?.let {
            holder.viewBinding.layoutItem.setOnClickListener(View.OnClickListener {
                onItemClickListener?.onItemCkick(position,item[position])
            })
        }




    }
            var onItemClickListener:OnItemClickListener? =null
    interface OnItemClickListener{
    fun onItemCkick(pos:Int,item:CategoryDataClass)

    }
    override fun getItemCount(): Int = item.size ?: 0


}