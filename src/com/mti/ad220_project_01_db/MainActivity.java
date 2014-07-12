/*
 * MainActivity.java
 * Daniel Borodenko
 */
package com.mti.ad220_project_01_db;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity {

	 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        
        setContentView(new MyView(this));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
        	
			Intent settings = new Intent(this, SettingsActivity.class);
			startActivityForResult(settings, 0);
            return true;
        }
        
        else if (id == R.id.action_about) {
        	
			Intent settings = new Intent(this, AboutActivity.class);
			startActivity(settings);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
 
    		setContentView(new MyView(this));
    }
 
    
}
