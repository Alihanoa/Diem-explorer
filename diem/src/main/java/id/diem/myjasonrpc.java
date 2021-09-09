/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.diem;

/**
 *
 * @author Tim
 */
import com.diem.DiemException;
import com.diem.jsonrpc.DiemJsonRpcClient;
import com.diem.types.ChainId;
import com.diem.jsonrpc.JsonRpc;
import com.diem.jsonrpc.JsonRpc.Transaction;
import java.util.List;
import com.google.protobuf.*;
import com.diem.*;
import com.diem.jsonrpc.JsonRpc.Event;
import java.util.ArrayList;
import java.time.Clock;
import java.time.ZoneId;


public class myjasonrpc {

    private static DiemJsonRpcClient client = new DiemJsonRpcClient("http://localhost:8080", new ChainId((byte) 4));
    private static Clock c = Clock.systemDefaultZone();
    private static long vers = 0;
    private static long sek;
    private static List<JsonRpc.Transaction> unseretrans;
    public static void main(String[] args) throws DiemException {

//        getTransactionsa10sec();
//      List<JsonRpc.Transaction> l = client.getTransactions(93676, 1, false);
//      System.out.println(l.get(0));
    }
    


    public static void getTransactionsasec(int a) throws DiemException {
           
        unseretrans = new ArrayList();
        while (true) {
            sek = c.millis() / 1000;
            if (sek % a == 0) {
                List<JsonRpc.Transaction> l = client.getTransactions(vers, 1000, false);
                int i = l.size();
                if (i != 0) {
                    vers = l.get(i - 1).getVersion();
                    for (Transaction t : l) {
                         if (t.getTransaction().getScript().getType().equals("peer_to_peer_with_metadata")
//                                 && !t.getTransaction().getSender().equals("000000000000000000000000000000dd")
                                 ){
                             unseretrans.add(t);
                             t.getTransaction().getScript().
                         System.out.println(t);
                         }
                            
                        
                    }
                }
                l.clear();
            }
        }
    }
}
