package ex1

/**
 * Represents a brand of a vehicle.
 *
 * @property name
 * @property country
 * @property phone
 * @property email
 */
class Brand(
    private var name: String,
    private var country: String,
    private var phone: String,
    private var email: String,
) : Info {
    /**
     * Outputs all available information of brand on the screen.
     */
    override fun printInfo() {
        println("Brand Info:")
        println("\tName: $name")
        println("\tCountry: $country")
        println("\tPhone: $phone")
        println("\tEmail: $email")
    }
}
