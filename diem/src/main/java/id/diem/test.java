package id.diem;

import Entitäten.DBAccount;
import com.diem.*;
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
 *
 */
public class test {

    public static void main(String[] args) throws DiemException {

        MyTestnet testnet = new MyTestnet();
//        DAO dao = new DAO();
//connect to testnet
        DiemClient client = testnet.createClient();
//        MyJSONRPC  rpc = new MyJSONRPC("qefq",new ChainId((byte) 4));

//generate private key for sender account
        PrivateKey senderPrivateKey = new Ed25519PrivateKey(new Ed25519PrivateKeyParameters(new SecureRandom()));

//generate auth key for sender account
        AuthKey senderAuthKey = AuthKey.ed24419(senderPrivateKey.publicKey());

//create sender account with 100 XUS balance
        MyTestnet.mintCoins(client, 100000000, senderAuthKey.hex(), "XUS");

//get sender account for sequence number
        Account account = client.getAccount(senderAuthKey.accountAddress());
        
//        System.out.println(account.toString());
//        dao.em.getTransaction().begin();
        
//System.out.println(account.toString());
//
//DBAccount dba = new DBAccount();
//
//dba.setAdress(account.getAddress());
//dba.setAuthenticationKey(account.getAuthenticationKey());
//
//
//        dao.em.persist(dba);
        
//generate private key for receiver account
        PrivateKey receiverPrivateKey = new Ed25519PrivateKey(new Ed25519PrivateKeyParameters(new SecureRandom()));

//generate auth key for receiver account
        AuthKey receiverAuthKey = AuthKey.ed24419(receiverPrivateKey.publicKey());



//create receiver account with 1 XUS balance. Account gets created after coins minted on it
        MyTestnet.mintCoins(client, 10000000, receiverAuthKey.hex(), "XUS");
        
//        Account receiver = client.getAccount(receiverAuthKey.accountAddress());
//        DBAccount empfaenger = new DBAccount();
//        
//        empfaenger.accToDBAcc(receiver);
//        dao.em.persist(empfaenger);
//        dao.em.getTransaction().commit();
//        
//create script
        TransactionPayload script = new TransactionPayload.Script(
                Helpers.encode_peer_to_peer_with_metadata_script(
                        CurrencyCode.typeTag("XUS"),
                        receiverAuthKey.accountAddress(),
                        10000000L,
                        new Bytes(new byte[0]),
                        new Bytes(new byte[0])));

//create transaction to send 1 XUS
        RawTransaction rawTransaction = new RawTransaction(
                senderAuthKey.accountAddress(),
                account.getSequenceNumber(),
                script,
                1000000L,
                0L,
                "XUS",
                (System.currentTimeMillis() / 1000) + 300,
                CHAIN_ID);

//sign transaction
        SignedTransaction st = Signer.sign(senderPrivateKey, rawTransaction);

//submit transaction
        try {
            client.submit(st);
        } catch (StaleResponseException e) {
            //ignore
        }

//wait for the transaction to complete
        Transaction transaction = client.waitForTransaction(st, 100000);
        System.out.println(transaction);



}
}


