package ex1

class Brand(
    private var name: String,
    private var country: String,
    private var phone: String,
    private var email: String,
) : Info {
    override fun printInfo() {
        println("Brand Info:")
        println("\tName: $name")
        println("\tCountry: $country")
        println("\tPhone: $phone")
        println("\tEmail: $email")
    }
}
