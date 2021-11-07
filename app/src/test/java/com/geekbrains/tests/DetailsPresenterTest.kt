package com.geekbrains.tests

import com.geekbrains.test.DECREMENTED_VALUE_9
import com.geekbrains.test.DEFAULT_VALUE_10
import com.geekbrains.test.INCREMENTED_VALUE_11
import com.geekbrains.tests.presenter.details.DetailsPresenter
import com.geekbrains.tests.view.details.ViewDetailsContract
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.times
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class DetailsPresenterTest {

    private lateinit var presenter: DetailsPresenter

    @Mock
    private lateinit var viewContract: ViewDetailsContract

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailsPresenter(DEFAULT_VALUE_10)
        presenter.onAttach(viewContract)
    }

    @Test
    fun detailsPresenter_onDecrement_RunOnce_ReturnTrue() {
        presenter.onDecrement()
        verify(viewContract, times(1))
            .setCount(ArgumentMatchers.eq(DECREMENTED_VALUE_9))
    }

    @Test
    fun detailsPresenter_onIncrement_RunOnce_ReturnTrue() {
        presenter.onIncrement()
        verify(viewContract, times(1))
            .setCount(ArgumentMatchers.eq(INCREMENTED_VALUE_11))
    }

    @Test
    fun detailsPresenter_onDecrement_ReturnCorrectValue() {
        presenter.onDecrement()
        verify(viewContract)
            .setCount(ArgumentMatchers.eq(DECREMENTED_VALUE_9))
    }

    @Test
    fun detailsPresenter_onIncrement_ReturnCorrectValue() {
        presenter.onIncrement()
        verify(viewContract)
            .setCount(ArgumentMatchers.eq(INCREMENTED_VALUE_11))
    }

    @Test
    fun detailsPresenter_onIncrement_NotRunAfterDetach_ReturnTrue() {
        presenter.onDetach()
        presenter.onIncrement()
        verify(viewContract, times(0))
            .setCount(ArgumentMatchers.eq(DEFAULT_VALUE_10))
    }

    @Test
    fun detailsPresenter_onDecrement_NotRunAfterDetach_ReturnTrue() {
        presenter.onDetach()
        presenter.onDecrement()
        verify(viewContract, never())
            .setCount(ArgumentMatchers.eq(DEFAULT_VALUE_10))
    }

    @After
    fun endTest() {
        presenter.onDetach()
    }
}