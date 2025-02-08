package com.lucascosta.catsanddogs.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lucascosta.catsanddogs.repository.api.client.ClientRetrofitCat
import com.lucascosta.catsanddogs.repository.api.client.ClientRetrofitDog
import com.lucascosta.catsanddogs.repository.api.model.CatEntity
import com.lucascosta.catsanddogs.repository.api.model.DogEntity
import com.lucascosta.catsanddogs.repository.api.service.CatService
import com.lucascosta.catsanddogs.repository.api.service.DogService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CuriositiesViewModel : ViewModel() {
    private var currentAnimal = MutableLiveData<String>()
    private var currentCuriosity = MutableLiveData<String>()

    fun getCurrentAnimal(): LiveData<String> {
        return currentAnimal
    }

    fun getCurrentCuriosity(): LiveData<String> {
        return currentCuriosity
    }

    fun setCurrentAnimal(animal: String) {
        currentAnimal.value = animal
        newCuriosity(animal)
    }

    fun newCuriosity(animal: String) {
        when (animal) {
            "cat" -> {
                val catService = ClientRetrofitCat.createService(CatService::class.java)
                val newFact: Call<CatEntity> = catService.getNewFact()

                newFact.enqueue(object : Callback<CatEntity> {
                    override fun onResponse(
                        call: Call<CatEntity>, response: Response<CatEntity>
                    ) {
                        val catEntity = response.body()
                        currentCuriosity.value = catEntity?.fact
                    }

                    override fun onFailure(call: Call<CatEntity>, t: Throwable) {
                        currentCuriosity.value = "Falha na requisição da API"
                    }
                })
            }

            "dog" -> {
                val dogService = ClientRetrofitDog.createService(DogService::class.java)
                val newFact: Call<DogEntity> = dogService.getNewFact()

                newFact.enqueue(object : Callback<DogEntity> {
                    override fun onResponse(
                        call: Call<DogEntity>, response: Response<DogEntity>
                    ) {
                        val dogEntity = response.body()
                        currentCuriosity.value = dogEntity?.facts?.get(0)
                    }

                    override fun onFailure(call: Call<DogEntity>, t: Throwable) {
                        currentCuriosity.value = "Falha na requisição da API"
                    }
                })
            }
        }
    }
}