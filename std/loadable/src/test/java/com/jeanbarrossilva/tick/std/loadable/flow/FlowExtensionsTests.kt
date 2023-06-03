package com.jeanbarrossilva.tick.std.loadable.flow

import app.cash.turbine.test
import com.jeanbarrossilva.loadable.list.serializableListOf
import com.jeanbarrossilva.tick.std.loadable.ListLoadable
import kotlin.test.Test
import kotlin.test.assertIs
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest

internal class FlowExtensionsTests {
    @Test
    fun `GIVEN a Flow WHEN converting it into a ListLoadable Flow THEN the initial value is loading`() { // ktlint-disable max-line-length
        runTest {
            flowOf(serializableListOf(1, 2, 3)).listLoadable(this, SharingStarted.Lazily).test {
                assertIs<ListLoadable.Loading<Int>>(awaitItem())
            }
        }
    }
}
