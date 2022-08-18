package com.example.weatherforecastapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import coil.compose.rememberAsyncImagePainter
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.data.DataOrException
import com.example.weatherforecastapp.model.Data
import com.example.weatherforecastapp.model.Weather
import com.example.weatherforecastapp.screens.main.MainViewModel
import com.example.weatherforecastapp.utils.formatDateTime
import com.example.weatherforecastapp.utils.formatDecimals
import com.example.weatherforecastapp.widgets.WeatherAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.weatherforecastapp.navigation.WeatherScreens
import com.example.weatherforecastapp.screens.setting.SettingsViewModel
import com.example.weatherforecastapp.utils.AppColor
import com.example.weatherforecastapp.widgets.WeatherDetailRow
import com.example.weatherforecastapp.widgets.WeatherStateImage

@Composable
fun MainScreen(
    navController: NavController,
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = hiltViewModel(),
    city: String?
) {
    val curCity: String = if (city!!.isBlank()) "Seattle" else city
    val unitFromDb = settingsViewModel.unitList.collectAsState().value
    var unit by remember {
        mutableStateOf("M")
    }
    var isCelcius by remember {
        mutableStateOf(false)
    }
    if (!unitFromDb.isNullOrEmpty()) {
        unit = unitFromDb[0].unit
        isCelcius = unit == "M"
        val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
            initialValue = DataOrException(loading = true)
        ) {
            value = mainViewModel.getWeatherData(city = curCity, units = "M")
        }.value

        if (weatherData.loading == true) {
            CircularProgressIndicator()
        } else if (weatherData.data != null) {
            MainScaffold(
                weather = weatherData.data,
                navController = navController,
                isCelcius = isCelcius
            )
//            MainScreenPreview(weather = weatherData.data)

        }
    }

}

@Composable
fun MainScaffold(weather: Weather, navController: NavController, isCelcius: Boolean) {

    Scaffold(topBar = {
        WeatherAppBar(
            navController = navController,
            title = weather.cityName + ", ${weather.countryCode}",
            elevation = 5.dp,
            icon = Icons.Default.ArrowBack,
            onAddActionClick = {
                navController.navigate(WeatherScreens.SearchScreen.name)
            }
        )
    }) {
        MainContent(data = weather, isCelcius = isCelcius)

    }
}

@Composable
fun MainContent(data: Weather, isCelcius: Boolean) {
    val imageUrl = "https://www.weatherbit.io/static/img/icons/${data.data[0].weather.icon}.png"
    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            text = data.data[0].datetime.toString(),
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSecondary,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(6.dp)
        )
        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(200.dp), shape = CircleShape,
            color = Color(0xffffc400)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherImage(imageUrl = imageUrl)
                Text(
                    text = formatDecimals(data.data[0].temp) + "°",
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(text = data.data[0].weather.description, fontStyle = FontStyle.Italic)
            }
        }

        HumidityWindPressureRow(weather = data.data[0], isCelcius = isCelcius)
        Divider()
        SunsetSunRiseRow(weather = data)
        Text(
            "This Week",
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Bold
        )

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            color = Color(0xFFEEF1EF),
            shape = RoundedCornerShape(size = 14.dp)
        ) {
            LazyColumn(
                modifier = Modifier.padding(2.dp),
                contentPadding = PaddingValues(1.dp)
            ) {
                items(items = data.data) { item: Data ->
                    WeatherDetailRow(weather = item)

                }

            }

        }
    }

}

@Composable
fun WeatherImage(imageUrl: String, modifier: Modifier = Modifier) {
    Image(
        painter = rememberAsyncImagePainter(imageUrl),
        contentDescription = "icon image",
        modifier = modifier
    )
}

@Composable
fun SunsetSunRiseRow(weather: Weather) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp, bottom = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.sunrise),
                contentDescription = "sunrise",
                modifier = Modifier.size(30.dp)
            )
            Text(
                text = formatDateTime(weather.data[0].sunriseTs),
                style = MaterialTheme.typography.caption
            )

        }

        Row {

            Text(
                text = formatDateTime(weather.data[0].sunsetTs),
                style = MaterialTheme.typography.caption
            )
            Image(
                painter = painterResource(id = R.drawable.sunset),
                contentDescription = "sunset",
                modifier = Modifier.size(30.dp)
            )


        }

    }

}

