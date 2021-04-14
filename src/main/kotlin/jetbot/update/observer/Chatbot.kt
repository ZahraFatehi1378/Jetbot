package jetbot.update.observer

import jetbot.data.api.model.MeaningsModel
import jetbot.data.api.model.WordModel
import jetbot.data.repository.Repository
import jetbot.replyMessage
import jetbot.update.UpdateHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.telegram.telegrambots.meta.api.objects.Message
import retrofit2.Response


lateinit var result: Response<List<WordModel>>
val repository = Repository()
var language = "en_US"
val chatMap: MutableMap<String, String> = mutableMapOf(
    "zahra" to "is a nice girl \uD83D\uDEB6\uD83C\uDFFB\u200D♀️️",
    "moein" to "loves his cousin",
    "yasin" to "== \uD83D\uDC80",
    "sina" to "boro bekhab",
)
val languagesList: HashMap<String, String> = hashMapOf(
    "English(US)" to "en_US",
    "Hindi" to "hi",
    "Spanish" to "es",
    "French" to "fr",
    "Japanese" to "ja",
    "Russian" to "ru",
    "English(UK)" to "en_GB",
    "German" to "de",
    "Italian" to "it",
    "Korean" to "ko",
    "BrazilianPortuguese" to "pt-BR",
    "Arabic" to "ar",
    "Turkish" to "tr"
)

fun UpdateHandler.answer(message: Message) {

    message.text?.let {

        if (it.startsWith("/jetbot ")) {
            val newMapComponent = it.substring(8).trim().split(":")
            if (newMapComponent.size == 2) chatMap.put(newMapComponent[0].trim(), newMapComponent[1].trim())
        }

        chatMap[message.text?.toLowerCase()]?.let { answer ->
            replyMessage(
                message.chatId.toString(),
                message.messageId,
                answer
            )
        }

        //dictionary word definition
        if (it.startsWith("/define ")) {
            val word = it.substring(8).trim()
            if (word.isNotEmpty()) {
                CoroutineScope(IO).launch {
                    result = repository.getWordData(language = language, word = word)
                    println("$result")
                    if (result.isSuccessful)
                        replyMessage(
                            message.chatId.toString(),
                            message.messageId,
                            makeDefinitionString(result)
                        )
                }
            }
        }

        //dictionary setting language
        if (it.startsWith("/setLang ")) {
            val lang = it.substring(9).trim()
            languagesList.let { map ->
                if (map.containsKey(lang)) {
                    language = map.getValue(lang)
                }
            }
        }
    }

}

fun makeDefinitionString(result: Response<List<WordModel>>): String {
    val stringBuilder = StringBuilder()
    val result = result.body()?.get(0)
    stringBuilder.append("❕${result!!.word.toUpperCase()}❕\npartOfSpeech:${result.meanings[0].partOfSpeech} , phonetics:${result.phonetics[0].text}\n \n")
    result?.meanings.let {
        stringBuilder.append(getSynonyms(it))
        stringBuilder.append(getDefinition(it))
        stringBuilder.append(getExamples(it))
    }
    return stringBuilder.toString()
}

fun getSynonyms(meanings: List<MeaningsModel>): String {
    val stringBuilder = StringBuilder()
    stringBuilder.append("❗synonyms:\n")
    for (meaning in meanings) {
        for (def in meaning.definitions) {
            if (def.synonyms != null)
                stringBuilder.append("${def.synonyms},")
        }
    }
    stringBuilder.deleteCharAt(stringBuilder.lastIndex)
    stringBuilder.append("\n \n")
    if (stringBuilder.trim().length == 10)
        return ""
    return stringBuilder.toString()
}

fun getDefinition(meanings: List<MeaningsModel>): String {
    val stringBuilder = StringBuilder()
    var counter = 0
    stringBuilder.append("❗definition:\n")
    for (meaning in meanings) {
        for (def in meaning.definitions) {
            counter++
            if (def.definition != null)
                stringBuilder.append("$counter: ${def.definition}\n")
        }
    }
    stringBuilder.append("\n")
    if (stringBuilder.trim().length == 12)
        return ""
    return stringBuilder.toString()
}

fun getExamples(meanings: List<MeaningsModel>): String {
    val stringBuilder = StringBuilder()
    var counter = 0
    stringBuilder.append("❗examples:\n")
    for (meaning in meanings) {
        for (def in meaning.definitions) {
            counter++
            if (def.example != null)
                stringBuilder.append("$counter: ${def.example}\n")
        }
    }
    stringBuilder.append("\n")
    if (stringBuilder.trim().length == 10)
        return ""
    return stringBuilder.toString()
}