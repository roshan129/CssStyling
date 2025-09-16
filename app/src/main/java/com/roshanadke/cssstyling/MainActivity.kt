package com.roshanadke.cssstyling

import android.os.Bundle
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.roshanadke.cssstyling.ui.theme.CssStylingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CssStylingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    LocalHtmlWebView(
                        modifier = Modifier.padding(
                            innerPadding
                        ),
                        htmlFileName = "sample.html",
                        cssFileName = "styles.css"
                    )
                }
            }
        }
    }
}

@Composable
fun LocalHtmlWebView(modifier: Modifier, htmlFileName: String, cssFileName: String) {
    val context = LocalContext.current

    AndroidView(
        factory = { ctx ->
            WebView(ctx).apply {
                true.also { settings.javaScriptEnabled = it }

                // Load HTML file from assets
                val htmlString =
                    context.assets.open(htmlFileName).bufferedReader().use { it.readText() }
                val cssString =
                    context.assets.open(cssFileName).bufferedReader().use { it.readText() }

                val htmlWithCss = """
                    <html>
                    <head>
                    <style>
                    $cssString
                    </style>
                    </head>
                    <body>
                    $htmlString
                    </body>
                    </html>
                """.trimIndent()

                loadDataWithBaseURL(
                    null,
                    htmlWithCss,
                    "text/html",
                    "utf-8",
                    null
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}
