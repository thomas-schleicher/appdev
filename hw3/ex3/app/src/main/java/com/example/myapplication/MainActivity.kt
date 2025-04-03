package com.example.myapplication

import androidx.compose.foundation.layout.fillMaxWidth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Button

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize().systemBarsPadding()
                ) {
                    UnitConverterScreen()
                }
            }
        }
    }
}

@Composable
fun SelectionDropdownMenu(
    options: List<String>,
    default: String,
    onSelect: (String)->Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var currentSelection by remember { mutableStateOf(default) }
    Box(
        modifier = Modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = currentSelection,
            modifier = Modifier.clickable { expanded = true }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onSelect(option)
                        expanded = false
                        currentSelection = option
                    }

                )
            }
        }
    }

}

@Composable
fun UnitConverter(
    lable: String,
    calculation1: (Double)->Double,
    calculation2: (Double)->Double
){
    var unit1 = lable.split(" to ")[0]
    var unit2 = lable.split(" to ")[1]
    var input1 by remember {mutableStateOf("")}
    var input2 by remember {mutableStateOf("")}
    Row(
        modifier = Modifier.fillMaxWidth()
    ){
        Column(){
            TextField(
                value = input1,
                onValueChange = {change->input1=change; input2 = ""}
            )
            Text(unit1)
        }

        Button(onClick={
            if(input1.isNotEmpty()){
                input2 = calculation1(input1.toDouble()).toString()
            }else{
                input1 = calculation2(input2.toDouble()).toString()
            }
        }){
            Text("Convert")
        }
        Column(){
            TextField(
                value = input2,
                onValueChange = {change->input2=change; input1 = ""}
            )
            Text(unit2)
        }
    }

}

@Composable
fun UnitConverterScreen(){ // main screen manager
    val conversionOptions = listOf("Meter to Inch", "Celsius to Fahrenheit")
    val conversionMethods1 = listOf<(Double)->Double>(
        {it * 39.3701},
        {(it*9/5)+32}
    )
    val conversionMethods2 = listOf<(Double)->Double>(
        {it / 39.3701},
        {(it-32)*5/9}
    )
    var conversionSelection by remember {mutableStateOf("Hi")}
    val onSelect = {value:String ->
        conversionSelection = value
    }

    Column (
        modifier = Modifier.fillMaxSize().padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        SelectionDropdownMenu(conversionOptions, conversionSelection, onSelect)
        if(conversionSelection.isNotEmpty()){
            if(conversionOptions.contains(conversionSelection)) {
                UnitConverter(conversionSelection, conversionMethods1[conversionOptions.indexOf(conversionSelection)], conversionMethods2[conversionOptions.indexOf(conversionSelection)])
            }
        }
    }
}
