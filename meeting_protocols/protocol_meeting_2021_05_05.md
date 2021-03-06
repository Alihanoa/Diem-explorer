# Meeting 05.05.2021

Time: 16:00 – 17:55  
Persons: Alihan, Daniel, Tevfik, Tim

## Discussed topics/doings: 
#### 1. C4 / UML diagram for the MVP:
- we presented and discussed every diagram
- criticism on Daniel’s diagram: not directly related to the MVP,
  definition ‘Diem Payment System – interested Person’ isn’t suitable (better: user)
- criticism on Alihan’s diagram: wasn’t done according to the guidelines of UML or C4 (mixture of both),
  number and definitions of actors don’t fit (user of the Diem network/validator/user of the Diem explorer),
  transactions on the MVP-testnet are fulfilled by two users (no more)
- criticism on Tevfik’s diagram: wasn’t done according to the guidelines of a
  C4 System Context diagram (too detailed), term ‘Client’ could be unsuitable,
  a person isn’t able to perform transactions or smart contracts with the Diem explorer,
  there’s no database (blockchain is the database)
- we decided to take a improved version of Daniel’s diagram as the System Context Diagram and
  a improved version of Alihan’s diagram as the Container diagram for our MVP
- Component and Code diagram are developed in the future when we know more about the
  technical realization and implementation
#### 2. Methods of the Diem-libraries and test network:
- we took a look at some of the Diem-classes and their methods and ran a simple main-class built by
  Alihan to test a couple of them and look at their output
- we informed us about the test validator network and how to implement it
- Alihan started to initialize the network using Docker

## Tasks to be done until the next meeting:
- everybody: inform, initialize and test the test validator network
- Alihan: rework the diagram
- Daniel: rework the diagram
