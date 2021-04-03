package jetbot.update.observer

import jetbot.replyMessage
import jetbot.update.UpdateHandler
import org.telegram.telegrambots.meta.api.objects.Message

val chatMap: MutableMap<String, String> = mutableMapOf(
    "hi" to "hello",
    "fuck" to "ok I'm gonna fuck @Moeinwt"
)

fun UpdateHandler.answer(message: Message) {
    chatMap[message.text?.toLowerCase()]?.let { answer ->
        replyMessage(
            message.chatId.toString(),
            message.messageId,
            answer
        )
    }
}