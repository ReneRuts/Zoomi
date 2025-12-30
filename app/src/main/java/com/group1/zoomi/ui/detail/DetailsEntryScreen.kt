package com.group1.zoomi.ui.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.group1.zoomi.R
import com.group1.zoomi.data.Workout
import com.group1.zoomi.ui.ZoomiViewModelProvider
import com.group1.zoomi.ui.theme.Blue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsEntryScreen(
    navigateBack: () -> Unit,
    workoutId: Int,
    viewModel: DetailsViewModel = viewModel(factory = ZoomiViewModelProvider.Factory)
) {
    val workoutDetails by viewModel.getWorkoutDetails(workoutId).collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.details_header)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Blue,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = navigateBack)
                    {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        DetailsEntryBody(
            workout = workoutDetails,
            modifier = Modifier.padding(innerPadding),
            onDownloadClick = {
                workoutDetails?.let {
                    viewModel.saveWorkoutDetails(context, it)
                }
            }
        )
    }
}

@Composable
fun DetailsEntryBody(
    modifier: Modifier = Modifier,
    workout: Workout?,
    onDownloadClick: () -> Unit = {}
) {
    if (workout == null) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Blue)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Loading workout...",
                color = Color.White
            )
        }
        return
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
        ) {
            Image(
                painter = painterResource(id = getWorkoutImage(workout)),
                contentDescription = workout.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Blue)
                    .padding(16.dp)
            ) {
                Text(
                    text = workout.title,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DetailItem("Type", workout.type, modifier = Modifier.weight(1f))
                    VerticalDivider()
                    DetailItem(
                        "Duration",
                        "${workout.durationHours}h ${workout.durationMinutes}m",
                        modifier = Modifier.weight(1f)
                    )
                }
                HorizontalDivider()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DetailItem("Weather", workout.weatherInfo, modifier = Modifier.weight(1f))
                    VerticalDivider()
                    DetailItem(
                        "Distance",
                        workout.distance?.toString()?.let { "$it km" } ?: "N/A",
                        modifier = Modifier.weight(1f)
                    )
                }
                HorizontalDivider()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DetailItem(
                        "Min Heartbeat",
                        workout.minHeartbeat?.toString()?.plus(" bpm") ?: "N/A",
                        modifier = Modifier.weight(1f)
                    )
                    VerticalDivider()
                    DetailItem(
                        "Max Heartbeat",
                        workout.maxHeartbeat?.toString()?.plus(" bpm") ?: "N/A",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onDownloadClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Blue,
                contentColor = Color.White
            )
        ) {
            Text(stringResource(R.string.download_workout))
        }
    }
}

@Composable
private fun DetailItem(label: String, value: String, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(vertical = 8.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = Color.White.copy(alpha = 0.6f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
private fun HorizontalDivider() {
    Divider(
        color = Color.White.copy(alpha = 0.2f),
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
private fun VerticalDivider() {
    Box(
        Modifier
            .height(60.dp)
            .width(1.dp)
            .background(color = Color.White.copy(alpha = 0.2f))
    )
}

@DrawableRes
private fun getWorkoutImage(workout: Workout): Int {
    return when (workout.type) {
        "Cycling" -> R.drawable.cycling
        "Hiking" -> R.drawable.hiking
        "Running" -> R.drawable.running
        "Sailing" -> R.drawable.sailing
        "Skiing" -> R.drawable.skiing
        "Swimming" -> R.drawable.swimming
        "Walking" -> R.drawable.walking
        "Weight Training" -> R.drawable.weight_training
        "Yoga" -> R.drawable.yoga
        else -> R.drawable.default_workout
    }
}
