package com.example.study.servie;

public interface ParallelStreamService {
    long sequentialSum(long maxNumber);
    long iterativeSum(long maxNumber);
    long streamParallelSum(long maxNumber);
}
