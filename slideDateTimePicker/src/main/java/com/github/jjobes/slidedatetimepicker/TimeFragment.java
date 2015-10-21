package com.github.jjobes.slidedatetimepicker;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.TimePicker;

/**
 * The fragment for the second page in the ViewPager that holds
 * the TimePicker.
 *
 * @author jjobes
 *
 */
public class TimeFragment extends Fragment
{
    /**
     * Used to communicate back to the parent fragment as the user
     * is changing the time spinners so we can dynamically update
     * the tab text.
     */
    public interface TimeChangedListener
    {
        void onTimeChanged(int hour, int minute);
    }

    private TimeChangedListener mCallback;
    private TimePicker mTimePicker;

    public TimeFragment()
    {
        // Required empty public constructor for fragment.
    }

    /**
     * Cast the reference to {@link SlideDateTimeDialogFragment} to a
     * {@link TimeChangedListener}.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        try
        {
            mCallback = (TimeChangedListener) getTargetFragment();
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException("Calling fragment must implement " +
                "TimeFragment.TimeChangedListener interface");
        }
    }

    /**
     * Return an instance of TimeFragment with its bundle filled with the
     * constructor arguments. The values in the bundle are retrieved in
     * {@link #onCreateView()} below to properly initialize the TimePicker.
     *
     * @param theme
     * @param hour
     * @param minute
     * @param isClientSpecified24HourTime
     * @param is24HourTime
     * @return
     */
    public static final TimeFragment newInstance(int theme, int hour, int minute,
        boolean isClientSpecified24HourTime, boolean is24HourTime)
    {
        TimeFragment f = new TimeFragment();

        Bundle b = new Bundle();
        b.putInt("theme", theme);
        b.putInt("hour", hour);
        b.putInt("minute", minute);
        b.putBoolean("isClientSpecified24HourTime", isClientSpecified24HourTime);
        b.putBoolean("is24HourTime", is24HourTime);
        f.setArguments(b);

        return f;
    }

    /**
     * Create and return the user interface view for this fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        int theme = getArguments().getInt("theme");
        int initialHour = getArguments().getInt("hour");
        int initialMinute = getArguments().getInt("minute");
        boolean isClientSpecified24HourTime = getArguments().getBoolean("isClientSpecified24HourTime");
        boolean is24HourTime = getArguments().getBoolean("is24HourTime");

        // Unless we inflate using a cloned inflater with a Holo theme,
        // on Lollipop devices the TimePicker will be the new-style
        // radial TimePicker, which is not what we want. So we will
        // clone the inflater that we're given but with our specified
        // theme, then inflate the layout with this new inflater.

        Context contextThemeWrapper = new ContextThemeWrapper(
                getActivity(),
                theme == SlideDateTimePicker.HOLO_DARK ?
                         android.R.style.Theme_Holo :
                         android.R.style.Theme_Holo_Light);

        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);

        View v = localInflater.inflate(R.layout.fragment_time, container, false);

        mTimePicker = (TimePicker) v.findViewById(R.id.timePicker);
        // block keyboard popping up on touch
        mTimePicker.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute)
            {
                mCallback.onTimeChanged(hourOfDay, minute);
            }
        });

        return v;
    }



}
