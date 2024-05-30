package Myinformation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.minseok.loginanddatabase.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Daycalcul extends Fragment {
    private EditText editTextDate1;
    private EditText editTextDate2;
    private Button buttonCalculate;
    private TextView textViewResult;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
   View view= inflater.inflate(R.layout.fragment_daycalcul, container, false);


        editTextDate1 = view.findViewById(R.id.editTextDate1);
        editTextDate2 = view.findViewById(R.id.editTextDate2);
        buttonCalculate = view.findViewById(R.id.buttonCalculate);
        textViewResult = view.findViewById(R.id.textViewResult);


        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateDateDifference();
            }
        });


        return view;
    }


    private void calculateDateDifference() {
        String date1Str = editTextDate1.getText().toString();
        String date2Str = editTextDate2.getText().toString();

        if (!date1Str.isEmpty() && !date2Str.isEmpty()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            try {
                Date date1 = dateFormat.parse(date1Str);
                Date date2 = dateFormat.parse(date2Str);

                if (date1 != null && date2 != null) {
                    long diff = date2.getTime() - date1.getTime();
                    long daysBetween = diff / (1000 * 60 * 60 * 24);
                    textViewResult.setText("Days between: " + daysBetween);
                } else {
                    textViewResult.setText("Invalid date format");
                }
            } catch (Exception e) {
                textViewResult.setText("Invalid date format");
            }
        } else {
            textViewResult.setText("Please enter both dates");
        }}

}