import jetbot.MessageHandler
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

fun main() {

    try {

        val messageHandler = MessageHandler(
            onMessageReceived = {
                println("message from ${it.chat.firstName} => ${it.text}")
            }
        )

        TelegramBotsApi(DefaultBotSession::class.java).apply {
            registerBot(messageHandler)
        }
    } catch (e: TelegramApiException) {
        e.printStackTrace()
    }
}