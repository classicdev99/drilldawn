/* Copyright (C) 2019 Interactive Brokers LLC. All rights reserved. This code is subject to the terms
 * and conditions of the IB API Non-Commercial License or the IB API Commercial License, as applicable. */

package com.drilldawn.ib.contracts;

import com.drilldawn.ib.client.Contract;
import com.drilldawn.ib.client.Types.SecType;

public class FutContract extends Contract {
    public FutContract(String symbol, String lastTradeDateOrContractMonth) {
        symbol(symbol);
        secType(SecType.FUT);
        exchange("ONE");
        currency("USD");
        lastTradeDateOrContractMonth(lastTradeDateOrContractMonth);
    }

    public FutContract(String symbol, String lastTradeDateOrContractMonth, String currency) {
        symbol(symbol);
        secType(SecType.FUT.name());
        currency(currency);
        lastTradeDateOrContractMonth(lastTradeDateOrContractMonth);
    }
}
