package com.sudoku.solver.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sudoku.solver.data.GridModel
import com.sudoku.solver.data.GridModel.Companion.BOTTOM_CENTER
import com.sudoku.solver.data.GridModel.Companion.BOTTOM_LEFT
import com.sudoku.solver.data.GridModel.Companion.BOTTOM_RIGHT
import com.sudoku.solver.data.GridModel.Companion.CENTER_CENTER
import com.sudoku.solver.data.GridModel.Companion.CENTER_LEFT
import com.sudoku.solver.data.GridModel.Companion.CENTER_RIGHT
import com.sudoku.solver.data.GridModel.Companion.TOP_CENTER
import com.sudoku.solver.data.GridModel.Companion.TOP_LEFT
import com.sudoku.solver.data.GridModel.Companion.TOP_RIGHT
import com.sudoku.solver.data.SampleGridModel
import com.sudoku.solver.ui.theme.SudokuSolverTheme
import com.sudoku.solver.ui.viewmodel.SudokuGridViewModel

@Composable
fun SudokuGridView(gridViewModel: SudokuGridViewModel = viewModel()) {
    val grid by gridViewModel.getGridModel()
    SudokuGridContainer(grid)
}

@Composable
private fun SudokuGridContainer(gridModel: GridModel) {
    Column(
    ) {
        Row(Modifier.weight(1f)) {
            SudokuSubGrid(gridModel.grid[TOP_LEFT],
                Modifier.weight(1f))
            SudokuSubGrid(gridModel.grid[TOP_CENTER],
                Modifier
                    .weight(1f)
                    .background(Color.LightGray))
            SudokuSubGrid(gridModel.grid[TOP_RIGHT],
                Modifier.weight(1f))
        }
        Row(Modifier.weight(1f)) {
            SudokuSubGrid(gridModel.grid[CENTER_LEFT],
                Modifier
                    .weight(1f)
                    .background(Color.LightGray))
            SudokuSubGrid(gridModel.grid[CENTER_CENTER],
                Modifier.weight(1f))
            SudokuSubGrid(gridModel.grid[CENTER_RIGHT],
                Modifier
                    .weight(1f)
                    .background(Color.LightGray))
        }
        Row(Modifier.weight(1f)) {
            SudokuSubGrid(gridModel.grid[BOTTOM_LEFT],
                Modifier.weight(1f))
            SudokuSubGrid(gridModel.grid[BOTTOM_CENTER],
                Modifier
                    .weight(1f)
                    .background(Color.LightGray))
            SudokuSubGrid(gridModel.grid[BOTTOM_RIGHT],
                Modifier.weight(1f))
        }
    }
}

@Composable
private fun SudokuSubGrid(subGrid: GridModel.SubGrid, modifier: Modifier = Modifier) {
    Column(
        modifier.border(2.dp, Color.Black)
    ) {
        Row(Modifier.weight(1f)) {
            SudokuGridItem(subGrid.items[TOP_LEFT], Modifier.weight(1f))
            SudokuGridItem(subGrid.items[TOP_CENTER], Modifier.weight(1f))
            SudokuGridItem(subGrid.items[TOP_RIGHT], Modifier.weight(1f))
        }
        Row(Modifier.weight(1f)) {
            SudokuGridItem(subGrid.items[CENTER_LEFT], Modifier.weight(1f))
            SudokuGridItem(subGrid.items[CENTER_CENTER], Modifier.weight(1f))
            SudokuGridItem(subGrid.items[CENTER_RIGHT], Modifier.weight(1f))
        }
        Row(Modifier.weight(1f)) {
            SudokuGridItem(subGrid.items[BOTTOM_LEFT], Modifier.weight(1f))
            SudokuGridItem(subGrid.items[BOTTOM_CENTER], Modifier.weight(1f))
            SudokuGridItem(subGrid.items[BOTTOM_RIGHT], Modifier.weight(1f))
        }
    }
}

@Composable
private fun SudokuGridItem(gridItem: GridModel.GridItem, modifier: Modifier = Modifier) {
    Box(
        modifier
            .border(1.dp, Color.Black)
            .fillMaxHeight(1f),
        contentAlignment = Alignment.Center
    ) {
        if (gridItem.solution != null) {
            Text(
                text = gridItem.value,
                style = MaterialTheme.typography.h3.copy(
                    textAlign = TextAlign.Center,
                    color = Color.Black
                ),
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(1f)
            )
        } else {
            var value by remember {mutableStateOf(gridItem.value) }
            BasicTextField(
                value = value,
                onValueChange = { text ->
                    val asInt = text.toIntOrNull()
                    asInt?.coerceIn(1..9)
                    gridItem.value = asInt?.toString() ?: ""
                    value = asInt?.toString() ?: ""
                },
                maxLines = 1,
                textStyle = MaterialTheme.typography.h3.copy(
                    textAlign = TextAlign.Center,
                    color = Color.Blue
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(1f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SudokuGridViewPreview() {
    SudokuSolverTheme {
        SudokuGridContainer(SampleGridModel())
    }
}