package ex1

fun main(args : Array<String>) {
    val brand = Brand("Audi", "Germany", "918231698", "audi@audi.com");
    val workshop1 = Workshop("Top Workshop 1", "Austria", 9020, "Klagenfurt", "Somewhere in Klagenfurt", "0931283019")
    val car1 = Vehicle(1, "Super Audi", brand, arrayListOf(workshop1), 1900, 3500, 0.0, 270.0)
    car1.printInfo()
    car1.drive(100);
    car1.printInfo()
    println(car1.getWorkshop(9020)?.printInfo())
    println(car1.getWorkshop(1010))
}