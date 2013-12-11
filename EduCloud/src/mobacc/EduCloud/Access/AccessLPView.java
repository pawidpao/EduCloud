package mobacc.EduCloud.Access;

import java.io.File;
import java.util.ArrayList;

import mobacc.EduCloud.DAO.AccessDatabaseObject;
import mobacc.EduCloud.DAO.LessonPlansData;
import mobacc.EduCloud.Main.R;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

public class AccessLPView extends Fragment {
	
	private final static String ACCESS_INTERNAL_DIRECTORY = "sdcard/Download/EduCloud/Access/Lesson Plans/";
	
	private AccessDatabaseObject myAccessDAO;
	private AccessLPEntryAdapter myALPEA;
	private ListView lv;
	private GridView gv;
	
	public AccessLPView(){
		createLPDirectory();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view;
		if(isTablet(getActivity().getApplicationContext())){//Device is tablet
			view = inflater.inflate(R.layout.local_lp_fragment_tablet_view,container,false);
			myALPEA = new AccessLPEntryAdapter(getActivity().getApplicationContext());
			gv = (GridView)view.findViewById(R.id.gvLocalLP);
			gv.setAdapter(myALPEA);
		}
		else{
			view = inflater.inflate(R.layout.local_lp_fragment_view,container,false);
			myALPEA = new AccessLPEntryAdapter(getActivity().getApplicationContext());
			lv = (ListView)view.findViewById(R.id.lvLocalLP);
			lv.setAdapter(myALPEA);
		}
		myAccessDAO = new AccessDatabaseObject(getActivity().getApplicationContext());
		
		this.getAccessFiles();
		
		return view;
		
	}
	
	private void createLPDirectory(){
		File directory = new File(ACCESS_INTERNAL_DIRECTORY);
		directory.mkdirs();
	}
	
	private void getAccessFiles(){
		//ArrayList<LessonPlansData> alLPDList = myDBLessonPlans.getLocalLPList();
		ArrayList<LessonPlansData> alLPDList = myAccessDAO.getAccessLPList();
		if(alLPDList != null){
			myALPEA.addMultipleEntries(alLPDList);
		}
	}
	
	public void getSearchedAccessFiles(String keyword){
		ArrayList<LessonPlansData> alLPDList = myAccessDAO.searchAccessLP(keyword);
		if(alLPDList != null){
			myALPEA.addMultipleEntries(alLPDList);
		}
	}
	
	public boolean isTablet(Context context) {
	    boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
	    boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
	    return (xlarge || large);
	}
	
}
