package com.sudoku.solver.data

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

open class GridModel {

    companion object {
        const val TOP_LEFT = 0
        const val TOP_CENTER = 1
        const val TOP_RIGHT = 2
        const val CENTER_LEFT = 3
        const val CENTER_CENTER = 4
        const val CENTER_RIGHT = 5
        const val BOTTOM_LEFT = 6
        const val BOTTOM_CENTER = 7
        const val BOTTOM_RIGHT = 8

        val ALL_OPTIONS = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9)

        val TOP_ROW =    arrayOf(0, 1, 2)
        val CENTER_ROW = arrayOf(3, 4, 5)
        val BOTTOM_ROW = arrayOf(6, 7, 8)
        val ROWS = arrayOf(TOP_ROW, CENTER_ROW, BOTTOM_ROW)

        val LEFT_COL =   arrayOf(0, 3, 6)
        val CENTER_COL = arrayOf(1, 4, 7)
        val RIGHT_COL =  arrayOf(2, 5, 8)
        val COLS = arrayOf(LEFT_COL, CENTER_COL, RIGHT_COL)
    }

    class GridItem(val position: Int) {
        var solution: Int? = null
            set(value) {
                field = value
                this.value.value = value.toString()
            }

        var value = mutableStateOf("")
        val options = mutableStateListOf(*ALL_OPTIONS)
    }

    class SubGrid(val position: Int) {
        val items = arrayOf(
            GridItem(0),
            GridItem(1),
            GridItem(2),
            GridItem(3),
            GridItem(4),
            GridItem(5),
            GridItem(6),
            GridItem(7),
            GridItem(8)
        )
    }

    val grid = arrayOf(
        SubGrid(0),
        SubGrid(1),
        SubGrid(2),
        SubGrid(3),
        SubGrid(4),
        SubGrid(5),
        SubGrid(6),
        SubGrid(7),
        SubGrid(8)
    )
}

class SampleGridModel: GridModel() {
    init {
        populateSampleGrid(this)
    }
}

fun populateSampleGrid(gridModel: GridModel) {
    gridModel.apply {
        grid[GridModel.TOP_LEFT].apply {
            items[GridModel.TOP_CENTER].solution = 7
            items[GridModel.CENTER_CENTER].solution = 6
            items[GridModel.BOTTOM_LEFT].solution = 2
        }
        grid[GridModel.TOP_CENTER].apply {
            items[GridModel.TOP_CENTER].solution = 2
            items[GridModel.BOTTOM_LEFT].solution = 8
        }
        grid[GridModel.TOP_RIGHT].apply {
            items[GridModel.TOP_CENTER].solution = 4
            items[GridModel.TOP_RIGHT].solution = 6
            items[GridModel.CENTER_LEFT].solution = 8
            items[GridModel.CENTER_CENTER].solution = 9
            items[GridModel.BOTTOM_LEFT].solution = 7
            items[GridModel.BOTTOM_CENTER].solution = 1
            items[GridModel.BOTTOM_RIGHT].solution = 5
        }

        grid[GridModel.CENTER_LEFT].apply {
            items[GridModel.TOP_CENTER].solution = 8
            items[GridModel.TOP_RIGHT].solution = 4
            items[GridModel.CENTER_LEFT].solution = 7
            items[GridModel.CENTER_CENTER].solution = 1
        }
        grid[GridModel.CENTER_CENTER].apply {
            items[GridModel.TOP_CENTER].solution = 9
            items[GridModel.TOP_RIGHT].solution = 7
            items[GridModel.BOTTOM_LEFT].solution = 1
            items[GridModel.BOTTOM_CENTER].solution = 3
        }
        grid[GridModel.CENTER_RIGHT].apply {
            items[GridModel.CENTER_CENTER].solution = 5
            items[GridModel.CENTER_RIGHT].solution = 9
            items[GridModel.BOTTOM_LEFT].solution = 4
            items[GridModel.BOTTOM_CENTER].solution = 8
        }

        grid[GridModel.BOTTOM_LEFT].apply {
            items[GridModel.TOP_LEFT].solution = 6
            items[GridModel.TOP_CENTER].solution = 9
            items[GridModel.TOP_RIGHT].solution = 7
            items[GridModel.CENTER_CENTER].solution = 5
            items[GridModel.CENTER_RIGHT].solution = 8
            items[GridModel.BOTTOM_LEFT].solution = 4
            items[GridModel.BOTTOM_CENTER].solution = 3
        }
        grid[GridModel.BOTTOM_CENTER].apply {
            items[GridModel.TOP_RIGHT].solution = 2
            items[GridModel.BOTTOM_CENTER].solution = 8
        }
        grid[GridModel.BOTTOM_RIGHT].apply {
            items[GridModel.TOP_RIGHT].solution = 8
            items[GridModel.CENTER_CENTER].solution = 6
            items[GridModel.BOTTOM_CENTER].solution = 7
        }
    }
}