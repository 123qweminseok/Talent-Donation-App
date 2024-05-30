package activity;

import java.util.UUID;

public class ApplyItem {
    private String id;
    private String title;
    private String content;
    private String number;
    private String date;
    private int viewCount; //조회수에 대한 값임
    private String getPosition;


    private String Uid;

    private int count; // 조회수 필드 추가 이제 db에 있는 값이랑 비교해서 가져올거임.

   // private String nickname;

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

//    public String getNickname() {
//        return nickname;
//    }
//
//    public void setNickname(String nickname) {
//        this.nickname = nickname;
//    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    private int view; //조회수




    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }




    public void setUid(String uid1) {
        this.Uid = uid1;
    }

    public String getUid() {
        return Uid;
    }

    public void setGetPosition(String uid2) {
        this.getPosition = uid2;
    }

    public String getGetPosition() {
        return getPosition;
    }



    public ApplyItem() {
        this.id = UUID.randomUUID().toString();
    } //이게 자동으로 만들어짐 전부 구별가능

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public int getViewCount() {
        return viewCount;
    }





}