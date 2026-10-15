package com.example.agendasdeservios.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.agendasdeservios.utils.DateUtils

class AgendamentoAlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val agendamentoId = intent.getLongExtra("AGENDAMENTO_ID", 0L)
        val clienteNome = intent.getStringExtra("CLIENTE_NOME") ?: "Cliente"
        val servicoNome = intent.getStringExtra("SERVICO_NOME") ?: "Serviço"
        val dataHora = intent.getLongExtra("DATA_HORA", System.currentTimeMillis())

        val horarioStr = DateUtils.formatTime(dataHora)

        val title = "🔔 Lembrete de Agendamento!"
        val message = "$clienteNome - $servicoNome às $horarioStr"

        NotificationHelper.showNotification(
            context = context,
            notificationId = agendamentoId.toInt(),
            title = title,
            message = message
        )
    }
}
