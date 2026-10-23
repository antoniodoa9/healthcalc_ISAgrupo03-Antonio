package healthcalc;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

class HealthCalcImplSingletonTest {
    @Test
    void returnsTheSameInstanceForConcurrentCallers() throws Exception {
        Field instanceField = HealthCalcImpl.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        HealthCalcImpl existingInstance;
        synchronized (HealthCalcImpl.class) {
            existingInstance = (HealthCalcImpl) instanceField.get(null);
            instanceField.set(null, null);
        }

        int threadCount = 32;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch ready = new CountDownLatch(threadCount);
        CountDownLatch start = new CountDownLatch(1);
        List<Future<HealthCalcImpl>> results = new ArrayList<>();
        try {
            for (int i = 0; i < threadCount; i++) {
                results.add(executor.submit(() -> {
                    ready.countDown();
                    start.await();
                    return HealthCalcImpl.getInstance();
                }));
            }
            assertTrue(ready.await(5, TimeUnit.SECONDS));
            start.countDown();
            HealthCalcImpl first = results.get(0).get(5, TimeUnit.SECONDS);
            for (Future<HealthCalcImpl> result : results) {
                assertSame(first, result.get(5, TimeUnit.SECONDS));
            }
        } finally {
            start.countDown();
            executor.shutdownNow();
            synchronized (HealthCalcImpl.class) {
                instanceField.set(null, existingInstance);
            }
        }
    }
}
