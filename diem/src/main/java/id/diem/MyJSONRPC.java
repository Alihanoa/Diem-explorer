/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.diem;

import com.diem.DiemException;
import com.diem.jsonrpc.DiemJsonRpcClient;
import com.diem.jsonrpc.JsonRpc;
import com.diem.jsonrpc.JsonRpc.Transaction;
import com.diem.types.ChainId;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Msi
 */
public class MyJSONRPC extends DiemJsonRpcClient{
    String serverurl= "http://testnet.diem.com/v1";
    public MyJSONRPC(String serverURL, ChainId chainId) {
        super("http://testnet.diem.com/v1", new ChainId((byte) 2));
    }
    
    public static void main(String[] args) throws DiemException{
        
        MyJSONRPC rpc = new MyJSONRPC("geqf",new ChainId((byte) 2));
        Transaction transaction = rpc.getAccountTransaction("65d590ccefbf8229e1dea4dcc3d5455a", 0, false);
//        JsonRpc.Event even = transaction.getEvents(0);
//        System.out.println(even);
        System.out.println(transaction);
//        List<JsonRpc.CurrencyInfo> list =  rpc.getCurrencies();
//        for(JsonRpc.CurrencyInfo a : list){
//            System.out.println(a.toString());
//            System.out.println("");
//        }
//        
        
//        JsonRpc.Account account = rpc.getAccount("19df853b44e496303b42e82c7fdba3e4");
//       String key =  account.getReceivedEventsKey();
//       List<JsonRpc.Event> liste = rpc.getEvents(key,0,1);
//       for(JsonRpc.Event event : liste){
//           System.out.println(event.toString() + "\n");
       }
//        JsonRpc.Metadata meta = rpc.getMetadata();
//        System.out.println(meta);
    }
    

