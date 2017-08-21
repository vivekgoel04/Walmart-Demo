package vivek.goel.WalmartDemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import vivek.goel.WalmartDemo.UI.DetailFragment;
import vivek.goel.WalmartDemo.UI.SearchFragment;
import vivek.goel.WalmartDemo.models.DataModel.Items;

/**
 * Main activity for the application ,
 *
 * Host 2 fragments SearchFragment and DetailFragment
 *
 * @author Vivek
 */

public class MainActivity extends AppCompatActivity {

    public String TAG = "MainActivity";
    private boolean loading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(vivek.goel.WalmartDemo.R.layout.search_layout);

        // Check whether the activity is using the layout version with
        // the fragment_container FrameLayout. If so, we must add the first fragment
        if (findViewById(vivek.goel.WalmartDemo.R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create an instance of SearchFragment
            SearchFragment searchFragment = new SearchFragment();

            // In case this activity was started with special instructions from an Intent,
            // pass the Intent's extras to the fragment as arguments
            searchFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(vivek.goel.WalmartDemo.R.id.fragment_container, searchFragment).commit();
        }
    }

    public void onItemClick(int position, ArrayList<Items> result) {
        // Capture the detail fragment from the activity layout
        DetailFragment detailFragment = (DetailFragment) getFragmentManager()
                .findFragmentById(vivek.goel.WalmartDemo.R.id.detail_fragment);

        if (detailFragment != null) {
//             If detail frag is available, we're in two-pane layout...
//             Call a method in the DetailFragment to update its content
//            detailFragment.setData(result.get(position));
        } else {
            // If the frag is not available, we're in the one-pane layout and must swap frags...

            // Create fragment and give it an argument for the selected item
            DetailFragment newDetailFragment = new DetailFragment();
            Bundle args = new Bundle();
            args.putInt("position", position);
            args.putSerializable("result", result);
            newDetailFragment.setArguments(args);
            android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(vivek.goel.WalmartDemo.R.id.fragment_container, newDetailFragment);

            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_home:

                // Check whether the activity is using the layout version with
                // the fragment_container FrameLayout. If so, we must add the first fragment
                if (findViewById(vivek.goel.WalmartDemo.R.id.fragment_container) != null) {

                    // Create an instance of SearchFragment
                    SearchFragment searchFragment = new SearchFragment();

                    // In case this activity was started with special instructions from an Intent,
                    // pass the Intent's extras to the fragment as arguments
                    searchFragment.setArguments(getIntent().getExtras());

                    // Add the fragment to the 'fragment_container' FrameLayout
                    getSupportFragmentManager().beginTransaction()
                            .add(vivek.goel.WalmartDemo.R.id.fragment_container, searchFragment).commit();
                }
        }
        return (super.onOptionsItemSelected(menuItem));
    }
}