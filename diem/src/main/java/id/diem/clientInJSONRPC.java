/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.diem;

import com.diem.AuthKey;
import com.diem.DiemClient;
import com.diem.DiemException;
import com.diem.Ed25519PrivateKey;
import com.diem.PrivateKey;
import com.diem.Signer;
import static com.diem.Testnet.CHAIN_ID;
import com.diem.jsonrpc.JsonRpc.Account;
import com.diem.jsonrpc.JsonRpc.Transaction;
import com.diem.jsonrpc.StaleResponseException;
import com.diem.stdlib.Helpers;
import com.diem.types.ChainId;
import com.diem.types.RawTransaction;
import com.diem.types.SignedTransaction;
import com.diem.types.TransactionPayload;
import com.diem.utils.CurrencyCode;
import com.novi.serde.Bytes;
import java.security.SecureRandom;
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters;

/**
 *
 * @author Msi
 */
public class clientInJSONRPC {
    public static void main(String[] args) throws DiemException{
        //Unser Testnet mit der URL von lokalem Testnet und Faucet
        MyTestnet testnet = new MyTestnet();
        // unser lokaler endpoint, wo wir die ganzen Informationen herbekommen werden
        MyJSONRPC rpc = new MyJSONRPC("",new ChainId((byte) 4 ));
        
        DiemClient client = testnet.createClient();
        
        //erzeuge Account sender
        PrivateKey senderPrivateKey = new Ed25519PrivateKey(new Ed25519PrivateKeyParameters(new SecureRandom()));
        AuthKey senderAuthKey = AuthKey.ed24419(senderPrivateKey.publicKey());
        
        MyTestnet.mintCoins(client, 100, senderAuthKey.hex(), "XUS");
        Account sender = client.getAccount(senderAuthKey.accountAddress());
        
//        //erzeuge Account receiver
        PrivateKey receiverPrivateKey = new Ed25519PrivateKey(new Ed25519PrivateKeyParameters(new SecureRandom()));
        AuthKey receiverAuthKey = AuthKey.ed24419(receiverPrivateKey.publicKey());

        MyTestnet.mintCoins(client, 37, receiverAuthKey.hex(), "XUS");
        Account receiver = client.getAccount(receiverAuthKey.accountAddress());
        
        
        
        
        
        
//        Führe Transaktion durch um zu gucken ob man auf diese durch MyJSONRPC zugreifen kann
                TransactionPayload script = new TransactionPayload.Script(
                Helpers.encode_peer_to_peer_with_metadata_script(
                        CurrencyCode.typeTag("XUS"),
                        receiverAuthKey.accountAddress(),
                        3000000L,
                        new Bytes(new byte[0]),
                        new Bytes(new byte[0])));
                
                
                
        RawTransaction rawTransaction = new RawTransaction(
                senderAuthKey.accountAddress(),
                sender.getSequenceNumber(),
                script,
                1000000L,
                0L,
                "XUS",
                (System.currentTimeMillis() / 1000) + 300,
                CHAIN_ID);
        
        
        
        
        
        SignedTransaction st = Signer.sign(senderPrivateKey, rawTransaction);
        
        
        
        
        
        try {
            client.submit(st);
        } catch (StaleResponseException e) {
            //ignore%
        }
        Transaction transaction = rpc.getAccountTransaction(sender.getAddress(), 0, true);
        
        System.out.println(transaction);
        
    }
}
