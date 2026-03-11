package org.example;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<TestResult, List<CTest>> results = TestRunner.runTests(MyTests.class);

        for (Map.Entry<TestResult, List<CTest>> entry : results.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue().size());
            for (CTest t : entry.getValue()) {
                System.out.println(" - " + t.getName() + (t.getException() != null ? ": " + t.getException().getMessage() : ""));
            }
        }
    }
}