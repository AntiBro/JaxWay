package test;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

/**
 * @Author huaili
 * @Date 2019/5/21 10:48
 * @Description BenchMarkTest
 **/
@State(Scope.Benchmark)
@Fork(value = 1,
        jvmArgsAppend = {"-server", "-Xms4g", "-Xmx4g", "-Xmn1536m", "-XX:CMSInitiatingOccupancyFraction=82", "-Xss256k",
                "-XX:+DisableExplicitGC", "-XX:+UseConcMarkSweepGC", "-XX:+CMSParallelRemarkEnabled",
                "-XX:LargePageSizeInBytes=128m", "-XX:+UseFastAccessorMethods",
                "-XX:+UseCMSInitiatingOccupancyOnly", "-XX:+CMSClassUnloadingEnabled"})
@Threads(value = 1)
@BenchmarkMode({Mode.Throughput})
// time = 5,timeUnit = TimeUnit.SECONDS 意思是每个迭代测量的时间限制在5s
//@Measurement(iterations = 3, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@Warmup(iterations = 1, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@OutputTimeUnit(TimeUnit.SECONDS)
public class BenchMarkTest {



    @Setup
    public void init(){

    }

    @Benchmark
    public void dotest() throws InterruptedException {
    }
}
