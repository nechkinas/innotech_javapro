package org.example;

public class CTest {
    private final TestResult result;
    private final String name;
    private final Exception exception;

    public CTest(TestResult result, String name, Exception exception) {
        this.result = result;
        this.name = name;
        this.exception = exception;
    }

    public TestResult getResult() {
        return result;
    }

    public String getName() {
        return name;
    }

    public Exception getException() {
        return exception;
    }
}
