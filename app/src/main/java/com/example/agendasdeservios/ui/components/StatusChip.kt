package com.example.agendasdeservios.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agendasdeservios.ui.theme.StatusCanceladoBg
import com.example.agendasdeservios.ui.theme.StatusCanceladoText
import com.example.agendasdeservios.ui.theme.StatusConcluidoBg
import com.example.agendasdeservios.ui.theme.StatusConcluidoText
import com.example.agendasdeservios.ui.theme.StatusConfirmadoBg
import com.example.agendasdeservios.ui.theme.StatusConfirmadoText
import com.example.agendasdeservios.ui.theme.StatusPendenteBg
import com.example.agendasdeservios.ui.theme.StatusPendenteText

@Composable
fun StatusChip(
    status: String,
    modifier: Modifier = Modifier
) {
    val (bgColor, textColor, label) = when (status.uppercase()) {
        "PENDENTE" -> Triple(StatusPendenteBg, StatusPendenteText, "Pendente")
        "CONFIRMADO" -> Triple(StatusConfirmadoBg, StatusConfirmadoText, "Confirmado")
        "CONCLUIDO" -> Triple(StatusConcluidoBg, StatusConcluidoText, "Concluído")
        "CANCELADO" -> Triple(StatusCanceladoBg, StatusCanceladoText, "Cancelado")
        else -> Triple(Color.LightGray, Color.DarkGray, status)
    }

    Text(
        text = label,
        color = textColor,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        modifier = modifier
            .background(color = bgColor, shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    )
}
