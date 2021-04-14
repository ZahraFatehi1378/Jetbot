package jetbot.data.api.model

import com.google.gson.annotations.Expose

data class ResponseModel(
    @Expose
    val wordsList : List<WordModel>
)