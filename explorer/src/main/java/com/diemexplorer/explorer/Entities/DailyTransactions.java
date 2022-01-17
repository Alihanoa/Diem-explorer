package com.diemexplorer.explorer.Entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DailyTransactions {

    @Id
    private Long date;

    private int day;

    private int month;

    private int year;

    private int amountTransactions;

    private int amountSmartContracts;

    private long firsTransactionVersion;

    private long lastTransactionVersion;

    private long firstTimestamp;

    private long lastTimestamp;

    private long transactionVolume;



    public DailyTransactions(){

    }

    public DailyTransactions(Long date, int day, int month, int year, int amountTransactions, int amountSmartContracts, long firsTransactionVersion, long lastTransactionVersion, long firstTimestamp, long lastTimestamp, long transactionVolume) {
        this.date = date;
        this.day = day;
        this.month = month;
        this.year = year;
        this.amountTransactions = amountTransactions;
        this.amountSmartContracts = amountSmartContracts;
        this.firsTransactionVersion = firsTransactionVersion;
        this.lastTransactionVersion = lastTransactionVersion;
        this.firstTimestamp = firstTimestamp;
        this.lastTimestamp = lastTimestamp;
        this.transactionVolume = transactionVolume;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getAmountTransactions() {
        return amountTransactions;
    }

    public void setAmountTransactions(int amountTransactions) {
        this.amountTransactions = amountTransactions;
    }

    public int getAmountSmartContracts() {
        return amountSmartContracts;
    }

    public void setAmountSmartContracts(int amountSmartContracts) {
        this.amountSmartContracts = amountSmartContracts;
    }

    public long getFirsTransactionVersion() {
        return firsTransactionVersion;
    }

    public void setFirsTransactionVersion(long firsTransactionVersion) {
        this.firsTransactionVersion = firsTransactionVersion;
    }

    public long getLastTransactionVersion() {
        return lastTransactionVersion;
    }

    public void setLastTransactionVersion(long lastTransactionVersion) {
        this.lastTransactionVersion = lastTransactionVersion;
    }

    public long getFirstTimestamp() {
        return firstTimestamp;
    }

    public void setFirstTimestamp(long firstTimestamp) {
        this.firstTimestamp = firstTimestamp;
    }

    public long getLastTimestamp() {
        return lastTimestamp;
    }

    public void setLastTimestamp(long lastTimestamp) {
        this.lastTimestamp = lastTimestamp;
    }

    public long getTransactionVolume() {
        return transactionVolume;
    }

    public void setTransactionVolume(long transactionVolume) {
        this.transactionVolume = transactionVolume;
    }
}

