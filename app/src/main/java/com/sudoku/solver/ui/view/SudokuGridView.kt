package com.sudoku.solver.ui.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
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
import com.sudoku.solver.ui.theme.GreyLight
import com.sudoku.solver.ui.theme.SudokuSolverTheme
import com.sudoku.solver.ui.viewmodel.SudokuGridViewModel
import org.koin.core.component.get

@Composable
fun SudokuGridView(gridViewModel: SudokuGridViewModel = viewModel()) {
    val grid by gridViewModel.getGridModel()
    val hints by gridViewModel.showHints()

    SudokuPlayContainer(
        grid,
        hints,
        valueChanged = { value, position, gridPosition ->
            gridViewModel.updateValue(value, position, gridPosition)
        },
        onHintsClicked = {
            gridViewModel.toggleHints()
        },
        solveClicked = {
            gridViewModel.solve()
        },
        clearClicked = {
            gridViewModel.clear()
        })
}

@Composable
private fun SudokuPlayContainer(
    grid: GridModel,
    hints: Boolean,
    valueChanged: ((value: String, position: Int, grid: Int) -> Unit)? = null,
    onHintsClicked:(() -> Unit)? = null,
    solveClicked:(() -> Unit)? = null,
    clearClicked: (() -> Unit)? = null
) {
    Column(

    ) {
        Box(
            Modifier.weight(1f)
        ) {
            SudokuGridContainer(grid, hints, valueChanged = { value, position, gridPosition ->
                Log.d("SudokuGridView", "Updating $gridPosition-$position = $value")
                valueChanged?.invoke(value, position, gridPosition)
            })
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth(1f)
        ) {
            Button(onClick = {
                onHintsClicked?.invoke()
            }) {
                Text("Hints: ${if (hints) "On" else "Off"}")
            }

            Button(onClick = {
                solveClicked?.invoke()
            }) {
                Text("Solve")
            }

            Button(onClick = {
                clearClicked?.invoke()
            }) {
                Text("Clear")
            }
        }
    }
}

@Composable
private fun SudokuGridContainer(
    gridModel: GridModel,
    hints: Boolean = false,
    valueChanged: ((value: String, position: Int, grid: Int) -> Unit)? = null
) {
    Column(
    ) {
        Row(Modifier.weight(1f)) {
            SudokuSubGrid(
                gridModel.grid[TOP_LEFT],
                hints = hints,
                modifier = Modifier.weight(1f),
                valueChanged = { value, position ->
                    valueChanged?.invoke(value, position, TOP_LEFT)
                }
            )
            SudokuSubGrid(
                gridModel.grid[TOP_CENTER],
                hints = hints,
                modifier = Modifier
                    .weight(1f)
                    .background(GreyLight),
                valueChanged = { value, position ->
                    valueChanged?.invoke(value, position, TOP_CENTER)
                }
            )
            SudokuSubGrid(
                gridModel.grid[TOP_RIGHT],
                hints = hints,
                modifier = Modifier.weight(1f),
                valueChanged = { value, position ->
                    valueChanged?.invoke(value, position, TOP_RIGHT)
                }
            )
        }
        Row(Modifier.weight(1f)) {
            SudokuSubGrid(
                gridModel.grid[CENTER_LEFT],
                hints = hints,
                modifier = Modifier
                    .weight(1f)
                    .background(GreyLight),
                valueChanged = { value, position ->
                    valueChanged?.invoke(value, position, CENTER_LEFT)
                }
            )
            SudokuSubGrid(
                gridModel.grid[CENTER_CENTER],
                hints = hints,
                modifier = Modifier.weight(1f),
                valueChanged = { value, position ->
                    valueChanged?.invoke(value, position, CENTER_CENTER)
                }
            )
            SudokuSubGrid(
                gridModel.grid[CENTER_RIGHT],
                hints = hints,
                modifier = Modifier
                    .weight(1f)
                    .background(GreyLight),
                valueChanged = { value, position ->
                    valueChanged?.invoke(value, position, CENTER_RIGHT)
                }
            )
        }
        Row(Modifier.weight(1f)) {
            SudokuSubGrid(
                gridModel.grid[BOTTOM_LEFT],
                hints = hints,
                modifier = Modifier.weight(1f),
                valueChanged = { value, position ->
                    valueChanged?.invoke(value, position, BOTTOM_LEFT)
                }
            )
            SudokuSubGrid(
                gridModel.grid[BOTTOM_CENTER],
                hints = hints,
                modifier = Modifier
                    .weight(1f)
                    .background(GreyLight),
                valueChanged = { value, position ->
                    valueChanged?.invoke(value, position, BOTTOM_CENTER)
                }
            )
            SudokuSubGrid(
                gridModel.grid[BOTTOM_RIGHT],
                hints = hints,
                modifier = Modifier.weight(1f),
                valueChanged = { value, position ->
                    valueChanged?.invoke(value, position, BOTTOM_RIGHT)
                }
            )
        }
    }
}

