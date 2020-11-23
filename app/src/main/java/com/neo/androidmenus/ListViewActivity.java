package com.neo.androidmenus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class ListViewActivity extends AppCompatActivity {
    private static final String TAG = "ListViewActivity";

    private ListView mMainListView;
    private ArrayAdapter<String> mListAdapter;
    ArrayList<String> mPlansList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        mMainListView = findViewById(R.id.mainListView);

        // creates and populates a list of planer shapes
        String[] planets = new String[]{"Mercury", "Venus", "Earth", "Mars", "Jupiter",
                "Saturn", "Uranus", "Neptune"};
        mPlansList = new ArrayList<>();
        mPlansList.addAll(Arrays.asList(planets));

        mListAdapter = new ArrayAdapter<>(this, R.layout.list_simplerow, mPlansList);
        mMainListView.setAdapter(mListAdapter);

        mMainListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);    // allows selection of more than one item from the listView
        mMainListView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                // to something everyTime and item is selected and unselected from listView
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater menuInflater = getMenuInflater();
                menuInflater.inflate(R.menu.listview_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                // called when an item is clicked
                switch (item.getItemId()) {
                    case R.id.listDelete:
                        deleteSelectedItems();   // del selected items
                        mode.finish();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // free up resources
            }
        });
    }


    /**
     * deletes selected items from the listView
     */
    private void deleteSelectedItems() {
        SparseBooleanArray checkedItemPositions = mMainListView.getCheckedItemPositions();    // gets the checked items from the listView

        int itemCount = mMainListView.getCount();
        for (int i = itemCount - 1; i >= 0; i--) {
            if (checkedItemPositions.get(i)) {          // checks if the item at instant pos has been checked
                mListAdapter.remove(mPlansList.get(i));
            }
        }
        checkedItemPositions.clear();                 // clears from mapping to free up space and reorganise mapping
        mListAdapter.notifyDataSetChanged();


    }
}