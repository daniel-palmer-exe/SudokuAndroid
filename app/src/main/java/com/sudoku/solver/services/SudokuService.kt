package com.sudoku.solver.services

import com.sudoku.solver.data.GridModel
import com.sudoku.solver.data.GridModel.Companion.ALL_OPTIONS
import com.sudoku.solver.data.GridModel.Companion.COLS
import com.sudoku.solver.data.GridModel.Companion.ROWS

class SudokuService {

    fun filterAllOptions(gridModel: GridModel) {
        gridModel.grid.forEach { subGrid ->
            subGrid.items.forEach { item ->

                val options = item.options

                if (item.solution != null) {
                    options.clear()
                    return@forEach
                } else {
                    options.clear()
                    options.addAll(ALL_OPTIONS)
                }
                filterForItem(gridModel, subGrid.position, item.position, options)
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

}