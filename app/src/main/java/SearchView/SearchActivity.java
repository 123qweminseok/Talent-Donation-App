package SearchView;

import android.os.Bundle;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class SearchActivity extends AppCompatActivity {

    // UI 요소 선언: 검색창과 리사이클러뷰

    SearchView searchView;
    RecyclerView recyclerView_search;

    // 어댑터와 데이터 리스트 선언

    ApplyAdapter applyAdapter;
    ArrayList<ApplyItem> searchResults;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference("applyRecyclerView");
    ArrayList<CountItem> countItems; //여기에 이제 계속 업데이트가 되는거지.

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchView = findViewById(R.id.searchView);
        recyclerView_search = findViewById(R.id.recyclerView_search);
countItems = new ArrayList<>();
        searchResults = new ArrayList<>();
        applyAdapter = new ApplyAdapter(searchResults, countItems,getApplicationContext());//여기로 생성자 두번째껀사실 ㅡ이미없고
        //getApplicationContext이게 좀 의문이긴 한데

        recyclerView_search.setAdapter(applyAdapter);
        //해당 리사이클러뷰가 업데이트 된다. 즉 어댑터는 하나의 고정된 기계일 뿐. 생성자로 값 날려서 그냥 해당 리사이클러뷰에 띄워주는 역할을 한다.

        recyclerView_search.setLayoutManager(new LinearLayoutManager(this));
        // 리사이클러뷰 레이아웃 매니저 설정

        searchdata(searchView);
    }



    // 검색 기능을 설정하는 메서드
    public void searchdata(SearchView searchView){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // 검색어 제출 시 Firebase에서 검색

                searchInFirebase(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // 텍스트가 변경될 때는 아무 동작도 하지 않음

                return false;
            }
        });
    }


    // Firebase에서 데이터를 검색하는 메서드
    private void searchInFirebase(String query) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 임시 결과 리스트 초기화
                ArrayList<ApplyItem> tempResults = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ApplyItem item = snapshot.getValue(ApplyItem.class);
                    if (item != null && (item.getTitle().contains(query) || item.getContent().contains(query))) {
                        tempResults.add(item);
                    }
                }
                searchResults.clear();
                searchResults.addAll(tempResults);
                applyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors.
            }
        });
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.stay,R.anim.slide_out_right);
    }
}