package com.sudoku.solver.services

import android.util.Log
import androidx.compose.runtime.MutableState
import com.sudoku.solver.data.GridModel
import com.sudoku.solver.data.GridModel.Companion.ALL_OPTIONS
import com.sudoku.solver.data.GridModel.Companion.COLS
import com.sudoku.solver.data.GridModel.Companion.ROWS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SudokuService {

    companion object {
        private const val TAG = "SudokuService"
    }

    suspend fun solveIteration(gridModel: MutableState<GridModel>, allowGuess: Boolean) {
        withContext(Dispatchers.Default) {
            Log.d(TAG, "Start Solving")
            val solution = doSolveIterations(gridModel.value, true, allowGuess)
            if (solution != null && solution != gridModel.value) {
                gridModel.value.copy(solution)
            }
        }
    }

    suspend fun filterAllOptions(gridModel: MutableState<GridModel>, clear: Boolean = false, deep: Boolean = false) {
        withContext(Dispatchers.Default) {
            doFilterAll(gridModel.value, clear, deep)
        }
    }

    suspend fun clear(gridModel: MutableState<GridModel>) {
        withContext(Dispatchers.Default) {
            gridModel.value.grid.forEach { subGrid ->
                subGrid.items.forEach { item ->
                    if (item.solution == null) {
                        item.value.value = ""
                    }
                    item.options.clear()
                    item.options.addAll(ALL_OPTIONS)
                }
            }

            doFilterAll(gridModel.value, clear = true)
        }
    }

    private fun isSolved(gridModel: GridModel): Boolean {
        for (i in 0..8) {
            for (j in 0..8) {
                val value = gridModel.grid[i].items[j].value.value
                val options = gridModel.grid[i].items[j].options
                if (value.isEmpty() || options.size != 1 || value != options[0].toString()) {
                    return false
                }
            }
        }
        Log.d(TAG, "Solution Found")
        return true
    }

    private fun isUnsolvable(gridModel: GridModel): Boolean {
        for (i in 0..8) {
            for (j in 0..8) {
                val options = gridModel.grid[i].items[j].options
                if (options.isEmpty()) {
                    Log.d(TAG, "Solution Unsolvable")
                    return true
                }
            }
        }
        return false
    }

    private fun doSolveIterations(gridModel: GridModel, iterate: Boolean = true, allowGuess: Boolean): GridModel? {
        var solvedSomething = false

        doFilterAll(gridModel, deep = true)

        gridModel.grid.forEach { subGrid ->
            subGrid.items.forEach { item ->
                if (item.value.value.isEmpty() && item.options.size == 1) {
                    item.value.value = item.options[0].toString()
                    solvedSomething = true
                }
            }
        }

        return if (solvedSomething) {
            if (iterate)
                doSolveIterations(gridModel, iterate, allowGuess)
            else
                gridModel
        } else {
            if (isSolved(gridModel)) {
                Log.d(TAG, "Solved: Returning solution")
                gridModel
            } else if (isUnsolvable(gridModel)) {
                Log.d(TAG, "Can't Solve: Returning null")
                null
            } else if (allowGuess) {
                Log.d(TAG, "Can't Solve, start guessing")
                doGuess(gridModel)
            } else {
                Log.d(TAG, "Not allowing Guesses, return what we have so far")
                gridModel
            }
        }
    }

    private fun doGuess(gridModel: GridModel): GridModel? {
        for (i in 2..9) {
            gridModel.grid.forEachIndexed { gridIndex, subGrid ->
                subGrid.items.forEachIndexed { itemIndex, item ->
                    if (item.options.size == i) {

                        for (option in item.options) {
                            Log.d(TAG, "Start guessing for $gridIndex-$itemIndex - ${item.options.toList()} - $option")
                            val newGrid = GridModel.copy(gridModel)
                            val newItem = newGrid.grid[gridIndex].items[itemIndex]
                            newItem.value.value = option.toString()
                            newItem.options.clear()
                            newItem.options.add(option)

                            val solution = doSolveIterations(
                                newGrid,
                                iterate = true,
                                allowGuess = true
                            )
                            if (solution != null && isSolved(solution)) {
                                return solution
                            }
                            Log.d(TAG, "Bad guess, try something else")
                        }

                        Log.d(TAG, "Stuck")
                        return gridModel
                    }
                }
            }
        }

        Log.d(TAG, "Out of Options")
        return gridModel
    }

    private fun doFilterAll(gridModel: GridModel, clear: Boolean = false, deep: Boolean = false) {
        gridModel.grid.forEach { subGrid ->
            subGrid.items.forEach { item ->
                if (clear) {
                    val solution = item.solution
                    if (solution != null) {
                        item.options.clear()
                        item.options.add(solution)
                        return@forEach
                    } else {
                        item.options.clear()
                        item.options.addAll(ALL_OPTIONS)
                    }
                }
                filterForItem(gridModel, subGrid.position, item.position, item.options)
                if (deep) {
                    deepFilterForItem(gridModel, subGrid.position, item.position, item.options)
                }
            }
        }
    }

    private fun filterForItem(gridModel: GridModel, gridP: Int, itemP: Int, options: MutableList<Int>) {
        filterByRow(gridModel, gridP, itemP, options)
        filterByCol(gridModel, gridP, itemP, options)
        filterByGrid(gridModel, gridP, itemP, options)
    }

    private fun filterByRow(gridModel: GridModel, gridP: Int, itemP: Int, options: MutableList<Int>) {
        val itemRow = ROWS.find { it.contains(itemP) } ?: emptyArray()
        val gridRow = ROWS.find { it.contains(gridP) } ?: emptyArray()

        gridRow.forEach { gridI ->
            itemRow.forEach { itemI ->
                if (gridI == gridP && itemI == itemP) return@forEach

                val value = gridModel.grid[gridI].items[itemI].value.value.toIntOrNull()
                options.remove(value)
            }
        }
    }

    private fun filterByCol(gridModel: GridModel, gridP: Int, itemP: Int, options: MutableList<Int>) {
        val itemCol = COLS.find { it.contains(itemP) } ?: emptyArray()
        val gridCol = COLS.find { it.contains(gridP) } ?: emptyArray()

        gridCol.forEach { gridI ->
            itemCol.forEach { itemI ->
                if (gridI == gridP && itemI == itemP) return@forEach

                val value = gridModel.grid[gridI].items[itemI].value.value.toIntOrNull()
                options.remove(value)
            }
        }
    }

    private fun filterByGrid(gridModel: GridModel, gridP: Int, itemP: Int, options: MutableList<Int>) {
        gridModel.grid[gridP].items.forEach { item ->
            if (item.position == itemP) return@forEach

            val value = gridModel.grid[gridP].items[item.position].value.value.toIntOrNull()
            options.remove(value)
        }
    }

    private fun deepFilterForItem(gridModel: GridModel, gridP: Int, itemP: Int, options: MutableList<Int>) {
        deepRowFilter(gridModel, gridP, itemP, options)
        deepColFilter(gridModel, gridP, itemP, options)
        deepGridFilter(gridModel, gridP, itemP, options)
    }

    private fun deepRowFilter(gridModel: GridModel, gridP: Int, itemP: Int, options: MutableList<Int>) {
        filterByRow(gridModel, gridP, itemP, options)

        val itemRow = ROWS.find { it.contains(itemP) } ?: emptyArray()
        val gridRow = ROWS.find { it.contains(gridP) } ?: emptyArray()
        var valueHit: Int? = null

        for (value in options) {
            var found = false
            for (gridI in gridRow) {
                for (itemI in itemRow) {
                    if (gridI == gridP && itemI == itemP) continue

                    if (gridModel.grid[gridI].items[itemI].options.contains(value)) {
                        found = true
                        break
                    }
                }
                if (found) {
                    break
                }
            }
            if (!found) {
                valueHit = value
                break
            }
        }

        if (valueHit != null) {
            options.clear()
            options.add(valueHit)
        }
    }

    private fun deepColFilter(gridModel: GridModel, gridP: Int, itemP: Int, options: MutableList<Int>) {
        filterByRow(gridModel, gridP, itemP, options)

        val itemCol = COLS.find { it.contains(itemP) } ?: emptyArray()
        val gridCol = COLS.find { it.contains(gridP) } ?: emptyArray()
        var valueHit: Int? = null

        for (value in options) {
            var found = false
            for (gridI in gridCol) {
                for (itemI in itemCol) {
                    if (gridI == gridP && itemI == itemP) continue

                    if (gridModel.grid[gridI].items[itemI].options.contains(value)) {
                        found = true
                        break
                    }
                }
                if (found) {
                    break
                }
            }
            if (!found) {
                valueHit = value
                break
            }
        }

        if (valueHit != null) {
            options.clear()
            options.add(valueHit)
        }
    }

    private fun deepGridFilter(gridModel: GridModel, gridP: Int, itemP: Int, options: MutableList<Int>) {
        var valueHit: Int? = null
        for (value in options) {
            var found = false
            for (item in gridModel.grid[gridP].items) {
                if (item.position == itemP) continue

                if (gridModel.grid[gridP].items[item.position].options.contains(value)) {
                    found = true
                    break
                }
            }
            if (!found) {
                valueHit = value
                break
            }
        }
        if (valueHit != null) {
            options.clear()
            options.add(valueHit)
        }
    }
}