package jetbot

import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update

class MessageHandler(
    private val onMessageReceived: (Message) -> Unit
): Jetbot() {

    override fun onUpdate(update: Update) {
        update.message?.let(onMessageReceived)
    }
}