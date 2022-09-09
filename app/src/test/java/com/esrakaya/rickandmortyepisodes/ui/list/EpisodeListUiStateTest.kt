package com.esrakaya.rickandmortyepisodes.ui.list

import com.esrakaya.rickandmortyepisodes.EpisodeListQuery
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import org.junit.Test

class EpisodeListUiStateTest {

    private val result by lazy {
        EpisodeListQuery.Result(
            id = "id",
            name = "name",
            air_date = "air_date",
            created = "created"
        )
    }

    @Test
    fun `Verify empty state is gone when the items is null`() {
        val uiState = EpisodeListUiState(items = null)

        uiState.isEmptyStateVisible.shouldBeFalse()
    }

    @Test
    fun `Verify empty state is visible when the items is empty`() {
        val uiState = EpisodeListUiState(items = listOf())

        uiState.isEmptyStateVisible.shouldBeTrue()
    }

    @Test
    fun `Verify empty state is gone when the items is not empty`() {
        val uiState = EpisodeListUiState(items = listOf(result))

        uiState.isEmptyStateVisible.shouldBeFalse()
    }

    @Test
    fun `Verify load more is locked when the page is null`() {
        val uiState = EpisodeListUiState(page = null)

        uiState.lockLoadMore.shouldBeTrue()
    }

    @Test
    fun `Verify load more is not locked when the page is not null`() {
        val uiState = EpisodeListUiState(page = 1)

        uiState.lockLoadMore.shouldBeFalse()
    }
}
