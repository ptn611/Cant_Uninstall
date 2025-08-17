package com.nampt.cantuninstall

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.nampt.cantuninstall.ui.theme.CantUninstallTheme

class MainActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CantUninstallTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Composable
fun Greeting(modifier: Modifier = Modifier) {
    Button(
        onClick = { Log.d("CantUninstall", "Button clicked!") },
        modifier = modifier
    ) {
        val working=isWorkerRunning(LocalContext.current)
        if (working) {
            Text(text = "⏸")
        } else {
            Text(text = "▶")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CantUninstallTheme {
        Greeting()
    }
}



fun isWorkerRunning(context: Context): Boolean {
    val workManager = WorkManager.getInstance(context)

    val workInfos = workManager.getWorkInfosForUniqueWork("MyUniqueWork").get()

    val isWorkRunning = workInfos.any {
        it.state == WorkInfo.State.ENQUEUED || it.state == WorkInfo.State.RUNNING
    }

    return isWorkRunning
}
