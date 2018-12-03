package com.digicap.dcblock.dcblockapiserver.proxy;

import com.digicap.dcblock.caffeapiserver.proxy.AdminServer;
import com.digicap.dcblock.caffeapiserver.util.ApplicationProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ApplicationProperties.class })
public class AdminServerTest {

    @Autowired
    ApplicationProperties properties;

    @Test
    public void testWebClient() throws Exception {
        new AdminServer(properties).getUserByRfid("e8fcbceae80a396147347c3051a6cce0884e457e90929699d4de5713ff287d24");
    }
}
