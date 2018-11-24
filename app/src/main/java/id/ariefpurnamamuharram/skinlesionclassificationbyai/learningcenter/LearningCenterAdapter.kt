package id.ariefpurnamamuharram.skinlesionclassificationbyai.learningcenter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import id.ariefpurnamamuharram.skinlesionclassificationbyai.R
import org.jetbrains.anko.find

class LearningCenterAdapter(
    private val context: Context?,
    private val items: List<ContentItem>,
    private val listener: (ContentItem) -> Unit
) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_learning_content, parent, false)
    )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items[position], listener)
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val learningItem = view.find<TextView>(R.id.learning_item)

    fun bindItem(items: ContentItem, listener: (ContentItem) -> Unit) {
        learningItem.text = items.name
        itemView.setOnClickListener {
            listener(items)
        }
    }
}
