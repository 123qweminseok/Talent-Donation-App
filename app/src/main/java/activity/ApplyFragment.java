package activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.minseok.loginanddatabase.R;

import java.util.ArrayList;

import SearchView.SearchActivity;

public class ApplyFragment extends Fragment {
    //☆☆☆☆해당 프래그먼트가 RecruimentFragment에서 db로 저장한 값들을 불러와서 띄워줌!☆☆☆
    //여기서 아이템을 담아 어댑터로 반드시 보내야 한다!!
    // 즉 arraylist에 담긴것을 그대로 어댑터에 보내서 어댑터에서 갯수만큼 초기화 해주는거다!

    //1.db에있는값을 젤 아래쪽  arraylist1으로 저장시켜주고 순서대로. 그걸 ApplyAdapter의 생성자로 보냄
    //2.보낸 갯수만큼 이제 applayAdapter에서 갯수만큼 onBindViewHolder반복됨 그러면서 화면에 띄움 이게 핵심임.setAdapter을 하면 자동 발동 ㅇㅇ
    RecyclerView recyclerView_apply;
    ApplyAdapter applyAdapter;
    ArrayList<ApplyItem> applyItems1;
    ArrayList<CountItem> countItems; //여기에 이제 계속 업데이트가 되는거지.
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    DatabaseReference databaseReference=firebaseDatabase.getReference();

//String uid;

    TextView search;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView=(ViewGroup) inflater.inflate(R.layout.apply_fragment,container,false);
        applyItems1=new ArrayList<>();

        countItems=new ArrayList<>();//여기에
        search=rootView.findViewById(R.id.search);



        recyclerView_apply=rootView.findViewById(R.id.recyclerView_apply);
//      리사이클러뷰 특징 시작 1.ㅇㅇxml레이아웃  id가져옴
        //객체 빼와서 저장하는것도
        applyAdapter=new ApplyAdapter(applyItems1, countItems,getActivity()); //생성자로 넘겨준다 세가지의 값을 즉
        //applyAdapter로 값들 넘겨줌.

//★★★★★★★★★★
        //어댑터 가져와서 아래에 연결함.
        //★현재 담긴 내용들을 생성자를 통해 다 보내줌. ->여기서 이제 내용들을 다 보낸거임.★(처음에는 암것도 없고 아래에 applyItems1값 추가하는거 있다)
        //또한 현재 프래그먼트의 참조를 얻기 위해 getActivity가 사용된다.
        recyclerView_apply.setAdapter(applyAdapter); //리사이클러뷰에 어댑터 연결시 어댑터 내용들이 뜬다.
//       ※여기가 이제 리사이클러뷰 띄워주는 액티비티임 어댑터 연결해서 띄우는거 ㅇㅇ.※


        click_search(search);

        return rootView;
    }
    //그냥 해당 경로로 접근하고  리스너 추가시 addChildEventListener는 해당 위치의 데이터베이스에 새로운 자식 항목추가되면 자동으로 호출된다!
    // 각각의 콜백 메서드(onChildAdded(), onChildChanged(), onChildRemoved(), onChildMoved(), onCancelled())가 호출되어 해당 변경 사항을 처리합니다




    //클릭시 넘어가고
    public void click_search(TextView textView){
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
            }
        });
    }





    //☆☆☆이 값이 db에서 값을 가져옴!!!!!!!!! 어디에? applyItems1리스트에다가 추가☆☆☆
    // Recruitement 프래그먼트에서 추가한 값을 가져온다. 이제 큰 단락으로 보면 두번째 것이다.☆☆☆ +여기에 uid도 추가해서 클릭시 조회수 기능도 구현 예정.
    public void addChildEvent(){
        databaseReference.child("applyRecyclerView").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //해당 노드값에 접근시 자동으로 실행됨 ㅇㅇ
                //아래 for문은 db에 있는 자식들 순서대로 계속해서 반복시킴. 즉 한번 도는게 아니고 계속해서 반복함. apply list하위 노드갯수만큼. (아마 맞을거임)
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    ApplyItem item=dataSnapshot.getValue(ApplyItem.class);

                    applyItems1.add(0,item);//추가시 리스트의 제일 앞쪽에 추가하는거고,item은 추가할 객체이다.
                        //현재 item에는 값이 들어있다.

                }
                //이제 이걸 순서대로 넣어주면 됨
              //   리사이클러뷰 갱신 =>야! 이제 리스트 크기도 변할 거고, 아이템도 새로운 게 들어올 거야. 다시 새로 그려 즉 아이템간에 삽입, 삭제, 이동이 일어났을 때!"
            //즉 처음부터 실행된다고 생각하면 된다. ※정확한 구조는 확인해봐야함
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void addCountEvent(){
        // count에 대한 값들을 다 가져와서 countItems에 추가함.
        databaseReference.child("test").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //해당 노드값에 접근시 자동으로 실행됨 ㅇㅇ
                //아래 for문은 db에 있는 자식들 순서대로 계속해서 반복시킴. 즉 한번 도는게 아니고 계속해서 반복함. apply list하위 노드갯수만큼. (아마 맞을거임)
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    CountItem item2=dataSnapshot.getValue(CountItem.class); //이건 다 들어가야함 ㅇㅇ 일부만 빼는게 아니라 TEST아래에 CountItem클래스의 요소들이 다 존재해야함.

                    countItems.add(0,item2);//추가시 리스트의 제일 앞쪽에 추가하는거고,item은 추가할 객체이다.
                    // 이러면 제일 위 리스트에 추가가 된것이다.



                    Log.d("값확인",item2.toString());
                }
                //이제 이걸 순서대로 넣어주면 됨
                applyAdapter.notifyDataSetChanged();
                //   리사이클러뷰 갱신 =>야! 이제 리스트 크기도 변할 거고, 아이템도 새로운 게 들어올 거야. 다시 새로 그려 즉 아이템간에 삽입, 삭제, 이동이 일어났을 때!"
                //즉 처음부터 실행된다고 생각하면 된다. ※정확한 구조는 확인해봐야함
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

//새로고침 하는 기능이다.
    @Override
    public void onResume() {
        Log.d("간11111다","gd");

        super.onResume();
        addChildEvent();
        addCountEvent();
        applyItems1.clear();
    }




}
