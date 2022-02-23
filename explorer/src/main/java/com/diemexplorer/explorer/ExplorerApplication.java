package com.diemexplorer.explorer;

import com.diem.DiemException;
import com.diem.jsonrpc.DiemJsonRpcClient;
import com.diem.jsonrpc.JsonRpc;
import com.diem.jsonrpc.JsonRpc.Account;
import com.diem.types.ChainId;
import com.diemexplorer.explorer.Controller.TransactionController;
import com.diemexplorer.explorer.Repositories.TransactiondetailsRepository;
import com.diemexplorer.explorer.Repositories.TransactionsRepository;
import net.bytebuddy.dynamic.scaffold.MethodRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ExplorerApplication {
//
	 private static DiemJsonRpcClient client = new DiemJsonRpcClient("http://testnet.diem.com/v1", new ChainId((byte) 2));
	 private static long vers = 127;
	// private static int limit = 1;
	// private static long sek;
	// private static List<JsonRpc.Transaction> transactions;
	// private static Connection con;
	// private static List<JsonRpc.Account> accounts;
        
        
        
	public static void main(String[] args) throws SQLException, DiemException, InterruptedException {

		SpringApplication.run(ExplorerApplication.class, args);

		BlockchainThread blockchainThread = new BlockchainThread();
		blockchainThread.start();
//		PerformanceOptimizer optimizer = new PerformanceOptimizer();
//		optimizer.start();



	}
}

	/*public static void getTransactions()throws SQLException, DiemException, InterruptedException{
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/diemexplorer?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "password");
		List<JsonRpc.Transaction> transactions;
		PreparedStatement statement;
		while(true) {
			transactions = client.getTransactions(vers, limit, false);

			for (JsonRpc.Transaction transaction : transactions) {
				if(!transaction.getTransaction().getSender().equals("")) {
					String receiver = transaction.getTransaction().getScript().getReceiver();
					getAccountAndSaveInDB(transaction, receiver);
				}
				if (!versionInDB(transaction.getVersion())) {
					String date  = getDateFromTimeStamp(transaction);

					String query = "INSERT INTO transactiondetails (version, amount, currency, gas_used, gas_currency, public_key, sender_id, receiver_id, date, type) "
							+ "VALUES (" + transaction.getVersion() + ","  + transaction.getTransaction().getScript().getAmount() + ","
							+ "'" + transaction.getTransaction().getScript().getCurrency() + "'" + "," + transaction.getGasUsed() + ","
							+ "'" + transaction.getTransaction().getGasCurrency() + "'" + ", '" + transaction.getTransaction().getPublicKey() + "','"
							+ transaction.getTransaction().getSender() + "','" + transaction.getTransaction().getScript().getReceiver() + "','" + date + "', '" + transaction.getTransaction().getType() + "')";
					System.out.println(transaction.getTransaction().getTimestampUsecs());
					statement = con.prepareStatement(query);
					statement.executeUpdate();

					getTransactionBlockchaindetails(transaction);
					System.out.println(transaction);
					System.out.println(transaction.getTransaction().getTimestampUsecs());

				}

				vers = vers + limit;
			}
		}

	}


	*//*public static void getTransactionsasec(int a) throws DiemException, SQLException {
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/diemexplorer?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "password");
		PreparedStatement statement;
		transactions = new ArrayList();
		while (true) {
			sek = c.millis() / 1000;
			if (sek % a == 0) {
				List<JsonRpc.Transaction> listOfTransactions = client.getTransactions(vers, limit, false);
				int i = listOfTransactions.size();
				if (i != 0) {
					vers = listOfTransactions.get(i - 1).getVersion();
					for (JsonRpc.Transaction transaction : listOfTransactions) {
						//getAccountAndSaveInDB(transaction.getTransaction().getSender());

						if(!versionInDB(transaction.getVersion())) {


							if (transaction.getTransaction().getScript().getType().equals("peer_to_peer_with_metadata"))
							{
								transactions.add(transaction);
								String query = "INSERT INTO transactiondetails (version, sender_id, public_key, amount, currency, gas_currency, gas_used, expiration_timestamp_seconds) "
										+ "VALUES (" + transaction.getVersion() + "," + "'" + transaction.getTransaction().getSender() + "'" + ","
										+ "'" + transaction.getTransaction().getPublicKey() + "'" + "," + transaction.getTransaction().getScript().getAmount() + ","
										+ "'" + transaction.getTransaction().getScript().getCurrency() + "'" + ", '" + transaction.getTransaction().getGasCurrency() + "',"
										+ transaction.getGasUsed() + "," + transaction.getTransaction().getExpirationTimestampSecs() + ")";
								transaction.getTransaction().getTimestampUsecs();
								statement = con.prepareStatement(query);
								statement.executeUpdate();


							}
						}
					}
				}
				listOfTransactions.clear();
				vers = vers + limit;
			}
		}

	}*//*

	public static boolean versionInDB(long version) throws SQLException{

		*//* checks whether the version number is in the database or not *//*
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/diemexplorer?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "password");

		String query = "SELECT version FROM transactiondetails WHERE version=" + version;
		PreparedStatement statement = con.prepareStatement(query);

		ResultSet resultSet = statement.executeQuery();
		return resultSet.next();

	}

	public static void getAccountAndSaveInDB(JsonRpc.Transaction transaction, String receiver) throws SQLException, DiemException, InterruptedException {

		*//*For each transaction, this method checks whether the account executing the transacion is within our database. If the account is already
		 *in database its sequence number gets updated. If the account is not in the database its current values are being inserted in the database *//*
		String address = transaction.getTransaction().getSender();
		PreparedStatement statement;

		JsonRpc.Account account = client.getAccount(address);

		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/diemexplorer?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "password");
		if(accountInDB(address)){
			String increaseSequenceNumber = "UPDATE account SET sequence_number=" + account.getSequenceNumber()+ " WHERE address='" + account.getAddress() + "'";
			statement = con.prepareStatement(increaseSequenceNumber);
			statement.executeUpdate();
		}
		else {
			String insertstmnt = "INSERT INTO account (address, authentication_key,  is_frozen, sequence_number) "
					+" VALUES ('" + account.getAddress() + "'," + "'" + account.getAuthenticationKey() + "',"+ account.getIsFrozen() + "," + account.getSequenceNumber() +")";
			statement = con.prepareStatement(insertstmnt);
			statement.executeUpdate();
		}
		//setAccountinformation(account);
		setAccountBalances(transaction, receiver);

	}
	public static boolean accountInDB(String address)throws SQLException{

		*//* Checks whether an account is in the database or not *//*

		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/diemexplorer?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "password");

		String query = "SELECT address FROM account WHERE address='" + address + "'";
		PreparedStatement statement = con.prepareStatement(query);
		ResultSet resultSet = statement.executeQuery();
		return  resultSet.next();
	}

	public static void getTransactionBlockchaindetails(JsonRpc.Transaction transaction) throws SQLException{

		 con = DriverManager.getConnection("jdbc:mysql://localhost:3306/diemexplorer?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "password");

		String insertStatement = "INSERT INTO transactionblockchaindetails (version, chain_id, hash, metadata, metadata_signature, script_hash, abort_code, category, category_description, reason, reason_description, location, type)" +
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
							 + transaction.getVmStatus().getType() + "')";

		PreparedStatement statement = con.prepareStatement(insertStatement);
		statement.executeUpdate();
	}



	public static String getDateFromTimeStamp(JsonRpc.Transaction transaction) throws SQLException{

		*//*Timestamp is stored on the blockchain as microseconds. In order to safe it as a date, it is necessary to cut off the last 3 digits and format
		 * it in european date wit a SimpleDateFormat Object. Format: DD/MM/YYYY HH:MM:SS
		*//*
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/diemexplorer?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "password");

		PreparedStatement preparedStatement;
		SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd/MM/yyyy' 'HH:mm:ss");

		if ((transaction.getTransaction().getTimestampUsecs() == 0) && transaction.getVersion()>1) {

		String query = "SELECT date FROM transactiondetails WHERE version<" + transaction.getVersion() + " AND type='blockmetadata' ORDER BY version DESC LIMIT 1";
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

	public static String getDateFromExpirationTimestamp(JsonRpc.Transaction transaction) throws SQLException{
		*//*Expirartiontimestamp is stored on the blockchain in seconds. In order to safe it as a date, it is necessary to add 3 digits and format
		 * it in european date wit a SimpleDateFormat Object. Format: DD/MM/YYYY HH:MM:SS
		 *//*

		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/diemexplorer?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "password");

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

	public static  void setAccountBalances(JsonRpc.Transaction transaction, String receiver) throws SQLException, DiemException, InterruptedException {
		// final String receiveraddress;


		//Thread.sleep(10000);

		JsonRpc.Account receiveraccount;
			if(receiver.equals("")){
				receiveraccount = client.getAccount("000000000000000000000000000000dd");
		}
			else{
				receiveraccount = client.getAccount(receiver);
			}

		JsonRpc.Account sender = client.getAccount(transaction.getTransaction().getSender());






		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/diemexplorer?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "password");

		// String address = receiver.getAddress();
		// List<JsonRpc.Amount> amounts = receiver.getBalancesList();




				// Accountbalances needs to get updated / inserted per transaction from the receiver and sender. This section is shows the receiver
				*//*if(!amountInXUSDB(receiver)){

						String insertStatement = "INSERT INTO accountbalancexus (address, amount) VALUES ('" + address + "'," + receiver.getBalances(1).getAmount() + ")";
						PreparedStatement statement = con.prepareStatement(insertStatement);
						statement.executeUpdate();

					}

					else if(amountInXUSDB(receiver)){
						String updateStatement ="UPDATE accountbalancexus SET amount=" +  receiver.getBalances(1).getAmount() + " WHERE address='"  + address + "'";
						PreparedStatement statement = con.prepareStatement(updateStatement);
						statement.executeUpdate();
					}


				if(!amountInXDXDB(receiver)){

					String updateStatement = "INSERT INTO accountbalancexdx (address, amount) VALUES ('" + address + "'," + receiver.getBalances(0).getAmount() + ")";
					PreparedStatement statement = con.prepareStatement(updateStatement);
					statement.executeUpdate();
				}

				else if(amountInXDXDB(receiver)){
					String updateStatement = "UPDATE accountbalancexdx SET amount=" +  receiver.getBalances(0).getAmount() + " WHERE address='"  + address + "'";
					PreparedStatement statement = con.prepareStatement(updateStatement);
					statement.executeUpdate();
				}
*//*
			//This Section inserts / updates the sender in the database
			String addressSender = sender.getAddress();


*//*			if(!amountInXUSDB(sender) ){
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
			}*//*


			}







	public static boolean amountInXUSDB(JsonRpc.Account account) throws SQLException{
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/diemexplorer?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "password");
		String sender_id = account.getAddress();

		String query = "SELECT * FROM accountbalancexus where address= '" + sender_id + "'";

		PreparedStatement statement = con.prepareStatement(query);
		ResultSet resultSet = statement.executeQuery();
		return resultSet.next();
	}

	public static boolean amountInXDXDB(JsonRpc.Account account) throws SQLException{
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/diemexplorer?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "password");

		String sender_id= account.getAddress();

		String query ="SELECT * FROM accountbalancexdx WHERE address = '" +  sender_id + "'";

		PreparedStatement statement = con.prepareStatement(query);

		ResultSet resultSet = statement.executeQuery();
		return resultSet.next();
	}

	public static void setAccountinformation(JsonRpc.Account account) throws SQLException, DiemException {
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/diemexplorer?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "password");
		String expirationtime = String.valueOf(account.getRole().getExpirationTime());

		PreparedStatement statement;

		if (!accountInformationInDB(account)) {
			String insert = "INSERT INTO accountinformation 	(address," +
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
					+ (account.getRole().getType().equals("designated_dealer") ? account.getRole().getPreburnBalances(0) : null) + ",'"
					+ account.getRole().getReceivedMintEventsKey() + "')";

			statement = con.prepareStatement(insert);
			statement.executeUpdate();
		} else {
			String update = "UPDATE  accountinformation SET rtype = '" + account.getRole().getType() + "', expiration_time= '" + expirationtime + "', preburn_balancexus = " + (account.getRole().getType().equals("designated_dealer") ? account.getRole().getPreburnBalances(0).getAmount() : null) + " WHERE address='" + account.getAddress() + "'";
			statement = con.prepareStatement(update);
			statement.executeUpdate();

		}
	}

	public static boolean accountInformationInDB(JsonRpc.Account account) throws SQLException{
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/diemexplorer?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "password");
		String query = "SELECT * FROM AccountInformation WHERE address= '"  + account.getAddress() + "'";

		PreparedStatement statement = con.prepareStatement(query);
		ResultSet resultSet = statement.executeQuery();

		return resultSet.next();
	}
}
*/