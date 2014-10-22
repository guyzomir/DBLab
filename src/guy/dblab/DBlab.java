package guy.dblab;


import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.widget.TextView;

public class DBlab extends Activity {
TextView Hey;
DBHelper dbhelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dblab);
		Hey =(TextView)findViewById(R.id.Hello);
		
		
		
		
		
		Uri taskUri = Uri.parse(TaskProvider.CONTENT_URI+ "/1");
		ContentValues newsValues = new ContentValues();
		newsValues.put(TaskTable.COL_DESCRIPTION,"New Task name");
		newsValues.put(TaskTable.COL_PRIORITY,"1");
		newsValues.put(TaskTable.COL_DONE, 0);
		getContentResolver().update(taskUri, newsValues, null, null);
		
		Uri result = getContentResolver().insert(TaskProvider.CONTENT_URI, newsValues);
		Hey.setText(result.toString());
		
		Uri uri = Uri.parse(TaskProvider.CONTENT_URI+"/1");
		int rowsDeleted = getContentResolver().delete(uri, null, null);
		Hey.setText(rowsDeleted+ "row deleted");
		
		//String[]projection ={TaskTable.COL_ID, TaskTable.COL_PRIORITY, TaskTable.COL_DESCRIPTION};
		//Cursor cursor =
				//getContentResolver().query(uri, projection, null, null, null);
		//String queryResult="";
		//if(cursor ==null){
			//queryResult = "not found";
			
		//}else if(cursor.getCount()==1){
			//cursor.moveToNext();
			//queryResult = cursor.getString(2);
		//}else{
			//queryResult = "error";
		//}
		//Hey.setText(queryResult);
		
		
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dblab, menu);
		return true;
	}

}
