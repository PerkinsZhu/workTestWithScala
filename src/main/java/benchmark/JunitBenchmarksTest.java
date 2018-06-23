package benchmark;

import com.carrotsearch.junitbenchmarks.AbstractBenchmark;
import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.annotation.AxisRange;
import com.carrotsearch.junitbenchmarks.annotation.BenchmarkHistoryChart;
import com.carrotsearch.junitbenchmarks.annotation.BenchmarkMethodChart;
import com.carrotsearch.junitbenchmarks.annotation.LabelType;
import org.junit.Test;

/**
 * Created by PerkinsZhu on 2018/6/22 19:51
 **/
@BenchmarkMethodChart(filePrefix = "target/chars/JunitBenchmarksTest")  //指定报表的路径和文件名前缀
@BenchmarkHistoryChart(filePrefix = "target/chars/JunitBenchmarksTest-history", labelWith = LabelType.CUSTOM_KEY, maxRuns = 20)
@AxisRange(min = 0, max = 1)
//@BenchmarkMethodChart(filePrefix = "benchmark-lists")
public class JunitBenchmarksTest extends AbstractBenchmark {
    @Test
    @BenchmarkOptions(benchmarkRounds = 200000, warmupRounds = 5)
    public void testPutOne_100Thread_CN() {
        for (int i = 1; i < 1000000000; i++) {
            int d = i * i;
        }
    }

    @BenchmarkOptions(benchmarkRounds = 20, warmupRounds = 0)
    @Test
    public void twentyMillis() throws Exception {
        for (int i = 1; i < 1000000000; i++) {
            int d = i * i*i*i;
            Math.sqrt(d);

        }
    }
}