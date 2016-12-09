package com.example.leemoonseong.scheduler.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.leemoonseong.scheduler.Database.MyDBHelper;
import com.example.leemoonseong.scheduler.R;
import com.example.leemoonseong.scheduler.dao.ScheduleVO;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by LeeMoonSeong on 2016-11-28.
 */

public class ModifyScheduleActivity extends AppCompatActivity {
    private MyDBHelper helper;
    private Intent previousIntent;
    private ScheduleVO scheduleVO;
    final static String TAG="SQLITEDBTEST";
    String fileName;
    Bitmap photo;
    ActionBar ab;
    Date date1;
    Date date2;
    EditText title , memo , location;
    Button input_image, save , load;
    ImageView iv;
    String s_date, e_date;
    int s_year, s_month, s_day, s_hour, s_minute;
    int e_year, e_month, e_day, e_hour, e_minute;
    int start_year =0 , start_month=0, start_day =0, start_hour=0, start_mitute=0;
    int end_year=0 , end_month =0, end_day =0, end_hour =0, end_minute =0;
    TextView start;
    private final int PICK_FROM_ALBUM =1 ;
    TextView end , result;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ab = getSupportActionBar();
        ab.setTitle("일정 편집");
        setContentView(R.layout.activity_add_schedule);
        previousIntent = getIntent();
        helper = new MyDBHelper(this);
        GregorianCalendar calendar = new GregorianCalendar();
        s_year = calendar.get(Calendar.YEAR);
        s_month = calendar.get(Calendar.MONTH);
        s_day = calendar.get(Calendar.DAY_OF_MONTH);
        s_hour = calendar.get(Calendar.HOUR_OF_DAY);
        s_minute = calendar.get(Calendar.MINUTE);

        e_year = calendar.get(Calendar.YEAR);
        e_month = calendar.get(Calendar.MONTH);
        e_day = calendar.get(Calendar.DAY_OF_MONTH);
        e_hour = calendar.get(Calendar.HOUR_OF_DAY);
        e_minute = calendar.get(Calendar.MINUTE);

        start = (TextView)findViewById(R.id.start_schedule);
        end = (TextView)findViewById(R.id.end_schedule);

        input_image = (Button)findViewById(R.id.input_image);
        save = (Button)findViewById(R.id.save);
        iv = (ImageView)findViewById(R.id.iv_schedule);
        title = (EditText)findViewById(R.id.title);
        memo = (EditText)findViewById(R.id.memo);
        result = (TextView)findViewById(R.id.result);
        location = (EditText)findViewById(R.id.location);
        findViewById(R.id.start_date).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                DatePickerDialog datePickerDialog1 = new DatePickerDialog(ModifyScheduleActivity.this, dateSetListener, s_year, s_month, s_day);
                datePickerDialog1.show();
            }
        });
//        findViewById(R.id.start_time).setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                TimePickerDialog timePickerDialog1 = new TimePickerDialog(AddScheduleActivity.this, timeSetListener, s_hour, s_minute, false);
//                timePickerDialog1.show();
//            }
//        });
        findViewById(R.id.end_date).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(start_year ==0){
                    Toast.makeText(getApplicationContext(),"시작일을 먼저 입력해주세요.",Toast.LENGTH_SHORT).show();
                }
                else {
                    // TODO Auto-generated method stub
                    DatePickerDialog datePickerDialog2 = new DatePickerDialog(ModifyScheduleActivity.this, dateSetListener2, e_year, e_month, e_day);
                    datePickerDialog2.show();
                }
            }
        });
