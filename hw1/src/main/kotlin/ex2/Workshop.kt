package ex2

class Workshop(
    private var name: String,
    private var country: String,
    private var postcode: Int,
    private var city: String,
    private var street: String,
    private var phone: String
) : Info {
    override fun printInfo() {
        println("Workshop Info:")
        println("\tName: $name")
        println("\tCountry: $country")
        println("\tPostcode: $postcode")
        println("\tCity: $city")
        println("\tStreet: $street")
        println("\tPhone Number: $phone")
    }

    fun getPostalCode(): Int {
        return postcode
    }
}
