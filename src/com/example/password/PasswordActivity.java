package com.example.password;

import java.security.MessageDigest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PasswordActivity extends Activity 
{

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_password);
		Button StartButton = (Button)findViewById(R.id.button1);
		StartButton.setOnClickListener(GenerateButtonListener);
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
		EditText PasswordText = (EditText) findViewById(R.id.password);
		PasswordText.setText("");
		EditText WebsiteText = (EditText) findViewById(R.id.website);
		WebsiteText.setText("");
	}
	View.OnClickListener GenerateButtonListener = new View.OnClickListener() 
	{
		
		@Override
		public void onClick(View v) 
		{
	      DoClickyStuff();
		}
	};		
	
	public void DoClickyStuff()
	{
		// Gets a handle to the clipboard service.
		ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
		
		String Input= ((EditText) findViewById(R.id.password)).getText().toString().trim();
		Input += ((EditText) findViewById(R.id.website)).getText().toString().trim();
		Input += "\n";
		String Password = SHA1(Input);
		Password = Password.substring(0, 10).toLowerCase();
		Toast.makeText(
		  getApplicationContext(), 
		  Password+ " copied to clipboard", 
		  Toast.LENGTH_SHORT).show();
		ClipData clip = ClipData.newPlainText("simple text", Password);
		clipboard.setPrimaryClip(clip);
	}
	
  String SHA1( String toHash )
  {
	    String hash = null;
	try
    {
        MessageDigest digest = MessageDigest.getInstance( "SHA-1" );
        byte[] bytes = toHash.getBytes("UTF-8");
        digest.update(bytes, 0, bytes.length);
        bytes = digest.digest();
        StringBuilder sb = new StringBuilder();
        for( byte b : bytes )
        {
            sb.append( String.format("%02X", b) );
        }
        hash = sb.toString();
    }
    catch( NoSuchAlgorithmException e )
    {
        e.printStackTrace();
    }
    catch( UnsupportedEncodingException e )
    {
        e.printStackTrace();
    }
    return hash;
  }

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
	  // Inflate the menu; this adds items to the action bar if it is present.
	  getMenuInflater().inflate(R.menu.password, menu);
      return true;
	}

}