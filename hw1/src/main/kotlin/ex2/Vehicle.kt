package ex2

import kotlinx.coroutines.delay
import kotlin.random.Random

/**
 * Represents a vehicle.
 *
 * @property id
 * @property name
 * @property brand
 * @property workshops list of associated workshops
 * @property weight
 * @property maxPermissableWeight 3500 by default
 * @property speed 0.0 by default
 * @property maxSpeed
 */
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
    /**
     * Increases the current speed (speed) by a random value in the range 10-50, but without exceeding
     * the maximum speed of the vehicle (maxSpeed).
     *
     * @return the new speed
     */
    private fun accelerate(): Double {
        val newSpeed = speed + Random.nextDouble(10.0, 50.0)
        speed = if (newSpeed > maxSpeed) maxSpeed else newSpeed

        val roundedSpeed = Math.round(speed * 100) / 100.0
        println("\u001B[34m-->\u001B[0m $name accelerates by $roundedSpeed km/h")

        return speed
    }

    /**
     * Reduces the current speed by a random value in the range 10-50, whereby the speed must not be less
     * than 0.
     *
     * @return the new speed
     */
    private fun brake(): Double {
        val newSpeed = speed - Random.nextDouble(10.0, 50.0)
        speed = if (newSpeed < 0) 0.0 else newSpeed

        val roundedSpeed = Math.round(speed * 100) / 100.0
        println("\u001B[31m<--\u001B[0m $name brakes by $roundedSpeed km/h")

        return speed
    }

    /**
     * Simulates driving a vehicle. The accelerate() or brake() method should randomly be called 3-5 times
     * for each kilometer traveled.
     *
     * @param kilometers the length the car should drive
     */
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

    /**
     * Returns an authorized workshop with the given zip code.
     *
     * @param postalCode
     */
    fun getWorkshop(postalCode: Int): Workshop? {
        return workshops
            .filter { workshop: Workshop -> workshop.getPostalCode() == postalCode }
            .getOrNull(0);
    }

    /**
     * Outputs all available information of vehicle on the screen.
     */
    override fun printInfo() {
        println("Vehicle Info:")
        println("\tID: $id")
        println("\tName: $name")
        println("\tWeight: $weight kg")
        println("\tMax Permissible Weight: $maxPermissableWeight kg")
        println("\tCurrent Speed: $speed km/h")
        println("\tMax Speed: $maxSpeed km/h")

        brand.printInfo()
        printWorkshopInfo()
    }

    /**
     * Outputs all available information of the associated workshops on the screen.
     */
    private fun printWorkshopInfo() {
        for (workshop in workshops) {
            workshop.printInfo();
        }
    }

    /**
     * Returns the name of a given vehicle
     *
     * @return corresponding name
     */
    fun getName(): String {
        return name
    }
}