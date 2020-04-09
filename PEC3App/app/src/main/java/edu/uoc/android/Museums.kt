package edu.uoc.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.uoc.android.rest.MuseumService
import kotlinx.android.synthetic.main.activity_museos.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Museums : AppCompatActivity() {

    //Variables para definir el Recycler View
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_museos)

        Call.enqueue (object: Callback<Museums> {
            override fun onResponse (call: Call <Museums>, response: Response<Museums>){
                if (response.code()==200) {
                    //
                    //showProgress(false);
                    //
                    val museums = response.body()!!
                    //Adapter <<- muesums // elements
                }
            }
            override fun OnFailure (call: Call<Museums>, t: Throwable){
                Log.d("Retrobit","Fallo en llamada")
            }
        })

//        viewManager = LinearLayoutManager(this)
//        viewAdapter = MyAdapter(myDataset)
//
//        my_recycler_view.apply {
//            // use this setting to improve performance if you know that changes
//            // in content do not change the layout size of the RecyclerView
//            setHasFixedSize(true)
//
//            // use a linear layout manager
//            layoutManager = viewManager
//
//            // specify an viewAdapter (see also next example)
//            adapter = viewAdapter
//
//        }




    }
}
