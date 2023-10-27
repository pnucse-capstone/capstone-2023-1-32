package com.kick.npl.ui.map.component

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerColors
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.kick.npl.ui.common.ButtonStyle
import com.kick.npl.ui.common.NPLButton
import com.kick.npl.ui.common.NPLButtonSize
import com.kick.npl.ui.theme.Theme
import com.kick.npl.ui.util.noRippleClickable
import com.kick.npl.ui.util.toLocalDateTime
import com.kick.npl.ui.util.toMillis
import com.kick.npl.ui.util.toPatternedString
import com.kick.npl.ui.util.toUserFriendlyString
import java.time.Duration
import java.time.LocalDateTime
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimePickerBottomSheet(
    modifier: Modifier = Modifier,
    enabled: Boolean = false,
    startTime: LocalDateTime,
    endTime: LocalDateTime,
    onDismissRequest: () -> Unit = {},
    onClickConfirm: (LocalDateTime, LocalDateTime) -> Unit = { _, _ -> },
) {
    val localContext = LocalContext.current
    var cachedStartTime by remember(startTime) { mutableStateOf(startTime) }
    var cachedEndTime by remember(endTime) { mutableStateOf(endTime) }
    var isBottomSheetVisible by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )
    val diff by remember(cachedStartTime, cachedEndTime) {
        mutableStateOf(Duration.between(cachedStartTime, cachedEndTime))
    }
    val isConfirmEnabled by remember(diff) {
        derivedStateOf { diff.toMinutes() >= 10 }
    }

    LaunchedEffect(enabled) {
        if (enabled) {
            isBottomSheetVisible = enabled
            sheetState.expand()
        }
        else {
            sheetState.hide()
            isBottomSheetVisible = enabled
        }
    }

    LaunchedEffect(isConfirmEnabled) {
        if (!isConfirmEnabled) { cachedEndTime = cachedStartTime }
    }

    fun onSelectEndTime(endTimeLong: Long) {
        if (cachedStartTime.toMillis() >= endTimeLong) {
            Toast.makeText(
                localContext,
                "시작 날짜보다 이전 날짜를 선택할 수 없습니다.",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            cachedEndTime = endTimeLong.toLocalDateTime()
        }
    }

    if(isBottomSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = sheetState,
            containerColor = Theme.colors.surface,
            contentColor = Theme.colors.onSurface0,
            dragHandle = {},
            modifier = modifier
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "주차 시간 시간",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .padding(start = 24.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                DatePickerBox(
                    modifier = Modifier.weight(1f),
                    title = "시작 날짜",
                    selectedDate = cachedStartTime.toMillis(),
                    onDateSelected = { cachedStartTime = it.toLocalDateTime() }
                )
                Spacer(modifier = Modifier.width(8.dp))

                TimePickerBox(
                    modifier = Modifier.weight(1f),
                    title = "시작 시간",
                    selectedTime = cachedStartTime.toMillis(),
                    onTimeSelected = { cachedStartTime = it.toLocalDateTime() }
                )
            }

            Text(
                text = "주차 종료 시간",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .padding(top = 24.dp)
                    .padding(start = 24.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                DatePickerBox(
                    modifier = Modifier.weight(1f),
                    title = "종료 날짜",
                    selectedDate = cachedEndTime.toMillis(),
                    onDateSelected = ::onSelectEndTime
                )
                Spacer(modifier = Modifier.width(8.dp))

                TimePickerBox(
                    modifier = Modifier.weight(1f),
                    title = "종료 시간",
                    selectedTime = cachedEndTime.toMillis(),
                    onTimeSelected = ::onSelectEndTime
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(top = 24.dp)
                ,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf(10, 30, 60, -30).forEach { minutes ->
                    NPLButton(
                        text = (if(minutes > 0) "+" else "") + minutes.toString() + "분",
                        size = NPLButtonSize.Small,
                        buttonStyle = ButtonStyle.Outline,
                        isEnabled = isConfirmEnabled || minutes > 0,
                        onClickEnabled = {
                            cachedEndTime = cachedEndTime.plusMinutes(minutes.toLong())
                        }
                    )
                }
            }

            NPLButton(
                isEnabled = isConfirmEnabled,
                text = if (isConfirmEnabled) diff.toUserFriendlyString() else "10분 이상 선택해주세요",
                modifier = Modifier
                    .padding(bottom = 48.dp)
                    .fillMaxWidth()
                    .padding(24.dp),
                onClickEnabled = { onClickConfirm(cachedStartTime, cachedEndTime) }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerBox(
    modifier: Modifier,
    title: String,
    textStyle: TextStyle = Theme.typo.body,
    selectedDate: Long,
    onDateSelected: (Long) -> Unit,
) {
    var showPicker by remember { mutableStateOf(false) }
    val calendar = Calendar.getInstance().apply { timeInMillis = selectedDate }

    val localDateTime by remember(calendar) {
        mutableStateOf(selectedDate.toLocalDateTime())
    }

    OutlinedBoxPicker(
        text = localDateTime.toPatternedString("yy.MM.dd"),
        modifier = modifier,
        title = title,
        textStyle = textStyle,
    ) {
        showPicker = true
    }

    if (showPicker) {
        val datePickerState =
            rememberDatePickerState(initialSelectedDateMillis = calendar.getDatePickerMillis())
        DatePickerDialog(
            onDismissRequest = { showPicker = false },
            confirmButton = {
                DialogButton("확인") {
                    datePickerState.selectedDateMillis?.let {
                        calendar.setDateBeforeCurrent(it)
                    }
                    onDateSelected(calendar.timeInMillis)
                    showPicker = false
                }
            },
            dismissButton = {
                DialogButton("취소") { showPicker = false }
            },
            colors = DatePickerDefaults.colors(containerColor = Theme.colors.surface99)
        ) {
            DatePicker(
                state = datePickerState,
                showModeToggle = false,
                colors = getDatePickerColors()
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerBox(
    modifier: Modifier,
    title: String,
    selectedTime: Long,
    onTimeSelected: (Long) -> Unit,
) {
    var showPicker by remember { mutableStateOf(false) }
    val calendar = Calendar.getInstance().apply { timeInMillis = selectedTime }

    val localDateTime by remember(calendar) {
        mutableStateOf(selectedTime.toLocalDateTime())
    }

    OutlinedBoxPicker(
        modifier = modifier,
        title = title,
        text = localDateTime.toPatternedString("a hh:mm")
    ) {
        showPicker = true
    }

    if (showPicker) {
        Dialog(onDismissRequest = { showPicker = false }) {
            Surface(shape = RoundedCornerShape(12.dp), color = Theme.colors.surface99) {
                Column(modifier = Modifier.padding(24.dp)) {
                    val timePickerState = rememberTimePickerState(
                        initialHour = calendar.get(Calendar.HOUR_OF_DAY),
                        initialMinute = calendar.get(Calendar.MINUTE)
                    )
                    TimePicker(state = timePickerState, colors = getTimePickerColors())
                    DialogBottomButtons(
                        onCancel = { showPicker = false },
                        onComplete = {
                            calendar.setTimeBeforeCurrent(
                                timePickerState.hour,
                                timePickerState.minute
                            )
                            onTimeSelected(calendar.timeInMillis)
                            showPicker = false
                        })
                }
            }
        }
    }
}

private const val START_HOUR_OF_DATE_PICKER = 9
private fun Calendar.getDatePickerMillis(): Long {
    val calendar = Calendar.getInstance().also {
        it.timeInMillis = timeInMillis
        it.set(
            get(Calendar.YEAR),
            get(Calendar.MONTH),
            get(Calendar.DATE),
            START_HOUR_OF_DATE_PICKER,
            0
        )
    }
    return calendar.timeInMillis
}

private fun validateDate(timeInMillis: Long): Boolean {
    val currentCalendar = Calendar.getInstance().apply {
        set(
            get(Calendar.YEAR),
            get(Calendar.MONTH),
            get(Calendar.DATE),
            START_HOUR_OF_DATE_PICKER,
            0
        )
    }
    return timeInMillis <= currentCalendar.timeInMillis
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun getDatePickerColors(): DatePickerColors {
    return DatePickerDefaults.colors(
        containerColor = Theme.colors.surface99,
        titleContentColor = Theme.colors.onSurface0,
        headlineContentColor = Theme.colors.onSurface0,
        weekdayContentColor = Theme.colors.onSurface0,
        subheadContentColor = Theme.colors.onSurface0,
        yearContentColor = Theme.colors.onSurface0,
        currentYearContentColor = Theme.colors.primaryContainer,
        selectedYearContainerColor = Theme.colors.primaryContainer,
        selectedYearContentColor = Theme.colors.onPrimaryContainer,
        dayContentColor = Theme.colors.onSurface0,
        selectedDayContainerColor = Theme.colors.primaryContainer,
        selectedDayContentColor = Theme.colors.onPrimaryContainer,
        todayContentColor = Theme.colors.primaryContainer,
        todayDateBorderColor = Theme.colors.primary
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun getTimePickerColors(): TimePickerColors {
    return TimePickerDefaults.colors(
        clockDialColor = Theme.colors.surface90,
        clockDialSelectedContentColor = Theme.colors.surface,
        clockDialUnselectedContentColor = Theme.colors.onSurface0,
        selectorColor = Theme.colors.primary,
        periodSelectorBorderColor = Theme.colors.line,
        periodSelectorSelectedContainerColor = Theme.colors.primaryContainer,
        periodSelectorUnselectedContainerColor = Theme.colors.surface90,
        periodSelectorSelectedContentColor = Theme.colors.onPrimaryContainer,
        periodSelectorUnselectedContentColor = Theme.colors.onSurface0,
        timeSelectorSelectedContainerColor = Theme.colors.primaryContainer,
        timeSelectorUnselectedContainerColor = Theme.colors.surface90,
        timeSelectorSelectedContentColor = Theme.colors.onPrimaryContainer,
        timeSelectorUnselectedContentColor = Theme.colors.onSurface0
    )
}

@Composable
fun DialogBottomButtons(onCancel: () -> Unit, onComplete: () -> Unit, enabled: Boolean = true) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        DialogButton("취소", onClick = onCancel)
        Spacer(modifier = Modifier.width(8.dp))
        DialogButton("확인", enabled, onClick = onComplete)
    }
}

@Composable
private fun DialogButton(text: String, enabled: Boolean = true, onClick: () -> Unit) {
    Text(
        text = text,
        fontSize = 14.sp,
        color = if (enabled) Theme.colors.primary else Theme.colors.onSurface40,
        modifier = Modifier
            .padding(12.dp)
            .noRippleClickable { if (enabled) onClick() }
    )
}

private fun Calendar.setDateBeforeCurrent(dateMillis: Long) {
    val selectedCalendar = Calendar.getInstance().apply { timeInMillis = dateMillis }
    set(
        selectedCalendar.get(Calendar.YEAR),
        selectedCalendar.get(Calendar.MONTH),
        selectedCalendar.get(Calendar.DATE)
    )
    setCurrentIfNeed()
}

private fun Calendar.setTimeBeforeCurrent(hour: Int, minute: Int) {
    set(get(Calendar.YEAR), get(Calendar.MONTH), get(Calendar.DATE), hour, minute)
    setCurrentIfNeed()
}

private fun Calendar.setCurrentIfNeed() {
    System.currentTimeMillis().let { if (timeInMillis < it) timeInMillis = it }
}