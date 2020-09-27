package `in`.khofid.reactivesearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ed_place.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                lifecycleScope.launch {
                    viewModel.queryChannel.send(s.toString())
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        viewModel.searchresult.observe(this, { placeItem ->
            val placeName = arrayListOf<String?>()
            placeItem.map {
                placeName.add(it.placeName)
            }
            val adapter = ArrayAdapter(this@MainActivity, android.R.layout.select_dialog_item, placeName)
            adapter.notifyDataSetChanged()
            ed_place.setAdapter(adapter)
        })
    }
}