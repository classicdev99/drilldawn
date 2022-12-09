package com.drilldawn.util;

import com.drilldawn.ib.client.EClientSocket;
import com.drilldawn.ib.client.EReader;
import com.drilldawn.ib.client.EReaderSignal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class APIConnector {
    EWrapperImpl wrapper;
    final EClientSocket m_client;
    final EReaderSignal m_signal;
    EReader reader;

    private static final Logger logger = LogManager.getLogger(APIConnector.class);


    public APIConnector() {
        wrapper = new EWrapperImpl();
        m_client = wrapper.getClient();
        m_signal = wrapper.getSignal();
    }

    public EClientSocket createConnection(String host, int port, int clientId) throws InterruptedException {
        m_client.eConnect(host, port, clientId);
        //! [connect]
        //! [ereader]
        reader = new EReader(m_client, m_signal);

        reader.start();
        //An additional thread is created in this program design to empty the messaging queue
        new Thread(() -> {
            while (m_client.isConnected()) {
                m_signal.waitForSignal();
                try {
                    reader.processMsgs();
                } catch (Exception e) {
                    logger.error("Exception: " + e.getMessage());
                }
            }

        }).start();
        //! [ereader]
        // A pause to give the application time to establish the connection
        // In a production application, it would be best to wait for callbacks to confirm the connection is complete
        Thread.sleep(1000);
        tickDataOperations(m_client);

        return m_client;
    }

    public static void tickDataOperations(EClientSocket client) {
        /*** Requesting real time market data ***/
        client.reqPositions();

    }


}
