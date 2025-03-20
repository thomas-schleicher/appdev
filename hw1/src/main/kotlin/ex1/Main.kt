package ex1

fun main(args : Array<String>) {
    val brand = createDefaultBrand()
    val workshop = createDefaultWorkshop()
    val car = createDefaultVehicle(1, "Super Audi", brand, arrayListOf(workshop))

    car.printInfo()
    car.drive(100);
    car.printInfo()

    println(car.getWorkshop(9020)?.printInfo())    // prints "kotlin.Unit" <=> void in Java
    println(car.getWorkshop(1010))                 // null as workshop with 1010 doesn't exist
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