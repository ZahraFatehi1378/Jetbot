package jetbot.data.api.model

import com.google.gson.annotations.Expose

data class WordModel(
    @Expose
    val word: String,
    @Expose
    val phonetics: List<PhoneticsModel>,
    @Expose
    val meanings: List<MeaningsModel>
)