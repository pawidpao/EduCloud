package mobacc.EduCloud.Access;

import java.util.ArrayList;

import mobacc.EduCloud.Comment.CommentView;
import mobacc.EduCloud.DAO.CourseMaterialsData;
import mobacc.EduCloud.Main.R;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AccessCMEntryAdapter extends BaseAdapter {

	private Context myContext;
	private ArrayList<CourseMaterialsData> alCMList = new ArrayList<CourseMaterialsData>();
	
	public AccessCMEntryAdapter(Context context){
		myContext = context;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return alCMList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return alCMList.get(position);
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
		TextView tvTitle = (TextView)view.findViewById(R.id.tvTitle);
		tvTitle.setText(alCMList.get(position).getTitle());
		TextView tvTags = (TextView)view.findViewById(R.id.tvTags);
		tvTags.setText(alCMList.get(position).getTags());
		view.setOnClickListener(new AccessCourseMaterialChoiceClickListener());
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
	
	public class AccessCourseMaterialChoiceClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int position = Integer.parseInt(String.valueOf(v.getTag()));
			CourseMaterialsData cmd = alCMList.get(position);
			Intent myIntent = new Intent(myContext,CommentView.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			myIntent.putExtra("type","Course Materials");
			myIntent.putExtra("data",cmd);
			myContext.startActivity(myIntent);
		}
		
	}
}
