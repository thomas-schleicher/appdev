package ex2

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(args : Array<String>) {
    val brand = createDefaultBrand()
    val workshop = createDefaultWorkshop()
    val car1 = createDefaultVehicle(1, "Super Audi 1", brand, arrayListOf(workshop))
    val car2 = createDefaultVehicle(2, "Super Audi 2", brand, arrayListOf(workshop))

    car1.printInfo()
    car2.printInfo()

    // synchronized blocking coroutine
    // waits until all started coroutines are finished
    runBlocking {
        launch {
            println("\n// ==================== //")
            println("// Starting the race!!! //")
            println("// ==================== //\n")
            race(car1, car2)
        }
    }
}

/**
 *  Starts a race between two cars. Both vehicles start at the same time, drive 10 km
 *  and send a message when they finish
 *
 *  @param vehicle1
 *  @param vehicle2
 */
suspend fun race(vehicle1: Vehicle, vehicle2: Vehicle) = coroutineScope {

    val v1 =  launch { vehicle1.drive(10) }     // launch coroutine for first car
    val v2 =  launch { vehicle2.drive(10) }     // launch coroutine for second car

    v1.invokeOnCompletion { println("\u001B[32m${vehicle1.getName()} finished the race!\u001B[0m") }
    v2.invokeOnCompletion { println("\u001B[32m${vehicle2.getName()} finished the race!\u001B[0m") }
}

/**
 * Creates a default brand for testing purposes
 *
 * @return brand instance
 */
fun createDefaultBrand(): Brand {
    return Brand("Audi", "Germany", "918231698", "audi@audi.com")
}

/**
 * Creates a default workshop for testing purposes
 *
 * @return workshop instance
 */
fun createDefaultWorkshop(): Workshop {
    return Workshop("Top Workshop 1", "Austria", 9020, "Klagenfurt", "Somewhere in Klagenfurt", "0931283019")
}

/**
 * Creates a default vehicle for testing purposes
 *
 * @param id
 * @param name
 * @param brand
 * @param workshops a list of associated workshops
 */
fun createDefaultVehicle(id: Int, name: String, brand: Brand, workshops: ArrayList<Workshop>): Vehicle {
    return Vehicle(id, name, brand, workshops, 1900, 3500, 0.0, 270.0)
}