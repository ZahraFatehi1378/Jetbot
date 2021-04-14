package jetbot.data.api.services


import jetbot.data.api.model.WordModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("/api/v2/entries/{language_code}/{word}")
    suspend fun getWordData(
        @Path("language_code")language_code:String,
        @Path("word")word:String
    ):retrofit2.Response<List<WordModel>>

}