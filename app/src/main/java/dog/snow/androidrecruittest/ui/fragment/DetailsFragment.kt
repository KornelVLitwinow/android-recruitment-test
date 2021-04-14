package dog.snow.androidrecruittest.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dog.snow.androidrecruittest.basic.Const
import dog.snow.androidrecruittest.databinding.DetailsFragmentBinding
import dog.snow.androidrecruittest.extension.loadUrl
import dog.snow.androidrecruittest.model.ListItem

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        handleToolbar()
    }

    private fun initView() {
        val listItem = arguments?.getParcelable(Const.LIST_ITEM) as ListItem?
        listItem?.let {
            with(binding) {
                ivPhoto.loadUrl(it.url)
                tvPhotoTitle.text = it.title
                tvAlbumTitle.text = it.albumTitle
                tvUsername.text = it.username
                tvEmail.text = it.email
                tvPhone.text = it.phone
            }
        }
    }

    private fun handleToolbar() {
        binding.toolbarBack.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}