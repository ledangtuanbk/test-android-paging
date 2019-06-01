package com.ldt.myapplication

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: SearchRepositoriesViewModel;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        viewModel = ViewModelProviders.of(
            this,
            Injection.provideViewModelFactory(this)
        ).get(SearchRepositoriesViewModel::class.java)

        initAdapter()

        var query = savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY
        viewModel.searchRepo(query)
        initSearch(query)

    }

    private fun initAdapter() {
        viewModel.repos.observe(this, Observer<List<Repo>> {
            Log.d(TAG, "list ${it?.size}")
            showEmptyList(it?.size == 0)
        })

        viewModel.networkErrors.observe(this, Observer {
            Log.d(TAG, "error ${it}")
        })
    }

    private fun showEmptyList(show: Boolean) {
        if (show) {
            Toast.makeText(this, "empty", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, " not empty", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initSearch(query: String) {
        search_repo.setText(query)
        search_repo.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }

        search_repo.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }
    }

    private fun updateRepoListFromInput() {

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }



    companion object {
        private const val LAST_SEARCH_QUERY: String = "last_search_query"
        private const val DEFAULT_QUERY: String = "Android"
        private const val TAG = "MAIN_ACTIVITY"
    }
}
