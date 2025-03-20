package ex3

/**
 * Represents a contact
 *
 * @property name
 * @property email optional
 * @property phone optional
 * @property age optional
 */
// mainly used to story data in Kotlin, provides functionality like toString(), equals(), hashCode(), copy(), componentN()
data class Contact(
    var name: String,
    var email: String?,
    var phone: String?,
    var age: Int?
)