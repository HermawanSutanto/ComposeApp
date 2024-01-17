package com.dicoding.listcompose.ui


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.dicoding.listcompose.model.HeroesData

@Composable
fun DetailObject(
    heroId: String?,
    modifier: Modifier = Modifier,
) {
    val selectedHero = remember { HeroesData.heroes.find { it.id == heroId } }

    if (selectedHero != null) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                HeroListItem(
                    name = "Hero Name: ${selectedHero.name}",
                    photoUrl = "${selectedHero.photoUrl}",
                    description = "${selectedHero.description}",
                    genre = "${selectedHero.genre}",
                    modifier = Modifier,
                )
            }

        }
    } else {
        Text("Hero not found!")
    }
}


@Preview(showBackground = true)
@Composable
fun ListComposeAppPreview() {
    val heroId = "1"
    ListComposeAppTheme {
        DetailObject(heroId)
    }
}

@Composable
fun HeroListItem(
    name: String,
    photoUrl: String,
    description:String,
    genre: String,
    modifier: Modifier = Modifier,

    ) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            AsyncImage(
                model = photoUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = name,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Genre: $genre",
            color = Color.Gray,
            fontSize = 14.sp,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = description,
            fontSize = 16.sp,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Handle button click */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Add to Cart")
        }
    }
}


