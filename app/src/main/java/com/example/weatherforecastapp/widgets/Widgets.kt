package com.example.weatherforecastapp.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.weatherforecastapp.model.Data
import com.example.weatherforecastapp.model.WeatherItem
import com.example.weatherforecastapp.utils.formatDate
import com.example.weatherforecastapp.utils.formatDecimals


@Composable
fun WeatherDetailRow(weather: Data) {
    val imageUrl = "https://www.weatherbit.io/static/img/icons/${weather.weather.icon}.png"
    Surface(
        Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        shape = CircleShape.copy(topEnd = CornerSize(6.dp)),
        color = Color.White) {
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                weather.datetime
                    .split(",")[0],
                modifier = Modifier.padding(start = 5.dp)
            )
            WeatherStateImage(imageUrl = imageUrl)
            Surface(modifier = Modifier.padding(0.dp),
                shape = CircleShape,
                color = Color(0xFFFFC400)
            ) {
                Text(weather.weather.description,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.caption)

            }
            Text(text = buildAnnotatedString {
                withStyle(style = SpanStyle(
                    color = Color.Blue.copy(alpha = 0.7f),
                    fontWeight = FontWeight.SemiBold
                )
                ){
                    append(formatDecimals(weather.maxTemp) + "º")

                }
                withStyle(
                    style = SpanStyle(
                        color = Color.LightGray)
                ){
                    append(formatDecimals(weather.minTemp) + "º")
                }
            })

        }


    }

}


@Composable
fun WeatherStateImage(imageUrl: String) {
    Image(painter = rememberAsyncImagePainter(imageUrl),
        contentDescription = "icon image" ,
        modifier = Modifier.size(80.dp))

}