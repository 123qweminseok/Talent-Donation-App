package Myinformation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.minseok.loginanddatabase.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

import activity.ApplyAdapter;
import activity.ApplyItem;
import activity.CountItem;

//해당 액티비티는 내가 쓴 글 불러오는것
public class list1 extends Fragment {
    RecyclerView recyclerView_apply;
    private FirebaseAuth mFirebaseauth;//Firebase 인증 객체
    ApplyAdapter applyAdapter;
    ArrayList<ApplyItem> applyItems; //여기에 배열들 다 저장하고 생성자로 날린다음에 화면에 띄움
    //applyItems는 ApplyItem 객체의 목록을 담는 변수다. 즉 객체를 담는 그릇으로 쓰인다. 그래서 applyItems안에
    ArrayList<CountItem> CountItems;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.list1, container, false);
        mFirebaseauth=FirebaseAuth.getInstance();

        applyItems = new ArrayList<>();
        recyclerView_apply = rootView.findViewById(R.id.recyclerView_apply2);
        applyAdapter = new ApplyAdapter(applyItems,CountItems,getActivity());
        recyclerView_apply.setAdapter(applyAdapter); //그럼 이제 생성자로 날라간 배열로부터 3가지 메서드가 발동하며 값들이 띄워진다.
        //이건 db에 값을 저장하고 실행하려면 어쩔수 없이 그렇게 진행해야 한다. 모든 앱들이 또 그렇게 만들어지고.
        //어댑터, 자바파일 , xml 2개-(자바파일에 대한 xml, 그리고 각각의 디자인데 대한것) 딱 이 네가지면 리사이클러뷰의 조건이 끝
        addChildEvent();

        return rootView;
    }

    public void addChildEvent() {
        databaseReference.child("apply list").child(mFirebaseauth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Log.d("확인하기",snapshot.toString()); //하윗값 전부 가지고 있고
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    ApplyItem item=dataSnapshot.getValue(ApplyItem.class);
                    //Realtime Database에서 읽어온 JSON 데이터를 ApplyItem 클래스의 객체로 변환하는 것이다!
                    //그래서 해당 item안에 있는 getTitle등의 요소들을 쓸 수 있는것이다. item.getTitle이런식으로!
                    //이렇게 변환된 객체는 자바 프로그램에서 일반적인 객체처럼 사용할 수 있습니다.
                    //데이터를 ApplyItem의 변수값에 맞는 형태로 가져오고, 이걸 변환해준다는것이다 키-값 형태로 가져오니.
Log.d("1",dataSnapshot.toString()); //키에 고유번호, 값에 키,값 뭉치로 있음.
                    Log.d("2",item.toString());
                    applyItems.add(0,item);
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
  //  여기부터 새로 만들어야 함 리사이클러뷰에 띄울거임 아래 다 주석처리
  /*  private void addChildEvent() {
//       사용자 하위에 있는값들 다 가져오고
        databaseReference.child("apply list").child(mFirebaseauth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("보여줘",snapshot.toString());

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
            //하위 항목들에 대해 순차적으로 접근함 키,값 형태로
                    Log.d("순차접근",dataSnapshot.toString());
                    arrayList.add(dataSnapshot.getValue());
                    //각각 값을 저장시켜줬잖아. 근데 그 값이 지금  스트링 형태잖아 tostring필요없음 ㅇㅇ
                    Log.d("리스트 갑 확인",arrayList.toString());
System.out.println(arrayList);
                }

                textView1.setText(arrayList.get(0).toString());
                textView2.setText(arrayList.get(2).toString());

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
*/




