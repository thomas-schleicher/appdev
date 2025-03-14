package ex2

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main(args : Array<String>) {
    val brand = Brand("Audi", "Germany", "918231698", "audi@audi.com")
    val workshop1 = Workshop("Top Workshop 1", "Austria", 9020, "Klagenfurt", "Somewhere in Klagenfurt", "0931283019")
    val car1 = Vehicle(1, "Super Audi", brand, arrayListOf(workshop1), 1900, 3500, 0.0, 270.0)
    val car2 = Vehicle(1, "Super Audi2", brand, arrayListOf(workshop1), 1900, 3500, 0.0, 270.0)
    car1.printInfo()
    car2.printInfo()
    runBlocking {
        launch {
            println("Starting the race!!!")
            race(car1, car2)
        }
    }
}

suspend fun race(vehicle1: Vehicle, vehicle2: Vehicle) = coroutineScope {

    val v1 =  launch { vehicle1.drive(10) }
    val v2 =  launch { vehicle2.drive(10) }

    v1.invokeOnCompletion { println("Vehicle 1 finished the race!") }
    v2.invokeOnCompletion { println("Vehicle 2 finished the race!") }
}