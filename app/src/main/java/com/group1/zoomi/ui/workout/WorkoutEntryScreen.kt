package com.group1.zoomi.ui.workout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.group1.zoomi.R
import com.group1.zoomi.ui.ZoomiViewModelProvider
import com.group1.zoomi.ui.workout.WorkoutEntryViewModel.WorkoutUiState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutEntryScreen(
    navigateBack: () -> Unit,
    viewModel: WorkoutEntryViewModel = viewModel(factory = ZoomiViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.add_workout)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
            )
        }
    )   { innerPadding ->
          WorkoutEntryBody(
              workoutUiState = viewModel.workoutUiState,
              onWorkoutValueChange = viewModel::updateUiState,
              onSaveClick = {
                  coroutineScope.launch {
                      viewModel.saveWorkout()
                      navigateBack()
                  }
              },
              modifier = Modifier.padding(innerPadding)
          )
    }
}

@Composable
fun WorkoutEntryBody(
    workoutUiState: WorkoutUiState,
    onWorkoutValueChange: (WorkoutUiState) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        WorkoutInputForm(
            workoutUiState = workoutUiState,
            onValueChange = onWorkoutValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            enabled = workoutUiState.isValid(),
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.save_workout))
        }
    }
}

@Composable
fun WorkoutInputForm(
    workoutUiState: WorkoutUiState,
    modifier: Modifier = Modifier,
    onValueChange: (WorkoutUiState) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = workoutUiState.title,
            onValueChange = { onValueChange(workoutUiState.copy(title = it)) },
            label = { Text(stringResource(R.string.workout_title_required)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = workoutUiState.type,
            onValueChange = { onValueChange(workoutUiState.copy(type = it)) },
            label = { Text(stringResource(R.string.workout_type_required)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            OutlinedTextField(
                value = workoutUiState.durationHours,
                onValueChange = { onValueChange(workoutUiState.copy(durationHours = it)) },
                label = { Text(stringResource(R.string.workout_duration_hours)) },
                modifier = Modifier.weight(1f),
                enabled = enabled,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            OutlinedTextField(
                value = workoutUiState.durationMinutes,
                onValueChange = { onValueChange(workoutUiState.copy(durationMinutes = it)) },
                label = { Text(stringResource(R.string.workout_duration_minutes)) },
                modifier = Modifier.weight(1f),
                enabled = enabled,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
    }
}
