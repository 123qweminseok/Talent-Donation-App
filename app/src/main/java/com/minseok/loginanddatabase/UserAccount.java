package com.minseok.loginanddatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;



public class UserAccount  {
    public UserAccount() {

    }


    private String emailId;
    public int visit=0; //추가
    private String UID;
    private String nickname; //추가
    private String memo; //추가

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getNickname() { //추가
        return nickname;
    }

    public void setNickname(String nickname) { //추가
        this.nickname = nickname;
    } //추가

    public int getVisit() { //추가
        return visit;
    } //추가


    public String getUID(){ return UID;}

    public void setUID(String UID){

        this.UID= UID;

    }




    public String getEmailId(){
        return emailId;}


    //내부 에 설정=set
    public void setEmailId(String emailId){

        this.emailId=emailId;}




    //반환하는건 get메서드
    //즉 get,set메서드 ㅇㅇ
    public String getPassword() {   return password;}

    public void setPassword(String password){this.password=password;}
    private String password;}


