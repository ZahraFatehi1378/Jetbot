import jetbot.telegramBot
import jetbot.update.message.groupMessages
import jetbot.update.message.messageHandler
import jetbot.update.message.userMessages
import jetbot.update.observer.answer
import jetbot.update.observer.checkForForbiddenWords
import jetbot.update.updateHandler

fun main() {

    telegramBot {

        +updateHandler {

            messageHandler.handle {
                answer(it)
            }

            userMessages.handle { println("user message: ${it.text}") }

            groupMessages.handle {
                checkForForbiddenWords(it)
            }
        }
    }
}