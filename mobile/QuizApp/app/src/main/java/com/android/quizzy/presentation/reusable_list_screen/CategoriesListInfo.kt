package com.android.quizzy.presentation.reusable_list_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.quizzy.ui.theme.brown80
import com.android.quizzy.viewmodel.CategoryViewModel
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun CategoriesListInfo(
    categoryViewModel: CategoryViewModel = hiltViewModel()
) {
    val categories by categoryViewModel.getCategories().collectAsState(initial = listOf())

    val column1Weight = .4f
    val column2Weight = .6f

    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 40.dp)
    ) {

        item {
            Row(
                Modifier
                    .background(brown80)
                    .height(70.dp)) {
                TableCell(text = "Name", weight = column1Weight, largeText = false)
                TableCell(text = "Description", weight = column2Weight, largeText = false)
            }
        }

        items(categories) { category ->

            Row(
                Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                TableCell(text = category.name, weight = column1Weight)
                TableCell(text = category.description ?: "", weight = column2Weight)
            }
        }
    }
}