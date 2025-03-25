package ex3

fun main() {
    // Use scope functions such as apply or run to configure the contacts im- mediately after their creation.
    val contacts = mutableListOf<Contact>().apply {
        add(Contact("Alice", "alice@example.com", "123-456-7890", 30))
        add(Contact("Bob", "bob@example.com", "234-567-8901", 25))
        add(Contact("Charlie", null, "345-678-9012", 28))
        add(Contact("Diana", "diana@example.com", "234-567-8302", 35))
        add(Contact("Eve", "eve@example.com", "567-890-1234", 23))
    }

    // https://emailregex.com
    // Regular expression to validate email addresses
    val emailRegex =
        "(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])".toRegex()

    // Filtering: Create a function that filters all contacts with a valid email address.
    val validEmailContacts = contacts.filter { !it.email.isNullOrEmpty() }
        .filter { it.email!!.matches(emailRegex) }

    // Mapping: Generate a new list (e.g., a list of strings containing only the name and email) from the existing contacts list.
    val compactContacts = validEmailContacts.filter { it.name.isNotEmpty() }
        .map { it.name + ": " + it.email }

    // Accumulation: Calculate the average age of the contacts.
    val averageAgeContacts = contacts.mapNotNull { it.age }
        .average()

    println("Valid email contacts: $validEmailContacts")
    println("Compacted contacts: $compactContacts")
    println("Average age contacts: $averageAgeContacts years")
    println("================================")

    //https://cdn-images-1.medium.com/v2/resize:fit:800/1*pLNnrvgvmG6Mdi0Yw3mdPQ.png

    // Use let to process a contact only if its email address is not null.
    contacts.forEach { contact ->
        contact.email?.let { email -> // ? -> only call let if email not null
            println("Processing: $email")
            println(email.uppercase())
        }
    }

    println("================================")

    // Use apply to set properties of a contact within a configuration block.
    contacts[0].apply {
        name = "Omar"
        email = "omar@example.com"
    }

    // Use with to perform multiple operations on a contact without repeatedly referencing the object.
    with(contacts[0]) {
        println("$phone, $email")
        //other operations
    }

    println("================================")

    // Implement a search function (e.g., findByName) that uses a lambda function to search for a contact by name.
    fun findByName(name: String, contacts: List<Contact>): Contact? {
        return contacts.find { it.name.equals(name, ignoreCase = true) }
    }

    println(findByName("Omar", contacts))

    // Tabular Overview of Scope Functions
    /*
    | Function | Context (Receiver) | Return Value | Typical Application |
    |----------|-------------------|--------------|----------------------|
    | apply    | this (object)      | Object itself | Configure or modify an object |
    | run      | this (object)      | Lambda result | Compute a value based on an object |
    | let      | it (object)        | Lambda result | Execute code only if the object is non-null |
    | also     | it (object)        | Object itself | Perform side-effects like logging |
    | with     | this (object)      | Lambda result | Perform multiple operations on an object |
    */
}