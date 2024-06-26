package com.avgust.final_task.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.avgust.final_task.data.database.entities.BasketEntity
import com.avgust.final_task.data.database.entities.ProductEntity
import com.avgust.final_task.data.model.Products
import com.avgust.final_task.utils.NetworkResults
import com.avgust.final_task.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository, application: Application
): AndroidViewModel(application) {

    //database
    val readProduct: LiveData<List<ProductEntity>> = repository.local.readProduct().asLiveData()
    val readBasket: LiveData<List<BasketEntity>> = repository.local.readBasket().asLiveData()

    private fun insertProduct(productEntity: ProductEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertProduct(productEntity)
        }

    fun insertBasket(basketEntity: BasketEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertBasket(basketEntity)
        }

    fun deleteProduct(basketEntity: BasketEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteProduct(basketEntity)
        }

    fun clearBasket() =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.clearBasket()
        }

    //query
    fun searchDb(searchQuery: String): LiveData<List<ProductEntity>>{
        return repository.local.searchDb(searchQuery).asLiveData()
    }




    //retrofit
    var productResponse: MutableLiveData<NetworkResults<Products>> = MutableLiveData()

    fun getProducts(queries: Map<String,String>) = viewModelScope.launch {
        getProductsSafeCall(queries)
    }

    fun getProductsElectronic(queries: Map<String,String>) = viewModelScope.launch {
        getProductsElectronicSafeCall(queries)
    }

    fun getProductsJewelery(queries: Map<String,String>) = viewModelScope.launch {
        getProductsJewelerySafeCall(queries)
    }

    fun getProductsMen(queries: Map<String,String>) = viewModelScope.launch {
        getProductsMenSafeCall(queries)
    }

    fun getProductsWomen(queries: Map<String,String>) = viewModelScope.launch {
        getProductsWomenSafeCall(queries)
    }

    private suspend fun getProductsSafeCall(queries: Map<String, String>) {
        productResponse.value = NetworkResults.Loading()
        if(connectionInternet()){
            try {
                val response = repository.remote.getProducts(queries)
                productResponse.value = handleProductResponse(response)

                val product = productResponse.value!!.data
                if(product != null){
                    offlineCacheProducts(product)
                }

            }catch (e: Exception){
                productResponse.value = NetworkResults.Error("Products not found :(")
            }
        }else{
            productResponse.value = NetworkResults.Error("No internet Connection")
        }
    }


    private suspend fun getProductsElectronicSafeCall(queries: Map<String, String>) {
        productResponse.value = NetworkResults.Loading()
        if(connectionInternet()){
            try {
                val response = repository.remote.getElectronics(queries)
                productResponse.value = handleProductResponse(response)

                val product = productResponse.value!!.data
                if(product != null){
//                    offlineCacheProducts(product)
                }

            }catch (e: Exception){
                productResponse.value = NetworkResults.Error("Products not found :(")
            }
        }else{
            productResponse.value = NetworkResults.Error("No internet Connection")
        }
    }

    private suspend fun getProductsJewelerySafeCall(queries: Map<String, String>) {
        productResponse.value = NetworkResults.Loading()
        if(connectionInternet()){
            try {
                val response = repository.remote.getJewelery(queries)
                productResponse.value = handleProductResponse(response)

                val product = productResponse.value!!.data
                if(product != null){
//                    offlineCacheProducts(product)
                }

            }catch (e: Exception){
                productResponse.value = NetworkResults.Error("Products not found :(")
            }
        }else{
            productResponse.value = NetworkResults.Error("No internet Connection")
        }
    }

    private suspend fun getProductsMenSafeCall(queries: Map<String, String>) {
        productResponse.value = NetworkResults.Loading()
        if(connectionInternet()){
            try {
                val response = repository.remote.getMenClothing(queries)
                productResponse.value = handleProductResponse(response)

                val product = productResponse.value!!.data
                if(product != null){
//                    offlineCacheProducts(product)
                }

            }catch (e: Exception){
                productResponse.value = NetworkResults.Error("Products not found :(")
            }
        }else{
            productResponse.value = NetworkResults.Error("No internet Connection")
        }
    }

    private suspend fun getProductsWomenSafeCall(queries: Map<String, String>) {
        productResponse.value = NetworkResults.Loading()
        if(connectionInternet()){
            try {
                val response = repository.remote.getWomenClothing(queries)
                productResponse.value = handleProductResponse(response)

                val product = productResponse.value!!.data
                if(product != null){
//                    offlineCacheProducts(product)
                }

            }catch (e: Exception){
                productResponse.value = NetworkResults.Error("Products not found :(")
            }
        }else{
            productResponse.value = NetworkResults.Error("No internet Connection")
        }
    }


    private fun offlineCacheProducts(product: Products) {
        val productEntity = ProductEntity(product)
        insertProduct(productEntity)
    }


    private fun handleProductResponse(response: Response<Products>): NetworkResults<Products>? {
        when{
            response.message().toString().contains("timeout") -> {
                return NetworkResults.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResults.Error("Key fail!")
            }
            response.body()!!.isNullOrEmpty() -> {
                return NetworkResults.Error("product not found")
            }
            response.isSuccessful -> {
                val product = response.body()
                return NetworkResults.Success(product!!)
            }
            else ->{
                return NetworkResults.Error(response.message())
            }
        }
    }

    private fun connectionInternet():Boolean{
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when{
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }


}