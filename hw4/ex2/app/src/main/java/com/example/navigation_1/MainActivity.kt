package com.example.navigation_1

import android.content.ActivityNotFoundException
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.navDeepLink
import kotlinx.coroutines.launch
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.navigation.NavType
import androidx.navigation.navArgument
import java.util.Locale


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
                    val itemIndex =
                        backStackEntry.arguments?.getString("itemIndex")?.toIntOrNull()
                    DetailScreen(navController, itemIndex)
                }
                composable("settings") { SettingScreen(navController) }
                composable("settings/{user}") { backStackEntry ->
                    val user = backStackEntry.arguments?.getString("user")
                    SettingScreen(navController, user)
                }
                composable(
                    "webpage/{url}",
                    deepLinks = listOf(navDeepLink { uriPattern = "Navigation_1://webpage/{url}" })
                ) { backStackEntry ->
                    val encodedUrl = backStackEntry.arguments?.getString("url")
                    if(!encodedUrl.isNullOrEmpty()) {
                        val url = Uri.decode(encodedUrl)
                        val fullUrl = "https://developer.android.com/$url"
                        try {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                fullUrl.toUri()
                            )
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // Ensure it's a new task
                            startActivity(intent)
                        } catch (e: ActivityNotFoundException) {
                            // Handle the case where no app can handle the URL
                            Toast.makeText(
                                LocalContext.current,
                                "No app found to open the URL.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
                composable(
                    "localization/{location}",
                    deepLinks = listOf(navDeepLink { uriPattern = "Navigation_1://localization/geo:0,0?q={location}" })
                ) { backStackEntry ->
                    val location = backStackEntry.arguments?.getString("location")
                    if (!location.isNullOrEmpty()) {
                        val intent = Intent(Intent.ACTION_VIEW, "geo:0,0?q=$location".toUri())
                        intent.setPackage("com.google.android.apps.maps")
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        intent.resolveActivity(packageManager)?.let {
                            startActivity(intent)
                        }
                    }
                }
                composable(
                    "pdf?uri={Uri}", //? because the "/" would create problems (document%3A1000034343 => document:1000034343)
                    arguments = listOf(
                        navArgument("Uri") {
                            type = NavType.StringType
                            nullable = false
                        }
                    ),
                    deepLinks = listOf(navDeepLink { uriPattern = "Navigation_1://pdf?uri={Uri}" })
                ) { backStackEntry ->
                    val encodedUri = backStackEntry.arguments?.getString("Uri")!!
                    val uriFormatted = encodedUri.toUri() // to URI format

                    val context = LocalContext.current
                    Log.d("MyTag", "Check Permission: $uriFormatted")
                    checkAndGrantPermission(context, uriFormatted)
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setDataAndType(uriFormatted, "application/pdf")
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    Log.d("PDFUri", "Opening PDF with URI: $uriFormatted")

                    startActivity(intent)
                }
            }
        }
    }
}


fun checkAndGrantPermission(context: Context, uri: Uri) {
    try {
        context.contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
    } catch (e: SecurityException) {
        Log.e("PDFUri", "No permission granted for URI: $uri")
        // Handle the error
    }
}


@Composable
fun DrawerContent(navController: NavController, drawerState: DrawerState){
    val scope = rememberCoroutineScope()
    val locale = Locale.getDefault() //get localization of device
    var selectedPdfUri by remember {mutableStateOf<Uri?>(null)}
    val context = LocalContext.current
    // pick PDF with file picker "ActivityResultContracts.OpenDocument"
    val openPdfLauncher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        uri?.let {
            // Permission
            context.contentResolver.takePersistableUriPermission(it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            Log.d("PDFUri", "Permission granted for URI: $uri")

            selectedPdfUri = it
            navController.navigate("pdf?uri=$selectedPdfUri")
        }
    }
    ModalDrawerSheet {
        IconButton(onClick = {scope.launch { drawerState.close()} }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back Arrow"
            )
        }
        Text("Home", modifier = Modifier
            .padding(16.dp)
            .clickable {
                navController.navigate("home")
            })
        Text("Settings", modifier = Modifier
            .padding(16.dp)
            .clickable {
                navController.navigate("settings")
            })
        Text("Details", modifier = Modifier
            .padding(16.dp)
            .clickable {
                navController.navigate("details")
            })

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

        Text("webpage", modifier = Modifier
            .padding(16.dp)
            .clickable {
                val url = "develop/ui/compose/navigation"
                val encodedUrl = Uri.encode(url)
                navController.navigate("webpage/$encodedUrl")
            })

        Text("localization of ${locale.language}", modifier = Modifier.padding(16.dp).clickable {
                navController.navigate("localization/${locale.getDisplayCountry(locale)}")
            })

        Text("pdf", modifier = Modifier.padding(16.dp).clickable {
            if(selectedPdfUri == null) {
                openPdfLauncher.launch(arrayOf("application/pdf"))
            }else{
                navController.navigate("pdf?uri=$selectedPdfUri")
            }

        })

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
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(navController, drawerState)
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(title) },
                    navigationIcon = {
                        Row {
                            IconButton(
                                onClick = {
                                    scope.launch { drawerState.open() }
                                }) {
                                Icon(Icons.Default.Menu, contentDescription = "Menu")
                            }
                            if (showBackButton) {
                                IconButton(onClick = {
                                    navController.navigateUp()
                                }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Back Arrow"
                                    )
                                }
                            }
                        }
                    }
                )
            },
            content = { innerPadding ->
                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(16.dp)
                ) { content() }
            }
        )
    }
}


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