package mobacc.EduCloud.Access;

import java.io.File;
import java.util.ArrayList;

import mobacc.EduCloud.DAO.AccessDatabaseObject;
import mobacc.EduCloud.DAO.CourseMaterialsData;
import mobacc.EduCloud.Main.*;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

public class AccessCMView extends Fragment {
	
	private final static String ACCESS_INTERNAL_DIRECTORY = "sdcard/Download/EduCloud/Access/Course Materials/";
	
	private AccessDatabaseObject myAccessDAO;
	private AccessCMEntryAdapter myACMEA;
	private ListView lv;
	private GridView gv;
	
	public AccessCMView(){
		createCMDirectory();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view;
		if(isTablet(getActivity().getApplicationContext())){
			view = inflater.inflate(R.layout.local_cm_fragment_tablet_view,container,false);
			myACMEA = new AccessCMEntryAdapter(getActivity().getApplicationContext());
			gv = (GridView) view.findViewById(R.id.gvLocalCM);
			gv.setAdapter(myACMEA);
		}
		else{
			view = inflater.inflate(R.layout.local_cm_fragment_view,container,false);
			myACMEA = new AccessCMEntryAdapter(getActivity().getApplicationContext());
			lv = (ListView) view.findViewById(R.id.lvLocalCM);
			lv.setAdapter(myACMEA);
		}
		
		myAccessDAO = new AccessDatabaseObject(getActivity().getApplicationContext());
		
		
		this.getAccessFiles();
		
		return view;
		
	}
	
	private void createCMDirectory(){
		File directory = new File(ACCESS_INTERNAL_DIRECTORY);
		directory.mkdirs();
	}
	
	private void getAccessFiles(){
		//ArrayList<LessonPlansData> alLPDList = myDBLessonPlans.getLocalLPList();
		ArrayList<CourseMaterialsData> alCMDList = myAccessDAO.getAccessCMList();
		if(alCMDList != null){
			myACMEA.addMultipleEntries(alCMDList);
		}
	}
	
	public void getSearchedAccessFiles(String keyword){
		ArrayList<CourseMaterialsData> alCMDList = myAccessDAO.searchAccessCM(keyword);
		if(alCMDList != null){
			myACMEA.addMultipleEntries(alCMDList);
		}
	}
	
	public boolean isTablet(Context context) {
	    boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
	    boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
	    return (xlarge || large);
	}
	
}
