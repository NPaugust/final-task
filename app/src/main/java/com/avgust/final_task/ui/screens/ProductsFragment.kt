package com.avgust.final_task.ui.screens

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.avgust.final_task.MainActivity
import com.avgust.final_task.R
import com.avgust.final_task.adapter.ProductAdapter
import com.avgust.final_task.databinding.FragmentProductsBinding
import com.avgust.final_task.ui.screens.details.DetailsFragmentArgs
import com.avgust.final_task.ui.screens.foodlist.ProductsFragmentDirections
import com.avgust.final_task.utils.NetworkListener
import com.avgust.final_task.utils.NetworkResults
import com.avgust.final_task.utils.observeOnce
import com.avgust.final_task.viewModel.MainViewModel
import com.avgust.final_task.viewModel.ProductViewModel

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductsFragment : Fragment() {

    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!

    private val mAdapter by lazy { ProductAdapter() }

    private lateinit var mainViewModel: MainViewModel
    private lateinit var productViewModel: ProductViewModel

    private lateinit var networkListener : NetworkListener

    private val args by navArgs<DetailsFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        productViewModel = ViewModelProvider(requireActivity())[ProductViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel

        setupRecyclerView()
        readDb()


        lifecycleScope.launch {
            networkListener = NetworkListener()
            networkListener.checkNetworkAvailability(requireContext())
                .collect{ status ->
                    Log.d("NetworkListener", status.toString())

                    productViewModel.networkState = status
                    productViewModel.networkStatus()
                }
        }

        binding.errorImageView.setOnClickListener {
            if(productViewModel.networkState){
                //findNavController().navigate(R.id.action_productsFragment_to_detailsFragment)

                /*val resultBundle = Bundle()
               resultBundle.putParcelable("productBundle", args.productDto)*/

                  val action = ProductsFragmentDirections.actionProductsFragmentToDetailsFragment(args.productDto)
                 findNavController().navigate(action)

            }else{
                productViewModel.networkStatus()
            }
        }



        return binding.root
    }


    private fun setupRecyclerView(){
        binding.recyclerview.adapter = mAdapter
        //binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerview.layoutManager = GridLayoutManager(requireContext(),2)
    }


    private fun readDb() {
        lifecycleScope.launch{
            mainViewModel.readProduct.observeOnce(viewLifecycleOwner){ db ->
                if(db.isNotEmpty()){
                    Log.d("ProductDb","read db")
                    mAdapter.setData(db[0].product)
                }else{
                    requestApiData()
                }
            }
        }
    }


    private fun requestApiData(){
        Log.d("ProductDb","read api")
        mainViewModel.getProducts(productViewModel.applyQueries())
        mainViewModel.productResponse.observe(viewLifecycleOwner){response ->
            when (response) {
                is NetworkResults.Success -> {
                    response.data?.let { mAdapter.setData(it) }
                }
                is NetworkResults.Error -> {
                    loadDataCache()              //if error
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResults.Loading -> {

                }
            }
        }
    }

    //if error!
    private fun loadDataCache(){
        lifecycleScope.launch{
            mainViewModel.readProduct.observe(viewLifecycleOwner){ db ->
                if(db.isNotEmpty()){
                    mAdapter.setData(db[0].product)
                }
            }
        }

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.actionbar_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.action_basket -> {
                        findNavController().navigate(R.id.basketFragment)
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }





    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).showBottomNavigation()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}