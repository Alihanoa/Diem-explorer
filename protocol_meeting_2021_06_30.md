# Meeting 30.06.2021  

Time: 19:00 – 21:30  
Persons: Alihan, Daniel, Tevfik, Tim  

## Discussed topics/doings:   
#### Initialization of the validator-testnet and start a client using Docker:
- we composed an email to Marius Weise and Dominik Krakau to arrange a meeting  
  so that we can get to know each other and clarify some organizational questions  
- Daniel and Tevfik successfully set up the validator-testnet, but during the attempt  
  to start a diem-client using the command 'cargo run -p cli -- -c $CHAIN_ID -m $ROOT_KEY  
  -u http://127.0.0.1:8080 --waypoint $WAYPOINT' some errors occured:
  1. 'error: linking with `cc` failed: exit code: 1'   
  2. 'error: aborting due to previous error'  
  3. 'error: could not compile `cli`'  
- Daniel and Tevfik also tried to start the client out of the directory 'client-cli', but an error occured  
  there as well:
  '2021-07-02T08:58:01.797174Z [main] ERROR common/crash-handler/src/lib.rs:38 details = '''  
  panicked at 'Not able to connect to validator at http://validator:8080/v1. Error:  
  Error { inner: Inner { kind: JsonRpcError, source: None, json_rpc_error: Some(JsonRpcError  
  { code: -32601, message: "Method not found", data: None }) } }', testsuite/cli/src/main.rs:134:13'''  
  backtrace = ''''
  'ERROR: 12'
- we looked up the file, where the method couldn't be found, but didn't detect any error
- Daniel and Tevfik restarted and reset the validator-testnet with the commands 'docker-compose down'/  
  'docker-compose down -v' and 'docker-compose up', but even that has'nt changed anything
- during the whole process Alihan and Tim helped Daniel and Tevfik to find possible errors  
  and get new ideas how we could find a way to start the client,  
  but at the end we weren't successfully  