package jetbot.update.message

import jetbot.update.UpdateHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import org.telegram.telegrambots.meta.api.objects.Message

val UpdateHandler.messageHandler: Flow<Message>
    get() = updatesState
        .filter { it?.hasMessage() == true }
        .map { it?.message }
        .filterNotNull()

val UpdateHandler.groupMessages: Flow<Message>
    get() = messageHandler.filter { it.isGroupMessage || it.isSuperGroupMessage }

val UpdateHandler.userMessages: Flow<Message>
    get() = messageHandler.filter { it.isUserMessage }