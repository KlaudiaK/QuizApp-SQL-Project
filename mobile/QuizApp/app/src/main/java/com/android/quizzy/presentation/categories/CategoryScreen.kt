package com.android.quizzy.presentation.categories

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.quizzy.domain.model.Categories
import com.android.quizzy.domain.model.Category
import com.android.quizzy.presentation.destinations.QuizListDestination
import com.android.quizzy.ui.theme.black80
import com.android.quizzy.ui.theme.pastelWhite
import com.android.quizzy.viewmodel.UiViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(route = "community_quizes")
@Composable
fun CategoriesScreen(
    categories: List<Category>? = listOf(),
    navigator: DestinationsNavigator,
    uiViewModel: UiViewModel
) {
    uiViewModel.onBottomBarVisibilityChange(true)
    val categories2 = mutableListOf<Category>()
    Categories.values().forEach {
        categories2.add(Category(it.name, icon = it.icon, color = it.color))
    }
    Surface(color = black80) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Text(
                text = "Choose category",
                fontSize = 32.sp,
                fontWeight = FontWeight.W600,
                color = pastelWhite,
                modifier = Modifier.padding(start = 12.dp, top = 12.dp, bottom = 12.dp)
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),

            ) {
                items(categories2) { category ->
                    CategoryCard(category = category, onClick = {
                        navigator.navigate(
                            QuizListDestination(
                                category = category.name,
                            )
                        )
                    })
                }
            }
        }
    }
}

@Composable
fun CategoryCard(category: Category, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = category.color),
        modifier = Modifier
            .width(150.dp)
            .height(130.dp)
            .padding(10.dp)
            .clickable(enabled = true, onClick = {
                onClick()
            }),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = category.icon,
                contentDescription = category.name,
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .size(34.dp)
            )
            Text(text = category.name, fontSize = 20.sp, fontWeight = FontWeight.W600)
        }
    }
}
