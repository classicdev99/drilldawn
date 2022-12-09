/* Copyright (C) 2019 Interactive Brokers LLC. All rights reserved. This code is subject to the terms
 * and conditions of the IB API Non-Commercial License or the IB API Commercial License, as applicable. */

package com.drilldawn.util.contracts;

import com.drilldawn.ib.client.Contract;

public class ContractSamples {

    // stream 4 symbols on the top screen:
    public static Contract IndexSPX() {
        // SPX
        Contract contract = new Contract();
        contract.symbol("SPX");
        contract.secType("IND");
        contract.currency("USD");
        contract.exchange("CBOE");
        return contract;
    }

    public static Contract IndexRUT() {
        // RUT
        Contract contract = new Contract();
        contract.symbol("RUT");
        contract.secType("IND");
        contract.currency("USD");
        contract.exchange("RUSSELL");
        return contract;
    }

    public static Contract IndexVIX() {
        // VIX
        Contract contract = new Contract();
        contract.symbol("VIX");
        contract.secType("IND");
        contract.currency("USD");
        contract.exchange("CBOE");
        return contract;
    }

    public static Contract IndexRVX() {
        // RVX
        Contract contract = new Contract();
        contract.symbol("RVX");
        contract.secType("IND");
        contract.currency("USD");
        contract.exchange("CBOE");
        return contract;
    }

    public static Contract FutureWithLocalSymbol() {
//! [futcontract_local_symbol]
        Contract contract = new Contract();
        contract.localSymbol("ESM1");
        contract.secType("FUT");
        contract.currency("USD");
        contract.exchange("GLOBEX");
//! [futcontract_local_symbol]
        return contract;
    }

    public static Contract IndexESU1() {
//! [futcontract_local_symbol]
        Contract contract = new Contract();
        contract.localSymbol("ESU1");
        contract.secType("FUT");
        contract.currency("USD");
        contract.exchange("GLOBEX");
//! [futcontract_local_symbol]
        return contract;
    }


    public static Contract IndexNQU1() {
//! [futcontract_local_symbol]
        Contract contract = new Contract();
        contract.localSymbol("NQU1");
        contract.secType("FUT");
        contract.currency("USD");
        contract.exchange("GLOBEX");
//! [futcontract_local_symbol]
        return contract;
    }

    public static Contract Index(String symbol) {
        Contract contract = new Contract();
        contract.localSymbol(symbol);
        contract.secType("FUT");
        contract.currency("USD");
        contract.exchange("GLOBEX");
        return contract;
    }
}