@Composable
private fun SudokuSubGrid(
    subGrid: GridModel.SubGrid,
    modifier: Modifier = Modifier,
    hints: Boolean = false,
    valueChanged: ((value: String, position: Int) -> Unit)? = null
) {
    Column(
        modifier.border(2.dp, Color.Black)
    ) {
        Row(Modifier.weight(1f)) {
            SudokuGridItem(
                subGrid.items[TOP_LEFT],
                Modifier.weight(1f),
                hints,
                valueChanged = { valueChanged?.invoke(it, TOP_LEFT) })
            SudokuGridItem(
                subGrid.items[TOP_CENTER],
                Modifier.weight(1f),
                hints,
                valueChanged = { valueChanged?.invoke(it, TOP_CENTER) })
            SudokuGridItem(
                subGrid.items[TOP_RIGHT],
                Modifier.weight(1f),
                hints,
                valueChanged = { valueChanged?.invoke(it, TOP_RIGHT) })
        }
        Row(Modifier.weight(1f)) {
            SudokuGridItem(
                subGrid.items[CENTER_LEFT],
                Modifier.weight(1f),
                hints,
                valueChanged = { valueChanged?.invoke(it, CENTER_LEFT) })
            SudokuGridItem(
                subGrid.items[CENTER_CENTER],
                Modifier.weight(1f),
                hints,
                valueChanged = { valueChanged?.invoke(it, CENTER_CENTER) })
            SudokuGridItem(
                subGrid.items[CENTER_RIGHT],
                Modifier.weight(1f),
                hints,
                valueChanged = { valueChanged?.invoke(it, CENTER_RIGHT) })
        }
        Row(Modifier.weight(1f)) {
            SudokuGridItem(
                subGrid.items[BOTTOM_LEFT],
                Modifier.weight(1f),
                hints,
                valueChanged = { valueChanged?.invoke(it, BOTTOM_LEFT) })
            SudokuGridItem(
                subGrid.items[BOTTOM_CENTER],
                Modifier.weight(1f),
                hints,
                valueChanged = { valueChanged?.invoke(it, BOTTOM_CENTER) })
            SudokuGridItem(
                subGrid.items[BOTTOM_RIGHT],
                Modifier.weight(1f),
                hints,
                valueChanged = { valueChanged?.invoke(it, BOTTOM_RIGHT) })
        }
    }
}

@Composable
private fun SudokuGridItem(
    gridItem: GridModel.GridItem,
    modifier: Modifier = Modifier,
    hints: Boolean = false,
    valueChanged: ((value: String) -> Unit)? = null
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxHeight(1f)
            .border(Dp.Hairline, Color.Black)
    ) {
        val (valueText, helpRow) = createRefs()
        val value by gridItem.value

        if (gridItem.solution != null) {
            Text(
                text = value,
                style = MaterialTheme.typography.h3.copy(
                    textAlign = TextAlign.Center,
                    color = Color.Black
                ),
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(1f)
                    .constrainAs(valueText) {
                        centerTo(parent)
                    }
            )
        } else {
            BasicTextField(
                value = value,
                onValueChange = { text ->
                    val asInt = text.toIntOrNull()
                    asInt?.coerceIn(1..9)
                    val asString = asInt?.toString() ?: ""
                    valueChanged?.invoke(asString)
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
                    .constrainAs(valueText) {
                        centerTo(parent)
                    }
            )
        }

        if (hints && gridItem.solution == null) {
            Row(modifier = Modifier
                .clipToBounds()
                .constrainAs(helpRow) {
                    top.linkTo(parent.top)
                    centerHorizontallyTo(parent)
                }) {
                gridItem.options.forEach { option ->
                    Text(
                        text = option.toString(),
                        style = MaterialTheme.typography.subtitle2.copy(color = GreyLight)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SudokuGridViewPreview() {
    SudokuSolverTheme {
        SudokuPlayContainer(SampleGridModel(), false)
    }
}