package com.example.calendar.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.YearMonth
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import java.time.format.DateTimeFormatter
import kotlin.comparisons.then
import kotlin.text.format
import kotlin.toString
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.graphics.SolidColor

// Animate
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.runtime.remember
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.core.tween



@Composable
fun CalendarScreen() {
    val panelVisible = remember {
        MutableTransitionState(false).apply { targetState = true }
    }
    val currentMonth = YearMonth.now()
    val dateTitle = currentMonth.format(
        DateTimeFormatter.ofPattern("LLLL yyyy")
    ).replaceFirstChar { it.uppercase() }
    val firstDayOfMonth = currentMonth.atDay(1)
    val daysInMonth = currentMonth.lengthOfMonth()
    val emptyCellsCount = firstDayOfMonth.dayOfWeek.value - 1
    val weekDays = listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс")
    val today = LocalDate.now()
    val todayDay = today.dayOfMonth
    val shape = RoundedCornerShape(28.dp)

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    )
    {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(
                visibleState = panelVisible,
                enter = fadeIn(animationSpec = tween(1000)) +
                        slideInHorizontally(
                            animationSpec = tween(1000),
                            initialOffsetX = { it / 2 }  // начинается на половину высоты ниже
                        )
            ) {
                // Контейнер с Календарем и шапкой
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .dropShadow(
                            shape = shape,
                            shadow = Shadow(
                                radius = 5.dp,
                                spread = 6.dp,
                                color = Color(0xFF7658EA),
                                alpha = 0.1f
                            )
                        )
                        .background(Color.White, shape)
                        .padding(16.dp)
                )
                {
                    // Шапка
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = dateTitle,
                            modifier = Modifier.weight(1f),
                            fontSize = 20.sp,
                            textAlign = TextAlign.Left
                        )
                        IconButton(onClick = { /* предыдущий месяц */ }) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowLeft,
                                contentDescription = "Предыдущий месяц"
                            )
                        }
                        IconButton(onClick = { /* следующий месяц */ }) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                contentDescription = "Следующий месяц"
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        weekDays.forEach { day ->
                            Text(
                                text = day,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center,
                                fontSize = 12.sp,
                                color = Color(0xFF000000),
                            )
                        }
                    }
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(7),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(emptyCellsCount) {
                            Box(modifier = Modifier.aspectRatio(1f))
                        }
                        items(daysInMonth) { index ->
                            val dayNumber = index + 1
                            val isToday = dayNumber == todayDay
                            Box(
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .then(
                                        if (isToday) {
                                            Modifier.background(
                                                color = Color(0xFF7658EA),
                                                shape = CircleShape
                                            )
                                        } else Modifier
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = dayNumber.toString(),
                                    fontSize = 16.sp,
                                    color = if (isToday) Color.White else Color(0xFF222222)
                                )
                            }
                        }
                    }
                }

            }
        }
        // Нижняя панель
        AnimatedVisibility(
            visibleState = panelVisible,
            enter = fadeIn(animationSpec = tween(1000)) +
                    slideInVertically(
                        animationSpec = tween(1000),
                        initialOffsetY = { it / 2 }  // начинается на половину высоты ниже
                    )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .dropShadow(
                        shape = shape,
                        shadow = Shadow(
                            radius = 5.dp,
                            spread = 6.dp,
                            color = Color(0xFF7658EA),
                            alpha = 0.1f
                        )
                    )
                    .height(IntrinsicSize.Min)

                    .background(Color(0xFF7658EA), shape)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // День
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .border(
                            width = 1.dp,
                            color = Color(0xFFb7aaea),
                            shape = RoundedCornerShape(18.dp)
                        )
                        .background(Color(0xFF9d8ae5), shape = RoundedCornerShape(18.dp))
                        .padding(horizontal = 4.dp, vertical = 4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Окт".uppercase(),
                        fontSize = 12.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = todayDay.toString(),
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                // День недели
                Column(
                    modifier = Modifier
                        .weight(3f)
                        .padding(horizontal = 8.dp, vertical = 0.dp)
                ) {
                    Text(
                        text = "Воскресенье",
                        fontSize = 12.sp,
                        color = Color.White
                    )
                    Text(
                        text = "Сегодня",
                        fontSize = 20.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                // Кол-во задач
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .border(
                            width = 1.dp,
                            color = Color(0xFFb7aaea),
                            shape = RoundedCornerShape(18.dp)
                        )
                        .padding(horizontal = 4.dp, vertical = 0.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "4",
                            fontSize = 24.sp,
                            textAlign = TextAlign.End,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 24.sp
                        )
                        Text(
                            text = "ЗАДАЧИ",
                            fontSize = 8.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 8.sp
                        )
                    }
                }
            }

        }

    }

}