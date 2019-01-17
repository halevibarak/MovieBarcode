package movies.com.ui

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View


class MovieDecoration(private val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.top = space
        if (parent.getChildLayoutPosition(view) == state.itemCount - 1) {
            outRect.bottom = space
        }
    }
}