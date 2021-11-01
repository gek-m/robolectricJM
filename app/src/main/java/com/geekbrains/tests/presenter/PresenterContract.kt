package com.geekbrains.tests.presenter

interface PresenterContract<ViewContract> {
    fun onAttach(viewContract: ViewContract)
    fun onDetach()
}
