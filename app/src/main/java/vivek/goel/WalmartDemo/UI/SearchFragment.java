package vivek.goel.WalmartDemo.UI;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import vivek.goel.WalmartDemo.R;
import vivek.goel.WalmartDemo.Service.ItemSearchService;
import vivek.goel.WalmartDemo.listener.OnLoadMoreListener;
import vivek.goel.WalmartDemo.models.DataModel.ItemFeed;
import vivek.goel.WalmartDemo.models.DataModel.Items;

/**
 * Created by vivekgoel on 5/10/17.
 */

public class SearchFragment extends Fragment {
    LinearLayoutManager linearLayoutManager;
    private RecyclerView mRecyclerView;
    private ItemAdapter mAdapter;
    private SearchView searchView;
    private ItemFeed mItemSet;
    private ArrayList<Items> result;
    private static String nextPage = "";
    ProgressDialog progressDialog;
    private boolean flag = false;
    public String searchQuery;
    public MyBroadcastReceiver broadcastReceiver;
    public Toast toast;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView.setHasFixedSize(false);
    }

    @Override
    public void onResume() {
        super.onResume();

        broadcastReceiver = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("vivek.goel.DATA_LOADED");
        getActivity().registerReceiver(broadcastReceiver, filter);

        if (isConnected() && flag) {
            result.clear();
            mAdapter.clearAdapter();
            mItemSet = null;
            nextPage = "";
            getData(searchQuery, "");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("Query", searchView.getQuery().toString());
        outState.putString("nextPage", nextPage);
        outState.putSerializable("result", result);
    }

    @Override
    public void onPause() {
        super.onPause();
        flag = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        flag = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Processing...");
        progressDialog.setCancelable(false);
        toast = new Toast(getActivity());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        View view = inflater.inflate(R.layout.recycler_list, container, false);
        result = new ArrayList<>();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvlistItem);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mAdapter = new ItemAdapter(getActivity(), mRecyclerView, linearLayoutManager, result);

        mRecyclerView.setAdapter(mAdapter);

        if (savedInstanceState != null) {
            searchQuery = savedInstanceState.getString("Query");
            nextPage = savedInstanceState.getString("nextPage");
            result = (ArrayList<Items>) savedInstanceState.getSerializable("result");
            mAdapter.setData(result, null);
        } else {
            Log.d("#######","Inside first getdata");
            getData(null, "");
        }
        searchView = (SearchView) view.findViewById(R.id.search);
        searchView.setQueryHint("Search Brand");
        searchView.setBackgroundColor(Color.parseColor("#c9c9c9"));
        if (isConnected()) {
            if (getArguments() != null && searchQuery != null) {
                searchQuery = getArguments().getString("Query");
                result.clear();
                mAdapter.clearAdapter();
                mItemSet = null;
                nextPage = "";
                getData(searchQuery, nextPage);
            }
        } else
            buildDialog(getActivity()).show();

        //search the query
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String submittedQuery) {
                if (isConnected()) {
                    progressDialog.show();
                    result.clear();
                    mAdapter.clearAdapter();
                    mItemSet = null;
                    nextPage = "";
                    searchQuery = submittedQuery;
                    getData(searchQuery, nextPage);
                } else
                    buildDialog(getActivity()).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        //Load more items on scroll
        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void onLoadMore() {
                Log.d("#############", "onloadmore");

                if (nextPage != null && !nextPage.equals("")) {

                    result.add(null);

                    //Load more data for reyclerview
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("haint", "Load More 2");

                            //Remove loading item
                            if (result.size() > 1)
                                result.remove(result.size() - 1);
                            getData(searchQuery, nextPage);
                        }
                    }, 1);
                }
            }
        });
        return view;
    }

    public void getData(String searchQuery, String page) {

        if(page != null && isConnected()) {
            progressDialog.show();

            Intent intent = new Intent(getActivity(), ItemSearchService.class);
            Bundle outState = new Bundle();
            outState.putString("Query", searchQuery);
            outState.putString("nextPage", page);
            intent.putExtra("bundle", outState);
            getActivity().startService(intent);
        }
        else
            buildDialog(getActivity()).show();
    }

    private class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action.equals("vivek.goel.DATA_LOADED")) {

                mItemSet = (ItemFeed) intent.getSerializableExtra("itemFeed");

                if (result != null && mItemSet.items != null) {
                    for (int i = 0; i < mItemSet.items.size(); i++) {
                        result.add(mItemSet.items.get(i));
                    }
                    nextPage = mItemSet.getNextPage();
                    mAdapter.setData(result, mItemSet);
                }
                if(mItemSet.items == null)
                    nextPage = null;

                progressDialog.dismiss();

            }
        }
    }

    //Checking Internet Connection
    public boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    public AlertDialog.Builder buildDialog(Context c) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet connection.");
        builder.setMessage("You have no internet connection");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        return builder;
    }

}