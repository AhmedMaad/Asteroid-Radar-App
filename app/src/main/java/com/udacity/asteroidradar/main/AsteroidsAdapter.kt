package com.udacity.asteroidradar.main

import android.app.Activity
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.databinding.AsteroidsListItemBinding

class AsteroidsAdapter(val a: Activity, val asteroids: List<Asteroid>) :
    Adapter<AsteroidsAdapter.AsteroidViewHolder>() {

    class AsteroidViewHolder(val binding: AsteroidsListItemBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        val binding =
            AsteroidsListItemBinding.inflate(a.layoutInflater, parent, false)
        return AsteroidViewHolder(binding)
    }

    override fun getItemCount() = asteroids.size

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        holder.binding.data = asteroids[position]
    }
}