@Composable
fun HumidityWindPressureRow(weather: Data, isCelcius: Boolean) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = "humidity",
                modifier = Modifier.size(20.dp)
            )
            Text(text = "${weather.clouds}%", style = MaterialTheme.typography.caption)
        }

        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.pressure),
                contentDescription = "pressure",
                modifier = Modifier.size(20.dp)
            )
            Text(text = "${weather.pres} psi", style = MaterialTheme.typography.caption)
        }

        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.wind),
                contentDescription = "wind",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${weather.windSpd} " + if (!isCelcius) "mph" else "m/s",
                style = MaterialTheme.typography.caption
            )
        }
    }
}

@Preview
@Composable
fun Test() {
    Column(
        modifier = Modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = AppColor.sun
                )
            )
            .fillMaxSize()
            .padding(5.dp, top = 40.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

    }
}

@Composable
fun MainScreenPreview(weather: Weather) {
    Column(
        modifier = Modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = AppColor.sun
                )
            )

            .fillMaxSize()
            .padding(5.dp, top = 40.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CountryAppBar()
        WeatherMainContent(weather = weather.data[0])
        WeatherDaily(weather = weather)
    }

}

@Composable
fun WeatherDaily(weather: Weather) {
    LazyRow {
        items(weather.data) { item ->
            DetailWeather(item)
        }
    }

}

@Composable
fun DetailWeather(data: Data) {
    val imageUrl = "https://www.weatherbit.io/static/img/icons/${data.weather.icon}.png"
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(top = 50.dp)
            .clip(
                RoundedCornerShape(10.dp)
            )
            .height(220.dp)
            .width(150.dp)
    ) {
        Text(data.datetime, fontSize = 15.sp)
        WeatherImage(imageUrl = imageUrl, modifier = Modifier.size(80.dp))
        Text(data.weather.description)
        Row(
            modifier = Modifier.padding(30.dp, top = 10.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Text("${formatDecimals(data.maxTemp)}º", fontSize = 30.sp)
            Spacer(modifier = Modifier.width(20.dp))
            Text("${formatDecimals(data.minTemp)}º", fontSize = 30.sp, color = Color.Black.copy(0.5f))
        }

    }
}

@Composable
fun WeatherMainContent(weather: Data) {
    val imageUrl = "https://www.weatherbit.io/static/img/icons/${weather.weather.icon}.png"
    Column(
        modifier = Modifier.padding(top = 25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WeatherImage(imageUrl = imageUrl, modifier = Modifier.size(120.dp))
        Text(
            modifier = Modifier.padding(top = 10.dp),
            text = weather.weather.description,
            style = MaterialTheme.typography.h5
        )
        Text(
            modifier = Modifier.padding(top = 10.dp),
            text = formatDecimals(weather.temp) + "°",
            style = MaterialTheme.typography.h1
        )
        Row(modifier = Modifier.padding(top = 5.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.wind),
                modifier = Modifier.size(20.dp),
                contentDescription = "wind icon"
            )
            Text(text = weather.windSpd.toString() + " km/h", modifier = Modifier.padding(start = 5.dp))
            Spacer(Modifier.width(25.dp))
            Icon(
                painter = painterResource(id = R.drawable.humidity),
                modifier = Modifier.size(20.dp),
                contentDescription = "wind icon"
            )
            Text(weather.rh.toString()+"%", modifier = Modifier.padding(start = 5.dp))
        }
    }
}

@Composable
fun CountryAppBar() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(imageVector = Icons.Default.Favorite, contentDescription = "fav icon")
        Text(text = "San Fransico", modifier = Modifier.padding(start = 15.dp))
        Spacer(Modifier.width(130.dp))
        IconButton(onClick = {
        }) {
            Icon(imageVector = Icons.Default.Search, contentDescription = "search")
        }
        IconButton(onClick = {
        }) {
            Icon(imageVector = Icons.Rounded.MoreVert, contentDescription = "More icon")
        }
    }
}




