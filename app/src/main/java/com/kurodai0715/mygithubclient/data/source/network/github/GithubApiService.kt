package com.kurodai0715.mygithubclient.data.source.network.github

import androidx.core.net.ParseException
import com.kurodai0715.mygithubclient.data.source.network.NetworkProfile
import com.kurodai0715.mygithubclient.data.source.network.NetworkUserRepo
import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import java.net.URI
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val BASE_URL = "https://api.github.com"

/**
 * Retrofit で使用する Moshi オブジェクトを Kotlin アダプターファクトリを使用して生成します。
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .add(UriJsonAdapter())
    .add(DateJsonAdapter())
    .build()

private class UriJsonAdapter {
    @ToJson
    fun toJson(uri: URI): String {
        return uri.toString()
    }

    @FromJson
    fun fromJson(uriString: String): URI {
        return URI(uriString)
    }
}

private class DateJsonAdapter {

    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())

    @ToJson
    fun toJson(date: Date): String {
        return sdf.format(date)
    }

    @FromJson
    fun fromJson(dateString: String): Date {
        return try {
            sdf.parse(dateString)!!
        } catch (e: ParseException) {
            Date()
        }
    }
}


private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface GithubApiService {

    /**
     * ドキュメント URL : https://docs.github.com/ja/rest/users/users?apiVersion=2022-11-28
     */
    @Headers(
        "Accept: application/vnd.github+json",
        "X-GitHub-Api-Version: 2022-11-28"
    )
    @GET("user")
    suspend fun getProfile(@Header("Authorization") auth: String): Response<NetworkProfile>

    /**
     * トークンの所有者の全リポジトリの情報を取得する.
     *
     * ドキュメント URL : https://docs.github.com/ja/rest/repos/repos?apiVersion=2022-11-28#list-repositories-for-the-authenticated-user
     *
     * 【参考】
     * よく似た API に "/users/{username}/repos" というエンドポイントがあるが、こちらは、
     * ドキュメントに記載してある通り、 public リポジトリの一覧を取得する API であるため、
     * private リポジトリは取得できません。
     */
    @Headers(
        "Accept: application/vnd.github+json",
        "X-GitHub-Api-Version: 2022-11-28"
    )
    @GET("/user/repos")
    suspend fun getUserRepos(@Header("Authorization") auth: String): Response<List<NetworkUserRepo>>

}

/**
 * 公開 API オブジェクト (Retrofit Service を遅延初期化して公開)
 */
object GithubApi {
    val retrofitService: GithubApiService by lazy { retrofit.create(GithubApiService::class.java) }
}