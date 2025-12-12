package com.group1.zoomi.ui.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.group1.zoomi.R
import com.group1.zoomi.data.Workout
import com.group1.zoomi.ui.ZoomiViewModelProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsEntryScreen(
    navigateBack: () -> Unit,
    workoutId: Int,
    viewModel: DetailsViewModel = viewModel(factory = ZoomiViewModelProvider .Factory)
) {
    val workoutDetails by viewModel.getWorkoutDetails(workoutId).collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.details_header)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
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
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun DetailsEntryBody(
    modifier: Modifier = Modifier,
    workout: Workout?,
) {
    if (workout == null) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(24.dp),

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {
            Text(
                text = "Loading workout...",
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
                .background(color = MaterialTheme.colorScheme.primaryContainer, shape = RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
        ) {
            Image(
                painter = painterResource(id = getWorkoutImage(workout)),
                contentDescription = workout.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(bottom = 8.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = workout.title,
                    style = MaterialTheme.typography.headlineLarge
                )
                Spacer(modifier = Modifier.height(16.dp))

                DetailRow(label = "Type", value = workout.type)
                Spacer(modifier = Modifier.height(12.dp))

                DetailRow(
                    label = "Duration",
                    value = "${workout.durationHours}h ${workout.durationMinutes}m"
                )
                Spacer(modifier = Modifier.height(12.dp))

                DetailRow(label = "Weather", value = workout.weatherInfo)
                Spacer(modifier = Modifier.height(12.dp))

                DetailRow(label = "Min Heartbeat", value = workout.minHeartbeat?.toString() ?: "N/A")
                Spacer(modifier = Modifier.height(12.dp))

                DetailRow(label = "Max Heartbeat", value = workout.maxHeartbeat?.toString() ?: "N/A")
                Spacer(modifier = Modifier.height(12.dp))

                DetailRow(label = "Distance", value = workout.distance?.toString() ?: "N/A")
            }
        }
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label
        )
        Text(
            text = value
        )
    }
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
