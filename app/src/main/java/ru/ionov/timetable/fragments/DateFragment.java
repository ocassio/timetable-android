package ru.ionov.timetable.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import ru.ionov.timetable.R;
import ru.ionov.timetable.models.DateRange;
import ru.ionov.timetable.providers.PreferencesProvider;
import ru.ionov.timetable.utils.DateUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class DateFragment extends Fragment
{
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_date, container);

        selectCurrentDateType();
        setCustomDateRangeValues();

        setOnDateTypeChangeListener();
        setOnCustomDateValuesClickListeners();

        return rootView;
    }

    private void selectCurrentDateType()
    {
        int dateTypeId = PreferencesProvider.getDateType();

        RadioButton currentDateType = (RadioButton) rootView.findViewById(dateTypeId);
        currentDateType.setChecked(true);

        if (dateTypeId == R.id.dateSelectionTypeCustom)
        {
            showCustomDateRange();
        }
    }

    private void setOnDateTypeChangeListener()
    {
        RadioGroup dateTypeGroup = (RadioGroup) rootView.findViewById(R.id.dateSelectionType);
        dateTypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                PreferencesProvider.setDateType(checkedId);
                if (checkedId == R.id.dateSelectionTypeCustom)
                {
                    showCustomDateRange();
                }
                else
                {
                    hideCustomDateRange();
                }
            }
        });
    }

    private void showCustomDateRange()
    {
        rootView.findViewById(R.id.customDateRange).setVisibility(View.VISIBLE);
    }

    private void hideCustomDateRange()
    {
        rootView.findViewById(R.id.customDateRange).setVisibility(View.GONE);
    }

    private void setCustomDateRangeValues()
    {
        DateRange customDateRange = PreferencesProvider.getCustomDateRange();

        TextView fromView = (TextView) rootView.findViewById(R.id.customDateFromValue);
        TextView toView = (TextView) rootView.findViewById(R.id.customDateToValue);

        fromView.setText(DateUtils.toDateString(customDateRange.getFrom()));
        toView.setText(DateUtils.toDateString(customDateRange.getTo()));
    }

    private void setOnCustomDateValuesClickListeners()
    {
        View fromView = rootView.findViewById(R.id.customDateFrom);
        fromView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showCustomDateFromPicker();
            }
        });

        View toView = rootView.findViewById(R.id.customDateTo);
        toView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showCustomDateToPicker();
            }
        });
    }

    private void showCustomDateFromPicker()
    {
        TextView fromView = (TextView) rootView.findViewById(R.id.customDateFromValue);
        Date date;
        try
        {
            date = DateUtils.toDate((String) fromView.getText());
        }
        catch (ParseException e)
        {
            date = Calendar.getInstance().getTime();
        }

        final Dialog.Builder builder = new DatePickerDialog.Builder(R.style.Material_App_Dialog_DatePicker_Light)
            {
                @Override
                public void onNegativeActionClicked(DialogFragment fragment)
                {
                    fragment.getDialog().dismiss();
                }

                @Override
                public void onPositiveActionClicked(DialogFragment fragment)
                {
                    DatePickerDialog dialog = (DatePickerDialog) fragment.getDialog();

                    DateRange dateRange = PreferencesProvider.getCustomDateRange();
                    dateRange.setFrom(new Date(dialog.getDate()));

                    PreferencesProvider.setCustomDateRange(dateRange);
                    setCustomDateRangeValues();

                    dialog.dismiss();
                }
            }
            .date(date.getTime())
            .positiveAction(getString(R.string.ok))
            .negativeAction(getString(R.string.cancel));

        DialogFragment dialogFragment = DialogFragment.newInstance(builder);
        dialogFragment.show(getFragmentManager(), null);
    }

    private void showCustomDateToPicker()
    {
        TextView toView = (TextView) rootView.findViewById(R.id.customDateToValue);
        Date date;
        try
        {
            date = DateUtils.toDate((String) toView.getText());
        }
        catch (ParseException e)
        {
            date = Calendar.getInstance().getTime();
        }

        final Dialog.Builder builder = new DatePickerDialog.Builder(R.style.Material_App_Dialog_DatePicker_Light)
            {
                @Override
                public void onNegativeActionClicked(DialogFragment fragment)
                {
                    fragment.getDialog().dismiss();
                }

                @Override
                public void onPositiveActionClicked(DialogFragment fragment)
                {
                    DatePickerDialog dialog = (DatePickerDialog) fragment.getDialog();

                    DateRange dateRange = PreferencesProvider.getCustomDateRange();
                    dateRange.setTo(new Date(dialog.getDate()));

                    PreferencesProvider.setCustomDateRange(dateRange);
                    setCustomDateRangeValues();

                    dialog.dismiss();
                }
            }
            .date(date.getTime())
                .positiveAction(getString(R.string.ok))
                .negativeAction(getString(R.string.cancel));

        DialogFragment dialogFragment = DialogFragment.newInstance(builder);
        dialogFragment.show(getFragmentManager(), null);
    }
}
