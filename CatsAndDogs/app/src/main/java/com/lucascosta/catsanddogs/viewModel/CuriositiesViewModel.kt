package com.lucascosta.catsanddogs.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lucascosta.catsanddogs.repository.CuriositiesRepository
import com.lucascosta.catsanddogs.repository.api.client.ClientRetrofit
import com.lucascosta.catsanddogs.repository.api.model.DogEntity
import com.lucascosta.catsanddogs.repository.api.service.DogService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CuriositiesViewModel : ViewModel() {
    private var curiosityRepository = CuriositiesRepository()
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
        var curiosity: String? = ""

        when (animal) {
            "cat" -> {

            }

            "dog" -> {
                val dogService = ClientRetrofit.createService(DogService::class.java)
                val newFact: Call<DogEntity> = dogService.getNewFact()

                newFact.enqueue(object : Callback<DogEntity> {
                    override fun onResponse(
                        call: Call<DogEntity>,
                        response: Response<DogEntity>
                    ) {
                        val dogEntity = response.body()
                        curiosity = dogEntity?.facts?.get(1)
                    }

                    override fun onFailure(call: Call<DogEntity>, t: Throwable) {
                        curiosity = "Falha na requisição da API"
                    }
                })
            }
        }

        currentCuriosity.value = curiosity
    }
}