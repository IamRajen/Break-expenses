package com.expenses.breakexpenses;

class Contact {


    String contact_name,contact_phone_number;

    public Contact(String contact_name, String contact_phone_number) {
        this.contact_name = contact_name;
        this.contact_phone_number = contact_phone_number;
    }

    public Contact() {
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getContact_phone_number() {
        return contact_phone_number;
    }

    public void setContact_phone_number(String contact_phone_number) {
        this.contact_phone_number = contact_phone_number;
    }
}
