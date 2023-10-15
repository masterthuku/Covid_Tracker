package com.example.covidtracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView

class StateRVAdapter (private val stateList: List<StateModal>) :
    RecyclerView.Adapter<StateRVAdapter.StateRVViewHolder>() {

    class StateRVViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val stateNameTv:TextView = itemView.findViewById(R.id.TvState)
        val casesTv:TextView = itemView.findViewById(R.id.TvCases)
        val deathsTv:TextView = itemView.findViewById(R.id.TvDeaths)
        val recoveredTv:TextView = itemView.findViewById(R.id.TvRecovered)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateRVViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.state_rv_item,parent,false)
        return StateRVViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return stateList.size
    }

    override fun onBindViewHolder(holder: StateRVViewHolder, position: Int) {
        val stateData = stateList[position]
        holder.casesTv.text = stateData.cases.toString()
        holder.stateNameTv.text = stateData.state
        holder.deathsTv.text = stateData.deaths.toString()
        holder.recoveredTv.text = stateData.recovered.toString()

    }

}