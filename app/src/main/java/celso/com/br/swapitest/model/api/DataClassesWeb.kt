package celso.com.br.swapitest.model.api

import com.google.gson.annotations.SerializedName

data class FilmResult(val result : List<Film>)

data class Film(val title : String,
                @SerializedName("episode_id") val episodeId : Int,
                @SerializedName("characters") val persoUrls : List<String> )

data class Person(val name : String, val gender : String)