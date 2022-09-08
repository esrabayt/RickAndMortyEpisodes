package com.esrakaya.rickandmortyepisodes.data

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.esrakaya.rickandmortyepisodes.EpisodeListQuery
import com.esrakaya.rickandmortyepisodes.EpisodeQuery
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EpisodeRepository @Inject constructor(
    private val apolloClient: ApolloClient
) {

    suspend fun getEpisodeList(): ApolloResponse<EpisodeListQuery.Data> {
        return apolloClient.query(EpisodeListQuery()).execute()
    }

    suspend fun getEpisode(id: String): ApolloResponse<EpisodeQuery.Data> {
        return apolloClient.query(EpisodeQuery(id)).execute()
    }

}