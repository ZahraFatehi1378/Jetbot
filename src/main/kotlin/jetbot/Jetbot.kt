package jetbot

import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update

abstract class Jetbot : TelegramLongPollingBot() {

    abstract fun onUpdate(update: Update)

    override fun getBotToken(): String = "1798539608:AAHs84gA4hx6mBTsVKDmiR3fzzGQYY6xARM"

    override fun getBotUsername(): String = "m4trix_jetbot"

    override fun onUpdateReceived(update: Update?) {
        update?.let { onUpdate(it) }
    }
}