package com.group1.zoomi.ui.home

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.group1.zoomi.R
import com.group1.zoomi.data.Workout
import com.group1.zoomi.ui.ZoomiViewModelProvider

@Composable
fun OverviewScreen(
    modifier: Modifier = Modifier,
    onLogout: () -> Unit,
    overviewViewModel: OverviewViewModel = viewModel(factory = ZoomiViewModelProvider.Factory)
) {

    val overviewUiState by overviewViewModel.overviewUiState.collectAsState()

    Column(modifier = modifier.fillMaxSize()) {

        // 🔹 Static header
        HeaderUi(onLogout)

        // 🔹 Scrollable list
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(overviewUiState.workoutList) { workout ->
                WorkoutCard(workout)
            }
        }

        // 🔹 Static footer
        FooterUi()
    }
}


@Composable
fun WorkoutCard(workout: Workout, modifier: Modifier = Modifier) {
    Card(modifier = modifier.padding(8.dp)) {
        Column {
            Image(
                painter = painterResource(R.drawable.default_workout),
                contentDescription = workout.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(194.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = workout.title,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

@Composable
fun HeaderUi(onLogout: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .statusBarsPadding(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(R.drawable.umizoomi_bot),
            contentDescription = "robot picture",
            modifier = Modifier.size(72.dp)
        )
        Button(onClick = { onLogout() }, modifier = Modifier.padding(start = 16.dp)) {
            Text(stringResource(R.string.login_button))
        }
    }
}

@Composable
fun FooterUi(modifier: Modifier = Modifier) {
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(stringResource(R.string.add_workout))
    }
}
