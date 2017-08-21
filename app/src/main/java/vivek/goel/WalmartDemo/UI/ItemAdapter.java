package vivek.goel.WalmartDemo.UI;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import vivek.goel.WalmartDemo.MainActivity;
import vivek.goel.WalmartDemo.R;
import vivek.goel.WalmartDemo.listener.OnLoadMoreListener;
import vivek.goel.WalmartDemo.models.DataModel.ItemFeed;
import vivek.goel.WalmartDemo.models.DataModel.Items;

/**
 * Created by vivekgoel on 5/11/17.
 */

//Adapter for Search Fragment
class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener mOnLoadMoreListener;
    private final LinearLayoutManager linearLayoutManager;
    private boolean isLoading;
    private int visibleThreshold = 10;
    private int lastVisibleItem, totalItemCount;
    private Context context;
    private ArrayList<Items> result;
    private RecyclerView mRecyclerView;
    private ItemFeed mItemDataSet;

    public ItemAdapter(Context context, RecyclerView recyclerView, final LinearLayoutManager linearLayoutManager, ArrayList<Items> result) {
        this.context = context;
        mRecyclerView = recyclerView;
        this.linearLayoutManager = linearLayoutManager;
        this.result = result;
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }

        });
    }

    public void setData(ArrayList<Items> result, ItemFeed itemFeed) {
        mItemDataSet = itemFeed;
        this.result = result;
        notifyDataSetChanged();
        setLoaded();
    }

    public void clearAdapter() {
        result.clear();
        linearLayoutManager.scrollToPositionWithOffset(7, 20);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        return result.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_user_item, parent, false);
            return new ItemViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading_item, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder movieViewHolder = (ItemViewHolder) holder;

            movieViewHolder.tvName.setText(result.get(position).getName());

            movieViewHolder.tvSalePrice.setText(Float.toString(result.get(position).getSalePrice()));
            movieViewHolder.tvStock.setText(result.get(position).getStock());
            if (result.get(position).getThumbnailImage() == null)
                movieViewHolder.ivImage.setImageResource(R.mipmap.no_image);
            else

                Glide.with(context).load(result.get(position)
                        .getThumbnailImage()).into(movieViewHolder.ivImage);

        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return result == null ? 0 : result.size();
    }

    public void setLoaded() {
        isLoading = false;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivImage;
        private TextView tvName;
        private TextView tvSalePrice;
        private TextView tvStock;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            tvName = (TextView) itemView.findViewById(R.id.tvTitle);
            tvSalePrice = (TextView) itemView.findViewById(R.id.tvSalePrice);
            tvStock = (TextView) itemView.findViewById(R.id.tvStock);

            final Context context = itemView.getContext();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("*****",""+getAdapterPosition());

                    ((MainActivity) context).onItemClick(getAdapterPosition(), result);
                }
            });
        }
    }

    public static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }
}