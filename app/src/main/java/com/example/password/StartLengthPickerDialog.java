package com.example.password;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

public class StartLengthPickerDialog extends DialogFragment
{
    public interface NumberPickerDialogListener
    {
      public void onDialogPositiveClick(
          int Value,
          boolean isLength);
      public void onDialogNegativeClick();
    }

    // Use this instance of the interface to deliver action events
    NumberPickerDialogListener mListener;

    public static StartLengthPickerDialog createInstance(
      String title,
      int StartPosition,
      int Length,
      boolean IsLength)
    {
      StartLengthPickerDialog dialog = new StartLengthPickerDialog();
      Bundle args = new Bundle();
      args.putString("title", title);
      args.putInt("Length", Length);
      args.putInt("StartPosition", StartPosition);
      args.putBoolean("IsLength", IsLength);
      dialog.setArguments(args);
      return dialog;
    }

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity)
    {
      super.onAttach(activity);
      // Verify that the host activity implements the callback interface
      try
      {
        // Instantiate the NoticeDialogListener so we can send events to the host
        mListener = (NumberPickerDialogListener) activity;
      } catch (ClassCastException e)
      {
        // The activity doesn't implement the interface, throw exception
        throw new ClassCastException(activity.toString()
            + " must implement NoticeDialogListener");
      }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
      super.onCreateDialog(savedInstanceState);
      LayoutInflater inflater = getActivity().getLayoutInflater();

      View view = inflater.inflate(R.layout.set_length_start_fragment, null);

      TextView textView = (TextView) view.findViewById(R.id.Title);

      mIsLength = getArguments().getBoolean("IsLength");
      int ButtonTextId;
      if (mIsLength)
      {
        textView.setText(R.string.setLength);
        ButtonTextId = R.string.setLength;
      }
      else
      {
        textView.setText(R.string.setStart);
        ButtonTextId = R.string.setStart;
      }

      AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
      builder.setTitle(getArguments().getString("title"));

      //View.OnClickListener dismiss = new View.OnClickListener();
      builder.setPositiveButton(ButtonTextId, new DialogInterface.OnClickListener()
      {
        @Override
        public void onClick(DialogInterface dialog, int id)
        {
          // Send the positive button event back to the host activity
          mListener.onDialogPositiveClick(mNumberPicker.getValue(), mIsLength);
        }
      });

      builder.setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener()
      {
        @Override
        public void onClick(DialogInterface dialog, int id)
        {
          // Send the positive button event back to the host activity
          mListener.onDialogNegativeClick();
        }
      });


      mNumberPicker = (NumberPicker) view.findViewById(R.id.NumberPicker);
      int Length = getArguments().getInt("Length");
      int StartPosition = getArguments().getInt("StartPosition");
      if (mIsLength)
      {
        mNumberPicker.setMaxValue(40);
        mNumberPicker.setMinValue(1);
        mNumberPicker.setValue(Length);
      }
      else {
        mNumberPicker.setMaxValue(40-Length);
        mNumberPicker.setMinValue(0);
        mNumberPicker.setValue(StartPosition);
      }

      mNumberPicker.setWrapSelectorWheel(true);

      builder.setView(view);
      return builder.create();
    }

    private NumberPicker mNumberPicker;
    private boolean mIsLength;
}

