package com.avgust.final_task.ui.screens.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.avgust.final_task.viewModel.MainViewModel
import com.avgust.final_task.databinding.FragmentDetailsBinding
import com.avgust.final_task.data.database.entities.BasketEntity

import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val args by navArgs<DetailsFragmentArgs>()

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)


        binding.imageView.load(args.productDto.image)
        binding.titleTextView.text = args.productDto.title
        binding.priceTextView.text = args.productDto.price.toString()
        binding.descriptionTextView.text = args.productDto.description
        binding.categoryTextView.text = args.productDto.category
        binding.rateTextView.text = args.productDto.rating?.rate.toString()
        binding.countTextView.text = args.productDto.rating?.count.toString()



        binding.addButton.setOnClickListener {
            addToBasket()
        }


        return binding.root
    }



    private fun addToBasket() {
        val basketEntity =
            BasketEntity(
                0,
                args.productDto
            )
        mainViewModel.insertBasket(basketEntity)
            Snackbar.make(binding.root,"Food added",Snackbar.LENGTH_SHORT).setAction("ok"){}
            .show()

    }


}