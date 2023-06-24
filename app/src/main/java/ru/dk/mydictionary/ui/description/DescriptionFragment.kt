package ru.dk.mydictionary.ui.description

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import ru.dk.mydictionary.data.model.DictionaryModel
import ru.dk.mydictionary.databinding.FragmentDescriptionBinding

class DescriptionFragment : Fragment() {

    private var _binding: FragmentDescriptionBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(bundle: Bundle) = DescriptionFragment().apply {
            arguments = bundle
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDescriptionBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val word = arguments?.getParcelable<DictionaryModel>("word")
        val image = word?.meanings?.first()?.imageUrl?.substringAfter("https")


        with(binding) {
            Glide.with(this@DescriptionFragment)
                .load("https" + image)
                .into(imageDescription)
            wordDescription.text = word?.text
            transcriptionDescription.text = word?.meanings?.first()?.transcription
            translationDescription.text = word?.meanings?.first()?.translation?.text
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}