//        findViewById(R.id.end_time).setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                TimePickerDialog timePickerDialog2 = new TimePickerDialog(AddScheduleActivity.this, timeSetListener2, e_hour, e_minute, false);
//                timePickerDialog2.show();
//            }
//        });
        input_image.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                doTakeAlbulAction();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveImage(getApplicationContext(),photo);
                try {
                    String sql = String.format (
                            "UPDATE  schedule\n"+
                                    "SET title = '%s', startTime = '%s', endTime = '%s', location = '%s', Memo = '%s', imageName = '%s'"+
                                    "WHERE _id= "+previousIntent.getIntExtra("scheduleId",0)+";",
                            title.getText(), s_date ,e_date , location.getText(), memo.getText(),fileName) ;
                    helper.getWritableDatabase().execSQL(sql);
                    Toast.makeText(getApplicationContext(),"변경 성공",Toast.LENGTH_SHORT).show();
                    ModifyScheduleActivity.this.finish();
                } catch (SQLException e) {
                    Log.e(TAG,"Error deleting recodes");
                }

            }
        });
        try {
            load_dailySchedule();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            start_year = year;
            start_month = monthOfYear +1;
            start_day = dayOfMonth;
            TimePickerDialog timePickerDialog1 = new TimePickerDialog(ModifyScheduleActivity.this, timeSetListener, s_hour, s_minute, false);
            timePickerDialog1.show();
        }
    };

    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // TODO Auto-generated method stub
            start_hour = hourOfDay;
            start_mitute = minute;
            String msg = String.format("%d년 %d월 %d일 %d시 %d분", start_year, start_month,start_day,start_hour,start_mitute);
            start.setText(msg);
        }
    };

    private DatePickerDialog.OnDateSetListener dateSetListener2 = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            end_year = year;
            end_month = monthOfYear +1;
            end_day = dayOfMonth;
            TimePickerDialog timePickerDialog2 = new TimePickerDialog(ModifyScheduleActivity.this, timeSetListener2, s_hour, s_minute, false);
            timePickerDialog2.show();
        }
    };

    private TimePickerDialog.OnTimeSetListener timeSetListener2 = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // TODO Auto-generated method stub
            end_hour = hourOfDay;
            end_minute = minute;
            SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA);
            s_date = start_year+"-"+start_month+"-"+start_day+" "+start_hour+":"+start_mitute;
            e_date = end_year+"-"+end_month+"-"+end_day+" "+end_hour+":"+end_minute;
            try {
                date1 = dateFormat.parse(s_date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                date2 = dateFormat.parse(e_date);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(date2.after(date1)){
                String msg = String.format("%d년 %d월 %d일 %d시 %d분", end_year, end_month,end_day,end_hour,end_minute);
                end.setText(msg);
            }
            else{
                Toast.makeText(getApplicationContext(),"시작시간은 종료시간보다 클 수 없습니다.",Toast.LENGTH_SHORT).show();
                start.setText("");
                end.setText("");
            }
        }
    };
    public void doTakeAlbulAction(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent,PICK_FROM_ALBUM);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PICK_FROM_ALBUM) {
            if(resultCode != RESULT_OK){
                iv.setImageBitmap(null);
                photo = null;
            }
            else{
            Uri mImageCaptureUri = data.getData();
             photo = null;
            try {
                photo = MediaStore.Images.Media.getBitmap(getContentResolver(), mImageCaptureUri);
                Cursor c = getContentResolver().query(Uri.parse(mImageCaptureUri.toString()), null, null, null, null);
                c.moveToNext();
                final String absolutePath = c.getString(c.getColumnIndex(MediaStore.MediaColumns.DATA));
                if (photo != null) {
                    iv.setImageBitmap(photo);
                }


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        }
    }
    public void load_dailySchedule() throws ParseException {
        SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA);
        String sql = "Select * FROM schedule WHERE _id = "+previousIntent.getIntExtra("scheduleId",0)+";";
        Cursor cursor = helper.getReadableDatabase().rawQuery(sql,null);
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            scheduleVO = new ScheduleVO(cursor.getInt(0),cursor.getString(1),
                         dateFormat.parse(cursor.getString(2)), dateFormat.parse(cursor.getString(3)),
                         cursor.getString(4), cursor.getString(5),cursor.getString(6));

        }
        setContent();
    }
    public void setContent(){
        SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA);
        try {
            title.setText(scheduleVO.getTitle());
            start.setText(dateFormat.format(scheduleVO.getStartTime()));
            end.setText(dateFormat.format(scheduleVO.getEndTime()));
            s_date = dateFormat.format(scheduleVO.getStartTime());
            e_date = dateFormat.format(scheduleVO.getEndTime());
            location.setText(scheduleVO.getLocation());
            memo.setText(scheduleVO.getMemo());
            iv.setImageBitmap(getImageBitmap(getApplicationContext(),scheduleVO.getImageName()));
             photo = getImageBitmap(getApplicationContext(),scheduleVO.getImageName());
        } catch (NullPointerException e) {
            Toast.makeText(getApplicationContext(), "해당 하는 데이터를 찾지 못했습니다.", Toast.LENGTH_SHORT).show();
        }
    }
    public Bitmap getImageBitmap(Context context, String name){
        try{
            FileInputStream fis = context.openFileInput(name);
            Bitmap bitmap = BitmapFactory.decodeStream(fis);
            fis.close();
            return bitmap;
        }
        catch(Exception e){
        }
        return null;
    }
    public void saveImage(Context context, Bitmap bitmap){
        fileName = Long.toString(System.currentTimeMillis()) + ".png";
        FileOutputStream out;
        try {
            out = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

