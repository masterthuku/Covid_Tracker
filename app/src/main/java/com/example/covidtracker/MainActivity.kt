package com.example.covidtracker

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class MainActivity : AppCompatActivity() {
    lateinit var worldCasesTV:TextView
    lateinit var worldRecoveredTV:TextView
    lateinit var worldDeathsTV:TextView
    lateinit var countryCasesTV:TextView
    lateinit var countryRecoveredTV:TextView
    lateinit var countryDeathsTV:TextView
    lateinit var stateRV:RecyclerView
    lateinit var stateRVAdapter: StateRVAdapter
    lateinit var stateList: List<StateModal>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        worldCasesTV = findViewById(R.id.TvWorldCases)
        worldRecoveredTV = findViewById(R.id.TvWorldRecovered)
        worldDeathsTV = findViewById(R.id.TvWorldDeaths)
        countryCasesTV = findViewById(R.id.TvCountryCases)
        countryRecoveredTV = findViewById(R.id.TvCountryRecovered)
        countryDeathsTV = findViewById(R.id.TvCountryDeaths)
        stateRV = findViewById(R.id.idRVStates)
        stateList = ArrayList<StateModal>()
        getStateInfo()
        getWorldInfo()

    }
    private fun getStateInfo(){
        val url = "https://api.rootnet.in/covid19-in/stats/latest"
        val queue = Volley.newRequestQueue(this@MainActivity)
        val request =
            JsonObjectRequest(Request.Method.GET,url,null,{ response->
                try {
                    val dataObj = response.getJSONObject("data")
                    val summaryObj = dataObj.getJSONObject("summary")
                    val cases:Int = summaryObj.getInt("total")
                    val recovered:Int = summaryObj.getInt("discharged")
                    val deaths:Int = summaryObj.getInt("deaths")

                    countryCasesTV.text = cases.toString()
                    countryRecoveredTV.text = recovered.toString()
                    countryDeathsTV.text = deaths.toString()

                    val regionalArray =dataObj.getJSONArray("regional")
                    for (i in 0 until regionalArray.length()){
                        val regionalObj = regionalArray.getJSONObject(i)
                        val stateName:String = regionalObj.getString("loc")
                        val cases:Int = regionalObj.getInt("totalConfirmed")
                        val deaths:Int = regionalObj.getInt("deaths")
                        val recovered:Int = regionalObj.getInt("discharged")

                        val stateModal = StateModal(stateName,recovered, deaths, cases)
                        stateList = stateList+stateModal
                    }

                    stateRVAdapter = StateRVAdapter(stateList)
                    stateRV.layoutManager = LinearLayoutManager(this)
                    stateRV.adapter = stateRVAdapter


                }catch (e:JSONException){
                    e.printStackTrace()
                }
            },{error->
                {
                    Toast.makeText(this,"failed to get data", Toast.LENGTH_SHORT).show()
            }
            })
        queue.add(request)
    }
    private fun getWorldInfo(){
        val url = "https://disease.sh/v3/covid-19/all"
        val queue = Volley.newRequestQueue(this@MainActivity)
        val request =
            JsonObjectRequest(Request.Method.GET,url,null, { response ->
                try {
                    val worldCases: Int = response.getInt("cases")
                    val worldRecovered: Int = response.getInt("recovered")
                    val worldDeaths: Int = response.getInt("deaths")
                    worldRecoveredTV.text = worldRecovered.toString()
                    worldDeathsTV.text = worldDeaths.toString()
                    worldCasesTV.text = worldCases.toString()
                }
                catch (e:JSONException){
                    e.printStackTrace()
                }
            },{
                error->
                    {
                        Toast.makeText(this,"failed to get data", Toast.LENGTH_SHORT).show()
                    }
                })
        queue.add(request)
    }
}