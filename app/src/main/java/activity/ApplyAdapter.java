package activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.minseok.loginanddatabase.R;

import java.util.ArrayList;


//걍 어댑터임 나중에 연결만 시키면 됨 -여기서 이제 모든것이 연결된다. 화면에 띄워주는 역할을 하는거지
public class ApplyAdapter extends RecyclerView.Adapter<ApplyAdapter.ViewHolder> {
//ApplyAdapter 클래스는 RecyclerView의 어댑터를 정의하는 것이며,
// 어떤 화면에도 직접적으로 연결되어 있지 않습니다. 따라서 getActivity()와 같은 메서드를 사용할 수 없습니다.
    //※그래서 생성자를 통해 값을 받아와 저장시켜준다 ※모든건 어댑터를 연결시키는 액티비티에서 해당 어댑터로 화면에
    // 각각의 뷰들이 생성되는거다! setAdapter 메서드시에 해당 내부 메서드들이 자동으로 실행된다!!※



    private ArrayList<ApplyItem> applyItems; //여기엔 이미 아래의 생성자를 통해 각각의 포지션에 맞게 값이 저장이 되어있다. 즉 applyItems(0.1,2,3...)으로 가능하다는 것이다.

    private Context context;
    private ArrayList<CountItem> abab; //아래 생성자를 통해 온값들이 저장된다

    FirebaseAuth mFirebaseauth;

    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    DatabaseReference databaseReference=firebaseDatabase.getReference();

    public ApplyAdapter(ArrayList<ApplyItem> applyItems1, ArrayList<CountItem> countItems, Context context) {
        this.applyItems = applyItems1;

    this.abab=countItems;
        this.context = context;

        //여기로 ApplyFragment클래스에 대한 화면이 제공된다.
        //ApplyFragment클래스에서 생성자를 통해  db에서 뺀 값들이 저장된다. 계속해서 넣어서 리스트에 차곡차곡 쌓음.
//즉 ApplyFragment에서 생성자를 통해 db에서 가져온 값들을 여기에다가 추가하고 즉 쌓인 것들 갯수만큼onCreateViewHolder 이 발동.해서 onBindViewHolder   의 매개변수로 들어감. getItemCount이것도 한번 실행후 매개변수로 들어감. 반복횟수라 생각하면 됨.
    }


    //1시작
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder= LayoutInflater.from(parent.getContext()).inflate(R.layout.apply_list,parent,false);

        mFirebaseauth=FirebaseAuth.getInstance();

        return new ViewHolder(holder);
        //리턴을 onBindViewHolder의 매개변수로 들어간다. 즉 아래에서 뷰홀더 객체가 만들어지며 값들이 리턴되는거임!
//그리고 안에 저장된 값들을 뽑음


    }


    //여기가 이제 화면에 setText로 나타나게 해주는것임!! 즉 position이라고 위치값이 설정이 되잖아 0번,1번,2번 그니까 이걸 그냥
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //위에있는 배열에서 위치값 뽑는다. 즉 하나의 객체처럼

        holder.text_title.setText(applyItems.get(position).getTitle());
        holder.text_content.setText(applyItems.get(position).getContent());
        holder.text_date.setText(applyItems.get(position).getDate());

            holder.bubble_number.setVisibility(View.VISIBLE);
            holder.bubble_number.setText(applyItems.get(position).getNumber());


Log.d("간다","ㄲ");
if(applyItems.get(position).getView()==0) {

    holder.text_view_count.setText("조회 0");
}else{
        holder.text_view_count.setText("조회" + applyItems.get(position).getView());

}


}





    @Override
    public int getItemCount() {
        if (applyItems != null) {
            return applyItems.size();
        } else {
            return 0;
        }
    }

    //2 시작
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text_title,text_content,bubble_number,text_date,speech_bubble,line,text_view_count;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            text_title=itemView.findViewById(R.id.text_title);
            text_content=itemView.findViewById(R.id.text_content);
            bubble_number=itemView.findViewById(R.id.bubble_number);
            text_date=itemView.findViewById(R.id.text_date);
            speech_bubble=itemView.findViewById(R.id.speech_bubble);
            line=itemView.findViewById(R.id.line);
           text_view_count = itemView.findViewById(R.id.item_content); // 추가된 텍스트뷰 즉 count다

            ////////////////////////////////////////////////////////////////////////////////////////////////
            //이제 클릭하면 db속의 값이 증가되도록 수정 setValue안에 해당 글에 대한 값이 들어가면 됨 +1을 하거나.
