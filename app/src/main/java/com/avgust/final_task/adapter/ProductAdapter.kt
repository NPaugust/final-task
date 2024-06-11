package com.avgust.final_task.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.avgust.final_task.data.model.ProductsItem
import com.avgust.final_task.data.model.Products
import com.avgust.final_task.databinding.ProductsRowLayoutBinding
import com.avgust.final_task.utils.ProductDiffUtil

class ProductAdapter:RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {

    private var products = emptyList<ProductsItem>()

    class MyViewHolder(private val binding: ProductsRowLayoutBinding) :RecyclerView.ViewHolder(binding.root) {

        fun bind(result: ProductsItem){
            binding.result = result               //
            binding.executePendingBindings()  //updat
        }

        companion object{
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ProductsRowLayoutBinding.inflate(layoutInflater,parent,false)
                return MyViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder.from(parent)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentProduct = products[position]
        holder.bind(currentProduct)

    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun setData(newData: Products){
        val recipesDiffUtil = ProductDiffUtil(products,newData)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)

        products = newData

        diffUtilResult.dispatchUpdatesTo(this) }

}