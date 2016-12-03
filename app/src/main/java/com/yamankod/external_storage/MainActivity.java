package com.yamankod.external_storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	private EditText editText;
	private Button writeButton, clearButton, readButton, closeButton;
	final static int PERMISSIONS_REQUEST_CODE = 1;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		getPermissionReadExternalStorage();
		getPermissionWriteExternalStorage();

		editText = (EditText) findViewById(R.id.txtData);
		writeButton = (Button) findViewById(R.id.btnWriteSDFile);
		writeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					File root = Environment.getExternalStorageDirectory();
					File myFile = new File(root, "mysdfile.txt");
					myFile.createNewFile();
					FileOutputStream fOut = new FileOutputStream(myFile, true);
					OutputStreamWriter myOutWriter = new OutputStreamWriter(
							fOut);
					myOutWriter.append(editText.getText());
					myOutWriter.close();
					Toast.makeText(getBaseContext(),
							"Done writing SD 'mysdfile.txt'",
							Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					Toast.makeText(getBaseContext(), e.getMessage(),
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		readButton = (Button) findViewById(R.id.btnReadSDFile);
		readButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					File myFile = new File("/sdcard/mysdfile.txt");
					FileInputStream fIn = new FileInputStream(myFile);
					BufferedReader myReader = new BufferedReader(
							new InputStreamReader(fIn));
					String aDataRow = "";
					String aBuffer = "";
					while ((aDataRow = myReader.readLine()) != null) {
						aBuffer += aDataRow + "\n";
					}
					editText.setText(aBuffer);
					myReader.close();
					Toast.makeText(getBaseContext(),
							"Done reading SD 'mysdfile.txt'", 1).show();
				} catch (Exception e) {
					Toast.makeText(getBaseContext(), e.getMessage(), 1).show();
				}
			}
		});
		clearButton = (Button) findViewById(R.id.btnClearScreen);
		clearButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				editText.setText("");
			}
		});
		closeButton = (Button) findViewById(R.id.btnClose);
		closeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	public void getPermissionReadExternalStorage() {
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
				!= PackageManager.PERMISSION_GRANTED) {


			if (shouldShowRequestPermissionRationale(
					Manifest.permission.READ_EXTERNAL_STORAGE)) {
			}
			requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
					PERMISSIONS_REQUEST_CODE);
		}
	}

	public void getPermissionWriteExternalStorage() {
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
				!= PackageManager.PERMISSION_GRANTED) {


			if (shouldShowRequestPermissionRationale(
					Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
			}
			requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
					PERMISSIONS_REQUEST_CODE);
		}
	}




	@Override
	public void onRequestPermissionsResult(int requestCode,
										   @NonNull String permissions[],
										   @NonNull int[] grantResults) {
		if (requestCode == PERMISSIONS_REQUEST_CODE) {
			if (grantResults.length == 1 &&
					grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
			}
		} else {
			super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		}
	}

}