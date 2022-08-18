package com.example.weatherforecastapp.navigation

//import SettingsScreen
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weatherforecastapp.screens.WeatherSplashScreen
import com.example.weatherforecastapp.screens.*
//import com.example.weatherforecastapp.screens.about.AboutScreen
//import com.example.weatherforecastapp.screens.favorite.FavoriteScreen
import com.example.weatherforecastapp.screens.main.MainViewModel
//import com.example.weatherforecastapp.screens.search.SearchScreen


@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = WeatherScreens.SplashScreen.name) {
        composable(WeatherScreens.SplashScreen.name) {
            WeatherSplashScreen(navController = navController)
        }

        composable(WeatherScreens.MainScreen.name + "/{city}", arguments = listOf(
            navArgument(name = "city") {
                type = NavType.StringType
            }
        )) { navBack ->
            navBack.arguments?.getString("city").let {

                val mainViewModel = hiltViewModel<MainViewModel>()
                MainScreen(navController = navController, mainViewModel =  mainViewModel, city = it)
            }
        }

//        composable(WeatherScreens.SearchScreen.name) {
//            SearchScreen(navController = navController)
//        }

//        composable(WeatherScreens.AboutScreen.name){
//            AboutScreen(navController = navController)
//        }
//        composable(WeatherScreens.SettingsScreen.name){
//            SettingsScreen(navController = navController)
//        }

//        composable(WeatherScreens.FavoriteScreen.name){
//            FavoriteScreen(navController = navController)
//        }
    }
}