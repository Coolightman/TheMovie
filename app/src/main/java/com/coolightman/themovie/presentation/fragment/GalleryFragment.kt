package com.coolightman.themovie.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.coolightman.themovie.App
import com.coolightman.themovie.databinding.FragmentGalleryBinding
import com.coolightman.themovie.presentation.adapter.GalleryAdapter
import com.coolightman.themovie.presentation.viewmodel.GalleryViewModel
import com.coolightman.themovie.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class GalleryFragment : Fragment() {

    private val component by lazy {
        (requireActivity().application as App).component
    }

    private val args by navArgs<GalleryFragmentArgs>()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GalleryViewModel::class.java]
    }

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    private lateinit var galleryAdapter: GalleryAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieId = args.movieId
        val position = args.framePosition
        viewModel.setMovieId(movieId)

        createRecycler()
        createObserver(position)
        createListeners()
    }

    private fun createListeners() {
        with(binding) {
            imgCloseGallery.setOnClickListener {
                closeFragment()
            }
        }
    }

    private fun closeFragment() {
        findNavController().popBackStack()
    }

    private fun createObserver(position: Int) {
        viewModel.frames.observe(viewLifecycleOwner) {
            galleryAdapter.submitList(it)
            setLayoutPosition(position)
        }
    }

    private fun createRecycler() {
        val recycler = binding.rvGallery
        createFrameAdapter(recycler)
        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recycler.layoutManager = layoutManager
    }

    private fun setLayoutPosition(position: Int) {
        if (position != prevPosition) {
            layoutManager.scrollToPosition(position)
            prevPosition = position
        }
    }

    private fun createFrameAdapter(recycler: RecyclerView) {
        galleryAdapter = GalleryAdapter()
        recycler.adapter = galleryAdapter
        galleryAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        PagerSnapHelper().attachToRecyclerView(recycler)
    }

    companion object {
        private var prevPosition = -1
    }

}