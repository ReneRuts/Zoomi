package com.group1.zoomi.ui.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.group1.zoomi.R
import com.group1.zoomi.data.Workout
import com.group1.zoomi.network.Feedback
import com.group1.zoomi.ui.ZoomiViewModelProvider
import com.group1.zoomi.ui.theme.Blue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsEntryScreen(
    navigateBack: () -> Unit,
    workoutId: Int = 0,
    viewModel: DetailsViewModel = viewModel(factory = ZoomiViewModelProvider.Factory)
) {
    val workoutDetails by viewModel.getWorkoutDetails(workoutId).collectAsState()
    val context = LocalContext.current

    // Trigger the IDOR-vulnerable request when the screen is loaded
    LaunchedEffect(workoutId) {
        viewModel.fetchPrivateFeedback(workoutId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.details_header)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
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
            privateNote = viewModel.privateNote,
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
    privateNote: Feedback?,
    onDownloadClick: () -> Unit = {}
) {
    if (workout == null) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Loading workout...")
        }
        return
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = painterResource(id = getWorkoutImage(workout)),
            contentDescription = workout.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = workout.title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        DetailRow(label = "Type", value = workout.type)
        Spacer(modifier = Modifier.height(12.dp))

        DetailRow(label = "Duration", value = "${workout.durationHours}h ${workout.durationMinutes}m")
        Spacer(modifier = Modifier.height(12.dp))

        if(workout.distance != null) {
            DetailRow(label = "Distance", value = "${workout.distance} km")
            Spacer(modifier = Modifier.height(12.dp))
        }
        if(workout.minHeartbeat != null) {
            DetailRow(label = "Min Heartbeat", value = "${workout.minHeartbeat} bpm")
            Spacer(modifier = Modifier.height(12.dp))
        }
        if(workout.maxHeartbeat != null) {
            DetailRow(label = "Max Heartbeat", value = "${workout.maxHeartbeat} bpm")
            Spacer(modifier = Modifier.height(12.dp))
        }

        DetailRow(label = "Weather", value = workout.weatherInfo)

        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Coach Private Feedback",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.Top) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                modifier = Modifier.size(20.dp).padding(top = 2.dp),
                tint = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = privateNote?.body ?: "Loading private feedback...",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onDownloadClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Blue,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(stringResource(R.string.download_workout))
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
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@DrawableRes
private fun getWorkoutImage(workout: Workout): Int {
    return when (workout.type) {
        "Climbing" -> R.drawable.climbing
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
