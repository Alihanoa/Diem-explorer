/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diemexplorer.explorer.JSONRPCtoDB;

import com.diem.Testnet;
import com.diem.types.ChainId;

/**
 *
 * @author Msi
 */
public class MyTestnet extends Testnet {

    public MyTestnet() {
        super();
        JSON_RPC_URL = "http://localhost:8080";
        FAUCET_SERVER_URL = "http://localhost:8000";
        CHAIN_ID = new ChainId((byte) 4);

    }
}
