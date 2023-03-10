package com.example.study.servie;

import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class ParallelStreamServiceImpl implements ParallelStreamService {

    @Override
    public long sequentialSum(long maxNumber) {
        return Stream.iterate(1L, i -> i + 1)
                .limit(maxNumber)
                .reduce(0L, Long::sum);
    }

    @Override
    public long iterativeSum(long maxNumber) {
        long result = 0L;

        for (long i = 1L; i <= maxNumber; i++) {
            result += i;
        }

        return result;
    }

    @Override
    public long streamParallelSum(long maxNumber) {
        return 0L;
    }
}
