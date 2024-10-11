package com.dicoding.dicodingevent.ui

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dicoding.dicodingevent.R
import com.dicoding.dicodingevent.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
//    private lateinit var apiService: ApiService // Inisialisasi API Service
//    private val activeStatus = 1 // Status acara aktif
//    private lateinit var eventAdapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_upcoming, R.id.navigation_finished
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}

//        eventAdapter = EventAdapter { event -> onEventClick(event) }
//        binding.rvUpcomingEvents.layoutManager = LinearLayoutManager(this)
//        binding.rvUpcomingEvents.adapter = eventAdapter
//
//        apiService = ApiConfig.getApiService()
//
//        binding.editTextSearch.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                val query = s.toString()
//                if (query.isNotEmpty()) {
//                    searchEvents(query)
//                } else {
//                    // Jika input kosong, bisa mengembalikan daftar awal atau mengosongkan adapter
//                    eventAdapter.submitList(emptyList())
//                }
//            }
//
//            override fun afterTextChanged(s: Editable?) {}
//        })
//    }

//    private fun searchEvents(keyword: String) {
//        apiService.searchEvents(activeStatus, keyword).enqueue(object : Callback<EventResponse> {
//            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
//                if (response.isSuccessful) {
//                    val events = response.body()?.events ?: emptyList()
//                    if (events.isNotEmpty()) {
//                        eventAdapter.submitList(events)
//                    } else {
//                        Toast.makeText(this@MainActivity, "No events found", Toast.LENGTH_SHORT).show()
//                    }
//                } else {
//                    Toast.makeText(this@MainActivity, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
//                Toast.makeText(this@MainActivity, "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
//
//    private fun onEventClick(event: ListEventsItem) {
//        val intent = Intent(this, DetailActivity::class.java).apply {
//            putExtra("event_id", event.id)
//        }
//        startActivity(intent)
//        Toast.makeText(this, "Clicked: ${event.name}", Toast.LENGTH_SHORT).show()
//    }
//}