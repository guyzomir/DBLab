package guy.dblab;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;


public class TaskProvider extends ContentProvider{
	DBHelper dbhelper;
	private static final String AUTHORITY =  "guy.dblab.TaskProvider";
	public static final Uri CONTENT_URI =  Uri.parse("content://" + AUTHORITY +"/tasks");
	private static final int ALL_TASKS = 10;
	private static final int SINGLE_TASK = 20;
private static final UriMatcher uriMatcher;
static{
	uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	uriMatcher.addURI(AUTHORITY, "tasks", ALL_TASKS);
	uriMatcher.addURI(AUTHORITY,"tasks/#", SINGLE_TASK);
	
}

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		
		int uriType = uriMatcher.match(uri);
		SQLiteDatabase sqlDB = dbhelper.getWritableDatabase();
		long id=0;
		switch (uriType) {
			case ALL_TASKS:
			id = sqlDB.insert(TaskTable.TABLE_TASKS, null, values);
			break;
			
			default:
			
			throw new IllegalArgumentException("Unknown URI:" + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return Uri.parse("TASK/" +id);
	
	
	
		
		
	}

	@Override
	public boolean onCreate() {
		dbhelper = new DBHelper(getContext());
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionargs,
			String sortOrder) {
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(TaskTable.TABLE_TASKS);
		int uriType=uriMatcher.match(uri);
		switch (uriType){
		case ALL_TASKS:
		break;
		case SINGLE_TASK:
			queryBuilder.appendWhere(TaskTable.COL_ID + "="
					+uri.getLastPathSegment()
					);
			break;
			default:
				throw new IllegalArgumentException("Unknown URI:" + uri);
		}
		SQLiteDatabase db =dbhelper.getWritableDatabase();
		Cursor cursor = queryBuilder.query(db, projection, selection, selectionargs, null, null, sortOrder);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	public int update(Uri uri, ContentValues values, String selection, String[]selectionArgs){
		SQLiteDatabase sqlDB = dbhelper.getWritableDatabase();
	int rowsUpdated=0;
	int uriType = uriMatcher.match(uri);
	switch(uriType){
	case SINGLE_TASK:
	String id = uri.getLastPathSegment();
	rowsUpdated = sqlDB.update(TaskTable.TABLE_TASKS, values, TaskTable.COL_ID + "="+ id, null);
	break;
	default:
		throw new IllegalArgumentException("Unknown URI:" +uri);
		
	}
	getContext().getContentResolver().notifyChange(uri, null);
	
	return rowsUpdated;
	}
	
	public int delete1(Uri uri, String selection, String [] selectionArgs){
		SQLiteDatabase sqlDB = dbhelper.getWritableDatabase();
		int rowsDeleted = 0;
		int uriType = uriMatcher.match(uri);
		switch(uriType){
		case ALL_TASKS:
		rowsDeleted = sqlDB.delete(TaskTable.TABLE_TASKS, selection, selectionArgs);
		break;
		
		case SINGLE_TASK:
			String id= uri.getLastPathSegment();
			if(TextUtils.isEmpty(selection)){
				rowsDeleted = sqlDB.delete(TaskTable.TABLE_TASKS,
						TaskTable.COL_ID+"=" +id, null);
			}else{
				rowsDeleted =  sqlDB.delete(TaskTable.TABLE_TASKS,
						TaskTable.COL_ID="="
						+id+"and"+ selection, selectionArgs);
				
				
			}
			break;
			default:
				throw new IllegalArgumentException("Unknnwn Uri:" +uri);
			}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsDeleted;
		
		}
	}
	

