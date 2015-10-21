package masterarbeit.com.myapplication1234;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private SlideDateTimeListener listener = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date)
        {
            // Do something with the date. This Date object contains
            // the date and time that the user has selected.
        }

        @Override
        public void onDateTimeCancel()
        {
            // Overriding onDateTimeCancel() is optional.
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new SlideDateTimePicker.Builder(getSupportFragmentManager())
                .setListener(listener)
                .setInitialDate(new Date())
                .build()
                .show();

    }


}
