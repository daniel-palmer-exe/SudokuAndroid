package com.sudoku.solver.data

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
    }

    class GridItem(val position: Int) {
        var solution: Int? = null
            set(value) {
                field = value
                this.value = value.toString()
            }

        var value: String = ""
        val options = arrayListOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9)
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
        grid[TOP_LEFT].apply {
            items[TOP_CENTER].solution = 7
            items[CENTER_CENTER].solution = 6
            items[BOTTOM_LEFT].solution = 2
        }
        grid[TOP_CENTER].apply {
            items[TOP_CENTER].solution = 2
            items[BOTTOM_LEFT].solution = 8
        }
        grid[TOP_RIGHT].apply {
            items[TOP_CENTER].solution = 4
            items[TOP_RIGHT].solution = 6
            items[CENTER_LEFT].solution = 8
            items[CENTER_CENTER].solution = 9
            items[BOTTOM_LEFT].solution = 7
            items[BOTTOM_CENTER].solution = 1
            items[BOTTOM_RIGHT].solution = 5
        }

        grid[CENTER_LEFT].apply {
            items[TOP_CENTER].solution = 8
            items[TOP_RIGHT].solution = 4
            items[CENTER_LEFT].solution = 7
            items[CENTER_CENTER].solution = 1
        }
        grid[CENTER_CENTER].apply {
            items[TOP_CENTER].solution = 9
            items[TOP_RIGHT].solution = 7
            items[BOTTOM_LEFT].solution = 1
            items[BOTTOM_CENTER].solution = 3
        }
        grid[CENTER_RIGHT].apply {
            items[CENTER_CENTER].solution = 5
            items[CENTER_RIGHT].solution = 9
            items[BOTTOM_LEFT].solution = 4
            items[BOTTOM_CENTER].solution = 8
        }

        grid[BOTTOM_LEFT].apply {
            items[TOP_LEFT].solution = 6
            items[TOP_CENTER].solution = 9
            items[TOP_RIGHT].solution = 7
            items[CENTER_CENTER].solution = 5
            items[CENTER_RIGHT].solution = 8
            items[BOTTOM_LEFT].solution = 4
            items[BOTTOM_CENTER].solution = 3
        }
        grid[BOTTOM_CENTER].apply {
            items[TOP_RIGHT].solution = 2
            items[BOTTOM_CENTER].solution = 8
        }
        grid[BOTTOM_RIGHT].apply {
            items[TOP_RIGHT].solution = 8
            items[CENTER_CENTER].solution = 6
            items[BOTTOM_CENTER].solution = 7
        }
    }

}