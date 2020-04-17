package edu.uoc.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class QuizzesActivity : AppCompatActivity() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    val datos:  MutableList<pregunta> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quizzes)

        val db = FirebaseFirestore.getInstance()
        db.collection("Quizzes").get()
            .addOnSuccessListener { result ->

                for (document in result) {
                    Log.d("Firebase", "${document.id} => ${document.data}")
                    var choice1 = document.data["choice1"]
                    var choice2 = document.data["choice2"]
                    var image = document.data["image"]
                    var title = document.data["title"]
                    var rightChoice = document.data["rightChoice"]
                    datos.add(pregunta(choice1 as String, choice2 as String, image as String, title as String, rightChoice as Long))

                }

                adaptar()

            }
            .addOnFailureListener { exception ->
                Log.w("Firebase", "Error getting documents.", exception)
            }




    }

    fun adaptar() {
        viewManager = LinearLayoutManager(this)
        viewAdapter = QuizzesAdapter(datos , this)

        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view_quizzes).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        } as RecyclerView
    }
}
