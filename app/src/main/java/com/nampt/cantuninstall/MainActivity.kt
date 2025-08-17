package com.nampt.cantuninstall

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

fun getText(context: Context): String = if (isWorkerRunning(context)) "⏸" else "▶"

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var text by remember { mutableStateOf(getText(context)) }
    Button(
        onClick = {
            text = getText(context)
        }, modifier = modifier
    ) {
        Text(text)
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
