package id.ariefpurnamamuharram.skinlesionclassificationbyai.learningcenter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.find
import id.ariefpurnamamuharram.skinlesionclassificationbyai.R

class LearningCenterFragment : Fragment() {

    private var items: MutableList<ContentItem> = mutableListOf()

    private fun initData() {
        val name = resources.getStringArray(R.array.learning_center_content)
        items.clear()
        for (i in name.indices) {
            items.add(ContentItem(name[i]))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fragment_learning_content, container, false)

        val list = rootView.find<RecyclerView>(R.id.content_list)
        initData()

        list.layoutManager = LinearLayoutManager(activity)
        list.adapter = LearningCenterAdapter(this.context, items) {}

        return rootView
    }

    companion object {
        fun newInstance(): LearningCenterFragment = LearningCenterFragment()
    }

}