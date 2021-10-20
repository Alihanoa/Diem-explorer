/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diemexplorer.explorer.JSONRPCtoDB;

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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.time.Clock;

public class myjsonrpc {

    private static DiemJsonRpcClient client = new DiemJsonRpcClient("http://testnet.diem.com/v1", new ChainId((byte) 2));
    private static Clock c = Clock.systemDefaultZone();
    private static long vers = 0;
    private static long sek;
    private static List<JsonRpc.Transaction> transactions;
    private static Connection con;

    public static void main(String[] args) throws DiemException, SQLException {

        getTransactionsasec(10);
      List<JsonRpc.Transaction> l = client.getTransactions(93676, 1, false);
      System.out.println(l.get(0));
        try {

            getTransactionsasec(3);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void getTransactionsasec(int a) throws DiemException, SQLException {
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/diemexplorer?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "password");
        PreparedStatement statement;
        transactions = new ArrayList();
        while (true) {
            sek = c.millis() / 1000;
            if (sek % a == 0) {
                List<JsonRpc.Transaction> l = client.getTransactions(vers, 1000, false);
                int i = l.size();
                if (i != 0) {
                    vers = l.get(i - 1).getVersion();
                    for (Transaction t : l) {
                        if (t.getTransaction().getScript().getType().equals("peer_to_peer_with_metadata") //                                 && !t.getTransaction().getSender().equals("000000000000000000000000000000dd")
                                ) {
                            transactions.add(t);
                            String query = "INSERT INTO transactiondetails (version, sender_id, public_key, amount, currency, gas_currency, gas_used, expiration_timestamp_seconds) "
                                    + "VALUES (" + t.getVersion() + "," + "'" + t.getTransaction().getSender() + "'" + ","
                                    + "'" + t.getTransaction().getPublicKey() + "'" + "," + t.getTransaction().getScript().getAmount() + ","
                                    + "'" + t.getTransaction().getScript().getCurrency() + "'" + ", '" + t.getTransaction().getGasCurrency() + "',"
                                    + t.getGasUsed() + "," + t.getTransaction().getExpirationTimestampSecs()  + ")";

                            statement = con.prepareStatement(query);
                            statement.executeUpdate();
                        }

                    }
                }
                l.clear();
            }
        }
    }

}
