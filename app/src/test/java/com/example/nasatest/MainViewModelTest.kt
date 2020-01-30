package com.example.nasatest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.nasatest.data.model.*
import com.example.nasatest.data.repository.NasaRepository
import com.example.nasatest.viewmodel.MainViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.BlockJUnit4ClassRunner
import java.net.UnknownHostException

@RunWith(BlockJUnit4ClassRunner::class)
class MainViewModelTest {

    lateinit var viewModel: MainViewModel

    @JvmField
    @Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @MockK
    lateinit var nasaRepository: NasaRepository

    @MockK
    lateinit var disposable: CompositeDisposable

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = MainViewModel(nasaRepository, disposable)
    }

    @Test
    fun getItemsSetValue_whenRepositoryReturnedNonEmptyList() {

        //When
        val items = listOf(
            Item(
                listOf(ItemData("Dummy Date", "Dummy Name", "Dummy Title")),
                listOf(ItemLink("dummy image url"))
            )
        )
        every { nasaRepository.fetchItems() } returns Single.just(
            SearchResponse(
                SearchCollection(
                    items
                )
            )
        )

        //Then
        viewModel.fetchItems()

        //Verify
        assertEquals(items, viewModel.items.value)
        assertEquals(null, viewModel.errorMessage.value)
        assertEquals(MainViewModel.LoadingState.SUCCESS, viewModel.loadingState.value)
    }

    @Test
    fun getItemsShowError_whenRepositoryReturnedEmptyList() {

        every { nasaRepository.fetchItems() } returns Single.just(
            SearchResponse(
                SearchCollection(
                    emptyList()
                )
            )
        )

        viewModel.fetchItems()

        assertEquals(null, viewModel.items.value)
        assertEquals("No Photo Found", viewModel.errorMessage.value)
        assertEquals(MainViewModel.LoadingState.ERROR, viewModel.loadingState.value)
    }


    @Test
    fun getItemsShowNetworkError_whenRepositoryReturnsUnknownHostException() {
        every { nasaRepository.fetchItems() } returns Single.error(UnknownHostException())

        viewModel.fetchItems()

        assertEquals(null, viewModel.items.value)
        assertEquals("Network Error Occurred", viewModel.errorMessage.value)
        assertEquals(MainViewModel.LoadingState.ERROR, viewModel.loadingState.value)
    }

    @Test
    fun getItemsShowLocalizedErrorMessage_whenRepositoryReturnsAnotherTypeOfException() {
        every { nasaRepository.fetchItems() } returns Single.error(RuntimeException("This is custom error message"))

        viewModel.fetchItems()

        assertEquals(null, viewModel.items.value)
        assertEquals("This is custom error message", viewModel.errorMessage.value)
        assertEquals(MainViewModel.LoadingState.ERROR, viewModel.loadingState.value)
    }
    
}