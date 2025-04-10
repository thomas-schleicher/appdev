package com.example.navigation_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.navigation_1.ui.theme.Navigation_1Theme
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.material.icons.filled.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "home") {
                composable("home") { HomeScreen(navController) }
                composable("details") { DetailScreen(navController) }
                composable("details/{itemIndex}") { backStackEntry ->
                    val itemIndex = backStackEntry.arguments?.getString("itemIndex")?.toIntOrNull()
                    DetailScreen(navController, itemIndex)
                }
                composable("settings") { SettingScreen(navController) }
                composable("settings/{user}") { backStackEntry ->
                    val user = backStackEntry.arguments?.getString("user")
                    SettingScreen(navController, user)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldScreen(
    navController: NavController,
    title: String,
    showBackButton: Boolean = true,
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    if (showBackButton) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back Arrow"
                            )
                        }
                    } }
            )
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier.padding(innerPadding).padding(16.dp)
            ) { content() }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController){
    ScaffoldScreen(
        navController,
        "Home",
        false,
    ) {
        var username by rememberSaveable { mutableStateOf("") }
        var itemIndex by rememberSaveable { mutableStateOf<Int?>(null) }
        Column (
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            TextField(
                value = itemIndex?.toString() ?: "",
                onValueChange = { val input = it; itemIndex = input.toIntOrNull()},
                singleLine = true,
                placeholder = { Text("3") },
            )
            Button(
                onClick = {navController.navigate("details/${itemIndex.toString()}")}
            ) {
                Text("Go to Details")
            }

            TextField(
                value = username,
                onValueChange = {username = it},
                singleLine = true,
                placeholder = { Text("Max Mustermann") },
            )
            Button(
                onClick = {navController.navigate("settings/$username")}
            ) {
                Text("Go to Settings")
            }

        }
    }

}



@Composable
fun DetailScreen(navController: NavHostController, itemIndex: Int?=null){
    ScaffoldScreen(
        navController,
        "Details",
        true,
    ) {
        val iconList = listOf(
            Icons.Default.Home,
            Icons.Default.Search,
            Icons.Default.Favorite,
            Icons.Default.Settings,
            Icons.Default.Info
        )
        Column (
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (itemIndex == null) {
                iconList.forEach {
                    Icon(
                        imageVector = it,
                        contentDescription = "Icon"
                    )
                }
            }else{
                Text("Selected Index = ${itemIndex}.")
                if(itemIndex>iconList.lastIndex){
                    Text("Index too big!")
                }else{
                    Icon(
                        imageVector = iconList[itemIndex],
                        contentDescription = "selected Icon"
                    )
                }
            }
        }


    }
}

@Composable
fun SettingScreen(navController: NavHostController, user: String?=null){
    ScaffoldScreen(
        navController,
        "Settings",
        true,
    ) {
        var showForm by remember { mutableStateOf(false) }
        var buttonText by remember { mutableStateOf("") }
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        Column (
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            if (user.isNullOrBlank()) {

                Button(
                    onClick = {showForm = true; buttonText="Log in"}
                ) {
                    Text("Log in")
                }
                Button(
                    onClick = {showForm = true; buttonText = "Register"}
                ) {
                    Text("Register")
                }
                if(showForm){
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(
                        value = username,
                        onValueChange = {username = it},
                        singleLine = true,
                        placeholder = { Text("username") },
                    )
                    TextField(
                        value = password,
                        onValueChange = {password = it},
                        singleLine = true,
                        placeholder = { Text("password") },
                    )
                    Button(
                        onClick = {navController.popBackStack()}
                    ) {
                        Text(buttonText)
                    }
                }
            }else{
                Text("Hello ${user}!")
                Button(
                    onClick = {navController.popBackStack()}
                ) {
                    Text("Log out")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    Navigation_1Theme {
        DetailScreen(rememberNavController())
    }
}