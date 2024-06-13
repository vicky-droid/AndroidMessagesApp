package com.vigneshtheagarajan.androidmessagesapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vigneshtheagarajan.androidmessagesapp.vm.MessageViewModel

@Composable
fun ReceiverScreen(messageViewModel: MessageViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val sentMessages by messageViewModel.sentMessages.collectAsState()

        LazyColumn {
            items(sentMessages) { msg ->
                Text(messageViewModel.decryptMessage(msg), modifier = Modifier.padding(8.dp))
            }
        }
    }
}
