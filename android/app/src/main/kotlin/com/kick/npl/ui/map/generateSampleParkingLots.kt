package com.kick.npl.ui.map

import com.kick.npl.model.ParkingLotData
import com.kick.npl.model.ParkingLotType
import com.naver.maps.geometry.LatLng

fun generateSampleParkingLots(): List<ParkingLotData> = buildList {
    ParkingLotData(
        id = "1",
        name = "분당구청 주차장",
        address = "경기도 성남시 분당구 정자동 206",
        addressDetail = "lacus",
        imageUri = "quas",
        latLng = LatLng(37.358315, 127.114454),
        favorite = false,
        pricePer10min = 500,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "2",
        name = "수지구청 주차장",
        address = "경기도 성남시 수정구 태평동 3377",
        addressDetail = "lacus",
        imageUri = "quas",
        latLng = LatLng(37.443947, 127.137157),
        favorite = true,
        pricePer10min = 1000,
        parkingLotType = ParkingLotType.TYPE_B
    ).let { add(it) }

    ParkingLotData(
        id = "3",
        name = "중원구청 주차장",
        address = "경기도 성남시 중원구 상대원동 3971",
        addressDetail = "lacus",
        imageUri = "quas",
        latLng = LatLng(37.437705, 127.154780),
        favorite = true,
        pricePer10min = 2000,
        parkingLotType = ParkingLotType.TYPE_C
    ).let { add(it) }

    ParkingLotData(
        id = "4",
        name = "성남시청 주차장",
        address = "경기도 성남시 중원구 여수동 200",
        addressDetail = "lacus",
        imageUri = "quas",
        latLng = LatLng(37.419662, 127.126939),
        favorite = false,
        pricePer10min = 3000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "5",
        name = "광명시청 주차장",
        address = "경기도 성남시 중원구 여수동 200",
        addressDetail = "lacus",
        imageUri = "quas",
        latLng = LatLng(37.477496, 126.866642),
        favorite = false,
        pricePer10min = 3000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "6",
        name = "광명구청 주차장",
        address = "경기도 성남시 중원구 여수동 200",
        addressDetail = "lacus",
        imageUri = "quas",
        latLng = LatLng(37.480601, 126.852971),
        favorite = false,
        pricePer10min = 3000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "7",
        name = "광명군청 주차장",
        address = "경기도 성남시 중원구 여수동 200",
        addressDetail = "lacus",
        imageUri = "quas",
        latLng = LatLng(37.465027, 126.854992),
        favorite = false,
        pricePer10min = 3000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "8",
        name = "종로구청 주차장",
        address = "서울특별시 종로구 종로",
        addressDetail = "lacus",
        imageUri = "quas",
        latLng = LatLng(37.5730, 126.9784),
        favorite = false,
        pricePer10min = 3000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "9",
        name = "중구청 주차장",
        address = "경기도 성남시 중원구 여수동 200",
        addressDetail = "lacus",
        imageUri = "quas",
        latLng = LatLng(37.5635, 126.9975),
        favorite = false,
        pricePer10min = 3000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "11",
        name = "용산구청 주차장",
        address = "서울시 용산구 후암동 111-1",
        addressDetail = "lacus",
        imageUri = "quas",
        latLng = LatLng(37.5311, 126.9810),
        favorite = false,
        pricePer10min = 3000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "12",
        name = "성동구청 주차장",
        address = "서울시 성동구 성수동1가 656-1",
        addressDetail = "lacus",
        imageUri = "quas",
        latLng = LatLng(37.5635, 127.0366),
        favorite = false,
        pricePer10min = 3000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "13",
        name = "광진구청 주차장",
        address = "서울시 광진구 능동로 1",
        addressDetail = "lacus",
        imageUri = "quas",
        latLng = LatLng(37.5382, 127.0835),
        favorite = false,
        pricePer10min = 3000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "14",
        name = "동대문 주차장",
        address = "서울시 동대문구 천호대로 145",
        addressDetail = "lacus",
        imageUri = "quas",
        latLng = LatLng(37.5744, 127.0395),
        favorite = false,
        pricePer10min = 3000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "15",
        name = "중량구 주차장",
        address = "서울시 중랑구 신내로 24길 10",
        addressDetail = "lacus",
        imageUri = "quas",
        latLng = LatLng(37.6053, 127.0930),
        favorite = false,
        pricePer10min = 3000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "16",
        name = "성북구청 주차장",
        address = "서울시 성북구 보문로 168",
        addressDetail = "lacus",
        imageUri = "quas",
        latLng = LatLng(37.5893, 127.0165),
        favorite = false,
        pricePer10min = 3000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "17",
        name = "강북구청 주차장",
        address = "서울시 강북구 도봉로 384",
        addressDetail = "lacus",
        imageUri = "quas",
        latLng = LatLng(37.6396, 127.0256),
        favorite = false,
        pricePer10min = 3000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "18",
        name = "도봉구청 주차장",
        address = "서울시 도봉구 도봉로 552",
        addressDetail = "lacus",
        imageUri = "quas",
        latLng = LatLng(37.6659, 127.0318),
        favorite = false,
        pricePer10min = 3000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "19",
        name = "노원구청 주차장",
        address = "서울시 노원구 노원로 283",
        addressDetail = "lacus",
        imageUri = "quas",
        latLng = LatLng(37.6659, 127.0318),
        favorite = false,
        pricePer10min = 3000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "21",
        name = "은평구청 주차장",
        address = "서울시 은평구 녹번로 16길 16",
        addressDetail = "lacus",
        imageUri = "quas",
        latLng = LatLng(37.6542, 127.0568),
        favorite = false,
        pricePer10min = 3000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "22",
        name = "서대문구청 주차장",
        address = "서울시 서대문구 연희로 248",
        addressDetail = "lacus",
        imageUri = "quas",
        latLng = LatLng(37.6176, 126.9227),
        favorite = false,
        pricePer10min = 3000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "23",
        name = "마포구청 주차장",
        address = "서울시 마포구 월드컵로 212",
        addressDetail = "lacus",
        imageUri = "quas",
        latLng = LatLng(37.5791, 126.9368),
        favorite = false,
        pricePer10min = 3000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "24",
        name = "양천구청 주차장",
        address = "서울시 양천구 목동 916-1",
        addressDetail = "lacus",
        imageUri = "quas",
        latLng = LatLng(37.5623, 126.9086),
        favorite = false,
        pricePer10min = 3000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "25",
        name = "강서구청 주차장",
        address = "서울시 강서구 화곡동 980-1",
        addressDetail = "lacus",
        imageUri = "quas",
        latLng = LatLng(37.5275, 126.8563),
        favorite = false,
        pricePer10min = 3000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "26",
        name = "구로구청 주차장",
        address = "서울시 구로구 구로동 436-1",
        addressDetail = "lacus",
        imageUri = "quas",
        latLng = LatLng(37.5509, 126.8495),
        favorite = false,
        pricePer10min = 3000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "27",
        name = "금천구청 주차장",
        address = "서울시 금천구 시흥동 1-235",
        addressDetail = "lacus",
        imageUri = "quas",
        latLng = LatLng(37.4952, 126.8870),
        favorite = false,
        pricePer10min = 3000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "28",
        name = "영등포구청 주차장",
        address = "서울시 영등포구 당산동3가 2-1",
        addressDetail = "lacus",
        imageUri = "quas",
        latLng = LatLng(37.4603, 126.9004),
        favorite = false,
        pricePer10min = 3000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "29",
        name = "동작구청 주차장",
        address = "서울시 동작구 노량진동 72-1",
        addressDetail = "lacus",
        imageUri = "quas",
        latLng = LatLng(37.5265, 126.8954),
        favorite = false,
        pricePer10min = 3000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "30",
        name = "관악구청 주차장",
        address = "서울시 관악구 봉천동 1695-5",
        addressDetail = "lacus",
        imageUri = "quas",
        latLng = LatLng(37.5125, 126.9394),
        favorite = false,
        pricePer10min = 3000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "31",
        name = "서초구청 주차장",
        address = "서울특별시 서초구 서초동 1376-1",
        addressDetail = "lacus",
        imageUri = "quas",
        latLng = LatLng(37.4653, 126.9438),
        favorite = false,
        pricePer10min = 3000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "32",
        name = "부산대학교 제도관 주차장",
        address = "서울특별시 서초구 서초동 1376-1",
        addressDetail = "lacus",
        imageUri = "quas",
        latLng = LatLng(35.230876642140956, 129.0822235741259),
        favorite = false,
        pricePer10min = 1500,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

    ParkingLotData(
        id = "33",
        name = "부산대학교 도서관 주차장",
        address = "서울특별시 서초구 서초동 1376-1",
        addressDetail = "lacus",
        imageUri = "quas",
        latLng = LatLng(35.23574428670318, 129.08135401107754),
        favorite = false,
        pricePer10min = 2000,
        parkingLotType = ParkingLotType.TYPE_A
    ).let { add(it) }

}
