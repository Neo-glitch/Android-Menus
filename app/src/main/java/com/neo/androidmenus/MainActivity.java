package com.neo.androidmenus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private static final String TAG = "MainActivity";
    ActionMode mActionMode;                // obj needed to show and hide the actionMode contextual menu


    ////// needed for implementation of actionMode menu ///////
    private ActionMode.Callback mActionModeCallBack = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.actionmode_context_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()){
                case R.id.actionMode1:
                    Toast.makeText(getApplicationContext(), "Action Mode 1 clicked", Toast.LENGTH_LONG).show();
                    mode.finish();
                    return true;
                case R.id.actionMode2:
                    Toast.makeText(getApplicationContext(), "Action Mode 2 clicked", Toast.LENGTH_LONG).show();
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textTitle = findViewById(R.id.txtTitle);
        this.registerForContextMenu(textTitle);                 // reg view for the floating context menu to be associated with it

        // associate button with the action mode menu
        Button button = findViewById(R.id.btnActionModeMenu);
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(mActionMode != null){    // logic prevents actionMode obj from being used before instantiating it.
                    return false;
                }
                mActionMode = startActionMode(mActionModeCallBack);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu, menu);

        // dynamically created menu implementation(intent based menus)
        Intent intent = new Intent(Intent.ACTION_SEND);      // intent action is to send
        intent.setType("text/plain");                       // text to be shared is plain text
        intent.putExtra(intent.EXTRA_TEXT, "check out the menu course app");

        // search and fills the menu with applications offering what intent needs
        menu.addIntentOptions(R.id.intent_group,        // menu group to which the items will be added
                0, 0,
                this.getComponentName(), null, intent, 0, null);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // logic unChecks checked item and checks unchecked items
        if(item.isChecked()) item.setChecked(false);
        else  item.setChecked(true);

        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.menuItem1:
                Toast.makeText(this, "Menu item 1 Clicked", Toast.LENGTH_LONG).show();
                return true;
            case R.id.menuItem2:
                Toast.makeText(this, "Menu item 2 Clicked", Toast.LENGTH_LONG).show();
                return true;
            case R.id.menuItem3:
                Toast.makeText(this, "Menu item 3 Clicked", Toast.LENGTH_LONG).show();
                if(item.isChecked()){
                    Toast.makeText(this, "It's checked", Toast.LENGTH_LONG).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /*
        For floating Context menu
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.floating_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.contextMenu1:
                Toast.makeText(this, "floating context menu 1 clicked", Toast.LENGTH_LONG).show();
                return true;
            case R.id.contextMenu2:
                Toast.makeText(this, "floating context menu 2 clicked", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    /*
        Floating Context menu implementation
     */


    // handles showing up the popUp Menu
    public void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);                    // init PopMenu obj
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.popupMenu1:
                Toast.makeText(this, "PopupMenu 1 clicked", Toast.LENGTH_LONG).show();
                return true;
            case R.id.popupMenu2:
                Toast.makeText(this, "PopUpMenu 2 clicked", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }

    }

    public void showListViewActivity(View view) {
        Intent intent = new Intent(this, ListViewActivity.class);
        startActivity(intent);
    }
}