package com.rosales.tecsupfit.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun PerfilScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Diego Ramos",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = 24.dp, bottom = 4.dp)
        )
        Text(
            text = "Plan Premium",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Card(modifier = Modifier.padding(8.dp)) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("14", style = MaterialTheme.typography.titleLarge)
                    Text("Clases", style = MaterialTheme.typography.bodySmall)
                }
            }
            Card(modifier = Modifier.padding(8.dp)) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("3", style = MaterialTheme.typography.titleLarge)
                    Text("Racha", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}