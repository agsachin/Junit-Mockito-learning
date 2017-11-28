package com.ibm.demo;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class RunnerAssertions {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestAssertions.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        System.out.println("FailureCount="+result.getFailureCount());
        System.out.println("totalCount="+result.getRunCount());
        System.out.println("ignoreCount="+result.getIgnoreCount());
        System.out.println("success="+result.wasSuccessful());
        System.out.println("runTime="+result.getRunTime());
    }
}