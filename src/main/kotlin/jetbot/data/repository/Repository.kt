package jetbot.data.repository

import jetbot.data.api.model.WordModel
import jetbot.data.api.services.RetrofitInstance
import retrofit2.Response

class Repository {
    suspend fun getWordData(language :String , word : String): Response<List<WordModel>> {
        return RetrofitInstance.myApi.getWordData(language , word)
    }
}