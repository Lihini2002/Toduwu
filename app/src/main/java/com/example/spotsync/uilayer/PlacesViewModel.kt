package com.example.spotsync.uilayer

import androidx.lifecycle.*
import com.example.spotsync.datalayer.Location
import com.google.firebase.database.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlacesViewModel @Inject constructor(
    firebaseDatabase: FirebaseDatabase
) : ViewModel() {

    private val databaseReference: DatabaseReference = firebaseDatabase.getReference("https://toduwu-default-rtdb.firebaseio.com/")

    private val _locations = MutableLiveData<List<Location>>()
    val locations: LiveData<List<Location>> = _locations

    init {
        fetchData()
    }

    private fun fetchData() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val places = mutableListOf<Location>()
                for (placeSnapshot in snapshot.children) {
                    val title = placeSnapshot.child("title").getValue(String::class.java) ?: ""
                    val distance = placeSnapshot.child("distance").getValue(String::class.java) ?: ""
                    val todosCount = placeSnapshot.child("todosCount").getValue(Int::class.java) ?: 0
                    val iconResName = placeSnapshot.child("iconResName").getValue(String::class.java) ?: ""
                    val usageCount = placeSnapshot.child("usageCount").getValue(Int::class.java) ?: 0

                    val location = Location(title, distance, todosCount, iconResName, usageCount)
                    places.add(location)
                }
                _locations.postValue(places)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error if data retrieval fails
            }
        })
    }
}
