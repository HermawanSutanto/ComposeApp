package com.dicoding.listcompose

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dicoding.listcompose.ui.ListComposeAppTheme

@Composable
fun About(
)  {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = "https://media.licdn.com/dms/image/C4E03AQGsz2chwr2aGg/profile-displayphoto-shrink_200_200/0/1632386835880?e=1706140800&v=beta&t=zEXvqi82-qfX7cfl-i2YJxErzmbiLhglpBFN8s-G9p0",
            contentDescription = "Gambar Profil",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .size(150.dp)
                .clip(shape = CircleShape)
                .border(4.dp, MaterialTheme.colorScheme.tertiary, CircleShape)
        )
        Text(
            text = "Hermawan Sutanto",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = "wawan.ws952.ws@gmail.com",
            modifier = Modifier.padding(top = 8.dp)
        )

        MyLinkedInButton()

    }
}


@Composable
@Preview(showBackground = true)
private fun AboutPreview() {
    ListComposeAppTheme {
       About()
    }
}
@Composable
fun MyLinkedInButton() {
    val context = LocalContext.current

    Button(
        onClick = {
            val linkedinUrl = "https://www.linkedin.com/in/hermawan-sutanto-9a89b0220"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkedinUrl))
            context.startActivity(intent)
        },
        modifier = Modifier
            .padding(top = 16.dp)
    ) {
        Text(text = "Kunjungi LinkedIn")
    }
}