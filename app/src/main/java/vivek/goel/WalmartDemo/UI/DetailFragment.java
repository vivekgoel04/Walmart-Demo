package vivek.goel.WalmartDemo.UI;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import vivek.goel.WalmartDemo.R;
import vivek.goel.WalmartDemo.models.DataModel.Items;

/**
 * Created by vivekgoel on 5/10/17.
 */

public class DetailFragment extends android.app.Fragment {
    private ArrayList<Items> result;
    int position;
    View view = null;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
        if (getArguments() != null) {
            position = getArguments().getInt("position");
            result = (ArrayList<Items>) getArguments().getSerializable("result");
        }
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.view_pager, container, false);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) view.findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(position);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private class ScreenSlidePagerAdapter extends android.support.v13.app.FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.app.Fragment getItem(int i) {
            return ScreenSlidePageFragment.create(i-1, result);
        }

        @Override
        public int getCount() {
            return result.size();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

//         Save the current article selection in case we need to recreate the fragment
        outState.putSerializable("result",result);
        outState.putInt("position",position);
    }
}