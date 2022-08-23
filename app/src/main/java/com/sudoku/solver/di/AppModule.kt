package com.sudoku.solver.di

import com.sudoku.solver.services.SudokuService
import org.koin.dsl.module

val appModule = module {

    // Services
    single { SudokuService() }


}