package mobacc.EduCloud.Access;

import mobacc.EduCloud.Local.LocalView;
import mobacc.EduCloud.Main.R;
import mobacc.EduCloud.Main.UploadView;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;


public class AccessView extends Activity {
	
	private ActionBar myActionBar;
	private ActionBar.Tab tabAccessCM, tabAccessLP;
	private Fragment fragCourseMaterials, fragLessonPlans;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.access_view);
		
		myActionBar = getActionBar();
		myActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		tabAccessCM = myActionBar.newTab();
		tabAccessCM.setText(R.string.coursematerials_tab);
		tabAccessCM.setTabListener(new CourseMaterialsTabListener());
		
		tabAccessLP = myActionBar.newTab();
		tabAccessLP.setText(R.string.lessonplans_tab);
		tabAccessLP.setTabListener(new LessonPlansTabListener());
		
		fragCourseMaterials = new AccessCMView();
		fragLessonPlans = new AccessLPView();
		
		myActionBar.addTab(tabAccessCM);
		myActionBar.addTab(tabAccessLP);
		
		myActionBar.selectTab(tabAccessCM);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		
		getMenuInflater().inflate(R.menu.access_menu, menu);
		
		return true;
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		myActionBar.selectTab(tabAccessCM);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		super.onOptionsItemSelected(item);
		switch(item.getItemId()){
			case R.id.home_icon: home_iconClicked(); break; 
			case R.id.access_upload_icon: access_upload_iconClicked(); break;
			case R.id.access_search_icon:access_search_iconClicked();break;
		}
		return true;
	}
	
	private void home_iconClicked(){
		Intent myIntent = new Intent(this,LocalView.class);
		startActivity(myIntent);
	}
	
	private void access_upload_iconClicked(){
		Intent myIntent = new Intent(this,UploadView.class);
		startActivity(myIntent);
	}
	
	private void access_search_iconClicked(){
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
	
	private void executeSearch(String keyword){
		if(myActionBar.getSelectedTab() == tabAccessCM){
			((AccessCMView)fragCourseMaterials).getSearchedAccessFiles(keyword);
		}
		else{
			((AccessLPView)fragLessonPlans).getSearchedAccessFiles(keyword);
		}
	}

	public void coursematerials_tabClicked(FragmentTransaction ft){
		ft.replace(R.id.access_fragment_container, fragCourseMaterials);
	}
	
	public void lessonplans_tabClicked(FragmentTransaction ft){
		ft.replace(R.id.access_fragment_container,fragLessonPlans);
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
