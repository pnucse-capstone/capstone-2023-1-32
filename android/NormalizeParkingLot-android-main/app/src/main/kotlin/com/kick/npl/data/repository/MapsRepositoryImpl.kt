package com.kick.npl.data.repository

import com.kick.npl.data.model.LngLat
import com.kick.npl.data.remote.api.MapsApi
import com.kick.npl.data.remote.dto.RouteUnitEnt
import com.naver.maps.geometry.LatLng
import java.io.IOException
import javax.inject.Inject

class MapsRepositoryImpl @Inject constructor(
    private val api: MapsApi,
) : MapsRepository {
    override suspend fun getMinifiedAddress(
        latlng: LatLng
    ): Result<String> {
        return runCatching {
            api.getReverseGeocoding(
                coords = LngLat(latlng.longitude, latlng.latitude).toCoordString(),
                output = "json"
            ).body()!!
        }
            .map { res ->
                if (res.status.code == 0)
                    run {
                        val region = res.results.first().region
                        val area1 = region.area1.name
                            .replace("광역", "")
                            .replace("특별", "")
                            .replace("시", "")
                        val area2 = region.area2.name
                        val area3 = region.area3.name
                        val area4 = region.area4.name
                        "$area1 $area2 $area3 $area4"
                    }
                else ""
            }
    }

    override suspend fun getDrivingRoute(
        start: LatLng,
        goal: LatLng,
        waypoints: List<LatLng>
    ): Result<RouteUnitEnt> {
        return runCatching {
            api.getDrivingRoute(
                LngLat(start.longitude, start.latitude).toCoordString(),
                LngLat(goal.longitude, goal.latitude).toCoordString(),
                waypoints.joinToString(separator = "|") {
                    LngLat(it.longitude, it.latitude).toCoordString()
                }
            ).body()!!
        }
            .map { res ->
                if (res.code == 0) res.route.traoptimal.first()
                else throw IOException(res.message)
            }
    }
}