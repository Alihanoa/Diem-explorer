# Meeting 22.05.2021  

Time: 19:20 – 22:30  
Persons: Alihan, Daniel, Tevfik, Tim  

## Discussed topics/doings:   
#### Troubleshooting docker integration/initializing and running FullNode:
- everyone tried to initialize and run the FullNode via Docker
- Daniel executed the FullNode via Docker, but then ran out of storage
- he tried to delete the FullNode and the container, but Docker said that he doesn't
  have the rights to do this (read-only)
- we searched for the path/file where the FullNode is stored, but didn't find it
- we reasearched a while and probably found the right path, but at this path there was no file
  located at Daniels filesystem  
- Daniel tried to run the FullNode again, but then Docker said that it's marked to be deleted, so it
  isn't able to execute it
- Daniel continued to research for a solution to the problems, but so far hasn't found out how to solve it
- Tim and Tevfik managed to perform the steps of initialization, but when they tried to run the FullNode,
  they got the message that they don't have permission to do it
- at the end only Alihan got success on running the FullNode via Docker
  
## Tasks to be done until the next meeting:  
- try to fix the problems on running the FullNode via Docker
- inform about the JSON RPC
