package com.kick.npl.ui.manage

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.GeoPoint
import com.kick.npl.data.repository.MapsRepository
import com.kick.npl.data.repository.ParkingLotsRepository
import com.kick.npl.model.ParkingLotData
import com.naver.maps.geometry.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddParkingLotViewModel @Inject constructor(
    private val mapsRepository: MapsRepository,
    private val parkingLotsRepository: ParkingLotsRepository
) : ViewModel() {
    val parkingLotData = mutableStateOf(AddParkingLotUiState())
    val temporalAddressName = mutableStateOf<String?>(null)

    var isLoading by mutableStateOf(false)
        private set

    private val _result = MutableSharedFlow<AddParkingLotResult>()
    val result = _result.asSharedFlow()

    fun getAddressName(latlng: LatLng) {
        viewModelScope.launch(Dispatchers.IO) {
            mapsRepository.getMinifiedAddress(latlng).onSuccess {
                temporalAddressName.value = it
            }.onFailure {
                _result.emit(AddParkingLotResult.Failure("주소를 불러오는데 실패했습니다."))
            }
        }
    }

    fun registerParkingLot() {
        Log.d("TEST", "registerParkingLot: ${parkingLotData.value}")
        isLoading = true
        parkingLotData.value = parkingLotData.value.copy(
            imageUrl = "https://mediahub.seoul.go.kr/uploads/mediahub/2022/02/uZmjEIGLXJCxhjAVQoPvTClPSIkOCIyN.png",
        )
        viewModelScope.launch(Dispatchers.IO) {
            parkingLotsRepository
                .setParkingLot(parkingLotData.value.toParkingLotData())
            _result.emit(AddParkingLotResult.Success)
        }
        isLoading = false
    }
}

sealed class AddParkingLotResult {
    data object Success : AddParkingLotResult()
    data class Failure(val message: String) : AddParkingLotResult()
}

data class AddParkingLotUiState(
    val id: String? = null,
    val title: String? = null,
    val address: String? = null,
    val description: String? = null,
    val latlng: GeoPoint? = null,
    val pricePer10min: Int? = null,
    val imageUrl: String? = null,
) {
    fun toParkingLotData() = ParkingLotData(
        id = id ?: "",
        name = title ?: "",
        address = address ?: "",
        addressDetail = description ?: "",
        imageUri = imageUrl ?: "",
        latLng = LatLng(latlng?.latitude ?: 0.0, latlng?.longitude ?: 0.0),
        favorite = false,
        pricePer10min = pricePer10min ?: 0,
        parkingLotType = com.kick.npl.model.ParkingLotType.TYPE_A,
    )
}
