package edu.uoc.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_museos.*
import retrofit2.http.GET
import retrofit2.http.Query
import javax.security.auth.callback.Callback


class Museos : AppCompatActivity() {

    //Variables para definir el Recycler View
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_museos)

        call.enqueue (object: Callback<Museums>)
        //
        viewManager = LinearLayoutManager(this)
        viewAdapter = MyAdapter(myDataset)

        my_recycler_view.apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }




    }
}
