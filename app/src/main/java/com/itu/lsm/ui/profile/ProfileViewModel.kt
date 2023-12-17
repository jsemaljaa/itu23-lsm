import android.app.Dialog
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.FirebaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.itu.lsm.R
import kotlin.math.log

class ProfileViewModel : ViewModel() {

    private val _name = MutableLiveData<String>().apply {
        value = "Name"
    }

    private val _surname = MutableLiveData<String>().apply {
        value = "Surname"
    }

    private val _username = MutableLiveData<String?>()

    val name: LiveData<String> = _name
    val surname: LiveData<String> = _surname
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
            }

            override fun onCancelled(error: DatabaseError) {
                // todo
            }
        })
    }

    fun updateUsername(newUsername: String) {
        // Assuming you have a user ID, replace "userId" with the actual user ID
        val userId = "1"

        // Update the username in the database
        usersRef.child(userId).child("username").setValue(newUsername)

        // Update the LiveData immediately
        _username.value = newUsername
    }


}

