package com.vigneshtheagarajan.androidmessagesapp.ui

import android.telephony.SmsManager
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vigneshtheagarajan.androidmessagesapp.utils.AESEncryption
import com.vigneshtheagarajan.androidmessagesapp.vm.MessageViewModel
import javax.crypto.SecretKey

@Composable
fun SenderScreen(messageViewModel: MessageViewModel, sendSms: (String) -> Unit) {
    var message by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicTextField(
            value = message,
            onValueChange = { message = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(16.dp)
//                .background(Color.Gray)
                .border(BorderStroke(1.dp, Color.Black),shape = RoundedCornerShape(10.dp)),
            decorationBox = { innerTextField ->
                androidx.compose.foundation.layout.Box(
                    Modifier
                        .padding(8.dp)
                ) {
                    innerTextField()
                }
            }

        )
        Button(
            onClick = {
                if (message.isNotEmpty()) {
                    messageViewModel.sendMessage(message)
                    sendSms(message)
                    message = ""

                }
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Send")
        }

        val sentMessages by messageViewModel.sentMessages.collectAsState()
        LazyColumn {
            items(sentMessages) { msg ->
                Text(messageViewModel.decryptMessage(msg), modifier = Modifier.padding(8.dp))
            }
        }
    }


}
