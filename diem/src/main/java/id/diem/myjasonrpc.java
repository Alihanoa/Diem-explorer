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
import com.diem.jsonrpc.Response;
import java.util.ArrayList;
import java.time.Clock;
import com.google.gson.JsonElement;


public class myjasonrpc {

    private static DiemJsonRpcClient client = new DiemJsonRpcClient("http://testnet.diem.com/v1", new ChainId((byte) 2));
    private static Clock c = Clock.systemDefaultZone();
    private static long vers = 0;
    private static long sek;
    private static List<JsonRpc.Transaction> unseretrans;
    public static void main(String[] args) throws DiemException, InterruptedException {

//        getTransactionsasec(1);
//      List<JsonRpc.Transaction> l = client.getTransactions(0, 1000000, false);
//      System.out.println(l.get(0));

        List<JsonRpc.Transaction> transaktionen = client.getTransactions(0, 1000, false);
        
        for( JsonRpc.Transaction t : transaktionen){
            if(!t.getTransaction().getSender().equals("")){
                
                String address = t.getTransaction().getSender();
                System.out.println(address);
                JsonRpc.Account account = client.getAccount(address);
                System.out.println(account.getAuthenticationKey());
                System.out.println(account.getIsFrozen());
                System.out.println(account.getSequenceNumber());
            }
        }
    }
    


    public static void getTransactionsasec(int a) throws DiemException, InterruptedException {
           
        unseretrans = new ArrayList();
        int zaehler = 1;
        while (zaehler <= 1000000) {
            sek = c.millis() / 1000;
            if (sek % a == 0) {
                List<JsonRpc.Transaction> liste = client.getTransactions(0, 1000, false);
                int i = liste.size();
                if (i != 0) {
                    vers = liste.get(i - 1).getVersion();
                    for (Transaction t : liste) {
                         if (t.getTransaction().getScript().getType().equals("peer_to_peer_with_metadata")
//                                 && !t.getTransaction().getSender().equals("000000000000000000000000000000dd")
                                 ){
                             unseretrans.add(t);
                             t.getTransaction().getScript();
                         System.out.println(t);
                         }
                            
                        
                    }
                }
                liste.clear();
            }
            zaehler ++;
            Thread.sleep(100);
        }
    }
    
    
}
