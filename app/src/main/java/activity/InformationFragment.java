    package activity;

    import static android.app.Activity.RESULT_OK;
    import static com.minseok.loginanddatabase.RegisterActivity.PICK_IMAGE_REQUEST;

    import android.content.Intent;
    import android.net.Uri;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.Button;
    import android.widget.ImageView;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.fragment.app.Fragment;
    import androidx.fragment.app.FragmentManager;
    import androidx.fragment.app.FragmentTransaction;

    import com.bumptech.glide.Glide;
    import com.google.android.gms.auth.api.signin.GoogleSignIn;
    import com.google.android.gms.auth.api.signin.GoogleSignInClient;
    import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
    import com.google.android.gms.tasks.OnCompleteListener;
    import com.google.android.gms.tasks.OnFailureListener;
    import com.google.android.gms.tasks.OnSuccessListener;
    import com.google.android.gms.tasks.Task;
    import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
    import com.google.android.material.floatingactionbutton.FloatingActionButton;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;
    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.ValueEventListener;
    import com.google.firebase.storage.FirebaseStorage;
    import com.google.firebase.storage.StorageReference;
    import com.google.firebase.storage.UploadTask;
    import com.minseok.loginanddatabase.LoginActivity;
    import com.minseok.loginanddatabase.R;
    import com.minseok.loginanddatabase.UserAccount;

    import CommentAdapter.comment1;
    import Myinformation.Daycalcul;
    import Myinformation.MyCalendarViewFragment;
    import Myinformation.countFregment;
    import Myinformation.list1;
    import Myinformation.myinforule;
    import Myinformation.protect;

    public class InformationFragment extends Fragment {
        TextView button;//내가 쓴 글 보기 액티비티
        TextView button2; //내가 쓴 댓글 보기 액티비티
        Boolean setting = false;//이건 xml파일과 상관없는거라 전역으로 선언해주는게 편함.
        TextView button3;//로그아웃 버튼

        TextView button10;
        TextView button11;

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        // 해당 버튼의 클릭 이벤트 처리

        TextView name;
        TextView email;
        //프로필 부분아래의 이름과 이메일
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        ImageView imageView6;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.information_fragment, container, false);

            //플로팅바 정의
            ExtendedFloatingActionButton add1 = rootView.findViewById(R.id.floatingActionButton1);
            FloatingActionButton add2 = rootView.findViewById(R.id.floatingActionButton2);
            FloatingActionButton add3 = rootView.findViewById(R.id.floatingActionButton3);
            FloatingActionButton add4 = rootView.findViewById(R.id.floatingActionButton4);

            TextView textView1 = rootView.findViewById(R.id.textView1);
            TextView textView2 = rootView.findViewById(R.id.textView2);
            TextView textView3 = rootView.findViewById(R.id.textView3);
            name = rootView.findViewById(R.id.name);
            email = rootView.findViewById(R.id.email);
            imageView6 = rootView.findViewById(R.id.imageView6);

            button10= rootView.findViewById(R.id.button10);//개인정보처리

            button10.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getContext(), myinforule.class);
                    startActivity(intent);



                }
            });

            button11=rootView.findViewById(R.id.button11);
            button11.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    Intent intent = new Intent(getContext(), protect.class);
                    startActivity(intent);



                }
            });




            button = rootView.findViewById(R.id.button4);//내가 쓴 글 보기
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    list1 a = new list1();
                    FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
                    fragmentTransaction1.replace(R.id.container, a).commit(); // commit() 호출을 빠뜨리지 마세요!
                }
            });

            button2 = rootView.findViewById(R.id.button5);//내가 쓴 댓글 보기
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    comment1 a = new comment1();
                    FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();//매니저의 트랜잭션 메서드 사용해서 이동하는거임.
                    fragmentTransaction1.replace(R.id.container, a).commit(); // commit() 호출을 빠뜨리지 마세요!
                }
            });

            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(getContext(), gso);

            button3 = rootView.findViewById(R.id.button6);
            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseAuth.getInstance().signOut();

                    googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // 로그아웃 성공
                                Intent intent = new Intent(getContext(), LoginActivity.class);
                                startActivity(intent);

                                Toast.makeText(getContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                                // 로그아웃 후에 추가 작업을 수행할 수 있습니다.
                            } else {
                                // 로그아웃 실패
                                Toast.makeText(getContext(), "로그아웃에 실패했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });

            //플로팅바에 대한 값들 정의 . 불값은 이제 보이게 하려면 해야함
            add2.setVisibility(View.GONE);
            add3.setVisibility(View.GONE);
            add4.setVisibility(View.GONE);

            textView1.setVisibility(View.GONE);
            textView2.setVisibility(View.GONE);
            textView3.setVisibility(View.GONE);

            add1.shrink();

            add2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    MyCalendarViewFragment cal = new MyCalendarViewFragment();
                    FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();//매니저의 트랜잭션 메서드 사용해서 이동하는거임.
                    fragmentTransaction1.replace(R.id.container, cal).commit(); // commit() 호출을 빠뜨리지 마세요!
                }
            });

            add3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    Daycalcul daycal = new Daycalcul();
                    FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();//매니저의 트랜잭션 메서드 사용해서 이동하는거임.
                    fragmentTransaction1.replace(R.id.container, daycal).commit(); // commit() 호출을 빠뜨리지 마세요!
                }
            });

            add4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    countFregment count = new countFregment();
                    FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();//매니저의 트랜잭션 메서드 사용해서 이동하는거임.
                    fragmentTransaction1.replace(R.id.container, count).commit(); // commit() 호출을 빠뜨리지 마세요!
                }
            });

            add1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!setting) {
                        add2.setVisibility(View.VISIBLE);
                        add3.setVisibility(View.VISIBLE);
                        add4.setVisibility(View.VISIBLE);

                        textView1.setVisibility(View.VISIBLE);
                        textView2.setVisibility(View.VISIBLE);
                        textView3.setVisibility(View.VISIBLE);

                        setting = true;
                        add1.extend();
                    } else {
                        add2.setVisibility(View.INVISIBLE);
                        add3.setVisibility(View.INVISIBLE);
                        add4.setVisibility(View.INVISIBLE);

                        textView1.setVisibility(View.GONE);
                        textView2.setVisibility(View.GONE);
                        textView3.setVisibility(View.GONE);

                        add1.shrink();

                        setting = false;
                    }
                }
            });

            //db에서 name에 대한 값 가져오기.
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();

            String uid = currentUser.getUid();
            //현재 uid
            databaseReference.child("UserAccount").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        UserAccount item = dataSnapshot.getValue(UserAccount.class);//이제 여기에 전부 다 저장되어있음 값들이. 여기서 이제 비교분 발동
                        //이 안에 저장시켜주고
                        Log.d("확인하기2", item.getUID()); //getUID로 각 UID 가져옴

                        Log.d("확인하기3", uid); //현재 uid가져옴.

                        String nickname1 = item.getNickname();

                        //현재 로그인된 사용자와 위에서 현재 사용자 뽑아준것.
                        if (uid.equals(item.getUID())) {
                            Log.d("확인하기5", nickname1); //현재 uid가져옴.

                            String nickname = item.getNickname();
                            //사용자 닉네임 불러옴.
                            name.setText(nickname);
                            //즉 클릭하면 화면에 뜸. 즉 data
                        }

                        if (uid.equals(item.getUID())) {
                            String emailId = item.getEmailId();
                            email.setText(emailId);
                            //즉 클릭하면 화면에 뜸. 즉 data
                        } else {
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            imageView6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 갤러리에서 이미지를 선택하기 위한 Intent 생성
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, PICK_IMAGE_REQUEST); // PICK_IMAGE_REQUEST는 이미지 선택 결과를 처리하는 requestCode입니다.
                }
            });

            return rootView;
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                Uri imageUri = data.getData();
                Log.d("InformationFragment", "Image URI: " + imageUri.toString()); // 디버깅용 로그 추가

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    String uid = user.getUid();
                    Log.d("InformationFragment", "User UID: " + uid); // 디버깅용 로그 추가

                    StorageReference userRef = storageRef.child("uploads/" + uid + "/profile.jpg");



                    userRef.putFile(imageUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Log.d("InformationFragment", "Image upload successful"); // 디버깅용 로그 추가

                                    userRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            Log.d("InformationFragment", "Download URL: " + uri.toString()); // 디버깅용 로그 추가

                                            Glide.with(requireContext())
                                                    .load(uri)
                                                    .into(imageView6);
                                            Toast.makeText(getContext(), "이미지 업로드 성공", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e("InformationFragment", "Image upload failed", e); // 디버깅용 로그 추가
                                    Toast.makeText(getContext(), "이미지 업로드 실패: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }

                else {
                    Log.d("InformationFragment", "User is null"); // 디버깅용 로그 추가
                }
            } else {
                Log.d("InformationFragment", "No image selected or result is not OK"); // 디버깅용 로그 추가
            }
        }
            }
