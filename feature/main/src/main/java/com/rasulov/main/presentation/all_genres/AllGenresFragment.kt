package com.rasulov.main.presentation.all_genres

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rasulov.feature.presentation.shared.movie_delegate.MovieItemDelegate
import com.rasulov.main.R
import com.rasulov.main.databinding.FragmentAllGenresBinding
import com.rasulov.ui.rv_adapter_delegate.ItemsAdapter
import com.rasulov.ui.rv_adapter_delegate.MediatorItemDelegate
import com.rasulov.ui.viewbinding_delegate.viewBindings

class AllGenresFragment : Fragment(R.layout.fragment_all_genres) {

    private val binding: FragmentAllGenresBinding by viewBindings()
    private val viewModel: AllGenresViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupList()


    }

    private fun setupList() {

    }
}