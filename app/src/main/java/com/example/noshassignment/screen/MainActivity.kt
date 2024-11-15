package com.example.noshdishes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow

import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.noshassignment.R
import com.example.noshassignment.model.Dish
import com.example.noshassignment.model.NoshDishResponse.Companion.dishes
import com.example.noshassignment.screen.CategoryTabs

import com.example.noshassignment.screen.ScheduleCookingDialog
import com.example.noshassignment.screen.SideBar
import com.example.noshassignment.ui.theme.NoshAssignmentTheme
import com.example.noshassignment.utils.CommonButton
import com.example.noshassignment.viewModel.DishViewModel
import dagger.hilt.android.AndroidEntryPoint

var indexItem = -1
var formattedTime = "4:20AM"


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val dishViewModel: DishViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoshAssignmentTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    content = { innerPadding ->

                        AppContent(   modifier = Modifier.padding(innerPadding),
                            dishViewModel = dishViewModel)

                    }
                )
            }
        }
    }
}

@Composable
fun AppContent(modifier: Modifier, dishViewModel: DishViewModel) {
    var selectedItem by remember { mutableStateOf("Cook") }


    Row(modifier = Modifier.fillMaxSize()) {

        SideBar(
            selectedItem = selectedItem,
            onItemSelected = { selectedItem = it }
        )
        MainScreenContent(selectedItem = selectedItem, modifier, dishViewModel)
    }
}

@Composable
fun MainScreenContent(selectedItem: String, modifier: Modifier, dishViewModel: DishViewModel) {
    when (selectedItem) {
        "Cook" -> MainScreen(modifier, dishViewModel)
        else -> NoItemToDisplay(modifier)
    }
}




@Composable
fun MainScreen(modifier: Modifier, dishViewModel: DishViewModel) {
    var selectedDish by remember { mutableStateOf<Dish?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Row(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEAF8FB))
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            SearchBar(selectedDish)
            Spacer(modifier = Modifier.height(16.dp))
            CategoryTabs(dishes = dishes)
            Spacer(modifier = Modifier.height(16.dp))
            RecommendationsSection(
                dishViewModel = dishViewModel,
                onDishSelected = { selectedDish = it },
                showDialog = { showDialog = true }
            )
            if (showDialog) {
                ScheduleCookingDialog(
                    onDismissRequest = { showDialog = false },
                    onDeleteClick = {  },
                    onRescheduleClick = { },
                    onCookNowClick = { },
                    dishViewModel = dishViewModel,
                    onDishSelected = { selectedDish = it }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            BottomButtons()
        }
    }
}

@Composable
fun NoItemToDisplay(modifier: Modifier) {
    Column (modifier = Modifier.fillMaxSize(),

        verticalArrangement = Arrangement.Center
     ){
        Text(
            text = "No item to Display",
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier.fillMaxSize(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(selectedDish: Dish?) {
    val roundedShape = RoundedCornerShape(40.dp)
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        TextField(
            value = "",
            onValueChange = {},
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
            shape = roundedShape,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                containerColor = Color.White
            ),
            placeholder = { Text("Search for dishes or Ingredient ") },
            modifier = Modifier.weight(1f)
        )
        AllDishesList()
        Image(
            painter = painterResource(id = R.drawable.bell),
            contentDescription = "Bell Icon",
            modifier = Modifier.size(24.dp),
            colorFilter = ColorFilter.tint(Color(0XFF383a90))
        )
        Image(
            painter = painterResource(id = R.drawable.logout),
            contentDescription = "Logout Icon",
            modifier = Modifier.size(24.dp),
            colorFilter = ColorFilter.tint(Color(0xFFFFA500))
        )
    }
}

@Composable
fun AllDishesList() {
    val dishViewModel: DishViewModel = viewModel()
    val dishes by dishViewModel.savedDishes.observeAsState(emptyList())
    var expanded by remember { mutableStateOf(false) }
    var selectedDish by remember { mutableStateOf(dishes.firstOrNull()) }

    LaunchedEffect(Unit) {
        dishViewModel.fetchSavedDishes()
    }

    if (dishes.isNotEmpty()) {
        Box {

            OutlinedButton(
                onClick = { expanded = !expanded },
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color(0xFF20283e)),
                border = BorderStroke(0.dp, Color.Transparent), // Make border transparent
                modifier = Modifier
                    .clip(RoundedCornerShape(10))
                    .height(50.dp)
                    .padding(0.dp)
            ) {
                if (selectedDish != null) {

                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                        Image(
                            painter = rememberImagePainter(selectedDish?.imageUrl),
                            contentDescription = "Selected Dish Image",
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color.Gray),
                            contentScale = ContentScale.Crop
                        )
                        Column {
                            Text(
                                text = selectedDish!!.dishName,
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Row {
                                selectedDish?.schedule?.let {
                                    Text(
                                        text = it,
                                        color = Color.White,
                                        fontSize = 12.sp
                                    )
                                }
                                Spacer(modifier = Modifier.width(5.dp))
                                selectedDish?.scheduleTime?.let {
                                    Text(
                                        text = it,
                                        color = Color.White,
                                        fontSize = 12.sp
                                    )
                                }
                            }

                        }
                    }
                } else {

                    Text(
                        text = "No Dish Selected",
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                dishes.forEach { dish ->
                    DropdownMenuItem(
                        onClick = {
                            selectedDish = dish
                            expanded = false
                        },
                        text = {

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Image(
                                    painter = rememberImagePainter(dish.imageUrl),
                                    contentDescription = "Dish Image",
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                                Text(dish.dishName)
                            }
                        }
                    )
                }
            }
        }
    } else {
        Text("No dish selected yet")
    }
}





@Composable
fun RecommendationsSection(
    dishViewModel: DishViewModel,
    onDishSelected: (Dish) -> Unit,
    showDialog: () -> Unit
) {
    val recommendedDishes by dishViewModel.recommendedDishes.observeAsState(emptyList())

    Column {
        Row{
            Text(
                text = "Recommendations",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp),
                color = Color(0XFF383a90)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Show All",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp),
                color = Color(0XFF383a90)
            )
        }

        if (recommendedDishes.isEmpty()) {
            Text(
                text = "No dishes available",
                fontSize = 16.sp,
                modifier = Modifier.padding(16.dp)
            )
        } else {
            LazyRow(modifier = Modifier.fillMaxWidth()) {
                itemsIndexed(recommendedDishes) { index, dish ->
                    RecommendationCard(
                        dish = dish,
                        index = index,
                        onDishSelected = onDishSelected,
                        showDialog = showDialog
                    )
                }
            }
        }
    }
}

@Composable
fun RecommendationCard(
    dish: Dish,
    index: Int,
    onDishSelected: (Dish) -> Unit,
    showDialog: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .width(160.dp)
            .padding(8.dp)
            .clickable {
                indexItem = index
                showDialog()
            }
    ) {
        Column(
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberImagePainter(dish.imageUrl),
                contentDescription = "Dish Image",
                modifier = Modifier
                    .size(100.dp)
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = dish.dishName,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                color = Color(0XFF383a90),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 4.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "5 mins read",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0XFF383a90)
                )
            }
        }
    }
}

@Composable
fun BottomButtons() {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceAround) {
        CommonButton(text = "Explore all dishes", onClick = {  },
            modifier = Modifier
                .weight(1f)
                .padding(4.dp))
        CommonButton(
            text = "Confused what to cook?",
            onClick = {},
            modifier = Modifier
                .weight(1f)
                .padding(4.dp)
        )
    }
}







