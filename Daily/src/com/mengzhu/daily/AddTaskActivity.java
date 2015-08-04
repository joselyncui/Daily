package com.mengzhu.daily;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.mengzhu.daily.db.DailyDataSource;
import com.mengzhu.daily.entity.Task;
import com.mengzhu.daily.view.TextSeekBar;

public class AddTaskActivity extends Activity implements OnClickListener{
	private static final String TAG = "AddTaskActivity";
	
	private ImageView saveImageView;
	private ImageView backImageView;
	
	private EditText comTextView;
	private TextSeekBar levelSeekBar;
	private TextSeekBar hourSeekBar;
	private Button delButton;
	private DailyDataSource dailyDataSource;
	private Context context;
	private Task task;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_task);
		context = this;
		
		comTextView = (EditText) findViewById(R.id.task_title);
		levelSeekBar = (TextSeekBar) findViewById(R.id.level_seekbar);
		hourSeekBar = (TextSeekBar) findViewById(R.id.time_seekbar);
		saveImageView = (ImageView) findViewById(R.id.toolbar_action_img);
		backImageView = (ImageView) findViewById(R.id.toolbar_back_img);
		delButton = (Button) findViewById(R.id.btn_delete);
		
		levelSeekBar.setMax(3);
		hourSeekBar.setMax(5);
		
		dailyDataSource = DailyDataSource.getInstance(this);
		
		saveImageView.setOnClickListener(this);
		backImageView.setOnClickListener(this);
		delButton.setOnClickListener(this);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		
		if (bundle != null) {
			Gson gson = new Gson();
			String taskGson = bundle.getString(Task.GSON_KEY);
			task = gson.fromJson(taskGson, Task.class);
			delButton.setVisibility(View.VISIBLE);
			
			comTextView.setText(task.getComment());
			levelSeekBar.setProgress(task.getLevel());
			hourSeekBar.setProgress((int)task.getHours());
		}
		
		setNotification();
	}
	
	private void setNotification(){
		 // 在Android进行通知处理，首先需要重系统哪里获得通知管理器NotificationManager，它是一个系统Service。  
       NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);  
    // 创建一个PendingIntent，和Intent类似，不同的是由于不是马上调用，需要在下拉状态条出发的activity，所以采用的是PendingIntent,即点击Notification跳转启动到哪个Activity  
       PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,  
               new Intent(this, MainActivity.class), 0);  
       // 下面需兼容Android 2.x版本是的处理方式  
       // Notification notify1 = new Notification(R.drawable.message,  
       // "TickerText:" + "您有新短消息，请注意查收！", System.currentTimeMillis());  
       Notification notify1 = new Notification();  
       notify1.tickerText = "TickerText:您有新短消息，请注意查收！";  
       notify1.when = System.currentTimeMillis();  
       notify1.setLatestEventInfo(this, "Notification Title",  
               "This is the notification message", pendingIntent);  
       notify1.number = 1;  
       notify1.flags |= Notification.FLAG_AUTO_CANCEL; // FLAG_AUTO_CANCEL表明当通知被用户点击时，通知将被清除。  
       // 通过通知管理器来发起通知。如果id不同，则每click，在statu那里增加一个提示  
       manager.notify(-1, notify1);  
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.toolbar_action_img:
			addTask();
			break;
		case R.id.toolbar_back_img:
			finish();
			break;
		case R.id.btn_delete:
			deleteTask(task);
			finish();
			break;
		default:
			break;
		}
		
	}
	
	
	
	public static void start(Activity activity, Task task) {
		Intent intent = new Intent(activity, AddTaskActivity.class);
		
		if (task != null) {
			Gson gson = new Gson();
			String taskGson = gson.toJson(task);
			System.out.println(taskGson);
			intent.putExtra(Task.GSON_KEY, taskGson);
		}
		
		activity.startActivity(intent);
	}
	
	private void addTask(){
		if (task == null) {
			task = new Task();
		}
		
		task.setComment(comTextView.getText().toString());
		task.setIsOpen(Task.OPEN);
		task.setLevel((int)levelSeekBar.getProgress());
		task.setHours(hourSeekBar.getProgress());
		
		if (task.getId() != 0) {
			dailyDataSource.updateTask(task);
		} else {
			dailyDataSource.addTask(task);
		}
		
		finish();
	}
	
	
	private void deleteTask(Task taskId) {
		dailyDataSource.deleteTask(task);
	}
	
}
