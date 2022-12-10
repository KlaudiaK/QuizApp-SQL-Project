package com.android.quizzy.presentation.question_list

import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.quizzy.domain.model.Answer
import com.android.quizzy.ui.theme.easyGreen
import com.android.quizzy.ui.theme.pastelBlue20
import com.android.quizzy.ui.theme.redLight

@Composable
fun QuestionCardItem(
    questionId: Int,
    answers: List<Answer>,
    onDeleteClicked: (Int) -> Unit,
    onEditClicked: (Int) -> Unit
) {

    Box(Modifier.fillMaxWidth().padding(top = 10.dp)) {
        Column(modifier = Modifier
            .wrapContentSize()
            .align(Alignment.CenterStart), content = {
            answers.forEachIndexed { index, answer ->
                Row(
                    Modifier
                        .wrapContentSize()
                        .padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(answer.content)
                    if (answer.isCorrect)
                        Icon(
                            Icons.Outlined.Check,
                            contentDescription = null,
                            tint = easyGreen,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                }
            }
        })
        IconButton(
            onClick = { onEditClicked(questionId) }, modifier = Modifier
                .wrapContentSize()
                .padding(top = 10.dp)
                .align(Alignment.CenterEnd)
        ) {
            Icon(
                Icons.Outlined.Edit,
                null,
                tint = pastelBlue20,

                )
        }
        IconButton(
            onClick = { onDeleteClicked(questionId) }, modifier = Modifier
                .wrapContentSize()
                .padding(top = 10.dp)
                .align(Alignment.BottomEnd)
        ) {
            Icon(
                Icons.Outlined.Delete,
                null,
                tint = redLight
            )
        }
    }
}