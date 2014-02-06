package ualberta.cmput301.camerademo;



import java.io.File;
import java.util.Date;

import ualberta.cmput301.camerodemo.R;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class CameraDemoActivity extends Activity {

	private TextView textView;
	private ImageButton imageButton;
	private Uri imageFileUri;
	private String file;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camero_demo);
		
		// Retrieve handlers
		textView = (TextView) findViewById(R.id.status);
		imageButton = (ImageButton) findViewById(R.id.image);
		
		// Set up the listener
		OnClickListener listener = new OnClickListener() {
			public void onClick(View view) {
				takeAPhoto(); // implement this method
			}
		};
		// Register a callback to be invoked when this view is clicked
		imageButton.setOnClickListener(listener);
		
		
	}


	// Implement takeAPhoto() method to allow you to take a photo when you click the ImageButton.
	// Notice that startActivity() method will not return any result when the launched activity 
	// finishes, while startActivityForResult() method will. To retrieve the returned result, you may 
	// need implement onAcitityResult() method.
	public void takeAPhoto() {
		// To Do		
		Date date = new Date();
		file = Environment.getExternalStorageDirectory().getAbsolutePath() + "/temp/" + date.toString() + ".jpg";
		File _file = new File(file);
		imageFileUri = Uri.fromFile(_file);
		
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra("MediaStoreEXTRA_OUTPUT", imageFileUri);
		startActivityForResult(intent, 0);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// To Do
		
		
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				Bitmap bm = (Bitmap) data.getExtras().getParcelable("data");
				//Bitmap bMap = BitmapFactory.decodeStream(data.getExtras());
				Bitmap newbm = Bitmap.createScaledBitmap(bm, imageButton.getWidth(), imageButton.getHeight(), false);
				imageButton.setImageBitmap(newbm);
				textView.setText("Photo OK");
			} else if(resultCode == RESULT_CANCELED) {
				textView.setText("Photo Cancelled");
			} else {
				textView.setText("Not sure what happened.");
			}
		}
	}	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.camero_demo, menu);
		return true;
	}

}
