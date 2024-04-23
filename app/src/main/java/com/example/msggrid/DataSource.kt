package com.example.msggrid



//import com.nbc.messenger.model.My
//import com.nbc.messenger.model.ProfileImage
//import com.nbc.messenger.model.User

object DataSource {

    private val users: MutableList<User> = MemoryStorage.users.toMutableList()

    fun addUser(user: User) {
        users.add(user)
    }

    fun editData(Index: Int, user: User){
        //
    }
    fun getUsers(): List<User> = users

    private var my: My = My(
        id = "1",
        name = "Anna",
        nickname = "anna",
        phoneNumber = "1717171717",
        email = "anna@example.com",
        profileImage = ProfileImage.DefaultImage,
    )

    fun setMyData(my: My) {
        this.my = my
    }

    fun getMyData(): My = my

}
