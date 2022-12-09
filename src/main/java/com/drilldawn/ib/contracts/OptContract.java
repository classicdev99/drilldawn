/* Copyright (C) 2019 Interactive Brokers LLC. All rights reserved. This code is subject to the terms
 * and conditions of the IB API Non-Commercial License or the IB API Commercial License, as applicable. */

package com.drilldawn.ib.contracts;

import com.drilldawn.ib.client.Contract;
import com.drilldawn.ib.client.Types.SecType;

public class OptContract extends Contract {
    public OptContract(String symbol, String lastTradeDateOrContractMonth, double strike, String right) {
        this(symbol, "SMART", lastTradeDateOrContractMonth, strike, right);
    }

    public OptContract(String symbol, String exchange, String lastTradeDateOrContractMonth, double strike, String right) {
        symbol(symbol);
        secType(SecType.OPT.name());
        exchange(exchange);
        currency("USD");
        lastTradeDateOrContractMonth(lastTradeDateOrContractMonth);
        strike(strike);
        right(right);
    }
}
