package cupcakes.br.cbi.commons


import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class RecyclerScrollListener(
        val funcScrollUp: () -> Unit,
        val funcScrollDown: () -> Unit,
        val layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {

    companion object {

    }

    private var firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if ( dy > 0 )
            funcScrollUp()
        else
            funcScrollDown()
    }

}