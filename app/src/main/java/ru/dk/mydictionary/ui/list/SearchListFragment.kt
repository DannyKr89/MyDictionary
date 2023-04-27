package ru.dk.mydictionary.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ru.dk.mydictionary.App
import ru.dk.mydictionary.data.state.AppState
import ru.dk.mydictionary.databinding.FragmentSearchBinding
import ru.dk.mydictionary.ui.adapters.SearchListAdapter
import ru.dk.mydictionary.ui.search.SearchDialogFragment

class SearchListFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var adapter = SearchListAdapter()
    private val viewModel: SearchListViewModel by lazy {
        ViewModelProvider(
            this,
            App.instance.viewModelFactory
        )[SearchListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getLiveData().observe(viewLifecycleOwner) {
            renderData(it)
        }
        initViews()
    }

    private fun initViews() {

        with(binding) {
            searchListRv.layoutManager = LinearLayoutManager(requireContext())
            searchListRv.adapter = adapter
            searchFab.setOnClickListener {
                SearchDialogFragment.newInstance().apply {
                    listener = {
                        viewModel.requestData(it)
                    }
                }.show(parentFragmentManager, "search")
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        viewModel.onClear()
        super.onDestroyView()
    }

    companion object {
        fun newInstance() = SearchListFragment()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> {
                with(binding) {
                    Toast.makeText(requireContext(), appState.throwable.message, Toast.LENGTH_SHORT)
                        .show()
                    successLayout.visibility = View.GONE
                    errorLayout.visibility = View.VISIBLE
                    progressbar.visibility = View.GONE
                }
            }

            is AppState.Loading -> {
                with(binding) {
                    successLayout.visibility = View.GONE
                    errorLayout.visibility = View.GONE
                    progressbar.visibility = View.VISIBLE
                }
            }

            is AppState.Success -> {
                with(binding) {
                    successLayout.visibility = View.VISIBLE
                    errorLayout.visibility = View.GONE
                    progressbar.visibility = View.GONE
                    if (appState.list.isNullOrEmpty()) {
                        emptyLayout.visibility = View.VISIBLE
                    } else {
                        adapter.submitList(appState.list)
                        emptyLayout.visibility = View.GONE
                    }
                }

            }
        }
    }
}