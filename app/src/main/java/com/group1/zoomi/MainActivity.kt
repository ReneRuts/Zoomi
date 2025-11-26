package com.group1.zoomi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.group1.zoomi.model.Workouts
import com.group1.zoomi.ui.theme.ZoomiTheme
import com.group1.zoomi.data.Datasource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZoomiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ComposeOverviewScreen()
                }
            }
        }
    }
}

@Composable
fun ComposeOverviewScreen(
    modifier: Modifier = Modifier,
    workouts: List<Workouts> = Datasource().loadWorkouts()
) {
    Column(modifier = modifier.fillMaxSize()) {

        // 🔹 Static header
        HeaderUi()

        // 🔹 Scrollable list
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(workouts.size) { index ->
                WorkoutCard(workouts[index])
            }
        }

        // 🔹 Static footer
        FooterUi()
    }
}


@Composable
fun WorkoutCard(workout: Workouts) {
    Card(modifier = Modifier.padding(8.dp)) {
        Column {
            Image(
                painter = painterResource(workout.imageResourceId),
                contentDescription = stringResource(workout.stringResourceID),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(194.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = stringResource(workout.stringResourceID),
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

@Composable
fun HeaderUi() {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(24.dp)
        .statusBarsPadding(),
        horizontalArrangement = Arrangement.SpaceBetween) {
        Image(
            painter = painterResource(R.drawable.umizoomi_bot),
            contentDescription = "robot picture",
            modifier = Modifier.size(72.dp)
        )
        Button(onClick = { /*TODO*/ }, modifier = Modifier.padding(start = 16.dp)) {
            Text(stringResource(R.string.login_button))
        }
    }
}

@Composable
fun FooterUi() {
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(stringResource(R.string.add_workout))
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun WelcomeGreeting() {
    ZoomiTheme {
        ComposeOverviewScreen()
    }
}