////////////////////////////////////////////////////////////////////////////////////////


          itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    //이제 인덱스 값에 따라 db에 넣었다. 이제 불러오면 된다. 해당 인덱스에 맞는 값을 눌렀을때 count값을 증가시키면 된다.

                    int positon=getAdapterPosition(); //해당 리사이클러뷰에 위치를 받아온다 각각의 리사이클러뷰 위치임.(인덱스)

                    Intent intent=new Intent(context, RecruitmentActivity.class);
                    //해당 액티비티로 클릭하면 넘어가는데 이건 댓글 달수있게 하는 액티비티이다.
                    intent.putExtra("id",applyItems.get(positon).getId()); // 해당 고유 id를 모집하기 엑티비티로
                    intent.putExtra("title",applyItems.get(positon).getTitle()); //해당 리사이클러뷰에 제목을 모집하기 엑티비티로
                    intent.putExtra("content",applyItems.get(positon).getContent()); //해당 리사이클러뷰에 내용을 모집하기 엑티비티로
                    intent.putExtra("date",applyItems.get(positon).getDate()); //해당 리사이클러뷰에 날짜를 모집하기 엑티비티로
                    context.startActivity(intent);
                    ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.stay);

                    DatabaseReference applyListRef=databaseReference.child("applyRecyclerView");
                    applyListRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot applySnapshot: snapshot.getChildren()){

                                ApplyItem applyItem= applySnapshot.getValue(ApplyItem.class);//파베에 있는 값을
                                //applyItem에 값을 저장해준다. id랑 값들을 applyItem에 저장한다, 즉 클래스에 저장이 된다.
                                //객체를 만든거다.! 즉 해당 applyItem이란 객체가 생성되고 그 안에 값들이 저장이 된것이다.

                                if(applyItem.getId().equals(applyItems.get(positon).getId())){
                                    applySnapshot.getRef().child("view").setValue(applyItem.getView()+1);
                                    //db에서 값을 가져와주니 계속해서 증가할 것이다,


                                }

                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                }            });
//            윗부분은 화면이 넘어갔을때 값을 전달해주는 역할을 한다. 즉 클릭시 다른 액티비티로 정보를 넘겨준다. 모집하기로 넘겨준다.
        }
    }
}

/*            DatabaseReference applyRecyclerView=databaseReference.child("applyRecyclerView");
            applyRecyclerView.push().setValue(); //여기 안에 이제 해당 리사이클러뷰에 대해 클릭하면 증가되도록 구성되어야함.
            //즉 뷰홀더 객체가 들어가는데 해당 포지션 즉 위치에 맞는 값이 들어가야 함. 그 값이 1씩 증가되도록 만들어야 함.

*/

            //이건 글 모집하기 위한것임.  그리고 지금 해당 글 클릭하면 조회수가 증가해야 하니까 여기에도 추가를 해줘야 겠음.
            //1.쓰는 글들 즉 글들이 추가되는 db값에다가 조회수에 대한걸 넣어줘야함.->  recruitment프래그먼트에서 넣어줬음 applyRecycler뷰안에. 이제 해당 값을 증가시켜야지.
            //2.그리고 여기서 클릭이 될때마다 값이 1씩 증가하도록 설계해야함 즉 setValue로 +1씩 해주면 됨//
            //3.이제 db값을 가져와야지
//            윗부분은 화면이 넘어갔을때 값을 전달해주는 역할을 한다. 즉 클릭시 다른 액티비티로 정보를 넘겨준다. 모집하기로 넘겨준다.
