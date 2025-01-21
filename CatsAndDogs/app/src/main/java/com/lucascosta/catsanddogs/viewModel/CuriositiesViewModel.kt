package com.lucascosta.catsanddogs.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lucascosta.catsanddogs.repository.CuriositiesRepository

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
        val curiosity = when (animal) {
            "cat" -> curiosityRepository.getCatCuriosity()
            "dog" -> curiosityRepository.getDogCuriosity()
            else -> ""
        }

        currentCuriosity.value = curiosity
    }
}