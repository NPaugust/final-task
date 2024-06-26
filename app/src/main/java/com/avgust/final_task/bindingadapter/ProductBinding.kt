package com.avgust.final_task.bindingadapter

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.avgust.final_task.data.model.ProductsItem
import com.avgust.final_task.data.database.entities.ProductEntity
import com.avgust.final_task.data.model.Products
import com.avgust.final_task.ui.screens.foodlist.ProductsFragmentDirections
import com.avgust.final_task.ui.screens.search.SearchFragmentDirections
import com.avgust.final_task.utils.NetworkResults

class ProductBinding {

    companion object{

        //for main page(production)...
        @BindingAdapter("productClickListener")
        @JvmStatic
        fun productClickListener(productsRowLayout: ConstraintLayout, product: ProductsItem){
            productsRowLayout.setOnClickListener {
                try {
                    val action = ProductsFragmentDirections.actionProductsFragmentToDetailsFragment(product)
                    productsRowLayout.findNavController().navigate(action)
                }catch (e: Exception){
                    Log.d("productClickListener", e.toString())
                }
            }
        }

        //for search page...
        @BindingAdapter("productSearchClickListener")
        @JvmStatic
        fun productSearchClickListener(productsRowLayout: ConstraintLayout, product: ProductsItem){
            productsRowLayout.setOnClickListener {
                try {
                    val action = SearchFragmentDirections.actionSearchFragmentToDetailsFragment(product)
                    productsRowLayout.findNavController().navigate(action)
                }catch (e: Exception){
                    Log.d("productSearchClickListener", e.toString())
                }
            }
        }

        @BindingAdapter("readApi", "readDb", requireAll = true)
        @JvmStatic
        fun errorImage(
            imageView: ImageView,
            apiResponse: NetworkResults<Products>?,
            db: List<ProductEntity>?
        ){
            if(apiResponse is NetworkResults.Error && db.isNullOrEmpty()){
                imageView.visibility = View.VISIBLE
            } else if(apiResponse is NetworkResults.Loading){
                imageView.visibility = View.INVISIBLE
            }else if(apiResponse is NetworkResults.Success){
                imageView.visibility = View.INVISIBLE
            }

        }

        @BindingAdapter("readApiText", "readDbText", requireAll = true)
        @JvmStatic
        fun errorText(
            textView: TextView,
            apiResponse: NetworkResults<Products>?,
            db: List<ProductEntity>?
        ){
            if(apiResponse is NetworkResults.Error && db.isNullOrEmpty()){
                textView.visibility = View.VISIBLE
                textView.text = apiResponse.message.toString()
            } else if(apiResponse is NetworkResults.Loading){
                textView.visibility = View.INVISIBLE
            }else if(apiResponse is NetworkResults.Success){
                textView.visibility = View.INVISIBLE
            }

        }

    }

}