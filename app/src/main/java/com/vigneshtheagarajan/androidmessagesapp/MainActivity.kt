package com.vigneshtheagarajan.androidmessagesapp

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.getSystemService
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vigneshtheagarajan.androidmessagesapp.ui.ReceiverScreen
import com.vigneshtheagarajan.androidmessagesapp.ui.SenderScreen
import com.vigneshtheagarajan.androidmessagesapp.utils.AESEncryption
import com.vigneshtheagarajan.androidmessagesapp.vm.MessageViewModel

class MainActivity : ComponentActivity() {
    private val messageViewModel: MessageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp(messageViewModel, ::sendSms)
        }
        requestPermissions()
    }

    private fun requestPermissions() {
        requestPermissionLauncher.launch(
            arrayOf(
                android.Manifest.permission.SEND_SMS,
            )
        )
    }


    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[android.Manifest.permission.SEND_SMS] == true)
        {
            // All required permissions are granted
        } else {
            Toast.makeText(this, "SMS permission is required.", Toast.LENGTH_SHORT).show()
        }
    }


    fun sendSms(message: String) {
        try {
            val encryptedMessage =  messageViewModel.getEncryptedMessage(message)
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage("+919999999999", null, encryptedMessage, null, null)
            Toast.makeText(this, "Encrypted SMS Sent. Check default Message App", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Log.e("Error", "sendSms: ${e.toString()}", )
            Toast.makeText(this, "Unable to send SMS. Check SMS permission", Toast.LENGTH_LONG).show()

        }
    }

}

@Composable
fun MyApp(messageViewModel: MessageViewModel, sendSms: (String) -> Unit) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("sender") },
                    label = { Text("Sender") },
                    icon = { Icon(Icons.Default.Send, contentDescription = null) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("receiver") },
                    label = { Text("Receiver") },
                    icon = { Icon(Icons.Default.Message, contentDescription = null) }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = "sender",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("sender") { SenderScreen(messageViewModel, sendSms) }
            composable("receiver") { ReceiverScreen(messageViewModel) }
        }
    }
}
