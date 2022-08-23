package com.sudoku.solver.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.sudoku.solver.data.GridModel
import com.sudoku.solver.data.populateSampleGrid
import com.sudoku.solver.services.SudokuService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SudokuGridViewModel: ViewModel(), KoinComponent {

    private val sudokuService: SudokuService by inject()

    private val gridModel = mutableStateOf(GridModel())
    private val showHints = true

    init {
        populateSampleGrid(gridModel.value)
        sudokuService.filterAllOptions(gridModel.value)
    }

    fun getGridModel() = gridModel

    fun showHints() = showHints

    fun updateValue(value: String, position: Int, gridPosition: Int) {
        gridModel.value.grid[gridPosition].items[position].value.value = value
        sudokuService.filterAllOptions(gridModel.value)
    }

}