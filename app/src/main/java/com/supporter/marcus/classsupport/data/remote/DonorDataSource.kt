package com.supporter.marcus.classsupport.data.remote
import com.supporter.marcus.classsupport.data.remote.json.DonorSearchResult
import retrofit2.http.GET
import retrofit2.http.Query
import com.supporter.marcus.classsupport.data.remote.json.Proposal
import kotlinx.coroutines.experimental.Deferred
import retrofit2.Call

interface DonorDataSource {
    @GET("json_feed.html?")
    fun searchProjects(@Query("keywords") keywords: String,@Query("APIKey") key:String ): Deferred<DonorSearchResult>
   // @GET("api/unknown")
   // fun searchProjects( ): Deferred<DonorSearchResult>

}