package dog.snow.androidrecruittest.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.basic.Const
import dog.snow.androidrecruittest.model.ListItem
import dog.snow.androidrecruittest.databinding.ListFragmentBinding
import dog.snow.androidrecruittest.extension.gone
import dog.snow.androidrecruittest.extension.hideKeyboard
import dog.snow.androidrecruittest.extension.show
import dog.snow.androidrecruittest.ui.adapter.ListItemAdapter
import dog.snow.androidrecruittest.viewmodel.ListItemViewModel

@AndroidEntryPoint
class ListFragment : Fragment() {

    private var _binding: ListFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ListItemViewModel by viewModels()

    private val adapter by lazy {
        ListItemAdapter {
            findNavController().navigate(
                R.id.action_list_to_details, bundleOf(
                    Const.LIST_ITEM to it
                )
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvItems.adapter = adapter
        getInitListItems()
        getListItemsByQuery()
        closeKeyboardAfterScroll()
    }

    private fun closeKeyboardAfterScroll() {
        binding.rvItems.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 20) {
                    hideKeyboard()
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun getListItemsByQuery() {
        binding.layoutSearch.etSearch.addTextChangedListener { query ->
            viewModel.searchListItemByPhrase(query.toString().trim())
                .observe(viewLifecycleOwner) { listItems ->
                    setListItems(listItems)
                }
        }
    }

    private fun getInitListItems() {
        if (adapter.itemCount == 0) {
            viewModel.getListItemsDB().observe(viewLifecycleOwner) { listItems ->
                setListItems(listItems)
            }
        }
    }

    private fun setListItems(listItems: List<ListItem>?) {
        handleEmptyView(listItems)
        adapter.submitList(listItems)
    }

    private fun handleEmptyView(listItems: List<ListItem>?) {
        if (listItems.isNullOrEmpty()) {
            binding.layoutLottieEmptyView.lottieEmptyView.show()
        } else {
            binding.layoutLottieEmptyView.lottieEmptyView.gone()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}