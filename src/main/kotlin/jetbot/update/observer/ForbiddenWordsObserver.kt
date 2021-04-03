package jetbot.update.observer

import jetbot.deleteMessage
import jetbot.update.UpdateHandler
import org.telegram.telegrambots.meta.api.objects.Message

val forbiddenWords: MutableList<String> = mutableListOf(
    "زشت"
)

fun UpdateHandler.checkForForbiddenWords(message: Message) {
    forbiddenWords.forEach {
        if (message.text?.toLowerCase()?.contains(it) == true)
            deleteMessage(message)
    }
}