package Myinformation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.minseok.loginanddatabase.R;

import java.util.ArrayList;

import activity.ApplyAdapter;
import activity.ApplyItem;
import activity.CountItem;

public class countFregment extends Fragment {

    //해당 프래그먼트는 조회수가 10개가 넘은 글을 기준으로 글들을 불러옴. 즉 db의 count값이 10이 넘으면 불러온다.
    RecyclerView countFregment;
    private FirebaseAuth mFirebaseauth;//Firebase 인증 객체
    ApplyAdapter applyAdapter;
    ArrayList<ApplyItem> contItems; //★★★★해당 배열에 값들을 넣고 생성자로 값들을 넘겨 화면에 값들이 뜨게 되는것이다!! 즉 여기에 값들을 저장시켜야함.
    ArrayList<CountItem> CountItems;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.countfregment, container, false);

        mFirebaseauth = FirebaseAuth.getInstance();

        contItems = new ArrayList<>();
        countFregment = rootView.findViewById(R.id.countFregment);
        applyAdapter = new ApplyAdapter(contItems, CountItems, getActivity());//생성자를 통해 날려야 해당 화면에 값이 띄워지지 이제


        countFregment.setAdapter(applyAdapter);//어댑터 연결하면 이제 applyAdapter에있는 3가지들이 작동을 하며 해당 현재 프래그먼트에 대해서 작동을 한다.


        addChildEvent1();

        return rootView;


    }


    public void addChildEvent1() {
        databaseReference.child("applyRecyclerView").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Log.d("확인하기", snapshot.toString()); //하윗값 전부 가지고 있고
                //순서대로 각 노드를 가져옴.
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    //이 순서대로 가지고 온거에서 조건문으로 추리지.
                    ApplyItem item = dataSnapshot.getValue(ApplyItem.class);
                    //여기까지 다 순조롭게 값을 바꾸고 item의 객체로. 이제 조건문 발동.
                    if (item != null && item.getView() >= 10) {
                        contItems.add(0, item);
                    }
                    //여기서 추가를 해준다 각각의 요소에다가 item이라는 ApplyItem클래스 내
                }
                applyAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}





