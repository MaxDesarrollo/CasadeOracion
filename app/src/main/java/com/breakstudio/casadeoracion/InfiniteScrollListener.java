package com.breakstudio.casadeoracion;

import android.widget.AbsListView;

/**
 * Created by Marcelo on 22/05/2017.
 */

public abstract class InfiniteScrollListener implements AbsListView.OnScrollListener {
    private int bufferItemCount = 10;
    private int currentPage = 0;
    private int itemCount = 0;
    private boolean isLoading = true;

    public  InfiniteScrollListener(int bufferItemCount){
        this.bufferItemCount = bufferItemCount;
    }

    public abstract void loadMore(int page,int totalItemsCount);

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState){

    }

    @Override
    public  void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totallItemCount){
        if (totallItemCount < itemCount){
            this.itemCount = totallItemCount;
            if (totallItemCount == 0){
                this.isLoading = true;
            }
        }
        if (isLoading && (totallItemCount > itemCount)){
            isLoading = false;
            itemCount = totallItemCount;
            currentPage ++;
        }

        if (!isLoading && (totallItemCount - visibleItemCount)<=(firstVisibleItem + bufferItemCount)){
            loadMore(currentPage +1,totallItemCount);
            isLoading = true;
        }
    }
}
