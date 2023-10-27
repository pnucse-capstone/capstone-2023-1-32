package com.kick.npl.data.model

import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.PropertyName
import com.kick.npl.model.ParkingLotData
import com.kick.npl.model.ParkingLotType
import com.naver.maps.geometry.LatLng

data class ParkingLotEntity(
    @get:PropertyName("name")
    @set:PropertyName("name")
    var name: String = "",
    @get:PropertyName("isBlocked")
    @set:PropertyName("isBlocked")
    var isBlocked: Boolean = false,
    @get:PropertyName("isOccupied")
    @set:PropertyName("isOccupied")
    var isOccupied: Boolean = true,
    @get:PropertyName("pricePer10min")
    @set:PropertyName("pricePer10min")
    var pricePer10min: Int = 0,
    @get:PropertyName("imageUrl")
    @set:PropertyName("imageUrl")
    var imageUrl: String = "",
    @get:PropertyName("latlng")
    @set:PropertyName("latlng")
    var latlng: GeoPoint = GeoPoint(0.0, 0.0),
    @get:PropertyName("favorite")
    @set:PropertyName("favorite")
    var favorite: Boolean = false,
    @get:PropertyName("address")
    @set:PropertyName("address")
    var address: String = "",
    @get:PropertyName("addressDetail")
    @set:PropertyName("addressDetail")
    var addressDetail: String = "",
)

fun ParkingLotEntity.toParkingLotData(id: String): ParkingLotData {
    return ParkingLotData(
        id = id,
        name = name,
        address = address,
        addressDetail = addressDetail,
        imageUri = imageUrl,
        latLng = LatLng(latlng.latitude, latlng.longitude),
        favorite = favorite,
        pricePer10min = pricePer10min,
        parkingLotType = ParkingLotType.TYPE_A,
        isBlocked = isBlocked
    )
}
