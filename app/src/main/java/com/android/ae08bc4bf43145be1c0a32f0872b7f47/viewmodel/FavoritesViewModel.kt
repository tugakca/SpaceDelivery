package com.android.ae08bc4bf43145be1c0a32f0872b7f47.viewmodel

import androidx.lifecycle.ViewModel
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.usecase.SpaceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class FavoritesViewModel
@Inject constructor(var coinUseCase: SpaceUseCase) : ViewModel() {
}