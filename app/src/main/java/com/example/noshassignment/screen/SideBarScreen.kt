package com.example.noshassignment.screen



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.foundation.clickable

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle

import androidx.compose.material.icons.filled.FavoriteBorder


import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.res.painterResource
import com.example.noshassignment.R



@Composable
fun SideBar(
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(Color(0xFFE5E5E5))
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SidebarIcon(
            icon = R.drawable.cooking,
            description = "Cook",
            label = "Cook",
            isSelected = selectedItem == "Cook",
            onClick = { onItemSelected("Cook") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        SidebarIcon(
            icon = Icons.Default.FavoriteBorder,
            description = "Favourites",
            label = "Favourites",
            isSelected = selectedItem == "Favourites",
            onClick = { onItemSelected("Favourites") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        SidebarIcon(
            icon = R.drawable.manual,
            description = "Manual",
            label = "Manual",
            isSelected = selectedItem == "Manual",
            onClick = { onItemSelected("Manual") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        SidebarIcon(
            icon = R.drawable.device,
            description = "Device",
            label = "Device",
            isSelected = selectedItem == "Device",
            onClick = { onItemSelected("Device") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        SidebarIcon(
            icon = Icons.Default.AccountCircle,
            description = "Preferences",
            label = "Preferences",
            isSelected = selectedItem == "Preferences",
            onClick = { onItemSelected("Preferences") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        SidebarIcon(
            icon = Icons.Default.Settings,
            description = "Settings",
            label = "Settings",
            isSelected = selectedItem == "Settings",
            onClick = { onItemSelected("Settings") }
        )
    }
}






@Composable
fun SidebarIcon(
    icon: Any,
    description: String,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val iconColor = if (isSelected) Color(0xFFFFA500) else Color(0XFF383a90) // Orange color when selected

    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (icon is Int) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = description,
                tint = iconColor,
                modifier = Modifier.size(24.dp)
            )
        } else if (icon is ImageVector) {
            Icon(
                imageVector = icon,
                contentDescription = description,
                tint = iconColor,
                modifier = Modifier.size(24.dp)
            )
        }
        Text(
            text = label,
            color = iconColor,
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

