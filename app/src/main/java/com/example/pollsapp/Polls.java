package com.example.pollsapp;
public class Polls {
    private String topic, question,option1, option2;
    private int option1_count, option2_count;

    public Polls(String topic, String question, String option1, String option2) {
        this.topic = topic;
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        option1_count = 0;
        option2_count = 0;
    }

    public Polls(){

    }

    public int getOption1_count() {
        return option1_count;
    }

    public int getOption2_count() {
        return option2_count;
    }

    public String gettopic() {
        return topic;
    }

    public String getquestion() {
        return question;
    }

    public String getoption1() {
        return option1;
    }

    public String getoption2() {
        return option2;
    }
}
