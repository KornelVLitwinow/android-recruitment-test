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
import dagger.hilt.android.AndroidEntryPoint
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.basic.Const
import dog.snow.androidrecruittest.model.ListItem
import dog.snow.androidrecruittest.databinding.ListFragmentBinding
import dog.snow.androidrecruittest.extension.gone
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
            viewModel.getDB().observe(viewLifecycleOwner) { listItems ->
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
            binding.layoutEmptyView.tvEmpty.show()
        } else {
            binding.layoutEmptyView.tvEmpty.gone()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}