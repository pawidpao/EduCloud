package mobacc.EduCloud.Local;

import java.io.File;
import java.util.ArrayList;

import mobacc.EduCloud.DAO.CourseMaterialsData;
import mobacc.EduCloud.Main.R;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LocalCMEntryAdapter extends BaseAdapter{

	private ArrayList<CourseMaterialsData> alCMList = new ArrayList<CourseMaterialsData>();
	
	private Context myContext;
	
	public LocalCMEntryAdapter(Context myContext){
		this.myContext = myContext;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return alCMList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return alCMList.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = convertView;
		if(view == null){
			LayoutInflater inflater = (LayoutInflater)myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.cm_file_choice_layout,parent,false);
			
		}
		view.setTag(position);
		view.setOnClickListener(new LocalCourseMaterialsEntryClickListener());
		TextView tvTitle = (TextView)view.findViewById(R.id.tvTitle);
		tvTitle.setText(alCMList.get(position).getTitle());
		TextView tvTags = (TextView)view.findViewById(R.id.tvTags);
		tvTags.setText(alCMList.get(position).getTags());
		
		return view;
	}
	
	public void addEntry(CourseMaterialsData newCMData){
		alCMList.add(newCMData);
		notifyDataSetChanged();
	}
	
	public void addMultipleEntries(ArrayList<CourseMaterialsData> newCMList){
		alCMList = newCMList;
		notifyDataSetChanged();
	}
	
	public void removeAll(){
		alCMList.clear();
		notifyDataSetChanged();
	}
	
	public class LocalCourseMaterialsEntryClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int position = Integer.parseInt(String.valueOf(v.getTag()));
			File targetFile = new File(alCMList.get(position).getPath());
		    Uri targetUri = Uri.fromFile(targetFile);
		      
		    Intent intent;
		    intent = new Intent(Intent.ACTION_VIEW).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		    intent.setDataAndType(targetUri, "application/pdf");
		      
		    myContext.startActivity(intent);
			
		}
		
	}
	

}
