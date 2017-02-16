package com.lambdaworks.redis;

import static org.junit.Assert.fail;

import org.junit.Test;

public class LeakTest {

    @Test
    public void leakTest() throws InterruptedException {
        final RedisClient client = RedisClient.create();

        for (int i = 0; i < 1000; i++) {
            assertConnectionFailure(client);
            System.gc();
        }

        client.shutdown();
    }

    private static void assertConnectionFailure(RedisClient client) {
        try {
            client.connect(RedisURI.create("redis://localhost:12345/?timeout=1s"));
            fail();
        } catch (RedisConnectionException e) {
            // connection failed.
        }
    }
}
