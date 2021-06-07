# Meeting 06.06.2021  

Time: 19:00 – 21:30  
Persons: Alihan, Daniel, Tim  

## Discussed topics/doings:   
#### Configurate WSL to fix our performance-problems:
- we researched for a solution to fix our performance-problems during the execution of the FullNode  
  and other containers on Docker
- Tim found out, that you can configurate WSL with a config-file, which you place on a certain  
  location in your filesystem
- we were not sure if it really works, because we did not find any other way to determine if it works  
  except by eye-checking the resource usage
#### Renewed initialization of the FullNode:
- Alihan set up the FullNode again to check the correctness of the FullNode using the listed commands  
  on the website of the tutorial 'Configure and run a public FullNode'
#### Initialization of the validator-testnet using Diem Core source code:
- Daniel tried to initialize the validator-testnet using Diem Core source code, but after about  
  two hours of processing the Ubuntu terminal crashed and could not be started again
#### Write an issue on GitHub:
- we wrote a first issue, which thematizes our problem of getting the data on Docker/in our testnet persistent

## Tasks to be done until the next meeting:  
- follow the developments within the issue
- think about other topics for issues
