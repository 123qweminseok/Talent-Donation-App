    package com.minseok.loginanddatabase;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.appcompat.app.AppCompatActivity;

    import com.google.android.gms.tasks.OnFailureListener;
    import com.google.android.gms.tasks.OnSuccessListener;
    import com.google.firebase.storage.FirebaseStorage;
    import com.google.firebase.storage.StorageReference;

    import android.content.Intent;
    import android.net.Uri;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ImageView;
    import android.widget.Toast;

    import com.google.android.gms.tasks.OnCompleteListener;
    import com.google.android.gms.tasks.Task;
    import com.google.firebase.auth.AuthResult;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.storage.UploadTask;

    import activity.ApplyFragment;
    import activity.MainActivity;

    public class RegisterActivity extends AppCompatActivity {

        public static final int PICK_IMAGE_REQUEST = 1;
        private ImageView imageView;
        private Uri imageUri;
        private StorageReference storageReference;

            private FirebaseAuth mFirebaseauth;//Firebase 인증 객체

            private DatabaseReference mDatabaseRef;//실시간 데이터베이스 참소 객체
            private EditText mEtEmail,mEtpwd,editTextText;
            private Button mBtnRegister;

    //가입버튼임.

            @Override
            protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);

            // Firebase 인증 객체 초기화
            mFirebaseauth=FirebaseAuth.getInstance();

            // Firebase 실시간 데이터베이스 참조 객체 초기화
            mDatabaseRef= FirebaseDatabase.getInstance().getReference();


                editTextText=findViewById(R.id.editTextText);

            mEtEmail=findViewById(R.id.et_email);
            mEtpwd=findViewById(R.id.et_pwd);
            mBtnRegister=findViewById(R.id.btn_register);//가입버튼 누르면




                storageReference = FirebaseStorage.getInstance().getReference("uploads");


                // 회원가입 버튼 클릭 시 동작하는 클릭 리스너 설정

                mBtnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    // 사용자가 입력한 이메일과 비밀번호 가져오기
                    String strEmail = mEtEmail.getText().toString(); //입력한 텍스트를 불러와 문자열로 변환하고
                    String strPwd = mEtpwd.getText().toString();//이것도
                    String memo= editTextText.getText().toString();//불러옴.


                    // Firebase Auth 객체의 createUserWithEmailAndPassword 메서드를 사용하여 사용자를 생성한다.(이메일,비번) 이걸로 사용자 정보가 설정됨.
                    mFirebaseauth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            //성공적이냐고 묻는거다. 불값 반환
                            if (task.isSuccessful()) {



                                //회원가입이 성공했을때 "새로 생성된 사용자의" 정보를 가져온다.
                                //이 정보를 가져오는 이유는 회원가입 성공 후에 해당 사용자의 UID나 이메일과 같은 정보를 이용하여 추가적인 작업을 수행하기 위함
                                FirebaseUser firebaseUser = mFirebaseauth.getCurrentUser();

                                // 만들어준 사용자 정보 클래스의 객체에 값들을 설정. 즉 아래 값들로 account객체참조변수의 멤버들에 값들을 저장시켜줌.
                                UserAccount account = new UserAccount();

                                //사용자 uid를 계정 정보에 설정
                                account.setUID(firebaseUser.getUid());
                                //name변수에 uid값 저장


                                //사용자 이메일을 계정 정보에 설정
                                account.setEmailId(firebaseUser.getEmail());
                                //사용자 패스워드도 계정 정보에 설정
                                account.setPassword(strPwd);
                                account.setMemo(memo);
                                //위 값들은 다 UserAccount 클래스에 있는거다. 직접 만든것임.
                                //내가 적은  텍스트를 momo에 저장한걸 보내서 account객참변에 저장해줌.

                                // Firebase 데이터베이스에 사용자 계정 정보 저장
                                //데이터베이스 참조변수 들어가서 UserAccount라는 제일 상위 노드, 그아래 getuid노드설정.
                                //마지막 setValue메서드로 해당 노드에 데이터를 저장한다. 이 메서드로 실시간 db에 기록이 되는것이다.
                                mDatabaseRef.child("UserAccount").push().setValue(account);
                                // 회원가입 성공 메시지 표시
                                Toast.makeText(RegisterActivity.this, "회원가입 성공", Toast.LENGTH_LONG).show();
                            }else{
                                // 회원가입 실패 시 실패 메시지 표시
                                Toast.makeText(RegisterActivity.this, "실패", Toast.LENGTH_LONG).show();


                            }

                        }

                    });
                }
            });











        }

        // 파일 선택기 열기

        // 파일 선택 결과 처리



        // 이미지 업로드




    }

