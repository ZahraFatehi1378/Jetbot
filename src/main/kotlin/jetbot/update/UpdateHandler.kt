package jetbot.update

import jetbot.Jetbot
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.telegram.telegrambots.meta.api.objects.Update

class UpdateHandler : Jetbot() {

    private val coroutineScope = CoroutineScope(CoroutineName("update-handler") + Dispatchers.Default)

    private val _updatesState = MutableStateFlow<Update?>(null)
    val updatesState: StateFlow<Update?> get() = _updatesState

    override fun onUpdate(update: Update) {
        _updatesState.value = update
    }

    fun shutdown() = coroutineScope.cancel()

    internal inline fun <T> Flow<T>.handle(crossinline action: suspend (value: T) -> Unit) {
        coroutineScope.launch { collect(action) }
    }
}

inline fun updateHandler(block: UpdateHandler.() -> Unit): UpdateHandler = UpdateHandler().apply(block)