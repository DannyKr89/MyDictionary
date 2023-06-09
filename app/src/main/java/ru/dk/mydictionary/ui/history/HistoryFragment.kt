package ru.dk.mydictionary.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.android.ext.android.getKoin
import org.koin.core.scope.Scope
import ru.dk.mydictionary.R
import ru.dk.mydictionary.data.state.AppState
import ru.dk.mydictionary.databinding.FragmentHistoryBinding
import ru.dk.mydictionary.ui.adapters.ItemListAdapter
import ru.dk.mydictionary.ui.description.DescriptionFragment

class HistoryFragment : Fragment() {

    companion object {
        fun newInstance() = HistoryFragment()
    }

    private val scope: Scope by lazy {
        getKoin().getOrCreateScope<HistoryFragment>("")
    }

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HistoryViewModel by lazy {
        scope.get()
    }

    private val adapter: ItemListAdapter by lazy { scope.get<ItemListAdapter>() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater)
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
            historyListRv.layoutManager = LinearLayoutManager(requireContext())
            historyListRv.adapter = adapter.apply {
                listener = {
                    parentFragmentManager.beginTransaction()
                        .replace(
                            R.id.main_container,
                            DescriptionFragment.newInstance(Bundle().apply {
                                putParcelable("word", it)
                            })
                        )
                        .addToBackStack(it.word)
                        .commit()
                }
            }
        }
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> {
                with(binding) {
                    Toast.makeText(requireContext(), appState.throwable.message, Toast.LENGTH_SHORT)
                        .show()
                    successLayout.visibility = View.GONE
                    progressbar.visibility = View.GONE
                }
            }

            is AppState.Loading -> {
                with(binding) {
                    successLayout.visibility = View.GONE
                    progressbar.visibility = View.VISIBLE
                }
            }

            is AppState.Success -> {
                with(binding) {
                    successLayout.visibility = View.VISIBLE
                    progressbar.visibility = View.GONE
                    adapter.submitList(appState.list)
                }

            }
        }
    }

    override fun onDestroy() {
        _binding = null
        viewModel.onClear()
        scope.close()
        super.onDestroy()
    }
}