package Myinformation;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.minseok.loginanddatabase.R;

public class MyCalendarViewFragment  extends Fragment {

    //해당 액티비티는 캘린더 액티비티로 데이터가 있는 캘린더의 색상까지 수정해 주고 싶었지만 해당 부분은 구현을 못하였다.
    //CalendarView에는 각 날짜에 대한 텍스트 스타일을 직접 설정할 수 있는 기능이 없습니다. 대신에 특정 날짜에 대한 색상을 변경하려면 커스텀 렌더러를
    // 사용하거나 라이브러리를 이용해야 할 수 있다고 한다.
    private CalendarView calendarView;
    private EditText editText;
    private Button saveButton;
    private TextView showButton;

    private String selectedDate;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calendar_view, container, false);

        calendarView = rootView.findViewById(R.id.calendarView);
        editText = rootView.findViewById(R.id.editText);
        saveButton = rootView.findViewById(R.id.saveButton);
        showButton=rootView.findViewById(R.id.showButton);
        // Firebase 데이터베이스 인스턴스 가져오기
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("applyRecyclerView");

        // 캘린더 뷰 날짜 선택 리스너 설정
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // 선택된 날짜를 문자열로 저장
                selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;

                // 선택된 날짜에 해당하는 데이터 조회
               loadDataForSelectedDate(selectedDate);
            }
        });

        // 저장 버튼 클릭 리스너 설정
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });

        return rootView;
    }


    //데이터 가져오기.
    private void loadDataForSelectedDate(String selectedDate) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        databaseReference.child(uid).child("Calendar").child(selectedDate).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot snapshot = task.getResult();
                if (snapshot.exists()) {
                    String note = snapshot.getValue(String.class);
                    showButton.setText(note);

                    calendarView.setDateTextAppearance(R.style.SpecialDateTextAppearance);
                    //이게 바꾸는건데 구현못해서 의미없음

                } else {
                    showButton.setText("데이터 추가요망");

                    // 일정이 없는 경우 기본 색상 사용
                    calendarView.setDateTextAppearance(R.style.DefaultDateTextAppearance); // 기본 텍스트 스타일 적용
                    //<!-- res/values/styles.xml -->에서 적용시킴
                }

            }


            else {
                Toast.makeText(getContext(), "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveNote() {
        String note = editText.getText().toString().trim();

        if (selectedDate == null) {
            Toast.makeText(getContext(), "Please select a date", Toast.LENGTH_SHORT).show();
            return;
        }

        if (note.isEmpty()) {
            Toast.makeText(getContext(), "Please enter a note", Toast.LENGTH_SHORT).show();
            return;
        }

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // 선택된 날짜에 이미 데이터가 있는지 확인
        databaseReference.child(uid).child("Calendar").child(selectedDate).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot snapshot = task.getResult();
                if (snapshot.exists()) {
                    // 이미 데이터가 있으면 덮어쓰기
                    databaseReference.child(uid).child("Calendar").child(selectedDate).setValue(note).addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            Toast.makeText(getContext(), "Note updated", Toast.LENGTH_SHORT).show();
                            // 저장 후 편집창 비우기
                            editText.setText("");
                        } else {
                            Toast.makeText(getContext(), "Failed to update note", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // 데이터가 없으면 새로운 데이터 저장
                    databaseReference.child(uid).child("Calendar").child(selectedDate).setValue(note).addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            Toast.makeText(getContext(), "Note saved", Toast.LENGTH_SHORT).show();
                            // 저장 후 편집창 비우기
                            editText.setText("");
                        } else {
                            Toast.makeText(getContext(), "Failed to save note", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else {
                Toast.makeText(getContext(), "Failed to save note", Toast.LENGTH_SHORT).show();
            }
        });
    }}