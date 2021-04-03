package jetbot

import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.MessageEntity
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

inline fun telegramBot(block: TelegramBotConfig.() -> Unit): TelegramBotsApi =
    TelegramBotConfig().apply(block).telegramBotsApi

class TelegramBotConfig {

    val telegramBotsApi = TelegramBotsApi(DefaultBotSession::class.java)

    operator fun TelegramLongPollingBot.unaryPlus() {
        telegramBotsApi.registerBot(this)
    }
}

fun TelegramLongPollingBot.deleteMessage(message: Message) {
    if (message.isGroupMessage || message.isSuperGroupMessage)
        execute(
            DeleteMessage(
                message.chatId.toString(),
                message.messageId
            )
        )
}

fun TelegramLongPollingBot.replyMessage(
    chatId: String,
    messageId: Int,
    text: String,
    parseMode: String = "",
    disableWebPagePreview: Boolean = false,
    disableNotification: Boolean = false,
    replyMarkUp: ReplyKeyboard = ReplyKeyboardRemove(false, false),
    entities: List<MessageEntity> = listOf(),
    allowSendingWithoutReply: Boolean = false
) {
    execute(
        SendMessage(
            chatId,
            text,
            parseMode,
            disableWebPagePreview,
            disableNotification,
            messageId,
            replyMarkUp,
            entities,
            allowSendingWithoutReply
        )
    )
}
