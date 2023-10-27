package com.kick.npl.ui.manage

import android.Manifest
import android.graphics.PointF
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toComposeRect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.mlkit.vision.barcode.common.Barcode
import com.kick.npl.ui.manage.component.BarcodeScanningAnalyzer
import com.kick.npl.ui.manage.component.CameraView
import com.kick.npl.ui.manage.component.adjustPoint
import com.kick.npl.ui.manage.component.adjustSize
import com.kick.npl.ui.manage.component.drawBounds
import kotlinx.coroutines.delay

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun BarcodeScanningScreen(
    navController: NavController,
) {
    val cameraPermissionState = rememberPermissionState(
        Manifest.permission.CAMERA
    )

    if (cameraPermissionState.status.isGranted) {
        ScanSurface(
            onClickUp = navController::popBackStack,
            onBarcodeScanned = {
                navController.navigate(ADD_PARKING_LOT_ROUTE)
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    "barcode",
                    it.displayValue
                )
            }
        )
    } else {
        LaunchedEffect(true) {
            cameraPermissionState.launchPermissionRequest()
        }
    }
}

@Composable
fun ScanSurface(
    onClickUp: () -> Unit = {},
    onBarcodeScanned: (Barcode) -> Unit = {},
) {


    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val detectedBarcode = remember { mutableStateListOf<Barcode>() }

    val screenWidth = remember { mutableStateOf(context.resources.displayMetrics.widthPixels) }
    val screenHeight = remember { mutableStateOf(context.resources.displayMetrics.heightPixels) }

    val imageWidth = remember { mutableStateOf(0) }
    val imageHeight = remember { mutableStateOf(0) }

    var barcodeScanned by remember { mutableStateOf<Barcode?>(null) }

    LaunchedEffect(barcodeScanned) {
        if (barcodeScanned != null) {
            Log.d("TEST, ", "once barcodeScanned: $barcodeScanned")
            delay(1000)
            onBarcodeScanned(barcodeScanned!!)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (barcodeScanned != null) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.Center),
                color = Color.White,
                strokeWidth = 5.dp
            )
        }

        CameraView(
            context = context,
            lifecycleOwner = lifecycleOwner,
            analyzer = BarcodeScanningAnalyzer { barcodes, width, height ->
                imageWidth.value = width
                imageHeight.value = height
                if(barcodeScanned != null) return@BarcodeScanningAnalyzer
                barcodeScanned = null
                detectedBarcode.clear()
                detectedBarcode.addAll(barcodes)
                if (barcodes.isNotEmpty()) {
                    barcodeScanned = barcodes.first()
                }
            }
        )
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxHeight()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                IconButton(onClick = onClickUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "back",
                        tint = Color.White
                    )
                }
                Text(
                    text = "바코드 스캔",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
            }
        }
        DrawBarcode(
            barcodes = detectedBarcode,
            imageWidth = imageWidth.value,
            imageHeight = imageHeight.value,
            screenWidth = screenWidth.value,
            screenHeight = screenHeight.value
        )
    }
}

@Composable
fun DrawBarcode(
    barcodes: List<Barcode>,
    imageWidth: Int,
    imageHeight: Int,
    screenWidth: Int,
    screenHeight: Int,
) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        barcodes.forEach { barcode ->
            barcode.boundingBox?.toComposeRect()?.let {
                val topLeft = adjustPoint(
                    PointF(it.topLeft.x, it.topLeft.y),
                    imageWidth,
                    imageHeight,
                    screenWidth,
                    screenHeight
                )
                val size = adjustSize(it.size, imageWidth, imageHeight, screenWidth, screenHeight)
                drawBounds(topLeft, size, Color.Yellow, 10f)
            }
        }
    }
}