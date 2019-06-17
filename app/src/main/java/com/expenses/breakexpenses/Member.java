package com.expenses.breakexpenses;

public class Member {


    String id,member_name,member_phone_number,to_be_paid,paid,member_parent_id;

    public Member(String id,String member_name, String member_phone_number, String to_be_paid, String paid) {
        this.member_name = member_name;
        this.member_phone_number = member_phone_number;
        this.to_be_paid = to_be_paid;
        this.paid = paid;
        this.id=id;
    }

    public Member(String id, String member_name, String member_phone_number, String to_be_paid, String paid, String member_parent_id) {
        this.id = id;
        this.member_name = member_name;
        this.member_phone_number = member_phone_number;
        this.to_be_paid = to_be_paid;
        this.paid = paid;
        this.member_parent_id = member_parent_id;

    }

    public Member() {
    }

    public String getMember_parent_id() {
        return member_parent_id;
    }

    public void setMember_parent_id(String member_parent_id) {
        this.member_parent_id = member_parent_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getMember_phone_number() {
        return member_phone_number;
    }

    public void setMember_phone_number(String member_phone_number) {
        this.member_phone_number = member_phone_number;
    }

    public String getTo_be_paid() {
        return to_be_paid;
    }

    public void setTo_be_paid(String to_be_paid) {
        this.to_be_paid = to_be_paid;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }
}
