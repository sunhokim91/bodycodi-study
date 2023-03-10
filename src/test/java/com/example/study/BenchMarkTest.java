package com.example.study;

import com.example.study.servie.ParallelStreamService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;
import java.util.stream.Stream;

@SpringBootTest
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Slf4j
public class BenchMarkTest {

    @Autowired
    private ParallelStreamService parallelStreamService;

    private static Logger logger = Logger.getLogger(Benchmark.class.getName());

    private static long MAX_NUMBER = 10_000_000L;

    @BeforeAll
    public static void setUp() {
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(java.util.logging.Level.INFO);
        logger.addHandler(handler);
    }

    @Test
    public void sumTest() throws RunnerException {

        Options jmhRunnerOptions = new OptionsBuilder()
                .include("\\." + this.getClass().getSimpleName() + "\\.")
                .warmupIterations(3)
                .measurementIterations(3)
                .forks(0)
                .threads(1)
                .shouldDoGC(true)
                .shouldFailOnError(true)
                .resultFormat(ResultFormatType.JSON)
                .result("/dev/null")
                .shouldFailOnError(true)
                .jvmArgs("-server")
                .build();

        new Runner(jmhRunnerOptions).run();
    }

    @Benchmark
    @DisplayName("iterative Sum Test")
    public void iterativeSum() {
        long result = 0L;

        for (long i = 1L; i <= MAX_NUMBER; i++) {
            result += i;
        }

        //parallelStreamService.iterativeSum(MAX_NUMBER);
    }

    @Benchmark
    @DisplayName("sequential Sum Test")
    public void sequentialSumTest() {
        long result = Stream.iterate(1L, i -> i + 1)
                .limit(MAX_NUMBER)
                .reduce(0L, Long::sum);
    }

    @TearDown(Level.Invocation)
    public void tearDown() {
        System.gc();
    }
}
