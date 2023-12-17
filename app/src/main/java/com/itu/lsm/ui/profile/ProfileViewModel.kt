import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class ProfileViewModel : ViewModel() {

    private val _name = MutableLiveData<String?>()
    private val _surname = MutableLiveData<String?>()
    private val _username = MutableLiveData<String?>()

    val name: MutableLiveData<String?> = _name
    val surname: MutableLiveData<String?> = _surname
    val username: MutableLiveData<String?> = _username

    private val usersRef = Firebase.database.reference.child("users")

    init {
        fetchUsernameFromDatabase()
    }

    private fun fetchUsernameFromDatabase() {
        val userId = "1"

        usersRef.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val fetchedUsername = snapshot.child("username").getValue(String::class.java)
                _username.value = fetchedUsername
                val fetchedName = snapshot.child("name").getValue(String::class.java)
                _name.value = fetchedName
                val fetchedSurname = snapshot.child("surname").getValue(String::class.java)
                _surname.value = fetchedSurname
            }

            override fun onCancelled(error: DatabaseError) {
                // todo
            }
        })
    }

    fun updateUsername(newUsername: String) {
        val userId = "1"
        // Update the username in the database
        usersRef.child(userId).child("username").setValue(newUsername)
        // Update the LiveData immediately
        _username.value = newUsername
    }


}

