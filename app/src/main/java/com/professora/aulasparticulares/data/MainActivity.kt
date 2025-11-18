package com.professora.aulasparticulares.data

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.professora.aulasparticulares.data.data.database.AppDatabase
import com.professora.aulasparticulares.data.ui.navigation.NavigationGraph
import com.professora.aulasparticulares.data.ui.theme.GestaoAulasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getDatabase(applicationContext)

        setContent {
            GestaoAulasTheme {
                val navController = rememberNavController()
                NavigationGraph(
                    navController = navController,
                    database = database
                )
            }
        }
    }
}
