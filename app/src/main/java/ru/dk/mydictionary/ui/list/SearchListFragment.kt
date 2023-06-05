package ru.dk.mydictionary.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.dk.mydictionary.R
import ru.dk.mydictionary.data.state.AppState
import ru.dk.mydictionary.databinding.FragmentSearchBinding
import ru.dk.mydictionary.ui.adapters.ItemListAdapter
import ru.dk.mydictionary.ui.description.DescriptionFragment
import ru.dk.mydictionary.ui.search.SearchDialogFragment

class SearchListFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var adapter = ItemListAdapter()

    private val viewModel: SearchListViewModel by viewModel()

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
            searchListRv.adapter = adapter.apply {
                listener = {
                    parentFragmentManager.beginTransaction()
                        .replace(
                            R.id.main_container,
                            DescriptionFragment.newInstance(Bundle().apply {
                                putParcelable("word", it)
                            })
                        )
                        .addToBackStack(it.text)
                        .commit()
                }
            }
            searchFab.setOnClickListener {
                SearchDialogFragment.newInstance().apply {
                    listener = {
                        viewModel.requestData(it)
                    }
                }.show(parentFragmentManager, "search")
            }
            reloadBtn.setOnClickListener {
                viewModel.getLastRequest()
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