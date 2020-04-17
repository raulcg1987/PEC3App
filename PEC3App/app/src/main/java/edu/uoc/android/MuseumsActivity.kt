package edu.uoc.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.uoc.android.models.Element
import edu.uoc.android.rest.RetrofitFactory
import edu.uoc.android.models.Museums
import kotlinx.android.synthetic.main.activity_museos.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MuseumsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    var elementos: List<Element> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_museos)

        val museumAPI = RetrofitFactory.museumAPI
        val call = museumAPI.museums("1", "5")
        call.enqueue (object: Callback<Museums> {

            override fun onResponse (call: Call <Museums>, response: Response<Museums>) {

                if (response.code()==200) {

                    progress_bar.visibility = View.GONE

                    val museums = response.body()!!
                    //Adapter <<- museums // elements
                    elementos = museums.getElements()

                    for (elemento in elementos){
                        Log.d("Retrofit",elemento.adrecaNom)
                    }

                    adaptar()

                }
            }

            override fun onFailure(call: Call<Museums>, t: Throwable) {
                Log.d("Retrofit", "Fallo en llamada: $t")
            }


        })

        Log.d("Retrofit", "Call done")

    }

    fun adaptar(){
        viewManager = LinearLayoutManager(this)
        viewAdapter = MuseumsAdapter(elementos,this)

        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply {
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


