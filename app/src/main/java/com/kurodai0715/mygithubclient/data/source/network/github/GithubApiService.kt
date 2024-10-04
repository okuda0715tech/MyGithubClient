package com.kurodai0715.mygithubclient.data.source.network.github

import com.kurodai0715.mygithubclient.data.source.network.NetworkProfile
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

private const val BASE_URL = "https://api.github.com"

/**
 * Retrofit で使用する Moshi オブジェクトを Kotlin アダプターファクトリを使用して生成します。
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface GithubApiService {

    @Headers(
        "Accept: application/vnd.github+json",
        "X-GitHub-Api-Version: 2022-11-28"
    )
    @GET("user")
    suspend fun getProfile(@Header("Authorization") auth: String): NetworkProfile

}

/**
 * 公開 API オブジェクト (Retrofit Service を遅延初期化して公開)
 */
object GithubApi {
    val retrofitService: GithubApiService by lazy { retrofit.create(GithubApiService::class.java) }
}