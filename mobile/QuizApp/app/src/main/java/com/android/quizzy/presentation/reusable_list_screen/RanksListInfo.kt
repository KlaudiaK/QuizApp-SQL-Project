package com.android.quizzy.presentation.reusable_list_screen


import android.util.Size
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MilitaryTech
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.quizzy.domain.model.Rank
import com.android.quizzy.ui.theme.*
import com.android.quizzy.viewmodel.QuestionViewModel
import com.android.quizzy.viewmodel.RanksViewModel
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun RanksListInfo(
    ranksViewModel: RanksViewModel = hiltViewModel()
) {
    val ranks by ranksViewModel.getRanks().collectAsState(initial = listOf())

    val column1Weight = .4f
    val column2Weight = .2f

    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 40.dp)
    ) {

        item {
            Row(
                Modifier
                    .background(brown80)
                    .height(80.dp)) {
                TableCell(text = "Rank name", weight = column1Weight, largeText = false)
                TableCell(text = "", weight = column2Weight)
                TableCell(text = "Starts from: ", weight = column2Weight, largeText = false)
                TableCell(text = "Ends at: ", weight = column2Weight, largeText = false)
            }
        }

        items(ranks) { rank ->

            Row(
                Modifier
                    .fillMaxWidth()
                    .height(80.dp),
            horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                TableCell(text = rank.name, weight = column1Weight)
                TableCellIcon(color = Rank.valueOf(rank.name).color, weight = column2Weight)
                TableCell(text = rank.minPoints, weight = column2Weight)
                TableCell(text = rank.maxPoints, weight = column2Weight)
            }
        }
    }



}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float,
  largeText: Boolean = true
) {
    Text(
        text = text,
        Modifier
            .border(1.dp, Color.Black)
            .weight(weight)
            .height(80.dp)
            .padding(8.dp)
            .align(Alignment.CenterVertically),
        fontSize = if(largeText) 24.sp else 18.sp,
        color = if(largeText) pastelWhite  else  pastelBlack80
    )
}

@Composable
fun RowScope.TableCellIcon(
    color: Color,
    weight: Float
) {
    Icon(Icons.Outlined.MilitaryTech, contentDescription = null, Modifier
        .border(1.dp, Color.Black)
        .weight(weight)
        .height(80.dp)
        .padding(8.dp)
        .align(Alignment.CenterVertically), tint = color)
}