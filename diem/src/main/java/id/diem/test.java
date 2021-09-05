package id.diem;

import com.diem.*;
import static com.diem.Testnet.CHAIN_ID;
import com.diem.jsonrpc.JsonRpc.Account;
import com.diem.jsonrpc.JsonRpc.Transaction;
import com.diem.jsonrpc.StaleResponseException;
import com.diem.stdlib.Helpers;
import com.diem.types.RawTransaction;
import com.diem.types.SignedTransaction;
import com.diem.types.TransactionPayload;
import com.diem.utils.CurrencyCode;
import com.novi.serde.Bytes;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters;

/**
 *
 * @author Msi
 *
 */
public class test {

    public static void main(String[] args) throws DiemException, InterruptedException, SQLException {

        MyTestnet testnet = new MyTestnet();
//        DAO dao = new DAO();
//connect to testnet
        DiemClient client = testnet.createClient();

        int i = 0;
        while (i<20) {

//generate private key for sender account
            PrivateKey senderPrivateKey = new Ed25519PrivateKey(new Ed25519PrivateKeyParameters(new SecureRandom()));

//generate auth key for sender account
            AuthKey senderAuthKey = AuthKey.ed24419(senderPrivateKey.publicKey());

//create sender account with 100 XUS balance
            Random random = new Random();
            int randomValue = random.nextInt((100000000 - 90000000 + 1) + 90000000);
            MyTestnet.mintCoins(client, 100000000L, senderAuthKey.hex(), "XUS");

//get sender account for sequence number
            Account account = client.getAccount(senderAuthKey.accountAddress());

//generate private key for receiver account
            PrivateKey receiverPrivateKey = new Ed25519PrivateKey(new Ed25519PrivateKeyParameters(new SecureRandom()));

//generate auth key for receiver account
            AuthKey receiverAuthKey = AuthKey.ed24419(receiverPrivateKey.publicKey());

//create receiver account with 1 XUS balance. Account gets created after coins minted on it
            Random randomrec = new Random();
            int randomValuerec = random.nextInt((100000000 - 90000000 + 1) + 90000000);
            MyTestnet.mintCoins(client, 100000000L, receiverAuthKey.hex(), "XUS");
      
//create script
            Random randomtransaktion = new Random();
            long transaktionswert = random.nextInt((100 - 90 + 1) + 90);
            TransactionPayload script = new TransactionPayload.Script(
                    Helpers.encode_peer_to_peer_with_metadata_script(
                            CurrencyCode.typeTag("XUS"),
                            receiverAuthKey.accountAddress(),
                            transaktionswert,
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

// Connection to database and insert generated data
//            Transaction transaction = client.waitForTransaction(st, 100000);
//            System.out.println(transaction);
//
//            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/diemexplorer?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "password");
//
//            String insertbefehl = " INSERT INTO account (accountid, authenticatiom_key, adress, is_frozen, sequence_number) VALUES"
//                    + " (" + i
//                    + ", " + "'" + account.getAuthenticationKey() + "'"
//                    + ", " + "'" + account.getAddress() + "'"
//                    + ", " + account.getIsFrozen()
//                    + ", " + account.getSequenceNumber() + ")";
//
//            PreparedStatement statement = con.prepareStatement(insertbefehl);
//            statement.executeUpdate();
//            
//            
//            
//            Thread.sleep(10000L);
            i++;
        }
    }
}
