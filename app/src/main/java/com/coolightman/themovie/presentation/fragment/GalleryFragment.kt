package com.coolightman.themovie.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
        val movieId = getMovieIdArg()
        val position = getFramePositionArg()

        createRecycler()
        createObserver(movieId, position)
        createListeners()
    }

    private fun createListeners() {
        with(binding) {
            imgCloseGallery.setOnClickListener {
                closeGallery()
            }
        }
    }

    private fun closeGallery() {
        requireActivity().supportFragmentManager.popBackStack()
    }

    private fun createObserver(movieId: Long, position: Int) {
        viewModel.getFrames(movieId).observe(viewLifecycleOwner) {
            it?.let {
                galleryAdapter.submitList(it)
                setLayoutPosition(position)
            }
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

    private fun getFramePositionArg() = requireArguments().getInt(ARG_FRAME_POSITION, -1)

    private fun getMovieIdArg() = requireArguments().getLong(ARG_MOVIE_ID, 0)


    companion object {
        private const val ARG_MOVIE_ID = "movieId"
        private const val ARG_FRAME_POSITION = "framePosition"
        private var prevPosition = -1

        fun newInstance(movieId: Long, framePosition: Int) =
            GalleryFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_MOVIE_ID, movieId)
                    putInt(ARG_FRAME_POSITION, framePosition)
                }
            }
    }

}