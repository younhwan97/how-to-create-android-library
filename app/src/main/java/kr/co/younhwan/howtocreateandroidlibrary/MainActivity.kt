package kr.co.younhwan.howtocreateandroidlibrary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import kr.co.younhwan.howtocreateandroidlibrary.ui.theme.HowToCreateAndroidLibraryTheme
import kr.co.younhwan.image_preview.ImagePreview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HowToCreateAndroidLibraryTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    Box() {
                        ImagePreview(
                            image = painterResource(id = R.drawable.sample),
                            modifier = Modifier,
                            description = "",
                            contentDescription = "",
                            onImageClick = {}
                        )
                    }
                }
            }
        }
    }
}