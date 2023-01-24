package com.android.quizzy.presentation.quiz_list

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.android.quizzy.domain.model.Categories
import com.android.quizzy.domain.model.Quiz
import com.android.quizzy.ui.theme.black60
import com.android.quizzy.ui.theme.black80
import com.android.quizzy.ui.theme.redLight

@Composable
fun QuizCard(
    item: Quiz,
    onClick: () -> Unit,
    backgroundColor: Color = black60,
    onLikeClicked: (Long) -> Unit,
    onDislikeClicked: (Long) -> Unit,
    isLiked: Boolean = false
) {
    var isFavorite by remember { mutableStateOf(isLiked) }
    val title = item.title
    val author = item.author
    var url = item.image
    url = url?.replace("http:", "https:")

    Card(
        shape = RoundedCornerShape(24),
        modifier = Modifier
            .padding(vertical = 16.dp)
            .heightIn(max = 120.dp)
            .clickable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = black80.copy(0.8F)),
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
                .padding(end = 14.dp)
        ) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(url)
                    .crossfade(true)
                    .build(),
                contentDescription = "thumbnail",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(120.dp)
                    .width(140.dp)
                    .align(Alignment.CenterVertically)
                    .clip(RoundedCornerShape(24)),
                alpha = 0.95f,
                onLoading = { Log.i("Load", "Loading") },
                onSuccess = { Log.i("Load", "Success") },
                onError = { Log.i("Load", "Error") },
            )

            Box(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .fillMaxWidth(0.9f)
                    .fillMaxHeight()
            ) {
                Text(
                    text = title,
                    modifier = Modifier
                        .wrapContentWidth(Alignment.Start)
                        .padding(start = 8.dp, top = 10.dp)
                        .align(Alignment.TopStart),
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
                        .padding(start = 8.dp, top = 20.dp)
                        .align(Alignment.CenterStart),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.W300,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.align(Alignment.BottomCenter)
                    .padding(bottom = 12.dp).fillMaxWidth()
                    .height(2.dp).clip(RoundedCornerShape(12.dp))
                    .background(backgroundColor.copy(0.8F))
                    .align(Alignment.BottomCenter))
            }

            IconToggleButton(
                checked = isFavorite,
                onCheckedChange = {
                    if(isFavorite) onDislikeClicked(item.id) else onLikeClicked(item.id)
                    isFavorite = !isFavorite
                },
                modifier = Modifier.size(60.dp)
            )  {

                Icon( imageVector = if (isFavorite) {
                    Icons.Filled.Favorite
                } else {
                    Icons.Default.FavoriteBorder
                }, tint = redLight, contentDescription = "Like Quiz")
            }
        }
    }
}