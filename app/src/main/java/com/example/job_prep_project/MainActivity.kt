package com.example.job_prep_project

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.job_prep_project.ui.theme.Job_Prep_ProjectTheme
import com.example.job_prep_project.ui.theme.ThemeSettings
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Job_Prep_ProjectTheme {
                val profileTab = TabBarItem(
                    title = "Profile",
                    selectedIcon = Icons.Filled.Person,
                    unselectedIcon = Icons.Outlined.Person
                )
                val chatbotTab = TabBarItem(
                    title = "Friends",
                    selectedIcon = Icons.Filled.Search,
                    unselectedIcon = Icons.Outlined.Search
                )
                val heartRateTab = TabBarItem(
                    title = "Heart Rate",
                    selectedIcon = Icons.Filled.Favorite,
                    unselectedIcon = Icons.Outlined.FavoriteBorder
                )
                val settingsTab = TabBarItem(
                    title = "Account",
                    selectedIcon = Icons.Filled.Settings,
                    unselectedIcon = Icons.Outlined.Settings
                )

                val navController = rememberNavController()
                val tabBarItems = listOf(profileTab, chatbotTab, heartRateTab, settingsTab)

                Job_Prep_ProjectTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Scaffold(bottomBar = { TabView(tabBarItems, navController) }) {
                            NavigationHost(navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TabView(tabBarItems: List<TabBarItem>, navController: NavController) {
    val currentRoute by navController.currentBackStackEntryFlow
        .map { it.destination.route }
        .collectAsState(initial = tabBarItems.firstOrNull()?.title)

    NavigationBar {
        tabBarItems.forEach { tabBarItem ->
            NavigationBarItem(
                selected = currentRoute == tabBarItem.title,
                onClick = {
                    if (currentRoute != tabBarItem.title) {
                        navController.navigate(tabBarItem.title) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (currentRoute == tabBarItem.title) tabBarItem.selectedIcon else tabBarItem.unselectedIcon,
                        contentDescription = tabBarItem.title
                    )
                },
                label = { Text(tabBarItem.title) }
            )
        }
    }
}


// Navigation Host
@Composable
fun NavigationHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "Profile") {
        composable("Profile") { Profile() }
        composable("Friends") { Friends() }
        composable("Heart Rate") { HeartRateScreen() }
        composable("Account") { AccountScreen(isLightTheme = ThemeSettings.isLightTheme, navController) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Friends() {
    val friendsList = listOf(
        FriendData(
            name = "Khabib",
            steps = "8,400",
            message = "Keep pushing forward!",
            pictureResId = R.drawable.download // Replace with your drawable resource
        ),
        FriendData(
            name = "Ronaldo",
            steps = "6,200",
            message = "You're doing great!",
            pictureResId = R.drawable.ronaldo // Replace with your drawable resource
        ),
        FriendData(
            name = "Artur Beterbiev",
            steps = "10,000",
            message = "Way to go!",
            pictureResId = R.drawable.arturbeterbiev // Replace with your drawable resource
        ),
        FriendData(
            name = "Ali",
            steps = "4,000",
            message = "Wohoo!",
            pictureResId = R.drawable.ali // Replace with your drawable resource
        ),
        FriendData(
            name = "Mackhachev",
            steps = "1,000",
            message = "Tired!",
            pictureResId = R.drawable.mackhachev // Replace with your drawable resource
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Friends") })
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(friendsList) { friend ->
                FriendRow(friend)
            }
        }
    }
}

@Composable
fun FriendRow(friend: FriendData) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile Picture
        Image(
            painter = painterResource(id = friend.pictureResId),
            contentDescription = "Profile Picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Friend Info
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = friend.name,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "${friend.steps} steps",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = friend.message,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

// Data class for Friend
data class FriendData(
    val name: String,
    val steps: String,
    val message: String,
    val pictureResId: Int
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeartRateScreen() {
    var currentHeartRate by remember { mutableStateOf<Int?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Heart Rate Monitor") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Top Half with Button and Current Heart Rate
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if (currentHeartRate != null) {
                        Text(
                            text = "$currentHeartRate BPM",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }
                    Button(onClick = {
                        val randomRate = (60..120).random() // Simulate heart rate
                        val currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

                        // Update global HeartRateData
                        HeartRateData.history.add(randomRate to currentTime)
                        currentHeartRate = randomRate
                    }) {
                        Text("Measure Heart Rate")
                    }
                }
            }

            // Bottom Half with Heart Rate History
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp),
            ) {
                if (HeartRateData.history.isEmpty()) {
                    Text(
                        text = "No heart rate data available.",
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else {
                    LazyColumn {
                        items(HeartRateData.history) { (rate, timestamp) ->
                            HeartRateHistoryItem(rate = rate, timestamp = timestamp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HeartRateHistoryItem(rate: Int, timestamp: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "$rate BPM")
        Text(text = timestamp)
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(isLightTheme: Boolean, navController: NavController) {
    // State variables
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var weight by remember { mutableStateOf(TextFieldValue("")) }
    var age by remember { mutableStateOf(TextFieldValue("")) }
    var stepsGoal by remember { mutableStateOf(TextFieldValue("")) }
    var profilePictureResId by remember { mutableStateOf<Int?>(null) } // To store the selected drawable resource ID
    var showDialog by remember { mutableStateOf(false) } // Dialog visibility state

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Account") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (ProfileData.isLoggedIn) {
                // Show logged-in message
                Text("You are logged in!", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    // Logout Logic
                    ProfileData.isLoggedIn = false
                    ProfileData.name = ""
                    ProfileData.weight = ""
                    ProfileData.age = ""
                    ProfileData.stepsGoal = ""
                    ProfileData.profilePictureResId = null
                    navController.navigate("Account")
                }) {
                    Text("Log Out")
                }
            } else {
                // Show fields to log in
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable {
                            showDialog = true // Show the dialog when the profile picture is clicked
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (profilePictureResId == null) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .size(100.dp)
                                .padding(8.dp)
                        )
                    } else {
                        Image(
                            painter = painterResource(id = profilePictureResId!!),
                            contentDescription = "Profile Picture",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .align(Alignment.BottomEnd)
                            .clip(CircleShape)
                            .clickable { showDialog = true } // Trigger edit dialog
                            .padding(4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Create, // Replace with appropriate edit icon
                            contentDescription = "Edit Picture",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (showDialog) {
                    ProfilePictureSelectionDialog(
                        onDismiss = { showDialog = false },
                        onPictureSelected = { selectedResId ->
                            profilePictureResId = selectedResId
                            showDialog = false
                        }
                    )
                }

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Your Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = weight,
                    onValueChange = { weight = it },
                    label = { Text("Your Weight (kg)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = age,
                    onValueChange = { age = it },
                    label = { Text("Your Age") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = stepsGoal,
                    onValueChange = { stepsGoal = it },
                    label = { Text("Daily Steps Goal") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Button(
                        modifier = Modifier.padding(20.dp),
                        onClick = {
                            // Update global ProfileData
                            ProfileData.name = name.text
                            ProfileData.weight = weight.text
                            ProfileData.age = age.text
                            ProfileData.stepsGoal = stepsGoal.text
                            ProfileData.profilePictureResId = profilePictureResId
                            ProfileData.isLoggedIn = true

                            // Navigate to Profile Screen
                            navController.navigate("Profile")
                        }
                    ) {
                        Text("Save Settings")
                    }
                }
            }
            Button(
                modifier = Modifier.padding(20.dp),
                onClick = {
                    ThemeSettings.isLightTheme = !ThemeSettings.isLightTheme
                }
            ) {
                Text(if (isLightTheme) "Switch to Dark Theme" else "Switch to Light Theme")
            }
        }
    }
}


@Composable
fun ProfilePictureSelectionDialog(
    onDismiss: () -> Unit,
    onPictureSelected: (Int) -> Unit
) {
    val drawableOptions = listOf(
        R.drawable.download, // Replace with your drawable resource names
        R.drawable.download2,
        R.drawable.ronaldo,
        R.drawable.arturbeterbiev,
        R.drawable.ali,
        R.drawable.mackhachev
    )

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Select Profile Picture") },
        text = {
            Column {
                drawableOptions.forEach { drawableResId ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                onPictureSelected(drawableResId)
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = drawableResId),
                            contentDescription = null,
                            modifier = Modifier
                                .size(50.dp)
                                .padding(end = 8.dp)
                        )
                        Text("Picture $drawableResId")
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Summary") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (ProfileData.isLoggedIn) {
                if (ProfileData.profilePictureResId != null) {
                    Image(
                        painter = painterResource(id = ProfileData.profilePictureResId!!),
                        contentDescription = "Profile Picture",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text("Name: ${ProfileData.name}")
                Text("Weight: ${ProfileData.weight} kg")
                Text("Age: ${ProfileData.age} years")
                Text("Daily Steps Goal: ${ProfileData.stepsGoal} steps")
                Spacer(modifier = Modifier.height(50.dp))
                // Display Randomized Data
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(30.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    item {
                        DataRow(
                            label = "Current Heart Rate",
                            value = "${(60..100).random()} BPM",
                            icon = Icons.Filled.Favorite
                        )
                    }
                    item {
                        DataRow(
                            label = "Calories Burned",
                            value = "${(200..500).random()} kcal",
                            icon = Icons.Outlined.Person
                        )
                    }
                    item {
                        DataRow(
                            label = "Steps Taken",
                            value = "${(2000..10000).random()} steps",
                            icon = Icons.AutoMirrored.Outlined.Send
                        )
                    }
                    item {
                        DataRow(
                            label = "BMI",
                            value = calculateBMI(ProfileData.weight.toFloatOrNull() ?: 0f, 1.75f) + " (Healthy)",
                            icon = Icons.Outlined.AddCircle
                        )
                    }

                }
            } else {
                Text(
                    "Kindly Login First or Input your Info in Account Menu",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@SuppressLint("DefaultLocale")
fun calculateBMI(weight: Float, height: Float): String {
    if (weight <= 0 || height <= 0) return "N/A"
    val bmi = weight / (height * height)
    return String.format("%.1f", bmi)
}


@Composable
fun DataRow(label: String, value: String, icon: ImageVector) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = label, style = MaterialTheme.typography.bodyMedium)
        }
        Text(text = value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
    }
}



// Data class for Tabs
data class TabBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

//object ProfileData {
//    var name: String = ""
//    var weight: String = ""
//    var age: String = ""
//    var stepsGoal: String = ""
//    var profilePictureResId: Int? = null
//}

object ProfileData {
    var name: String = ""
    var weight: String = ""
    var age: String = ""
    var stepsGoal: String = ""
    var profilePictureResId: Int? = null
    var isLoggedIn: Boolean = false
}


object HeartRateData {
    val history = mutableStateListOf<Pair<Int, String>>() // State wrapper for history
}
