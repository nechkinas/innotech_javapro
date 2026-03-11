package org.example;

import org.example.anotation.*;

public class MyTests {
    @BeforeSuite
    public static void setupSuite() {
        System.out.println("Setup before suite");
    }

    @AfterSuite
    public static void tearDownSuite() {
        System.out.println("Tear down after suite");
    }

    @BeforeEach
    public void setup() {
        System.out.println("Setup before each test");
    }

    @AfterEach
    public void tearDown() {
        System.out.println("Tear down after each test");
    }

    @Test(name = "Test 1", priority = 1)
    public void test1() throws TestAssertionError {
        // Тест проходит успешно
    }

    @Test(name = "Test 2", priority = 2)
    public void test2() throws TestAssertionError {
        throw new TestAssertionError("Assertion failed");
    }

    @Test(name = "Test 3", priority = 3)
    @Disabled
    public void test3() {
        // Этот тест пропускается
    }

    @Test(name = "Test 4", priority = 4)
    public void test4() throws Exception {
        throw new Exception("Some error occurred");
    }
}
