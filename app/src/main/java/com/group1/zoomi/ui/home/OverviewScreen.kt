package com.group1.zoomi.ui.home

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.group1.zoomi.R
import com.group1.zoomi.data.Workout
import com.group1.zoomi.model.LocationData
import com.group1.zoomi.ui.ZoomiViewModelProvider

@Composable
fun OverviewScreen(
    modifier: Modifier = Modifier,
    onLogout: () -> Unit,
    onAddWorkoutClick: () -> Unit,
    overviewViewModel: OverviewViewModel = viewModel(factory = ZoomiViewModelProvider.Factory)
) {

    val overviewUiState by overviewViewModel.overviewUiState.collectAsState()
    val location by overviewViewModel.locationState.collectAsState()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            if (granted) {
                overviewViewModel.fetchLocation()
            }
        }
    )

    LaunchedEffect(Unit) {
        launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    Column(modifier = modifier.fillMaxSize()) {

        // 🔹 Static header
        HeaderUi(onLogout, location)

        // 🔹 Scrollable list
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(overviewUiState.workoutList) { workout ->
                WorkoutCard(workout)
            }
        }

        // 🔹 Static footer
        FooterUi(onAddWorkoutClick = onAddWorkoutClick)
    }
}


@Composable
fun WorkoutCard(workout: Workout, modifier: Modifier = Modifier) {
    Card(modifier = modifier.padding(8.dp)) {
        Column {
            Image(
                painter = if (workout.imagePath != null) {
                    painterResource(R.drawable.weight_training)
                } else {
                    painterResource(R.drawable.default_workout)
                },
                contentDescription = workout.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(194.dp),
                contentScale = ContentScale.Crop
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = workout.title,
                    modifier = Modifier.weight(1f, fill = false),
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = "${workout.durationHours}h ${workout.durationMinutes}m",
                    modifier = Modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
fun HeaderUi(onLogout: () -> Unit, location: LocationData?, modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .statusBarsPadding(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.umizoomi_bot),
            contentDescription = "robot picture",
            modifier = Modifier.size(72.dp)
        )
        Text(
            text = if (location != null) {
                "Lat: ${location.latitude}, Lon: ${location.longitude}"
            } else {
                "Loading..."
            },
            style = MaterialTheme.typography.titleMedium
        )
        Button(onClick = { onLogout() }, modifier = Modifier.padding(start = 16.dp)) {
            Text(stringResource(R.string.logout_button))
        }
    }
}

@Composable
fun FooterUi(modifier: Modifier = Modifier, onAddWorkoutClick: () -> Unit) {
    Button(
        onClick = { onAddWorkoutClick() },
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(stringResource(R.string.add_workout))
    }
}
