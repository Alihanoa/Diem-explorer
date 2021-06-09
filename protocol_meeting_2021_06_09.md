# Meeting 09.06.2021  

Time: 15:30 – 18:00  
Persons: Alihan, Daniel, Tevfik, Tim  

## Discussed topics/doings:   
#### Initialization of the validator-testnet using Docker:
- Alihan and Daniel informed about their progress on the last meeting 
- we all tried to set up the validator-testnet again, but after some time the first difficulties  
  occured regarding the Diem root key, because the key was encrypted in an unknown way,  
  so the 'cargo run'-command couln't be executed
- we didn't find a way to get the right, decrypted key yet
- also Daniel had the problem, that some errors were thrown concerning the access rights to  
  required files during the process of starting a client on the validator-testnet
- after some changes were made Daniel tried again, but this time the Ubuntu VM crashed  
  as already at the previous meeting and he wasn't able to start it again
- even after a reboot of Daniel's laptop the VM couln't be started again,  
  so he wasn't able to continue working at the current task

## Tasks to be done until the next meeting:  
- think about how we can get the correct key to start the diem-client 