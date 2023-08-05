package com.udacity.asteroidradar.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.database.AsteroidsDBFilter
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding

    //using lazy, will make us use the view model in onViewCreated()
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.asteroids.observe(viewLifecycleOwner) { asteroids ->
            asteroids?.apply {
                binding.asteroidRecycler.adapter =
                    AsteroidsAdapter(requireActivity(), this, AsteroidsAdapter.OnClickListener {
                        findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                    })
            }
        }

        viewModel.hasError.observe(viewLifecycleOwner) { hasError ->
            if (hasError) {
                binding.statusLoadingWheel.visibility = View.GONE
                Snackbar.make(
                    binding.root,
                    R.string.error_message,
                    BaseTransientBottomBar.LENGTH_INDEFINITE
                )
                    .setAction(R.string.try_again) {
                        binding.statusLoadingWheel.visibility = View.VISIBLE
                        viewModel.getAsteroidsListFromRepository(AsteroidsDBFilter.SHOW_ALL)
                        viewModel.getPictureOfDayFromRepository()
                    }
                    .show()
            }

            viewModel.asteroids.observe(viewLifecycleOwner) {
                binding.statusLoadingWheel.visibility = View.GONE
            }


        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val filter = when (item.itemId) {
            R.id.view_week_menu -> AsteroidsDBFilter.SHOW_WEEK
            R.id.view_today_menu -> AsteroidsDBFilter.SHOW_TODAY
            else -> AsteroidsDBFilter.SHOW_ALL
        }
        viewModel.updateFilter(filter)
        return true
    }

}
