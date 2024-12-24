package com.example.ktwitnesses.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.example.ktwitnesses.R

@Composable
fun PaymentScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Способ оплаты",
            style = MaterialTheme.typography.h2.copy(fontSize = 24.sp),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))

        MethodCard(title = "Картой онлайн", iconResId = R.drawable.ic_credit_card)
        Spacer(modifier = Modifier.height(8.dp))

        MethodCard(title = "При получении", iconResId = R.drawable.ic_cash_on_delivery)
        Spacer(modifier = Modifier.height(8.dp))

        MethodCard(title = "Привязать СБП", iconResId = R.drawable.ic_sbp)
    }
}

@Composable
fun MethodCard(title: String, iconResId: Int) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.body2.copy(fontSize = 16.sp),
                modifier = Modifier.padding(start = 8.dp)
            )

            Image(
                painter = painterResource(id = iconResId),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 8.dp)
            )
        }
    }
}
