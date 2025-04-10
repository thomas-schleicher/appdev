package aau.ss25.apde.complocalsvsexplparampassing

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.TextStyle

// global variables, which can be used anywhere in the program
// compositionLocalOf -> creates a "context key", some sort of a global variable
val LocalTextAlpha = compositionLocalOf { 1.0f } // global variable for text transparency
val LocalTextStyle = compositionLocalOf { TextStyle.Default } // global variable for text style
