package com.esrakaya.rickandmortyepisodes.ui.detail

import com.esrakaya.rickandmortyepisodes.EpisodeQuery
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import org.junit.Test

class EpisodeDetailUiStateList {

    private val result by lazy {
        EpisodeQuery.Episode(
            id = "id",
            name = "name",
            air_date = "air_date",
            created = "created",
            episode = "episode"
        )
    }

    @Test
    fun `Verify details is gone when the detail is null`() {
        val uiState = EpisodeDetailUiState(episode = null)

        uiState.isDetailVisible.shouldBeFalse()
    }

    @Test
    fun `Verify details is visible when the detail is not null`() {
        val uiState = EpisodeDetailUiState(episode = result)

        uiState.isDetailVisible.shouldBeTrue()
    }

}
