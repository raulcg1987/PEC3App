package edu.uoc.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import edu.uoc.android.rest.RetrofitFactory
import edu.uoc.android.models.Museums
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MuseumsActivity : AppCompatActivity() {

    //Variables para definir el Recycler View
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_museos)

        val museumAPI = RetrofitFactory.museumAPI
        val call = museumAPI.museums("1", "5")

        call.enqueue (object: Callback<Museums> {
            override fun onResponse (call: Call <Museums>, response: Response<Museums>){
                if (response.code()==200) {
                    //
                    //showProgress(false);
                    //
                    val museums = response.body()!!
                    //Adapter <<- museums // elements
                    val elemento = museums.getElements()
                    val texto = elemento[1].adrecaNom
                    val imagen = elemento [1].imatge
                }
            }

            override fun onFailure(call: Call<Museums>, t: Throwable) {
                Log.d("Retrobit","Fallo en llamada")
            }


        })

        Log.d("Retrobit", "Call done")
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

private fun <T> Call<T>.enqueue(callback: Callback<Museums>) {

}

