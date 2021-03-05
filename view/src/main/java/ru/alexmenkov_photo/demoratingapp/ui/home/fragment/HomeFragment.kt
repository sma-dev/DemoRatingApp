package ru.alexmenkov_photo.demoratingapp.ui.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import androidx.paging.cachedIn
import androidx.recyclerview.widget.DiffUtil
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collectLatest
import ru.alexmenkov_photo.demoratingapp.R
import ru.alexmenkov_photo.demoratingapp.databinding.FragmentHomeBinding
import ru.alexmenkov_photo.demoratingapp.service.room.entity.Lot
import ru.alexmenkov_photo.demoratingapp.toast
import ru.alexmenkov_photo.demoratingapp.ui.home.adapter.DirectoryHeaderWrapper
import ru.alexmenkov_photo.demoratingapp.ui.home.adapter.LoaderStateAdapter
import ru.alexmenkov_photo.demoratingapp.ui.home.adapter.LotPageAdapter
import ru.alexmenkov_photo.demoratingapp.vmodel.HomeViewModel

class HomeFragment : Fragment() {

    // Instantiate VM
    private val homeViewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val headerWrapper: DirectoryHeaderWrapper by lazy {
        DirectoryHeaderWrapper { path ->
            /*val bundle = bundleOf(
                CATEGORY_PATH to path
            )
            val builder = NavOptions.Builder()
                .setLaunchSingleTop(false)
                .setEnterAnim(R.anim.slide_in_right)
                .setExitAnim(R.anim.slide_out_left)
                .setPopEnterAnim(R.anim.slide_in_left)
                .setPopExitAnim(R.anim.slide_out_right)
            findNavController().navigate(
                R.id.lotPageFragment,
                bundle, builder.build()
            )*/
        }
    }

    private val lotPageAdapter: LotPageAdapter by lazy {
        LotPageAdapter({
            toast("Navigate to next screen ${it.lotCode} ...")

            val builder = NavOptions.Builder()
                .setLaunchSingleTop(true)
                .setEnterAnim(R.anim.slide_in_right)
                .setExitAnim(R.anim.slide_out_left)
                .setPopEnterAnim(R.anim.slide_in_left)
                .setPopExitAnim(R.anim.slide_out_right)

            /*val options = builder.build()
            val bundle = bundleOf(LOT_DETAILS_ARG to it, INIT to true)
            requireActivity().findNavController(R.id.nav_host_catalog_group)
                .navigate(R.id.nav_lot_details, bundle, options)*/
        }, { parent -> headerWrapper.createViewHolder(parent) },
            object : DiffUtil.ItemCallback<Lot>() {
                override fun areItemsTheSame(
                    oldItem: Lot,
                    newItem: Lot
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: Lot,
                    newItem: Lot
                ): Boolean {
                    return oldItem == newItem
                }
            }
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    @InternalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        val layoutManager = FlexboxLayoutManager(context)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.SPACE_AROUND

        binding.rvHomeLot.let {
            it.layoutManager = layoutManager
            it.adapter = lotPageAdapter.withLoadStateFooter(LoaderStateAdapter {
                lotPageAdapter.retry()
            })
        }

        lifecycleScope.launchWhenCreated {
            homeViewModel.catalogPager.flow.buffer().cachedIn(homeViewModel.viewModelScope)
                .collectLatest {
                    lotPageAdapter.submitData(it)
                }
        }
    }
}