package com.sudoku.solver.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sudoku.solver.data.GridModel
import com.sudoku.solver.data.populateHardGrid
import com.sudoku.solver.data.populateSampleGrid
import com.sudoku.solver.services.SudokuService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SudokuGridViewModel: ViewModel(), KoinComponent {

    private val sudokuService: SudokuService by inject()

    private val gridModel = mutableStateOf(GridModel())
    private val showHints = mutableStateOf(false)

    init {
        populateHardGrid(gridModel.value)
    }

    fun getGridModel() = gridModel

    fun showHints() = showHints

    fun updateValue(value: String, position: Int, gridPosition: Int) {
        gridModel.value.grid[gridPosition].items[position].value.value = value
        viewModelScope.launch {
            sudokuService.filterAllOptions(gridModel, clear = true)
        }
    }

    fun toggleHints() {
        showHints.value = !showHints.value
        if (showHints.value) {
            viewModelScope.launch {
                sudokuService.filterAllOptions(gridModel)
            }
        }
    }

    fun clear() {
        viewModelScope.launch {
            sudokuService.clear(gridModel)
        }
    }

    fun solve() {
        viewModelScope.launch {
            sudokuService.solveIteration(gridModel, true)
        }
    }

}