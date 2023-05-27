package com.jeanbarrossilva.tick.feature.reminder

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.content.getSystemService
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.jeanbarrossilva.tick.app.R
import com.jeanbarrossilva.tick.core.domain.ToDo
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.UUID

class ReminderWorker internal constructor(context: Context, params: WorkerParameters) :
    Worker(context, params) {
    private val toDoTitle = params.inputData.getString(TO_DO_TITLE_KEY)
        ?: throw IllegalStateException("Missing to-do title.")

    private val notificationManager get() =
        applicationContext.getSystemService<NotificationManager>() ?: throw IllegalStateException(
            "Could not obtain the NotificationManager."
        )

    override fun doWork(): Result {
        sendNotification()
        return Result.success()
    }

    private fun sendNotification() {
        val id = id.hashCode()
        val notification = Notification
            .Builder(applicationContext, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_task_alt)
            .setContentTitle(toDoTitle)
            .build()
        createNotificationChannelIfAbsent()
        notificationManager.notify(id, notification)
    }

    private fun createNotificationChannelIfAbsent() {
        val isAbsent = notificationManager.getNotificationChannel(NOTIFICATION_CHANNEL_ID) == null
        if (isAbsent) {
            createNotificationChannel()
        }
    }

    private fun createNotificationChannel() {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, "Reminders", importance)
        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        private const val TO_DO_TITLE_KEY = "to_do_title"
        private const val NOTIFICATION_CHANNEL_ID = "reminders"

        fun getName(toDo: ToDo): String {
            return getID(toDo).toString()
        }

        fun request(toDo: ToDo): OneTimeWorkRequest {
            val dueDateTime = toDo.dueDateTime ?: throw IllegalArgumentException(
                "Cannot request a reminder for a to-do that cannot be due."
            )
            val id = getID(toDo)
            val inputData = Data.Builder().putString(TO_DO_TITLE_KEY, toDo.title).build()
            val zoneId = ZoneId.systemDefault()
            val zonedDueDateTime = dueDateTime.atZone(zoneId)
            val now = LocalDateTime.now().atZone(zoneId)
            val delayInMillis = now.until(zonedDueDateTime, ChronoUnit.MILLIS)
            val delay = Duration.ofMillis(delayInMillis)
            return OneTimeWorkRequestBuilder<ReminderWorker>()
                .setId(id)
                .setInputData(inputData)
                .setInitialDelay(delay)
                .build()
        }

        private fun getID(toDo: ToDo): UUID {
            return toDo.id
        }
    }
}
