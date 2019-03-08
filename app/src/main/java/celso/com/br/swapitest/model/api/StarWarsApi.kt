package celso.com.br.swapitest.model.api

import celso.com.br.swapitest.model.Character
import celso.com.br.swapitest.model.Movie
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable

class StarWarsApi {
    val path : String = "https://swapi.co/api/"


    val service : StarWarsApiDef

    init {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        val gson = GsonBuilder().setLenient().create()

        val retrofit = Retrofit.Builder()
            .baseUrl(path)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient.build())
            .build()

        service = retrofit.create(StarWarsApiDef::class.java)
    }


    fun loadMovies() : Observable<Movie>{

        val flatMap = service.listMovies()
            .flatMap { filmResult -> Observable.from(filmResult.result) }
            .flatMap { film -> Observable.just(Movie(film.title, film.episodeId, ArrayList<Character>())) }



        return flatMap
    }

}