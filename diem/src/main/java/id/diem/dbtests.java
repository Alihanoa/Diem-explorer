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
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Msi
 */
public class dbtests {
    static DiemJsonRpcClient client = new DiemJsonRpcClient("http://testnet.diem.com/v1", new ChainId((byte) 2));
    public static void main(String[] args) throws Exception{
       
       long version = 12;
 
       List<Transaction> liste = client.getTransactions(127, 1, false);
       
            for( Transaction t : liste){
//                System.out.println(t);
//                System.out.println(t.getTransaction().getType());
//                System.out.println(t.getTransaction().getTimestampUsecs());
//                System.out.println(t.getTransaction().getExpirationTimestampSecs());

//               String sender = t.getTransaction().getSender();
               
                System.out.println(t);
//                JsonRpc.Account sender = client.getAccount("00000000000000000000000000000000");
                 JsonRpc.Account receiver = client.getAccount(t.getTransaction().getScript().getReceiver());
                 System.out.println(t.getTransaction().getScript().getReceiver());
                 System.out.println(receiver);
                
//                setAccountBalances(t);
//               
//               
//               //Preburn Balances gibt es nur bei RoleType DesignatedDealer, also ist dieses Feld leer zu lassen,
//               //wenn der Sender/Receiver keindesignated Dealer ist. Außerdem ist das mit Preburn balances eh noch nicht implementiert,
//               //es ist immer 0
//               JsonRpc.Account sender = client.getAccount(t.getTransaction().getSender());
//               
//                System.out.println(sender.getBalances(1));
//               JsonRpc.Account receiver = client.getAccount(t.getTransaction().getScript().getReceiver());
//              JsonRpc.Amount amount= sender.getRole().getPreburnBalances(0);
//                System.out.println(sender.getRole().getType());
//                System.out.println(amount.getCurrency());
//                System.out.println(amount.getAmount());
//                System.out.println(sender);
//                System.out.println("________________________________________________________________");
//                System.out.println(receiver);
//               account.getRole().getPreburnBalances();
               //0 Ist XDX, 1 ist XUS
            }
//                if(!t.getTransaction().getSender().equals("")){
//                    JsonRpc.Account account = client.getAccount(t.getTransaction().getSender());
//                
//                    List<JsonRpc.Amount> amount =  account.getRole().getPreburnBalancesList();
//                    int i =0;
//                    for ( JsonRpc.Amount a : amount){
//                        System.out.println(i);
//                        System.out.println(a.getCurrency());
//                        i++;
//                    }
//                }
//
////                System.out.println(t);
////                System.out.println(account);
////                System.out.println(account.getRole().getHumanName());
////                System.out.println(account.getRole().getType());
//                
////                JsonRpc.Account receiver = client.getAccount(t.getTransaction().getScript().getReceiver());
////                System.out.println(receiver);
////                System.out.println(receiver.getRole().getHumanName());
////                System.out.println(receiver.getRole().getType());
////                System.out.println(receiver.getRole().getBaseUrl());
//            }
//            version ++;
//        }
    }
    
    public static  void setAccountBalances(JsonRpc.Transaction transaction) throws SQLException, DiemException{

		JsonRpc.Account sender = client.getAccount(transaction.getTransaction().getSender());
		JsonRpc.Account receiver = client.getAccount(transaction.getTransaction().getScript().getReceiver());

                      System.out.println(sender);
		      System.out.println(receiver);


			}
        
    }

