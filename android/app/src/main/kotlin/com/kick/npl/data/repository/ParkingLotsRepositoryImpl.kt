package com.kick.npl.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.kick.npl.data.model.ParkingLotEntity
import com.kick.npl.data.model.toParkingLotData
import com.kick.npl.data.util.module
import com.kick.npl.model.ParkingLotData
import com.kick.npl.model.toParkingLotEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.Flow
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class ParkingLotsRepositoryImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
) : ParkingLotsRepository {

    override suspend fun getParkingLot(id: String): ParkingLotEntity? {
        return suspendCancellableCoroutine { cancellableContinuation ->
            fireStore.module()
                .document(id)
                .get()
                .addOnSuccessListener { document ->
                    if (!document.exists()) {
                        cancellableContinuation.resume(null)
                        return@addOnSuccessListener
                    }

                    document.toObject(ParkingLotEntity::class.java).also { entity ->
                        cancellableContinuation.resume(entity)
                    }
                }
                .addOnFailureListener { exception ->
                    cancellableContinuation.resumeWithException(exception)
                }
        }
    }

    override suspend fun getAllParkingLots(): List<ParkingLotData>? {
        return suspendCancellableCoroutine { cancellableContinuation ->
            fireStore.module()
                .get()
                .addOnSuccessListener { documents ->
                    documents
                        .asSequence()
                        .map { it.id to it.toObject(ParkingLotEntity::class.java) }
                        .map { (id, data) -> data.toParkingLotData(id) }
                        .toList()
                        .let { cancellableContinuation.resume(it) }

                }
                .addOnFailureListener { exception ->
                    cancellableContinuation.resumeWithException(exception)
                }
        }
    }

    override suspend fun setParkingLot(parkingLotData: ParkingLotData) {
        return suspendCancellableCoroutine { cancellableContinuation ->
            fireStore.module()
                .document(parkingLotData.id)
                .set(parkingLotData.toParkingLotEntity())
                .addOnSuccessListener {
                    cancellableContinuation.resume(Unit)
                }
                .addOnFailureListener { exception ->
                    cancellableContinuation.resumeWithException(exception)
                }
        }
    }

    override suspend fun toggleFavorite(
        id: String,
        favorite: Boolean
    ) {
        Log.e("TEST", "toggleFavorite $id $favorite")
        return suspendCancellableCoroutine { cancellableContinuation ->
            fireStore.module()
                .document(id)
                .update("favorite", favorite)
                .addOnSuccessListener {
                    cancellableContinuation.resume(Unit)
                }
                .addOnFailureListener { exception ->
                    cancellableContinuation.resumeWithException(exception)
                }
        }
    }

    override suspend fun deleteTestParkingLot(id: String) {
        return suspendCancellableCoroutine { cancellableContinuation ->
            fireStore.module()
                .document(id)
                .delete()
                .addOnSuccessListener {
                    cancellableContinuation.resume(Unit)
                }
                .addOnFailureListener { exception ->
                    cancellableContinuation.resumeWithException(exception)
                }
        }
    }

    override suspend fun setIsBlocked(id: String, isBlocked: Boolean) {
        return suspendCancellableCoroutine { cancellableContinuation ->
            fireStore.module()
                .document(id)
                .update("isBlocked", isBlocked)
                .addOnSuccessListener {
                    cancellableContinuation.resume(Unit)
                }
                .addOnFailureListener { exception ->
                    cancellableContinuation.resumeWithException(exception)
                }
        }
    }

    override suspend fun setParkingLotData(parkingLotData: ParkingLotData) {
        return suspendCancellableCoroutine { cancellableContinuation ->

            val parkingLotEntity = parkingLotData.toParkingLotEntity()
            fireStore.module()
                .document(parkingLotData.id)
                .update(
                    "latlng", parkingLotEntity.latlng,
                    "name", parkingLotEntity.name,
                    "address", parkingLotEntity.address,
                    "price", parkingLotEntity.pricePer10min,
                )
                .addOnSuccessListener {
                    cancellableContinuation.resume(Unit)
                }
                .addOnFailureListener { exception ->
                    cancellableContinuation.resumeWithException(exception)
                }
        }
    }
}