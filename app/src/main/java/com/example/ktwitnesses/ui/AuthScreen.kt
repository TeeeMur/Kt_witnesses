package com.example.ktwitnesses.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import android.widget.Toast

@Composable
fun AuthScreen() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("register") { RegistrationScreen(navController) }
    }
}

@Composable
fun LoginScreen(navController: NavController) {
    val auth = Firebase.auth
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showMessage by remember { mutableStateOf(false)}
    var message by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Вход")
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") }
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            signin(auth, email, password) {msg ->
                showMessage = true
                message = msg
            }
        }) {
            Text("Войти")
        }
        Spacer(modifier = Modifier.height(70.dp))
        Button(onClick = { navController.navigate("register") }) {
            Text("Регистрация")
        }
        if(showMessage) {
            val context = LocalContext.current
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            showMessage = false
        }
    }
}

@Composable
fun RegistrationScreen(navController: NavController) {
    val auth = Firebase.auth
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showMessage by remember { mutableStateOf(false)}
    var message by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Регистрация")
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") }
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            signup(auth, email, password) {msg ->
                showMessage = true
                message = msg
            }
        }) {
            Text("Registration")
        }
        Spacer(modifier = Modifier.height(70.dp))
        Button(onClick = { navController.navigate("login") }) {
            Text("Вход")
        }
        if(showMessage) {
            val context = LocalContext.current
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            showMessage = false
        }
    }
}

private fun signin(auth: FirebaseAuth, email: String, password: String, callback: (String) -> Unit){
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful){
                callback("Success")
            } else {
                callback("Fail sign in: ${task.exception?.message}")
            }
        }
}

private fun signup(auth: FirebaseAuth, email: String, password: String, callback: (String) -> Unit){
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful){
                callback("Success")
            } else {
                callback("Fail sign up: ${task.exception?.message}")
            }
        }
}