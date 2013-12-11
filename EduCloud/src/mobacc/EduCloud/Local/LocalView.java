package mobacc.EduCloud.Local;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import mobacc.EduCloud.Access.AccessView;
import mobacc.EduCloud.Main.*;

public class LocalView extends Activity {
	
	private ActionBar myActionBar;
	private ActionBar.Tab tabLocalCM, tabLocalLP;
	private Fragment fragCourseMaterials, fragLessonPlans;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.local_view);
		
		//if(myDBAcct.someoneIsLoggedIn()){
			myActionBar = getActionBar();
			myActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
			tabLocalCM = myActionBar.newTab();
			tabLocalCM.setText(R.string.coursematerials_tab);
			tabLocalCM.setTabListener(new CourseMaterialsTabListener());
		
			tabLocalLP = myActionBar.newTab();
			tabLocalLP.setText(R.string.lessonplans_tab);
			tabLocalLP.setTabListener(new LessonPlansTabListener());
		
			fragCourseMaterials = new LocalCMView();
			fragLessonPlans = new LocalLPView();
		
			myActionBar.addTab(tabLocalCM);
			myActionBar.addTab(tabLocalLP);
		
			myActionBar.selectTab(tabLocalCM);
		//}
		/*else{
			Intent myIntent = new Intent(this,MainActivity.class);
			startActivity(myIntent);
		}*/
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		
		getMenuInflater().inflate(R.menu.local_menu, menu);
		
		return true;
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		super.onOptionsItemSelected(item);
		switch(item.getItemId()){
			case R.id.access_icon: access_iconClicked(); break; 
			case R.id.local_upload_icon: local_upload_iconClicked(); break;
			case R.id.local_search: local_searchClicked();break;
		}
		return true;
	}
	
	private void access_iconClicked(){
		Intent myIntent = new Intent(this,AccessView.class);
		startActivity(myIntent);
	}
	
	private void local_upload_iconClicked(){
		Intent myIntent = new Intent(this,UploadView.class);
		startActivity(myIntent);
	}
	
	private void local_searchClicked(){
		final EditText etSearch = new EditText(this);
		new AlertDialog.Builder(this)
	    .setTitle("Search")
	    .setMessage("Enter keyword here...")
	    .setView(etSearch)
	    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	         public void onClick(DialogInterface dialog, int whichButton) {
	             executeSearch(etSearch.getText().toString()); 
	             // deal with the editable
	         }
	    })
	    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	         public void onClick(DialogInterface dialog, int whichButton) {
	                // Do nothing.
	         }
	    }).show();
	}
	
	public void coursematerials_tabClicked(FragmentTransaction ft){
		ft.replace(R.id.local_fragment_container, fragCourseMaterials);
		//getFragmentManager().beginTransaction().replace(R.id.local_fragment_container,fragCourseMaterials).commit();
	}
	
	public void lessonplans_tabClicked(FragmentTransaction ft){
		ft.replace(R.id.local_fragment_container,fragLessonPlans);
		//getFragmentManager().beginTransaction().replace(R.id.local_fragment_container,fragLessonPlans).commit();
	}
	
	private void executeSearch(String keyword){
		if(myActionBar.getSelectedTab() == tabLocalCM){
			((LocalCMView)fragCourseMaterials).getSearchedLocalFiles(keyword);
		}
		else{
			((LocalLPView)fragLessonPlans).getSearchedLocalFiles(keyword);
		}
	}
	
	private class CourseMaterialsTabListener implements TabListener{

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			coursematerials_tabClicked(ft);
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private class LessonPlansTabListener implements TabListener{

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			lessonplans_tabClicked(ft);
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
