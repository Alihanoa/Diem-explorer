package com.diemexplorer.explorer;

import com.diem.DiemException;
import com.diem.jsonrpc.DiemJsonRpcClient;
import com.diem.jsonrpc.JsonRpc;
import com.diem.types.ChainId;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class BlockchainThread extends Thread{
    private  DiemJsonRpcClient client;
    private  Connection con;
    private int version = 127;


    public BlockchainThread () throws SQLException {
        client =  new DiemJsonRpcClient("http://testnet.diem.com/v1", new ChainId((byte) 2));
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/diemexplorer?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "password");

    }

    @Override
    public void run(){
        while (!interrupted()) {

            try {
                this.getTransactions();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (DiemException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }


    public void cancel() {
        interrupt();
    }

    public  void getTransactions()throws SQLException, DiemException, InterruptedException{
      //  con = DriverManager.getConnection("jdbc:mysql://localhost:3306/diemexplorer?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "password");
        List<JsonRpc.Transaction> transactions;
        PreparedStatement statement;

            transactions = client.getTransactions(version, 1, false);

            for (JsonRpc.Transaction transaction : transactions ) {
                if(!transaction.getTransaction().getSender().equals("")||transaction.getTransaction().getType().equals("user")) {
                    String receiver = transaction.getTransaction().getScript().getReceiver();
                    getAccountAndSaveInDB(transaction, receiver);
                    
                }
                if (!versionInDB(transaction.getVersion())) {
                    String date  = getDateFromTimeStamp(transaction);

                    String query = "INSERT INTO  transactions (version, amount, currency, gas_used, gas_currency, public_key, sender_id, receiver_id, date, type) "
                            + "VALUES (" + transaction.getVersion() + ","  + transaction.getTransaction().getScript().getAmount() + ","
                            + "'" + transaction.getTransaction().getScript().getCurrency() + "'" + "," + transaction.getGasUsed() + ","
                            + "'" + transaction.getTransaction().getGasCurrency() + "'" + ", '" + transaction.getTransaction().getPublicKey() + "','"
                            + transaction.getTransaction().getSender() + "','" + transaction.getTransaction().getScript().getReceiver() + "','" + date + "', '" + transaction.getTransaction().getType() + "')";

                    statement = con.prepareStatement(query);
                    statement.executeUpdate();

                    getTransactiondetails(transaction);



                }

                version = version + 1;
            }


    }




    public  boolean versionInDB(long version) throws SQLException{

        /* checks whether the version number is in the database or not */
         //con = DriverManager.getConnection("jdbc:mysql://localhost:3306/diemexplorer?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "password");

        String query = "SELECT version FROM transactiondetails WHERE version=" + version;
        PreparedStatement statement = con.prepareStatement(query);

        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();

    }

    public  void getAccountAndSaveInDB(JsonRpc.Transaction transaction, String receiver) throws SQLException, DiemException, InterruptedException {

        /*For each transaction, this method checks whether the account executing the transacion is within our database. If the account is already
         *in database its sequence number gets updated. If the account is not in the database its current values are being inserted in the database */
        String senderaddress = transaction.getTransaction().getSender();
        
        PreparedStatement statement;
//        receiver = transaction.getTransaction().getScript().getReceiver();

        JsonRpc.Account receiveraccount;
        if(receiver.equals("" )){
            receiveraccount = client.getAccount("000000000000000000000000000000dd");
        }
        else{
            receiveraccount = client.getAccount(receiver);
        }
        if(receiveraccount==null){
          System.out.println("Null Acc receiver");
        }
        JsonRpc.Account senderaccount = client.getAccount(senderaddress);
        
         //con = DriverManager.getConnection("jdbc:mysql://localhost:3306/diemexplorer?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "password");
        if(accountInDB(senderaddress)){
            String increaseSequenceNumber = "UPDATE accounts SET sequence_number=" + senderaccount.getSequenceNumber()+ " WHERE address='" + senderaccount.getAddress() + "'";
            statement = con.prepareStatement(increaseSequenceNumber);
            statement.executeUpdate();
        }
        else {
            String insertstmnt = "INSERT INTO accounts (address, authentication_key,  is_frozen, sequence_number) "
                    +" VALUES ('" + senderaccount.getAddress() + "'," + "'" + senderaccount.getAuthenticationKey() + "',"+ senderaccount.getIsFrozen() + "," + senderaccount.getSequenceNumber() +")";
            statement = con.prepareStatement(insertstmnt);
            statement.executeUpdate();
        }
        
        if (receiveraccount != null ){
                if(accountInDB(receiver)){
            String increaseSequenceNumber = "UPDATE accounts SET sequence_number=" + receiveraccount.getSequenceNumber()+ " WHERE address='" + receiveraccount.getAddress() + "'";
            statement = con.prepareStatement(increaseSequenceNumber);
            statement.executeUpdate();
        }
                else if (receiver!="") {
            String insertstmnt = "INSERT INTO accounts (address, authentication_key,  is_frozen, sequence_number) "
                    +" VALUES ('" + receiveraccount.getAddress() + "'," + "'" + receiveraccount.getAuthenticationKey() + "',"+ receiveraccount.getIsFrozen() + "," + receiveraccount.getSequenceNumber() +")";
            statement = con.prepareStatement(insertstmnt);
            statement.executeUpdate();
        }
                 setAccountinformation(receiveraccount);
        }
        
        setAccountinformation(senderaccount);
       
        setAccountBalances(transaction, receiver);

    }
    public  boolean accountInDB(String address)throws SQLException{

        /* Checks whether an account is in the database or not */

       // con = DriverManager.getConnection("jdbc:mysql://localhost:3306/diemexplorer?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "password");

        String query = "SELECT address FROM accounts WHERE address='" + address + "'";
        PreparedStatement statement = con.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        return  resultSet.next();
    }

    public  void getTransactiondetails(JsonRpc.Transaction transaction) throws SQLException{

       // con = DriverManager.getConnection("jdbc:mysql://localhost:3306/diemexplorer?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "password");

        String insertStatement = "INSERT INTO transactiondetails (version, chain_id, hash, metadata, metadata_signature, script_hash, abort_code, category, category_description, reason, reason_description, location, type, gas_unit_price, expiration_date)" +
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
                + transaction.getTransaction().getGasUnitPrice() +  ",'"
                + getDateFromExpirationTimestamp(transaction) + "')";

        PreparedStatement statement = con.prepareStatement(insertStatement);
        statement.executeUpdate();
    }



    public  String getDateFromTimeStamp(JsonRpc.Transaction transaction) throws SQLException{

        /*Timestamp is stored on the blockchain as microseconds. In order to safe it as a date, it is necessary to cut off the last 3 digits and format
         * it in european date wit a SimpleDateFormat Object. Format: DD/MM/YYYY HH:MM:SS
         */
       // con = DriverManager.getConnection("jdbc:mysql://localhost:3306/diemexplorer?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "password");

        PreparedStatement preparedStatement;
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd/MM/yyyy' 'HH:mm:ss");

        if ((transaction.getTransaction().getTimestampUsecs() == 0) && transaction.getVersion()>1) {

            String query = "SELECT date FROM transactions WHERE version<" + transaction.getVersion() + " AND type='blockmetadata' ORDER BY version DESC LIMIT 1";
            preparedStatement = con.prepareStatement(query);
            ResultSet resultset = preparedStatement.executeQuery();
            resultset.next();
            String date = null;





            return date;
        }

        String timestamp = String.valueOf(transaction.getTransaction().getTimestampUsecs());

        long time = Long.parseLong(timestamp);

        Date date = new Date(time/1000);

        String dateString = simpleDateFormat.format(date);



        return dateString;

    }

    public  String getDateFromExpirationTimestamp(JsonRpc.Transaction transaction) throws SQLException{
        /*Expirartiontimestamp is stored on the blockchain in seconds. In order to safe it as a date, it is necessary to add 3 digits and format
         * it in european date wit a SimpleDateFormat Object. Format: DD/MM/YYYY HH:MM:SS
         */

        // con = DriverManager.getConnection("jdbc:mysql://localhost:3306/diemexplorer?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "password");

        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd/MM/yyyy' 'HH:mm:ss");

        String exptimestamp = String.valueOf(transaction.getTransaction().getExpirationTimestampSecs()) + "000";
        PreparedStatement statement;
        if(transaction.getTransaction().getType().equals("blockmetadata")){

            String timestamp = String.valueOf(transaction.getTransaction().getTimestampUsecs());

            long date = Long.parseLong(timestamp)/1000;

            Date datum = new Date(date);

            return simpleDateFormat.format(datum);
        }

        long time = Long.parseLong(exptimestamp);

        Date date = new Date(time);

        return simpleDateFormat.format(date);
    }

    public   void setAccountBalances(JsonRpc.Transaction transaction, String receiver) throws SQLException, DiemException, InterruptedException {
        // final String receiveraddress;


        //Thread.sleep(10000);
        receiver = transaction.getTransaction().getScript().getReceiver();
        System.out.println(receiver);
        JsonRpc.Account receiveraccount;
        if(receiver.equals("" )){
            receiveraccount = client.getAccount("000000000000000000000000000000dd");
        }
        else{
            receiveraccount = client.getAccount(receiver);
        }
        if(receiveraccount==null){
          System.out.println("Null Acc receiver");
        }
        JsonRpc.Account sender = client.getAccount(transaction.getTransaction().getSender());
        String addressSender = sender.getAddress();

       if(sender==null){
          System.out.println("Null Acc receiver");
        }



      //  con = DriverManager.getConnection("jdbc:mysql://localhost:3306/diemexplorer?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "password");

        // String address = receiver.getAddress();
//         List<JsonRpc.Amount> amounts = client.getAccount(receiver).getBalancesList();


// Accountbalances needs to get updated / inserted per transaction from the receiver and sender. This section is shows the receiver

       //First we have to figure out which currency is used in the transaction
       //if the currency is XUS
        if(receiveraccount!=null && sender!=null){
	if(transaction.getTransaction().getScript().getCurrency().equals("XUS")){
                                        // Accountbalances needs to get updated / inserted per transaction from the receiver and sender. This section is shows the receiver
                                        if(!amountInXUSDB(receiveraccount)){

						String insertStatement = "INSERT INTO accountbalancexus (address, amount) VALUES ('" + receiver + "'," + receiveraccount.getBalances(0).getAmount() + ")";
						PreparedStatement statement = con.prepareStatement(insertStatement);
						statement.executeUpdate();

					}

					else if(amountInXUSDB(receiveraccount)){
						String updateStatement ="UPDATE accountbalancexus SET amount=" +  receiveraccount.getBalances(0).getAmount() + " WHERE address='"  + receiver + "'";
						PreparedStatement statement = con.prepareStatement(updateStatement);
						statement.executeUpdate();
					}
                                         //This Section inserts / updates the sender in the database
                                        if(!amountInXUSDB(sender) ){
				String insert = "INSERT INTO accountbalancexus ( address, amount) VALUES ('"+addressSender+ "'," + sender.getBalances(0).getAmount() + ")";
				PreparedStatement statement = con.prepareStatement(insert);
				statement.executeUpdate();
			}
                                        
			else if(amountInXUSDB(sender)){
				String update = "UPDATE accountbalancexus SET amount=" + sender.getBalances(0).getAmount() + " WHERE address='" + addressSender + "'";
				PreparedStatement statement = con.prepareStatement(update);
				statement.executeUpdate();
                                        }
    }
        //if the currency ist XDX                
        else if(transaction.getTransaction().getScript().getCurrency().equals("XDX")){
                                // Accountbalances needs to get updated / inserted per transaction from the receiver and sender. This section is shows the receiver
				if(!amountInXDXDB(receiveraccount)){

					String updateStatement = "INSERT INTO accountbalancexdx (address, amount) VALUES ('" + receiver + "'," + receiveraccount.getBalances(1).getAmount() + ")";
					PreparedStatement statement = con.prepareStatement(updateStatement);
					statement.executeUpdate();
				}

				else if(amountInXDXDB(receiveraccount)){
					String updateStatement = "UPDATE accountbalancexdx SET amount=" +  receiveraccount.getBalances(1).getAmount() + " WHERE address='"  + receiver + "'";
					PreparedStatement statement = con.prepareStatement(updateStatement);
					statement.executeUpdate();
				}
                                 //This Section inserts / updates the sender in the database
                                if(!amountInXDXDB(sender)){
				String insert = "INSERT INTO accountbalancexdx ( address, amount) VALUES ('" + addressSender + "'," + sender.getBalances(1).getAmount() + ")";
				PreparedStatement statement = con.prepareStatement(insert);
				statement.executeUpdate();
			}
			else if(amountInXDXDB(sender)){
				String update = "UPDATE accountbalancexdx SET amount="+ sender.getBalances(1).getAmount() + " WHERE address='"+addressSender + "'";
				PreparedStatement statement = con.prepareStatement(update);
				statement.executeUpdate();
			}}
        }   
        
        
        //This Section inserts / updates the sender in the database
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







    public  boolean amountInXUSDB(JsonRpc.Account account) throws SQLException{
       // con = DriverManager.getConnection("jdbc:mysql://localhost:3306/diemexplorer?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "password");
        String sender_id = account.getAddress();

        String query = "SELECT * FROM accountbalancexus where address= '" + sender_id + "'";

        PreparedStatement statement = con.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }

    public  boolean amountInXDXDB(JsonRpc.Account account) throws SQLException{
      //  con = DriverManager.getConnection("jdbc:mysql://localhost:3306/diemexplorer?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "password");

        String sender_id= account.getAddress();

        String query ="SELECT * FROM accountbalancexdx WHERE address = '" +  sender_id + "'";

        PreparedStatement statement = con.prepareStatement(query);

        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }

    public  void setAccountinformation(JsonRpc.Account account) throws SQLException, DiemException {
      //  con = DriverManager.getConnection("jdbc:mysql://localhost:3306/diemexplorer?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "password");
        String expirationtime = String.valueOf(account.getRole().getExpirationTime());

        PreparedStatement statement;

        if (!accountInformationInDB(account)) {
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
                    "											 preburn_balancexus," +
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
                    + account.getRole().getBaseUrlRotationEventsKey() + "',"
                    + (account.getRole().getType().equals("designated_dealer") ? account.getRole().getPreburnBalances(0).getAmount() : null) + ",'"
                    + account.getRole().getReceivedMintEventsKey() + "')";

            statement = con.prepareStatement(insert);
            statement.executeUpdate();
        } else {
            String update = "UPDATE  accountdetails SET rtype = '" + account.getRole().getType() + "', expiration_time= '" + expirationtime + "', preburn_balancexus = " + (account.getRole().getType().equals("designated_dealer") ? account.getRole().getPreburnBalances(0).getAmount() : null) + " WHERE address='" + account.getAddress() + "'";
            statement = con.prepareStatement(update);
            statement.executeUpdate();

        }
    }

    public  boolean accountInformationInDB(JsonRpc.Account account) throws SQLException{
        // con = DriverManager.getConnection("jdbc:mysql://localhost:3306/diemexplorer?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "password");
        String query = "SELECT * FROM accountdetails WHERE address= '"  + account.getAddress() + "'";

        PreparedStatement statement = con.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        return resultSet.next();
    }
}
