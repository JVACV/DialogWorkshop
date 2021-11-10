package com.example.mydialog;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button bt_select_version,bt_select_multipleversion,buttonCustomDialog,buttonDatePicker
            ,buttonTimePicker,buttonProgressDilog,
            buttonBarProgressDialog;
    int status = 0;
    Handler handler = new Handler();
    ProgressDialog progressdialog;

    final String[] androidVersionList = { " Cupcake ", " Donut ", " Eclair ",
            " Froyo ", " Gingerbread ", " Honeycomb  ",
            " Ice Cream Sandwich  ", " jelly Bean  ", " KitKat  ",
            " Lollipop  ","Marshmallow " ,"Nougat ","Oreo"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_select_version=findViewById(R.id.bt_select_version);
        bt_select_version.setOnClickListener(this);
        bt_select_multipleversion=findViewById(R.id.bt_select_multipleversion);
        bt_select_multipleversion.setOnClickListener(this);

        buttonCustomDialog=findViewById(R.id.buttonCustomDialog);
        buttonCustomDialog.setOnClickListener(this);
        buttonDatePicker = (Button) findViewById(R.id.bt_date_picker);
        buttonDatePicker.setOnClickListener(this);

        buttonTimePicker = (Button) findViewById(R.id.bt_time_picker);
        buttonTimePicker.setOnClickListener(this);

        buttonProgressDilog = (Button) findViewById(R.id.bt_progress_dilog);
        buttonProgressDilog.setOnClickListener(this);

        buttonBarProgressDialog = (Button) findViewById(R.id.bt_bar_progress_dilog);
        buttonBarProgressDialog.setOnClickListener(this);

        progressdialog=new ProgressDialog(MainActivity.this);


    }

    @Override
    public void onClick(View view) {

       switch (view.getId())
       {
           case R.id.bt_select_version:
               selectSingleChoice((Button) view);
               break;
           case R.id.bt_select_multipleversion:
               selectMultipleChoice((Button) view);
               break;
           case R.id.buttonCustomDialog:
               createCustomDialog();
               break;
           case R.id.bt_date_picker:
               selectDate((Button) view);
               break;
           case R.id.bt_time_picker:
               selectTime((Button) view);
               break;
           case R.id.bt_progress_dilog:
               spinProgress();
               break;
           case R.id.bt_bar_progress_dilog:
               CreateProgressDialog();
               barProgress();
               break;
           default:
               break;
       }
    }

    private void createCustomDialog() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.custom_dialog,null);
        final EditText mEmail=(EditText)  mView.findViewById(R.id.textDialog);
        final EditText mPassword=(EditText)  mView.findViewById(R.id.imageDialog);
        Button mLogin =(Button) mView.findViewById(R.id.declineButton);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mEmail.getText().toString().isEmpty() && !mPassword.getText().toString().isEmpty())
                {
                    Toast.makeText(MainActivity.this,R.string.success_login_msg,Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this,R.string.error_login_msg,Toast.LENGTH_SHORT).show();
                }
            }
        });
        mBuilder.setView(mView);
        AlertDialog dialog=mBuilder.create();
        dialog.show();

    }





    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        ExitDialog();
    }

    // --------------------------------------------------------------------------------------------------------------------

    private void ExitDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Do you really want to exit ? ")
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                        | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();

                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        dialog.dismiss();

                    }
                }).show();
    }

    private void selectSingleChoice(final Button button) {
        Toast.makeText(this,"selectSingleChoice",Toast.LENGTH_SHORT).show();
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                .setSingleChoiceItems(androidVersionList, 0, null)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int selectedPosition = ((AlertDialog) dialogInterface)
                                .getListView().getCheckedItemPosition();
                        button.setText(androidVersionList[selectedPosition]);
                        dialogInterface.dismiss();
                    }
                }).show();

    }
    StringBuilder stringBuilder;
    AlertDialog alertDialog = null;

    private void selectMultipleChoice(final Button button){
        final List<String> selectedItems = new ArrayList<String>();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select android version");
        builder.setMultiChoiceItems(androidVersionList, null,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface,  int indexSelected,boolean isChecked) {

            if(isChecked){
                selectedItems.add(androidVersionList[indexSelected]);
            }else if(selectedItems.contains(indexSelected)){
                selectedItems.remove(Integer.valueOf(indexSelected));

                    }
                }

    });
builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        stringBuilder = new StringBuilder();
        for(int j=0;j<selectedItems.size();j++){
            stringBuilder.append(selectedItems.get(j) + " ,");
        }
        button.setText(stringBuilder.toString());
    }
}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        alertDialog.dismiss();
    }
});
alertDialog = builder.create();
alertDialog.show();

    }



   int myYear,
    mMonth,
    mDay,
    mHour,
    mMinute,
    mSecond;
   private void selectDate(final Button button){
       final Calendar c= Calendar.getInstance();
       myYear = c.get(Calendar.YEAR);
       mMonth = c.get(Calendar.MONTH);
       mDay = c.get(Calendar.DAY_OF_MONTH);
       DatePickerDialog datePickerDialog =new DatePickerDialog(this,
               new DatePickerDialog.OnDateSetListener() {
                   @Override
                   public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                       button.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                   }
               },myYear,mMonth,mDay);
             datePickerDialog.show();
   }
   private void selectTime(final Button button){
       final Calendar c= Calendar.getInstance();
       mHour = c.get(Calendar.HOUR_OF_DAY);
       mMinute=c.get(Calendar.MINUTE);
       mSecond = c.get(Calendar.SECOND);
       TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
           @Override
           public void onTimeSet(TimePicker view, int hourOfDay,
                                 int minute) {
               button.setText(hourOfDay + ":" + (minute) + ":" + mSecond);
           }

           },mHour ,mMinute,true);

       timePickerDialog.show();
   }



    public void CreateProgressDialog()
    {
        progressdialog = new ProgressDialog(MainActivity.this);
        progressdialog.setIndeterminate(false);
        progressdialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressdialog.setCancelable(true);
        progressdialog.setMax(100);
        progressdialog.show();
    }
    private void barProgress() {

        status = 0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(status < 100){
                    status +=3;
                    try{
                        Thread.sleep(200);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressdialog.setProgress(status);
                            if(status >= 100){
                                progressdialog.dismiss();
                                Toast.makeText(MainActivity.this, "Task Complete ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        }).start();


    }

    // --------------------------------------------------------------------------------------------------------------------
    private void spinProgress() {

        final ProgressDialog ringProgressDialog = ProgressDialog.show(MainActivity.this, "Please wait ...", "Downloading Image ...", true);

        ringProgressDialog.setCancelable(true);

        new Thread(new Runnable() {

            @Override

            public void run() {

                try {

                    // Here you should write your time consuming task...

                    // Let the progress ring for 10 seconds...

                    Thread.sleep(10000);

                } catch (Exception e) {




                    ringProgressDialog.dismiss();}


            }}).start();}
}