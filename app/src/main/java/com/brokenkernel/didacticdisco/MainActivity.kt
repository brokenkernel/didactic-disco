package com.brokenkernel.didacticdisco

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.brokenkernel.didacticdisco.navigation.DidacticDiscoScaffold
import com.brokenkernel.didacticdisco.ui.theme.DidacticDiscoTheme

public class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DidacticDiscoTheme {
                DidacticDiscoScaffold()
            }
        }
    }
}
