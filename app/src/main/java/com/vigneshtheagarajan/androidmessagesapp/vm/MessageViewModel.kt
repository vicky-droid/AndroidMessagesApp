package com.vigneshtheagarajan.androidmessagesapp.vm

import android.telephony.SmsManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vigneshtheagarajan.androidmessagesapp.utils.AESEncryption
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.crypto.SecretKey

class MessageViewModel : ViewModel() {
    // For simplicity, data is not persisted to a database or shared preferences.
    private val _sentMessages = MutableStateFlow<List<String>>(emptyList())
    val sentMessages: StateFlow<List<String>> = _sentMessages

    private lateinit var secretKey: SecretKey

    init {
        secretKey = AESEncryption.generateKey()
    }

    fun sendMessage(message: String) {
        val encryptedMessage = AESEncryption.encrypt(message, secretKey)
        // Here message is encrypted and appended to the list
        _sentMessages.value = _sentMessages.value + encryptedMessage
    }


    fun decryptMessage(encryptedMessage: String): String {
        return AESEncryption.decrypt(encryptedMessage, secretKey)
    }

    fun getEncryptedMessage(message: String): String {
        return AESEncryption.encrypt(message, secretKey)
    }


}
