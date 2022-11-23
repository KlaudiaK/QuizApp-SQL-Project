package com.android.quizzy.presentation.quiz_list

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.android.quizzy.domain.Categories
import com.android.quizzy.domain.Quiz
import com.android.quizzy.ui.theme.black60

@Composable
fun QuizCard(
    item: Quiz,
    onClick: () -> Unit,
    backgroundColor: Color = black60
) {
    val title = item.title
    val author = item.author
    var url = item.image
    url = url?.replace("http:", "https:")

    Card(
        shape = RoundedCornerShape(24),
        modifier = Modifier
            .padding(16.dp)
            //  .border(BorderStroke(2.dp,MaterialTheme.colorScheme.secondary))

            .heightIn(max = 200.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        border = BorderStroke(
            0.5.dp, Categories.values().find {
                it.name.lowercase() == (item.category?.lowercase() ?: "")
            }?.color
                ?: Color.Transparent
        )
    ) {


        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(url)
                    .crossfade(true)
                    .build(),
                contentDescription = "thumbnail",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .height(120.dp)
                    .aspectRatio(0.8f)
                    .align(Alignment.CenterVertically)
                    .clip(RoundedCornerShape(24)),
                alpha = 0.95f,
                onLoading = { Log.i("Load", "Loading") },
                onSuccess = { Log.i("Load", "Success") },
                onError = { Log.i("Load", "Error") },
            )

            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .fillMaxWidth(0.9f)
            ) {
                Text(
                    text = title,
                    modifier = Modifier
                        .wrapContentWidth(Alignment.Start)
                        .padding(start = 8.dp, top = 10.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.W500,
                    fontSize = 23.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = author,
                    modifier = Modifier
                        .wrapContentWidth(Alignment.Start)
                        .padding(start = 8.dp, top = 8.dp),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.W300,
                    fontSize = 16.sp
                )
            }
        }
    }
}