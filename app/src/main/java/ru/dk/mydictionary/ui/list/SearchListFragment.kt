package ru.dk.mydictionary.ui.list

import android.app.AlertDialog
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.dk.mydictionary.R
import ru.dk.mydictionary.data.state.AppState
import ru.dk.mydictionary.databinding.FragmentSearchBinding
import ru.dk.mydictionary.ui.adapters.ItemListAdapter
import ru.dk.mydictionary.ui.description.DescriptionFragment
import ru.dk.mydictionary.ui.search.SearchDialogFragment
import ru.dk.mydictionary.utils.BlurEffect
import ru.dk.mydictionary.utils.OnlineLiveData

class SearchListFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var adapter = ItemListAdapter()

    private val viewModel: SearchListViewModel by viewModel()
    private val onlineLiveData: OnlineLiveData by inject()
    private var isNetworkAvailable: Boolean = true
    private val blur: BlurEffect by inject()

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
        subscribeBlurLiveData()
        subscribeToNetworkChange()
    }

    private fun subscribeBlurLiveData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            blur.observe(viewLifecycleOwner) {
                binding.root.setRenderEffect(
                    RenderEffect.createBlurEffect(
                        it,
                        it,
                        Shader.TileMode.MIRROR
                    )
                )
            }
        }
    }

    private fun subscribeToNetworkChange() {
        onlineLiveData.observe(viewLifecycleOwner) {
            isNetworkAvailable = it
            if (!isNetworkAvailable) {
                Toast.makeText(
                    requireContext(),
                    R.string.dialog_message_device_is_offline,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun initViews() {

        with(binding) {
            searchListRv.layoutManager = LinearLayoutManager(requireContext())
            searchListRv.adapter = adapter.apply {
                listener = {
                    viewModel.saveWordToHistory(it)
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
            searchFab.setOnClickListener {
                viewModel.getBlur().postValue(16f)
                SearchDialogFragment.newInstance().apply {
                    listener = {
                        if (isNetworkAvailable) {
                            viewModel.requestData(it)

                            viewModel.getBlur().postValue(0.01f)

                        } else {
                            AlertDialog.Builder(requireContext())
                                .setMessage(R.string.dialog_message_device_is_offline)
                                .setTitle(R.string.search)
                                .show()
                        }
                    }
                }.show(parentFragmentManager, "search")
            }
            reloadBtn.setOnClickListener {
                viewModel.getLastRequest()
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        viewModel.onClear()
        super.onDestroy()
    }

    companion object {
        fun newInstance() = SearchListFragment()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> {
                with(binding) {
                    Toast.makeText(
                        requireContext(),
                        appState.throwable.message,
                        Toast.LENGTH_SHORT
                    )
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