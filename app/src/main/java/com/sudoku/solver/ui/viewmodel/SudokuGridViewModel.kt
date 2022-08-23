package com.sudoku.solver.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.sudoku.solver.data.SampleGridModel

class SudokuGridViewModel: ViewModel() {

    private val gridModel = mutableStateOf(SampleGridModel())
    private val showHints = true

    fun getGridModel() = gridModel

}