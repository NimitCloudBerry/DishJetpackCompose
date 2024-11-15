package com.example.noshassignment.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.noshassignment.model.Dish
import com.example.noshassignment.viewModel.DishViewModel
import com.example.noshdishes.formattedTime
import com.example.noshdishes.indexItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleCookingDialog(
    onDismissRequest: () -> Unit,
    onDeleteClick: () -> Unit,
    onRescheduleClick: () -> Unit,
    onCookNowClick: () -> Unit,
    dishViewModel: DishViewModel,
    onDishSelected: (Dish) -> Unit, ) {
    var showTimePicker by remember { mutableStateOf(false) }
    var scheduledHour by remember { mutableStateOf(6) }
    var scheduledMinute by remember { mutableStateOf(30) }
    var scheduledAmPm by remember { mutableStateOf("AM") }
    var isDialogDismissed by remember { mutableStateOf(false) }
    val dishes by dishViewModel.recommendedDishes.observeAsState(emptyList())



    LaunchedEffect(isDialogDismissed) {
        if (isDialogDismissed) {
            onDismissRequest()
        }
    }

    AlertDialog(
        onDismissRequest = {
            if (!isDialogDismissed) {
                onDismissRequest()
            }
        },
        properties = DialogProperties(),
        content = {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .background(color = Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Schedule cooking time",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )


                Spacer(modifier = Modifier.height(12.dp))


                TimePicker(
                    initialHour = scheduledHour,
                    initialMinute = scheduledMinute,
                    initialAmPm = scheduledAmPm,
                    onDelete = {
                        showTimePicker = false
                        onDeleteClick()
                    },
                    onReschedule = {
                        showTimePicker = false
                        onRescheduleClick()
                    },
                    onCookNow = {

                        onCookNowClick()
                    }
                )



                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = {
                        isDialogDismissed = true
                        dishViewModel.deleteDish(dishes[indexItem])
                    }){
                        Text(text = "Delete", color = Color.Red)
                    }
                    Button(onClick = {
                        isDialogDismissed = true
                        dishes[indexItem].scheduleTime = formattedTime
                        dishes[indexItem].schedule = "ReSchedule"
                        dishViewModel.updateDish(dishes[indexItem])
                    }) {
                        Text(text = "Re-schedule", color = Color(0xFFFFA500))
                    }
                    Button(onClick = {
                        onCookNowClick()
                        isDialogDismissed = true
                        dishes[indexItem].scheduleTime = formattedTime
                        dishes[indexItem].schedule = "Schedule"

                        dishViewModel.saveDish(dishes[indexItem])



                    }) {

                        Text(text = "Cook Now")
                    }
                }
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePicker(
    initialHour: Int = 6,
    initialMinute: Int = 30,
    initialAmPm: String = "AM",
    onDelete: () -> Unit,
    onReschedule: () -> Unit,
    onCookNow: () -> Unit
) {
    var selectedHour by remember { mutableStateOf(initialHour) }
    var selectedMinute by remember { mutableStateOf(initialMinute) }
    var isAm by remember { mutableStateOf(initialAmPm == "AM") }

    val hours = (1..12).toList()
    val minutes = (0..59).toList()
    val amPmOptions = listOf("AM", "PM")


    formattedTime = remember(selectedHour, selectedMinute, isAm) {
        String.format("%d:%02d %s", selectedHour, selectedMinute, if (isAm) "AM" else "PM")
    }

    Box(
        Modifier
            .background(color = Color(0XFFdaeefe))
            .clickable(enabled = false) {},
        contentAlignment = Alignment.Center
    ) {
        Card(
            Modifier
                .width(300.dp)
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0XFFdaeefe)),
            shape = MaterialTheme.shapes.large
        ) {
            Column(
                Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    // Hour Picker
                    LazyColumn(
                        modifier = Modifier
                            .height(100.dp)
                            .width(60.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(hours) { hour ->
                            Text(
                                text = hour.toString().padStart(2, '0'),
                                fontSize = if (hour == selectedHour) 36.sp else 24.sp,
                                fontWeight = if (hour == selectedHour) FontWeight.Bold else FontWeight.Normal,
                                color = if (hour == selectedHour) Color.Black else Color.Gray,
                                modifier = Modifier
                                    .clickable { selectedHour = hour }
                                    .padding(vertical = 4.dp)
                            )
                        }
                    }

                    Text(
                        text = ":",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )


                    LazyColumn(
                        modifier = Modifier
                            .height(100.dp)
                            .width(60.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(minutes) { minute ->
                            Text(
                                text = minute.toString().padStart(2, '0'),
                                fontSize = if (minute == selectedMinute) 36.sp else 24.sp,
                                fontWeight = if (minute == selectedMinute) FontWeight.Bold else FontWeight.Normal,
                                color = if (minute == selectedMinute) Color.Black else Color.Gray,
                                modifier = Modifier
                                    .clickable { selectedMinute = minute }
                                    .padding(vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))


                    LazyColumn(
                        modifier = Modifier
                            .height(100.dp)
                            .width(60.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(amPmOptions) { amPm ->
                            Text(
                                text = amPm,
                                fontSize = if ((amPm == "AM") == isAm) 36.sp else 24.sp,
                                fontWeight = if ((amPm == "AM") == isAm) FontWeight.Bold else FontWeight.Normal,
                                color = if ((amPm == "AM") == isAm) Color.Black else Color.Gray,
                                modifier = Modifier
                                    .clickable { isAm = amPm == "AM" }
                                    .padding(vertical = 4.dp)
                            )
                        }
                    }
                }


            }
        }
    }
}