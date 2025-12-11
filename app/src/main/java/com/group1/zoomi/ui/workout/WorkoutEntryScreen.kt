package com.group1.zoomi.ui.workout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.rotate
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
                title = { Text(stringResource(R.string.add_new_workout)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
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
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutInputForm(
    workoutUiState: WorkoutUiState,
    modifier: Modifier = Modifier,
    onValueChange: (WorkoutUiState) -> Unit = {},
    enabled: Boolean = true
) {
    val workoutTypes = listOf("Running", "Weight Training", "Cardio", "Yoga", "Pilates", "Swimming")
    var expanded by remember { mutableStateOf(false) }
    var optionalExpanded by remember { mutableStateOf(false) }


    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.workout_title),
            style = MaterialTheme.typography.titleLarge
        )
        OutlinedTextField(
            value = workoutUiState.title,
            onValueChange = { onValueChange(workoutUiState.copy(title = it)) },
            label = { Text(stringResource(R.string.workout_title_required)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        Text(
            text = stringResource(R.string.workout_type),
            style = MaterialTheme.typography.titleLarge
        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = workoutUiState.type,
                onValueChange = { },
                label = { Text(stringResource(R.string.workout_type_required)) },
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier.fillMaxWidth().menuAnchor(),
                enabled = enabled,
                singleLine = true
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                workoutTypes.forEach { type ->
                    DropdownMenuItem(
                        text = { Text(type) },
                        onClick = {
                            onValueChange(workoutUiState.copy(type = type))
                            expanded = false
                        }
                    )
                }
            }
        }
        Text(
            text = stringResource(R.string.workout_duration),
            style = MaterialTheme.typography.titleLarge
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
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ){
            IconButton(
                onClick = { optionalExpanded = !optionalExpanded },
                modifier = Modifier.size(MaterialTheme.typography.titleLarge.fontSize.value.dp * 1.2f) // pijl even groot als text
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = if(optionalExpanded)
                        stringResource(R.string.collapse)
                    else
                        stringResource(R.string.expand),
                    modifier = Modifier.rotate(if (optionalExpanded) 180f else 0f)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Optional Data",
                style = MaterialTheme.typography.titleLarge
            )
        }
        if (optionalExpanded){
            OutlinedTextField(
                value = workoutUiState.distance,
                onValueChange = { onValueChange(workoutUiState.copy(distance = it)) },
                label = { Text(stringResource(R.string.workout_distance)) },
                modifier = Modifier.fillMaxWidth(),
                enabled = enabled,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = workoutUiState.minHeartbeat,
                    onValueChange = { onValueChange(workoutUiState.copy(minHeartbeat = it)) },
                    label = { Text(stringResource(R.string.workout_min_heartbeat)) },
                    modifier = Modifier.weight(1f),
                    enabled = enabled,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = workoutUiState.maxHeartbeat,
                    onValueChange = { onValueChange(workoutUiState.copy(maxHeartbeat = it)) },
                    label = { Text(stringResource(R.string.workout_max_heartbeat)) },
                    modifier = Modifier.weight(1f),
                    enabled = enabled,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        }
        if (workoutUiState.weatherInfo.isNotBlank()) {
            Column {
                Text(
                    text = stringResource(R.string.workout_weather),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = workoutUiState.weatherInfo,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            Text(
                text = stringResource(R.string.weather_loading),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}