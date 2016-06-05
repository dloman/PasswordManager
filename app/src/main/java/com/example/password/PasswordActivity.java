package com.example.password;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PasswordActivity extends FragmentActivity implements StartLengthPickerDialog.NumberPickerDialogListener
{
  public static final String PREFS_NAME = "passwordrc";
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_password);
    Button StartButton = (Button)findViewById(R.id.button1);
    StartButton.setOnClickListener(GenerateButtonListener);
    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
    mLength = settings.getInt("Length", 10);
    mStartPosition = settings.getInt("StartPosition", 0);
  }

  @Override
  protected void onStop()
  {
    super.onStop();

    // We need an Editor object to make preference changes.
    // All objects are from android.context.Context
    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
    SharedPreferences.Editor editor = settings.edit();
    editor.putInt("Length", mLength);
    editor.putInt("StartPosition", mStartPosition);

    // Commit the edits!
    editor.commit();
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
    Input = Input.replaceAll("\\s+","");
    Input += "\n";
    String Password = SHA1(Input);
    Password = Password.substring(mStartPosition, mStartPosition + mLength).toLowerCase();
    Password = AddSpecialCharactersAndCapitalLetter(Password);
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

  String AddSpecialCharactersAndCapitalLetter(String Password)
  {
    String SpecialCharacters[] = {"!", "@", "#", "$", "%", "^", "&", "*", "(", ")"};
    char TempCharacter;
    List<String> Characters = new ArrayList<String>();
    List<String> Digits = new ArrayList<String>();
    for (int i = 0, Length = Password.length(); i < Length; i++)
    {
      TempCharacter = Password.charAt(i);

      if (Character.isDigit(TempCharacter))
      {
        Digits.add(Character.toString(TempCharacter));
      }
      else
      {
        Characters.add(Character.toString(TempCharacter));
      }
    }

    if (Digits.size() > 1)
    {
      Password = Password.replaceFirst(Digits.get(0), SpecialCharacters[Integer.parseInt(Digits.get(0))]);
    }
    else
    {
      Password = "!2" + Password.substring(1);
    }

    if (Characters.size() > 1)
    {
      Password = Password.replaceFirst(Characters.get(0), Characters.get(0).toUpperCase());
    }
    else
    {
      Password = Password.substring(0, Password.length()-2) + "dD";
    }
    return Password;
  }





  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.password, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    DialogFragment dialogFragment;
    // Handle item selection
    switch (item.getItemId())
    {
      case R.id.SetLength:
        dialogFragment =
          StartLengthPickerDialog.createInstance("Set Length", mStartPosition, mLength, true);
        dialogFragment.show(getSupportFragmentManager(), "TimeEditor");
        return true;
      case R.id.SetStartPosition:
        dialogFragment =
          StartLengthPickerDialog.createInstance("Set Start Position", mStartPosition, mLength, false);
        dialogFragment.show(getSupportFragmentManager(), "TimeEditor");
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  public void onDialogPositiveClick(int Value, boolean IsLength)
  {
    if (IsLength)
    {
      mLength = Value;
    }
    else
    {
      mStartPosition = Value;
    }
  }

  @Override
  public void onDialogNegativeClick() {}

  private int mStartPosition;
  private int mLength;

}
