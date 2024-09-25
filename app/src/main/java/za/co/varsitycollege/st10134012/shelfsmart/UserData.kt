package za.co.varsitycollege.st10134012.shelfsmart

data class UserData(
    val id: String? = null,
    val email: String? = null,
    val passwordHash: String? = null,
    val salt: String? = null
) {
    // No-argument constructor for Firebase
    constructor() : this(null, null, null, null)
}
