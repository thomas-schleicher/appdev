package ex1

/**
 * Represents a workshop a vehicle is associated with
 *
 * @property name
 * @property country
 * @property postcode
 * @property city
 * @property street
 * @property phone
 */
class Workshop(
    private var name: String,
    private var country: String,
    private var postcode: Int,
    private var city: String,
    private var street: String,
    private var phone: String
) : Info {
    /**
     * Outputs all available information of workshop on the screen.
     */
    override fun printInfo() {
        println("Workshop Info:")
        println("\tName: $name")
        println("\tCountry: $country")
        println("\tPostcode: $postcode")
        println("\tCity: $city")
        println("\tStreet: $street")
        println("\tPhone Number: $phone")
    }

    /**
     * Returns the postal code of a given workshop
     *
     * @return corresponding postal code
     */
    fun getPostalCode(): Int {
        return postcode
    }
}
