package anime.stream.favourites

/**
 *  @author snehil
 * */

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import anime.stream.core.di.viewModel
import anime.stream.favourites.adapters.RecyclerViewMangaAdapter
import anime.stream.favourites.di.injector
import anime.stream.favourites.viewmodels.MangaViewModel
import kotlinx.android.synthetic.main.fragment_manga.view.*

class MangaFragment : Fragment(R.layout.fragment_manga) {

    private val viewModel by viewModel {
        injector.viewModel.create(this).create(MangaViewModel::class.java)
    }

    private val adapter by lazy { RecyclerViewMangaAdapter() }

    override fun onViewCreated(root: View, savedInstanceState: Bundle?) {
        super.onViewCreated(root, savedInstanceState)
        root.mangaList.adapter = adapter
        root.mangaList.layoutManager = LinearLayoutManager(requireActivity())
        viewModel.mangaFavourites.observe(this.viewLifecycleOwner, Observer {
            if (it == null) {
                hideRecyclerView(root)
            } else {
                adapter.submitData(this.lifecycle, it)
                showRecyclerView(root)
            }
        })
    }

    private fun hideRecyclerView(root: View) {
        root.mangaList.visibility = View.GONE
        root.noManga.visibility = View.VISIBLE
    }

    private fun showRecyclerView(root: View) {
        root.mangaList.visibility = View.VISIBLE
        root.noManga.visibility = View.GONE
    }


    companion object {
        private var mFragment: MangaFragment? = null

        @JvmStatic
        fun getInstance(): MangaFragment {
            synchronized(this) {
                if (mFragment == null) {
                    mFragment = MangaFragment()
                }
                return mFragment ?: MangaFragment()
            }
        }
    }
}