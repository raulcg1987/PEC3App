package edu.uoc.android

import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.text.TextUtils.split
import android.util.Log
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import edu.uoc.android.models.Element
import edu.uoc.android.models.Museums
import edu.uoc.android.rest.RetrofitFactory
import kotlinx.android.synthetic.main.activity_maps.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class MapsActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {

    private lateinit var mMap: GoogleMap

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    var elementos: List<Element> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        //Check for permissions
        var locationRequestCode = 1000;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request for permission
            Log.d("Permisos","Permisos no están dados")
            //request Permissions
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                locationRequestCode
            )
        } else
        {
            // Permission has already been granted
            mainfun()
        }

    }


    fun mainfun(){
        //Instancia cliente de proveedor de ubicación combinada
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val museumAPI = RetrofitFactory.museumAPI
        val call = museumAPI.museums("1", "5")
        call.enqueue (object: Callback<Museums> {

            override fun onResponse (call: Call<Museums>, response: Response<Museums>) {

                if (response.code()==200) {

                    //progress_bar.visibility = View.GONE

                    val museums = response.body()!!
                    //Adapter <<- museums // elements
                    elementos = museums.getElements()

                    for (elemento in elementos){
                        Log.d("Retrofit",elemento.localitzacio)
                    }

                    agrega_mapa(elementos)

                }
            }
            override fun onFailure(call: Call<Museums>, t: Throwable) {
                Log.d("Retrofit", "Fallo en llamada: $t")
            }

        })

        Log.d("Retrofit", "Call done")


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        //Location button Utility
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        
    }

    fun agrega_mapa(elementos: List<Element>){
        // Add a marker for each element (museum)
        for (elemento in elementos) {

            val pos = split(elemento.localitzacio,",")
            val pos1 = pos[0].toDouble()
            val pos2= pos[1].toDouble()
            val position = LatLng(pos1,pos2)

            mMap.addMarker(MarkerOptions().position(position).title(elemento.adrecaNom))
                .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.baseline_place_white_24dp))
        }

            Log.d("Permisos","Permisos dados")
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    if (location != null) {
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(location.getLatitude(), location.getLongitude())))
                    }
                }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
         permissions: Array<String>,
         grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1000 -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    // Permission granted
                    mainfun()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onMyLocationClick(p0: Location) {
        Toast.makeText(this, "Current location:\n" + p0, Toast.LENGTH_LONG).show();
    }

    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }




}

