package mobacc.EduCloud.Local;

import java.io.File;
import java.util.ArrayList;

import mobacc.EduCloud.DAO.CourseMaterialsData;
import mobacc.EduCloud.DAO.LocalDatabaseObject;
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

public class LocalCMView extends Fragment {

	private final static String LOCAL_INTERNAL_DIRECTORY = "sdcard/Download/EduCloud/Course Materials/";
	
	private LocalDatabaseObject myLocalDAO;
	private LocalCMEntryAdapter myLCMEA;
	private ListView lv;
	private GridView gv;
	
	public LocalCMView(){
		createCMDirectory();
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view;
		if(isTablet(getActivity().getApplicationContext())){
			view = inflater.inflate(R.layout.local_cm_fragment_tablet_view,container,false);
			myLCMEA = new LocalCMEntryAdapter(getActivity().getApplicationContext());
			gv = (GridView) view.findViewById(R.id.gvLocalCM);
			gv.setAdapter(myLCMEA);
		}
		else{
			view = inflater.inflate(R.layout.local_cm_fragment_view,container,false);
			myLCMEA = new LocalCMEntryAdapter(getActivity().getApplicationContext());
			lv = (ListView) view.findViewById(R.id.lvLocalCM);
			lv.setAdapter(myLCMEA);
		}
		
		myLocalDAO = new LocalDatabaseObject(getActivity().getApplicationContext());
		
		
		this.getLocalFiles();
		
		return view;
	}
	
	public void createCMDirectory(){
		File directory = new File(LOCAL_INTERNAL_DIRECTORY);
		directory.mkdirs();
	}

	private void getLocalFiles(){
		//ArrayList<CourseMaterialsData> alCMDList = myDBCourseMaterials.getLocalCMList();
		ArrayList<CourseMaterialsData> alCMDList = myLocalDAO.getLocalCMList();
		if(alCMDList != null){
			myLCMEA.addMultipleEntries(alCMDList);
		}
	}
	
	public void getSearchedLocalFiles(String keyword){
		ArrayList<CourseMaterialsData> alCMDList = myLocalDAO.searchLocalCM(keyword);
		if(alCMDList != null){
			myLCMEA.addMultipleEntries(alCMDList);
		}
	}
	
	public boolean isTablet(Context context) {
	    boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
	    boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
	    return (xlarge || large);
	}
	
}
