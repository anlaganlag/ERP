package com.tadpole.cloud.operationManagement.modular.stock.excel;



import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

@Configuration
public class ThreadPoolConfig {

    @Bean("excelThreadPool")
    public ExecutorService buildExcelThreadPool() {
        int cpuNum = Runtime.getRuntime().availableProcessors();
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(1000);
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("excel-pool-%d").build();
        return new ThreadPoolExecutor(15 * cpuNum, 30 * cpuNum,
                1, TimeUnit.MINUTES, workQueue, threadFactory);
    }
}
