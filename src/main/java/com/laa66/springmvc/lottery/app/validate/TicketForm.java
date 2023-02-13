package com.laa66.springmvc.lottery.app.validate;

@ValidTicketNumbers
public class TicketForm {

    private int field1;

    private int field2;

    private int field3;

    private int field4;

    private int field5;

    private int field6;

    public TicketForm() {

    }

    public TicketForm(int field1, int field2, int field3, int field4, int field5, int field6) {
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
        this.field4 = field4;
        this.field5 = field5;
        this.field6 = field6;
    }

    public int getField1() {
        return field1;
    }

    public void setField1(int field1) {
        this.field1 = field1;
    }

    public int getField2() {
        return field2;
    }

    public void setField2(int field2) {
        this.field2 = field2;
    }

    public int getField3() {
        return field3;
    }

    public void setField3(int field3) {
        this.field3 = field3;
    }

    public int getField4() {
        return field4;
    }

    public void setField4(int field4) {
        this.field4 = field4;
    }

    public int getField5() {
        return field5;
    }

    public void setField5(int field5) {
        this.field5 = field5;
    }

    public int getField6() {
        return field6;
    }

    public void setField6(int field6) {
        this.field6 = field6;
    }

    @Override
    public String toString() {
        return "TicketNumbersValid{" +
                "field1=" + field1 +
                ", field2=" + field2 +
                ", field3=" + field3 +
                ", field4=" + field4 +
                ", field5=" + field5 +
                ", field6=" + field6 +
                '}';
    }
}
