package com.example.weatherforecastapp.screens.about

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.widgets.WeatherAppBar

@Composable
fun AboutScreen(navController: NavController) {
    Scaffold(topBar = {
        WeatherAppBar(
            title = "About",
            icon = Icons.Default.ArrowBack,
            false,
            navController = navController){
            navController.popBackStack()
        }
    }) {
        Surface(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Text(text = stringResource(id = R.string.about_app),
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold)

                Text(text = stringResource(id = R.string.api_used),
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Light)

            }

        }

    }
}