package com.diemexplorer.explorer.JSONRPCtoDB;

import com.diemexplorer.explorer.Entities.Transactions;

public class test
{

    public static void main(String[]args){

        Transactions t = new Transactions(1234L,"sidugvhfspödiufh","12dpkllme4rt","apisfzhf23iusdpoaspiaepü", 12L, "XUS","XUS",1,"1.1.1","penis",123421L);
        t.setAddressshort();
        System.out.println(t.getaddressshort());
    }
}
