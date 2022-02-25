package com.diemexplorer.explorer;

import com.diem.DiemException;
import com.diem.jsonrpc.DiemJsonRpcClient;
import com.diem.jsonrpc.JsonRpc;
import com.diem.types.ChainId;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlockchainThread extends Thread {
    private DiemJsonRpcClient client;
    private Connection con;
    private int version = -1;
    final static Logger logger = LoggerFactory.getLogger(BlockchainThread.class);

    public BlockchainThread() throws SQLException {
        client = new DiemJsonRpcClient("http://testnet.diem.com/v1", new ChainId((byte) 3));
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/diemexplorer?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "password");

    }

    @Override
    public void run() {
        while (!interrupted()) {

            try {
                
                this.version = getLatestDBVersion() + 1;
                this.getTransactions();
                
            } catch (Exception e) {
                
                logger.error("Version: "+version+'\n',e.fillInStackTrace().toString()+'\n', e.getMessage() +'\n', e ,e.getCause().toString()+'\n'  );
                e.printStackTrace();
                Continue();
            }
//            catch (DiemException e) {
//                
//                e.printStackTrace();
//                
//            } catch (InterruptedException e) {
//                
//                e.printStackTrace();
//                
//            }


        }
    }
    
    public void Continue() {
     
        while(!interrupted()){
        try{
        this.version ++ ;
        this.getTransactions();
        }
        catch(Exception e){
            System.out.println(e);
            logger.error("Version: "+version+'\n',e.fillInStackTrace().toString()+'\n', e.getMessage() +'\n', e ,e.getCause().toString()+'\n'  );
            e.printStackTrace();
            Continue();
        }
        }
}

    public int getLatestDBVersion() throws SQLException {
        PreparedStatement statement;
        String query = "SELECT MAX(version) FROM transactions";
        statement = con.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        rs.next();
        int maxvalue = rs.getInt("MAX(version)");
        System.out.println(maxvalue);
        rs.close();
        statement.close();
        return maxvalue;
    
    }

    public void cancel() {
        interrupt();
    }

    public void getTransactions() throws SQLException, DiemException, InterruptedException {
        //  con = DriverManager.getConnection("jdbc:mysql://localhost:3306/diemexplorer?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "password");
        List<JsonRpc.Transaction> transactions;
        PreparedStatement statement;
        

        transactions = client.getTransactions(version, 100, false);

        for (JsonRpc.Transaction transaction : transactions) {
            if (!transaction.getTransaction().getSender().equals("") || transaction.getTransaction().getType().equals("user")) {
                String receiver = transaction.getTransaction().getScript().getReceiver();
                getAccountAndSaveInDB(transaction, receiver);

            }
            if (!versionInDB(transaction.getVersion())) {

                // For some reason when the transaction is an actual transaction / smart contract, the timestamp is set to 0.
                // Therefore we estimated that this transaction has to happen a couple miliseconds after the lalst blockmetadata. therefore we need to take its timestamp.
                long timestamp = transaction.getTransaction().getTimestampUsecs();
                if (transaction.getTransaction().getTimestampUsecs() == 0 && transaction.getVersion() > 0) {

                    PreparedStatement statementfortimestamp;
                    String timestampbefore = "SELECT timestamp FROM transactions WHERE version<" + transaction.getVersion() + " AND type='blockmetadata' ORDER BY version DESC LIMIT 1";
                    statementfortimestamp = con.prepareStatement(timestampbefore);
                    ResultSet resultset = statementfortimestamp.executeQuery();
                    resultset.next();
                    timestamp = resultset.getLong("timestamp");
                    resultset.close();
                }

                String date = getDateFromTimeStamp(transaction);
                if (transaction.getTransaction().getType().equals("blockmetadata")) {
                    timestamp = timestamp / 1000;

                }

                String query = "INSERT INTO  transactions (version, amount, currency, gas_used, gas_currency, public_key, sender_id, receiver_id, date, type, timestamp) "
                        + "VALUES (" + transaction.getVersion() + "," + transaction.getTransaction().getScript().getAmount() + ","
                        + "'" + transaction.getTransaction().getScript().getCurrency() + "'" + "," + transaction.getGasUsed() + ","
                        + "'" + transaction.getTransaction().getGasCurrency() + "'" + ", '" + transaction.getTransaction().getPublicKey() + "','"
                        + transaction.getTransaction().getSender() + "','" + transaction.getTransaction().getScript().getReceiver() + "','" + date + "', '" + transaction.getTransaction().getType() + "'," + timestamp + ")";

                statement = con.prepareStatement(query);
                statement.executeUpdate();

                getTransactiondetails(transaction);
                statement.close();


            }

            version = version + 1;


        }


    }


    public boolean versionInDB(long version) throws SQLException {

        /* checks whether the version number is in the database or not */
        //con = DriverManager.getConnection("jdbc:mysql://localhost:3306/diemexplorer?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "password");

        String query = "SELECT version FROM transactiondetails WHERE version=" + version;
        PreparedStatement statement = con.prepareStatement(query);

        ResultSet resultSet = statement.executeQuery();

        boolean result = resultSet.next();
        resultSet.close();
        statement.close();
        return result;

    }

    public void getAccountAndSaveInDB(JsonRpc.Transaction transaction, String receiver) throws SQLException, DiemException, InterruptedException {

        /*For each transaction, this method checks whether the account executing the transacion is within our database. If the account is already
         *in database its sequence number gets updated. If the account is not in the database its current values are being inserted in the database */
        String senderaddress = transaction.getTransaction().getSender();

        PreparedStatement statement;
//        receiver = transaction.getTransaction().getScript().getReceiver();

        JsonRpc.Account receiveraccount;
        if (receiver.equals("")) {
            receiveraccount = client.getAccount("000000000000000000000000000000dd");
        } else {
            receiveraccount = client.getAccount(receiver);
        }
        if (receiveraccount == null) {
            System.out.println("Null Acc receiver");
        }
        JsonRpc.Account senderaccount = client.getAccount(senderaddress);

        //con = DriverManager.getConnection("jdbc:mysql://localhost:3306/diemexplorer?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "password");
        if (accountInDB(senderaddress)) {
            String increaseSequenceNumber = "UPDATE accounts SET sequence_number=" + senderaccount.getSequenceNumber() + " WHERE address='" + senderaccount.getAddress() + "'";
            statement = con.prepareStatement(increaseSequenceNumber);
            statement.executeUpdate();
        } else {
            String insertstmnt = "INSERT INTO accounts (address, authentication_key,  is_frozen, sequence_number) "
                    + " VALUES ('" + senderaccount.getAddress() + "'," + "'" + senderaccount.getAuthenticationKey() + "'," + senderaccount.getIsFrozen() + "," + senderaccount.getSequenceNumber() + ")";
            statement = con.prepareStatement(insertstmnt);
            statement.executeUpdate();
        }

        if (receiveraccount != null) {
            if (accountInDB(receiver)) {
                String increaseSequenceNumber = "UPDATE accounts SET sequence_number=" + receiveraccount.getSequenceNumber() + " WHERE address='" + receiveraccount.getAddress() + "'";
                statement = con.prepareStatement(increaseSequenceNumber);
                statement.executeUpdate();
            } else if (receiver != "") {
                String insertstmnt = "INSERT INTO accounts (address, authentication_key,  is_frozen, sequence_number) "
                        + " VALUES ('" + receiveraccount.getAddress() + "'," + "'" + receiveraccount.getAuthenticationKey() + "'," + receiveraccount.getIsFrozen() + "," + receiveraccount.getSequenceNumber() + ")";
                statement = con.prepareStatement(insertstmnt);
                statement.executeUpdate();
            }
            setAccountdetails(receiveraccount);
        }

        setAccountdetails(senderaccount);

        setAccountBalances(transaction, receiver);
        statement.close();


    }

    public boolean accountInDB(String address) throws SQLException {

        /* Checks whether an account is in the database or not */

        // con = DriverManager.getConnection("jdbc:mysql://localhost:3306/diemexplorer?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "password");

        String query = "SELECT address FROM accounts WHERE address='" + address + "'";
        PreparedStatement statement = con.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        boolean ergebnis= resultSet.next();

        resultSet.close();
        statement.close();
        return ergebnis;
    }

    public void getTransactiondetails(JsonRpc.Transaction transaction) throws SQLException {

        // con = DriverManager.getConnection("jdbc:mysql://localhost:3306/diemexplorer?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "password");

        String insertStatement = "INSERT INTO transactiondetails (version, chain_id, hash, metadata, metadata_signature, script_hash, abort_code, category, category_description, reason, reason_description, location, type, gas_unit_price, expiration_date, max_gas_amount)" +
                " VALUES ( " + transaction.getVersion() + ","
                + transaction.getTransaction().getChainId() + ",'"
                + transaction.getHash() + "','"
                + transaction.getTransaction().getScript().getMetadata() + "','"
                + transaction.getTransaction().getScript().getMetadataSignature() + "','"
                + transaction.getTransaction().getScriptHash() + "',"
                + transaction.getVmStatus().getAbortCode() + ",'"
                + transaction.getVmStatus().getExplanation().getCategory() + "','"
                + transaction.getVmStatus().getExplanation().getCategoryDescription() + "','"
                + transaction.getVmStatus().getExplanation().getReason() + "','" + transaction.getVmStatus().getExplanation().getReasonDescription() + "','"
                + transaction.getVmStatus().getLocation() + "','"
                + transaction.getVmStatus().getType() + "', "
                + transaction.getTransaction().getGasUnitPrice() + ",'"
                + getDateFromExpirationTimestamp(transaction) + "',"
                + transaction.getTransaction().getMaxGasAmount() + ")";

        PreparedStatement statement = con.prepareStatement(insertStatement);
        statement.executeUpdate();
        statement.close();
    }


    public String getDateFromTimeStamp(JsonRpc.Transaction transaction) throws SQLException {

        /*Timestamp is stored on the blockchain in microseconds. In order to safe it as a date, it is necessary to cut off the last 3 digits and format
         * it in european date wit a SimpleDateFormat Object. Format: DD/MM/YYYY HH:MM:SS
         */
        // con = DriverManager.getConnection("jdbc:mysql://localhost:3306/diemexplorer?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "password");

        PreparedStatement preparedStatement;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy' 'HH:mm:ss.SSS");

        if ((transaction.getTransaction().getTimestampUsecs() == 0) && transaction.getVersion() > 1) {

            String query = "SELECT date FROM transactions WHERE version<" + transaction.getVersion() + " AND type='blockmetadata' ORDER BY version DESC LIMIT 1";
            preparedStatement = con.prepareStatement(query);
            ResultSet resultset = preparedStatement.executeQuery();
            resultset.next();
            String date = resultset.getString("date");


            resultset.close();
            preparedStatement.close();
            return date;
        }

        String timestamp = String.valueOf(transaction.getTransaction().getTimestampUsecs());


        long time = Long.parseLong(timestamp);

        Date date = new Date(time / 1000);

        String dateString = simpleDateFormat.format(date);


        return dateString;

    }

    public String getDateFromExpirationTimestamp(JsonRpc.Transaction transaction) throws SQLException {
        /*Expirartiontimestamp is stored on the blockchain in seconds. In order to safe it as a date, it is necessary to add 3 digits and format
         * it in european date wit a SimpleDateFormat Object. Format: DD/MM/YYYY HH:MM:SS
         */

        // con = DriverManager.getConnection("jdbc:mysql://localhost:3306/diemexplorer?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "password");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy' 'HH:mm:ss");

        String exptimestamp = String.valueOf(transaction.getTransaction().getExpirationTimestampSecs()) + "000";
        if (transaction.getTransaction().getType().equals("blockmetadata")) {

            String timestamp = String.valueOf(transaction.getTransaction().getTimestampUsecs());

            long date = Long.parseLong(timestamp) / 1000;

            Date datum = new Date(date);


            return simpleDateFormat.format(datum);
        }

        long time = Long.parseLong(exptimestamp);

        Date date = new Date(time);

        return simpleDateFormat.format(date);
    }

    public void setAccountBalances(JsonRpc.Transaction transaction, String receiver) throws SQLException, DiemException, InterruptedException {


        receiver = transaction.getTransaction().getScript().getReceiver();

        JsonRpc.Account receiveraccount;

        if (receiver.equals("")) {
            receiveraccount = client.getAccount("000000000000000000000000000000dd");

        } else {
            receiveraccount = client.getAccount(receiver);
        }
        if (receiveraccount == null) {
            System.out.println("Null Acc receiver");
        }
        JsonRpc.Account sender = client.getAccount(transaction.getTransaction().getSender());
        String addressSender = sender.getAddress();



        if (sender == null) {
            System.out.println("Null Acc receiver");
        }


        //  con = DriverManager.getConnection("jdbc:mysql://localhost:3306/diemexplorer?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "password");

        // String address = receiver.getAddress();
//         List<JsonRpc.Amount> amounts = client.getAccount(receiver).getBalancesList();


// Accountbalances needs to get updated / inserted per transaction from the receiver and sender. This section is shows the receiver

        //First we have to figure out which currency is used in the transaction
        //if the currency is XUS

        

        if (receiveraccount != null && sender != null) {

            int[] balancelistplacing = getBalanceListOfTransaction(transaction, sender, receiveraccount); 
            if (receiver.equals("") || receiver.equals("000000000000000000000000000000dd") || addressSender.equals("") || addressSender.equals("000000000000000000000000000000dd")) {
                setAccountBalanceFaucet(transaction, sender, receiveraccount);
            } else {
                if (transaction.getTransaction().getScript().getCurrency().equals("XUS")) {


// Accountbalances needs to get updated / inserted per transaction from the receiver and sender. This section is shows the receiver                                      
                    if (!amountInXUSDB(receiveraccount)) {

                        String insertStatement = "INSERT INTO accountbalancexus (address, amount) VALUES ('" + receiver + "'," + receiveraccount.getBalances(balancelistplacing[1]).getAmount() + ")";
                        PreparedStatement statement = con.prepareStatement(insertStatement);
                        statement.executeUpdate();
                        statement.close();

                    } else if (amountInXUSDB(receiveraccount)) {
                        String updateStatement = "UPDATE accountbalancexus SET amount=" + receiveraccount.getBalances(balancelistplacing[1]).getAmount() + " WHERE address='" + receiver + "'";
                        PreparedStatement statement = con.prepareStatement(updateStatement);
                        statement.executeUpdate();
                        statement.close();
                    }
                    //This Section inserts / updates the sender in the database
                    if (!amountInXUSDB(sender)) {
                        String insert = "INSERT INTO accountbalancexus ( address, amount) VALUES ('" + addressSender + "'," + sender.getBalances(balancelistplacing[0]).getAmount() + ")";
                        PreparedStatement statement = con.prepareStatement(insert);
                        statement.executeUpdate();
                        statement.close();
                    } else if (amountInXUSDB(sender)) {
                        String update = "UPDATE accountbalancexus SET amount=" + sender.getBalances(balancelistplacing[0]).getAmount() + " WHERE address='" + addressSender + "'";
                        PreparedStatement statement = con.prepareStatement(update);
                        statement.executeUpdate();
                        statement.close();
                    }
                }
                //if the currency is XDX
                else if (transaction.getTransaction().getScript().getCurrency().equals("XDX")) {
                    // Accountbalances needs to get updated / inserted per transaction from the receiver and sender. This section is shows the receiver
                    if (!amountInXDXDB(receiveraccount)) {

                        String updateStatement = "INSERT INTO accountbalancexdx (address, amount) VALUES ('" + receiver + "'," + receiveraccount.getBalances(balancelistplacing[1]).getAmount() + ")";
                        PreparedStatement statement = con.prepareStatement(updateStatement);
                        statement.executeUpdate();
                        statement.close();
                    } else if (amountInXDXDB(receiveraccount)) {
                        String updateStatement = "UPDATE accountbalancexdx SET amount=" + receiveraccount.getBalances(balancelistplacing[1]).getAmount() + " WHERE address='" + receiver + "'";
                        PreparedStatement statement = con.prepareStatement(updateStatement);
                        statement.executeUpdate();
                        statement.close();
                    }
                    //This Section inserts / updates the sender in the database
                    if (!amountInXDXDB(sender)) {
                        String insert = "INSERT INTO accountbalancexdx ( address, amount) VALUES ('" + addressSender + "'," + sender.getBalances(balancelistplacing[0]).getAmount() + ")";
                        PreparedStatement statement = con.prepareStatement(insert);
                        statement.executeUpdate();
                        statement.close();
                    } else if (amountInXDXDB(sender)) {
                        String update = "UPDATE accountbalancexdx SET amount=" + sender.getBalances(balancelistplacing[0]).getAmount() + " WHERE address='" + addressSender + "'";
                        PreparedStatement statement = con.prepareStatement(update);
                        statement.executeUpdate();
                        statement.close();
                    }
                }
            }
        }


        //This Section inserts / updates  the sender in the database
//        String addressSender = sender.getAddress();


/*			if(!amountInXUSDB(sender) ){
				String insert = "INSERT INTO accountbalancexus ( address, amount) VALUES ('"+addressSender+ "'," + sender.getBalances(0).getAmount() + ")";
				PreparedStatement statement = con.prepareStatement(insert);
				statement.executeUpdate();
			}
			else if(amountInXUSDB(sender)){
				String update = "UPDATE accountbalancexus SET amount=" + sender.getBalances(1).getAmount() + " WHERE address='" + addressSender + "'";
				PreparedStatement statement = con.prepareStatement(update);
				statement.executeUpdate();
			}

			if(!amountInXDXDB(sender)){
				String insert = "INSERT INTO accountbalancexdx ( address, amount) VALUES ('" + addressSender + "'," + sender.getBalances(0).getAmount() + ")";
				PreparedStatement statement = con.prepareStatement(insert);
				statement.executeUpdate();
			}
			else if(amountInXDXDB(sender)){
				String update = "UPDATE accountbalancexdx SET amount="+ sender.getBalances(0).getAmount() + " WHERE address='"+addressSender + "'";
				PreparedStatement statement = con.prepareStatement(update);
				statement.executeUpdate();
			}*/


    }


    public void setAccountBalanceFaucet(JsonRpc.Transaction transaction, JsonRpc.Account sender, JsonRpc.Account receiveraccount) throws SQLException, DiemException, InterruptedException {
        String receiver = receiveraccount.getAddress();
        String addressSender = sender.getAddress();

        int[] balancelistplacing = getBalanceListOfTransaction(transaction, sender,receiveraccount);
        if (receiver.equals("") || receiver.equals("000000000000000000000000000000dd")) {
            if (transaction.getTransaction().getScript().getCurrency().equals("XUS")) {


// Accountbalances needs to get updated / inserted per transaction from the receiver and sender. This section is shows the receiver                                      
                if (!amountInXUSDB(receiveraccount)) {

                    String insertStatement = "INSERT INTO accountbalancexus (address, amount) VALUES ('" + receiver + "'," + receiveraccount.getBalances(balancelistplacing[1]).getAmount() + ")";
                    PreparedStatement statement = con.prepareStatement(insertStatement);
                    statement.executeUpdate();
                    statement.close();

                } else if (amountInXUSDB(receiveraccount)) {
                    String updateStatement = "UPDATE accountbalancexus SET amount=" + receiveraccount.getBalances(balancelistplacing[1]).getAmount() + " WHERE address='" + receiver + "'";
                    PreparedStatement statement = con.prepareStatement(updateStatement);
                    statement.executeUpdate();
                    statement.close();
                }
                //This Section inserts / updates the sender in the database
                if (!amountInXUSDB(sender)) {
                    String insert = "INSERT INTO accountbalancexus ( address, amount) VALUES ('" + addressSender + "'," + sender.getBalances(balancelistplacing[0]).getAmount() + ")";
                    PreparedStatement statement = con.prepareStatement(insert);
                    statement.executeUpdate();
                    statement.close();
                } else if (amountInXUSDB(sender)) {
                    String update = "UPDATE accountbalancexus SET amount=" + sender.getBalances(balancelistplacing[0]).getAmount() + " WHERE address='" + addressSender + "'";
                    PreparedStatement statement = con.prepareStatement(update);
                    statement.executeUpdate();
                    statement.close();
                }
            }
            //if the currency ist XDX
            else if (transaction.getTransaction().getScript().getCurrency().equals("XDX")) {
                // Accountbalances needs to get updated / inserted per transaction from the receiver and sender. This section is shows the receiver
                if (!amountInXDXDB(receiveraccount)) {

                    String updateStatement = "INSERT INTO accountbalancexdx (address, amount) VALUES ('" + receiver + "'," + receiveraccount.getBalances(balancelistplacing[1]).getAmount() + ")";
                    PreparedStatement statement = con.prepareStatement(updateStatement);
                    statement.executeUpdate();
                    statement.close();
                } else if (amountInXDXDB(receiveraccount)) {
                    String updateStatement = "UPDATE accountbalancexdx SET amount=" + receiveraccount.getBalances(balancelistplacing[1]).getAmount() + " WHERE address='" + receiver + "'";
                    PreparedStatement statement = con.prepareStatement(updateStatement);
                    statement.executeUpdate();
                    statement.close();
                }
                //This Section inserts / updates the sender in the database
                if (!amountInXDXDB(sender)) {
                    String insert = "INSERT INTO accountbalancexdx ( address, amount) VALUES ('" + addressSender + "'," + sender.getBalances(balancelistplacing[0]).getAmount() + ")";
                    PreparedStatement statement = con.prepareStatement(insert);
                    statement.executeUpdate();
                    statement.close();
                } else if (amountInXDXDB(sender)) {
                    String update = "UPDATE accountbalancexdx SET amount=" + sender.getBalances(balancelistplacing[0]).getAmount() + " WHERE address='" + addressSender + "'";
                    PreparedStatement statement = con.prepareStatement(update);
                    statement.executeUpdate();
                    statement.close();
                }
            }
        } else if (addressSender.equals("") || addressSender.equals("000000000000000000000000000000dd")) {


            if (transaction.getTransaction().getScript().getCurrency().equals("XUS")) {


// Accountbalances needs to get updated / inserted per transaction from the receiver and sender. This section is shows the receiver                                      
                if (!amountInXUSDB(receiveraccount)) {

                    String insertStatement = "INSERT INTO accountbalancexus (address, amount) VALUES ('" + receiver + "'," + receiveraccount.getBalances(0).getAmount() + ")";
                    PreparedStatement statement = con.prepareStatement(insertStatement);
                    statement.executeUpdate();
                    statement.close();

                } else if (amountInXUSDB(receiveraccount)) {
                    String updateStatement = "UPDATE accountbalancexus SET amount=" + receiveraccount.getBalances(0).getAmount() + " WHERE address='" + receiver + "'";
                    PreparedStatement statement = con.prepareStatement(updateStatement);
                    statement.executeUpdate();
                    statement.close();
                }
                //This Section inserts / updates the sender in the database
                if (!amountInXUSDB(sender)) {
                    String insert = "INSERT INTO accountbalancexus ( address, amount) VALUES ('" + addressSender + "'," + sender.getBalances(1).getAmount() + ")";
                    PreparedStatement statement = con.prepareStatement(insert);
                    statement.executeUpdate();
                    statement.close();
                } else if (amountInXUSDB(sender)) {
                    String update = "UPDATE accountbalancexus SET amount=" + sender.getBalances(1).getAmount() + " WHERE address='" + addressSender + "'";
                    PreparedStatement statement = con.prepareStatement(update);
                    statement.executeUpdate();
                    statement.close();
                }
            }
            //if the currency ist XDX
            else if (transaction.getTransaction().getScript().getCurrency().equals("XDX")) {
                // Accountbalances needs to get updated / inserted per transaction from the receiver and sender. This section is shows the receiver
                if (!amountInXDXDB(receiveraccount)) {

                    String updateStatement = "INSERT INTO accountbalancexdx (address, amount) VALUES ('" + receiver + "'," + receiveraccount.getBalances(0).getAmount() + ")";
                    PreparedStatement statement = con.prepareStatement(updateStatement);
                    statement.executeUpdate();
                    statement.close();
                } else if (amountInXDXDB(receiveraccount)) {
                    String updateStatement = "UPDATE accountbalancexdx SET amount=" + receiveraccount.getBalances(0).getAmount() + " WHERE address='" + receiver + "'";
                    PreparedStatement statement = con.prepareStatement(updateStatement);
                    statement.executeUpdate();
                    statement.close();
                }
                //This Section inserts / updates the sender in the database
                if (!amountInXDXDB(sender)) {
                    String insert = "INSERT INTO accountbalancexdx ( address, amount) VALUES ('" + addressSender + "'," + sender.getBalances(0).getAmount() + ")";
                    PreparedStatement statement = con.prepareStatement(insert);
                    statement.executeUpdate();
                    statement.close();
                } else if (amountInXDXDB(sender)) {
                    String update = "UPDATE accountbalancexdx SET amount=" + sender.getBalances(0).getAmount() + " WHERE address='" + addressSender + "'";
                    PreparedStatement statement = con.prepareStatement(update);
                    statement.executeUpdate();
                    statement.close();
                }
            }


        }

    }


    public boolean amountInXUSDB(JsonRpc.Account account) throws SQLException {
        // con = DriverManager.getConnection("jdbc:mysql://localhost:3306/diemexplorer?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "password");
        String sender_id = account.getAddress();

        String query = "SELECT * FROM accountbalancexus where address= '" + sender_id + "'";

        PreparedStatement statement = con.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        boolean result = resultSet.next();
        resultSet.close();
        statement.close();
        return result;
    }

    public boolean amountInXDXDB(JsonRpc.Account account) throws SQLException {
        //  con = DriverManager.getConnection("jdbc:mysql://localhost:3306/diemexplorer?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "password");

        String sender_id = account.getAddress();

        String query = "SELECT * FROM accountbalancexdx WHERE address = '" + sender_id + "'";

        PreparedStatement statement = con.prepareStatement(query);

        ResultSet resultSet = statement.executeQuery();

        boolean result =  resultSet.next();
        resultSet.close();
        statement.close();
        return result;
    }

    public void setAccountdetails(JsonRpc.Account account) throws SQLException, DiemException {
        //  con = DriverManager.getConnection("jdbc:mysql://localhost:3306/diemexplorer?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "password");
        String expirationtime = String.valueOf(account.getRole().getExpirationTime());

        PreparedStatement statement;

        if (!accountdetailsInDB(account)) {
            String insert = "INSERT INTO accountdetails 	(address," +
                    "											 sent_events_key," +
                    "											 receive_events_key," +
                    "											 rtype," +
                    "											 parent_vasp_name," +
                    "											 base_url," +
                    "											 expiration_time," +
                    "											 compliance_key," +
                    "											 compliance_key_rotation_events_key," +
                    "											 base_url_rotation_events_key," +
                    "											 received_mint_events_key) VALUES (" +
                    "'" + account.getAddress() + "','"
                    + account.getSentEventsKey() + "','"
                    + account.getReceivedEventsKey() + "','"
                    + account.getRole().getType() + "','"
                    + account.getRole().getParentVaspAddress() + "','"
                    + account.getRole().getBaseUrl() + "','"
                    + expirationtime + "','"
                    + account.getRole().getComplianceKey() + "','"
                    + account.getRole().getComplianceKeyRotationEventsKey() + "','"
                    + account.getRole().getBaseUrlRotationEventsKey() + "','"
                    + account.getRole().getReceivedMintEventsKey() + "')";

            statement = con.prepareStatement(insert);
            statement.executeUpdate();
            statement.close();
        } else {
            String update = "UPDATE  accountdetails SET rtype = '" + account.getRole().getType() + "', expiration_time= '" + expirationtime + "' WHERE address='" + account.getAddress() + "'";
            statement = con.prepareStatement(update);
            statement.executeUpdate();
            statement.close();

        }
    }

    public boolean accountdetailsInDB(JsonRpc.Account account) throws SQLException {
        // con = DriverManager.getConnection("jdbc:mysql://localhost:3306/diemexplorer?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "password");
        String query = "SELECT * FROM accountdetails WHERE address= '" + account.getAddress() + "'";

        PreparedStatement statement = con.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        boolean ergebnis =  resultSet.next();
        resultSet.close();
        statement.close();
        return ergebnis;
    }

    public int[] getBalanceListOfTransaction(JsonRpc.Transaction transaction, JsonRpc.Account sender, JsonRpc.Account receiver) throws DiemException {

        List<JsonRpc.Amount> senderamount = sender.getBalancesList();
        List<JsonRpc.Amount> receiveramount = receiver.getBalancesList();

        int senderreturnvalue =0;
        int receiverreturnvalue  =0;
        String transactioncurrency = transaction.getTransaction().getScript().getCurrency();

        if (transactioncurrency.equals("XDX")) {
            if (senderamount.get(0).getCurrency().equals("XDX")) {
                senderreturnvalue = 0;
            } else {
                senderreturnvalue = 1;
            }


            if (receiveramount.get(0).getCurrency().equals("XDX")) {
                receiverreturnvalue = 0;
            } else {
                receiverreturnvalue = 1;
            }
        }
        else if (transactioncurrency.equals("XUS")) {
            if (senderamount.get(0).getCurrency().equals("XUS")) {
                senderreturnvalue = 0;
            } else {
                senderreturnvalue = 1;
            }


            if (receiveramount.get(0).getCurrency().equals("XUS")) {
                receiverreturnvalue = 0;
            } else {
                receiverreturnvalue = 1;
            }
        }

        int[] result = new int[]{senderreturnvalue, receiverreturnvalue};

        return result;
    }
}
