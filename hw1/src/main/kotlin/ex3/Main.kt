package ex3

fun main() {
    val contacts = mutableListOf<Contact>().apply {
        add(Contact("Alice", "alice@example.com", "123-456-7890", 30))
        add(Contact("Bob", "bob@example.com", "234-567-8901", 25))
        add(Contact("Charlie", null, "345-678-9012", 28))
        add(Contact("Diana", "diana@example.com", "234-567-8302", 35))
        add(Contact("Eve", "eve@example.com", "567-890-1234", 23))
    }

    //https://emailregex.com
    val emailRegex =
        "(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])".toRegex()

    val validEmailContacts = contacts.filter { !it.email.isNullOrEmpty() }
        .filter { it.email!!.matches(emailRegex) }

    val compactContacts = validEmailContacts.filter { it.name.isNotEmpty() }
        .map { it.name + ": " + it.email }

    val averageAgeContacts = contacts.mapNotNull { it.age }
        .average()

    println(validEmailContacts)
    println(compactContacts)
    println(averageAgeContacts)

    //https://cdn-images-1.medium.com/v2/resize:fit:800/1*pLNnrvgvmG6Mdi0Yw3mdPQ.png

    contacts.forEach { contact ->
        contact.email?.let { email ->
            println("Processing: $email")
            println(email.uppercase())
        }
    }

    contacts[0].apply {
        name = "Omar"
        email = "omar@example.com"
    }

    with(contacts[0]) {
        println("$phone, $email")
        //other operations
    }

    fun findByName(name: String, contacts: List<Contact>): Contact? {
        return contacts.find { it.name.equals(name, ignoreCase = true) }
    }

    println(findByName("Omar", contacts))
}