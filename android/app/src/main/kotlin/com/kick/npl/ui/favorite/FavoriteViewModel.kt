package com.kick.npl.ui.favorite

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kick.npl.data.repository.ParkingLotsRepository
import com.kick.npl.model.ParkingLotData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val parkingLotsRepository: ParkingLotsRepository,
) : ViewModel() {

    var favoriteParkingLots = mutableStateListOf<ParkingLotData>()
        private set

    var isLoading = MutableStateFlow<Boolean>(true)
        private set

    init {
        getFavoriteParkingLots()
    }

    fun onClickFavorite(parkingLotData: ParkingLotData) = viewModelScope.launch(Dispatchers.IO) {

        isLoading.emit(true)

        parkingLotsRepository.toggleFavorite(
            id = parkingLotData.id, favorite = !parkingLotData.favorite
        )
        getFavoriteParkingLots()
    }

    fun getFavoriteParkingLots() {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.emit(true)
            favoriteParkingLots = parkingLotsRepository
                .getAllParkingLots()
                ?.filter { it.favorite }
                ?.toMutableStateList()
                ?: run {
                    isLoading.emit(false)
                    return@launch
                }
            isLoading.emit(false)
        }
    }
}
