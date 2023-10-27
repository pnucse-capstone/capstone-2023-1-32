package com.kick.npl.ui.setting

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.user.UserApiClient
import com.kick.npl.data.model.toParkingLotData
import com.kick.npl.data.repository.ParkingLotsRepository
import com.kick.npl.model.ParkingLotData
import com.kick.npl.ui.map.generateSampleParkingLots
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val parkingLotsRepository: ParkingLotsRepository,
) : ViewModel() {

    var parkingLotData by mutableStateOf<ParkingLotData?>(null)
        private set

    private val _toastMessage = Channel<String>()
    val toastMessage = _toastMessage.receiveAsFlow()

    var isLoading by mutableStateOf(false)
        private set


    fun deleteAllTestParkingLots() {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading = true
            parkingLotsRepository.getAllParkingLots()?.forEach {
                if (it.id.startsWith("Test:")) {
                    parkingLotsRepository.deleteTestParkingLot(it.id)
                }
            }
            _toastMessage.send("Deleted all test parking lots")
            isLoading = false
        }
    }

    fun getTestParkingLot(id: String) = viewModelScope.launch(Dispatchers.IO) {
        isLoading = true
        val parkingLotEntity = parkingLotsRepository.getParkingLot(id) ?: run {
            isLoading = false
            return@launch
        }
        parkingLotData = parkingLotEntity.toParkingLotData(id)
        _toastMessage.send("data loaded")
        isLoading = false
    }

    fun updateField(isBlocked: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        parkingLotData ?: return@launch
        isLoading = true
        parkingLotsRepository.setIsBlocked(parkingLotData!!.id, isBlocked)
        _toastMessage.send("Updated")
        isLoading = false
    }

    fun mockData() = viewModelScope.launch(Dispatchers.IO) {
        isLoading = true
        generateSampleParkingLots()
            .forEachIndexed { index, data ->
                parkingLotsRepository.setParkingLot(
                    data.copy(
                        id = "Test:${1000 + index}",
                        imageUri = "https://i.imgur.com/nVutgKq.png",
                        isBlocked = false
                    )
                )
            }
        _toastMessage.send("Mock data generated")
        isLoading = false
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            UserApiClient.instance.logout { error ->
                error?.printStackTrace()
            }
        }
    }
}
