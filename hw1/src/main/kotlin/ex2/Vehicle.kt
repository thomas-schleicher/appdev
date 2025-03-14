package ex2

import kotlinx.coroutines.delay
import kotlin.random.Random

class Vehicle(
    private var id: Int,
    private var name: String,
    private var brand: Brand,
    private var workshops: ArrayList<Workshop>,
    private var weight: Int,
    private var maxPermissableWeight: Int = 3500,
    private var speed: Double = 0.0,
    private var maxSpeed: Double
) : Info {
    fun accelerate(): Double {
        val newSpeed = speed + Random.nextDouble(10.0, 50.0)
        speed = if (newSpeed > maxSpeed) maxSpeed else newSpeed
        return speed
    }

    fun brake(): Double {
        val newSpeed = speed - Random.nextDouble(10.0, 50.0)
        speed = if (newSpeed < 0) 0.0 else newSpeed
        return speed
    }

    suspend fun drive(kilometers: Int) {
        repeat(kilometers) {
            val actions = Random.nextInt(3, 6)
            repeat(actions) {
                delay(100)
                if (Random.nextBoolean()) {
                    accelerate()
                } else {
                    brake()
                }
            }
        }
    }

    fun getWorkshop(postalCode: Int): Workshop? {
        return workshops
            .filter { workshop: Workshop -> workshop.getPostalCode() == postalCode }
            .getOrNull(0);
    }

    override fun printInfo() {
        println("Vehicle Info:")
        println("\tID: $id")
        println("\tName: $name")
        println("\tWeight: $weight kg")
        println("\tMax Permissible Weight: $maxPermissableWeight kg")
        println("\tCurrent Speed: $speed km/h")
        println("\tMax Speed: $maxSpeed km/h")
        brand.printInfo();
        for (workshop in workshops) {
            workshop.printInfo();
        }
    }
}