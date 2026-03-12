package org.example;

import org.example.anotation.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class TestRunner {

    public static Map<TestResult, List<CTest>> runTests(Class<?> c) {
        Map<TestResult, List<CTest>> results = new HashMap<>();
        results.put(TestResult.Success, new ArrayList<>());
        results.put(TestResult.Failed, new ArrayList<>());
        results.put(TestResult.Error, new ArrayList<>());
        results.put(TestResult.Skipped, new ArrayList<>());

        Method[] methods = c.getDeclaredMethods();
        List<Method> testMethods = new ArrayList<>();
        List<Method> beforeEachMethods = new ArrayList<>();
        List<Method> afterEachMethods = new ArrayList<>();
        Method beforeSuiteMethod = null;
        Method afterSuiteMethod = null;

        for (Method method : methods) {
            if (method.isAnnotationPresent(BeforeSuite.class)) {
                if (!Modifier.isStatic(method.getModifiers())) {
                    throw new BadTestClassError("@BeforeSuite должен быть статическим методом");
                }
                beforeSuiteMethod = method;
            } else if (method.isAnnotationPresent(AfterSuite.class)) {
                if (!Modifier.isStatic(method.getModifiers())) {
                    throw new BadTestClassError("@AfterSuite должен быть статическим методом");
                }
                afterSuiteMethod = method;
            } else if (method.isAnnotationPresent(BeforeEach.class)) {
                if (Modifier.isStatic(method.getModifiers())) {
                    throw new BadTestClassError("@BeforeEach не может быть применен к статическому методу: " + method.getName());
                }
                beforeEachMethods.add(method);
            } else if (method.isAnnotationPresent(AfterEach.class)) {
                if (Modifier.isStatic(method.getModifiers())) {
                    throw new BadTestClassError("@AfterEach не может быть применен к статическому методу: " + method.getName());
                }
                afterEachMethods.add(method);
            } else if (method.isAnnotationPresent(Test.class)) {
                if (Modifier.isStatic(method.getModifiers())) {
                    throw new BadTestClassError("@Test не может быть применен к статическому методу: " + method.getName());
                }
                if(method.isAnnotationPresent(Order.class)) {
                    if (Modifier.isStatic(method.getModifiers())) {
                        throw new BadTestClassError("@Order не может быть применен к статическому методу: " + method.getName());
                    }
                }
                testMethods.add(method);
            }
        }

        // Сортировка тестов по приоритету и имени
        testMethods.sort(Comparator
                .<Method>comparingInt(m -> m.getAnnotation(Test.class).priority()).reversed()
                .thenComparingInt(m -> m.isAnnotationPresent(Order.class) ? m.getAnnotation(Order.class).value() : 5)
                .thenComparing(Method::getName)
        );

        // Создание экземпляра класса тестов
        Object testClassInstance;
        try {
            testClassInstance = c.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new BadTestClassError("Невозможно создать экземпляр класса тестов: " + e.getMessage());
        }

        // Выполнение методов @BeforeSuite
        if (beforeSuiteMethod != null) {
            try {
                beforeSuiteMethod.invoke(null); // Статический метод
            } catch (Exception e) {
                throw new RuntimeException("Ошибка при выполнении @BeforeSuite: " + e.getMessage());
            }
        }

        // Выполнение тестов
        for (Method testMethod : testMethods) {
            Test testAnnotation = testMethod.getAnnotation(Test.class);
            String testName = testAnnotation.name().isEmpty() ? testMethod.getName() : testAnnotation.name();
            TestResult result = TestResult.Success;
            Exception thrownException = null;

            if (testMethod.isAnnotationPresent(Disabled.class)) {
                result = TestResult.Skipped;
            } else {
                for (Method beforeEach : beforeEachMethods) {
                    try {
                        beforeEach.invoke(testClassInstance);
                    } catch (Exception e) {
                        thrownException = e;
                        result = TestResult.Error;
                        break;
                    }
                }

                if (result == TestResult.Success) {
                    try {
                        testMethod.invoke(testClassInstance);
                    } catch (InvocationTargetException e) {
                        if (e.getCause() instanceof TestAssertionError) {
                            result = TestResult.Failed;
                        } else {
                            result = TestResult.Error;
                            thrownException = (Exception) e.getCause();
                        }
                    } catch (Exception e) {
                        result = TestResult.Error;
                        thrownException = e;
                    }
                }

                for (Method afterEach : afterEachMethods) {
                    try {
                        afterEach.invoke(testClassInstance);
                    } catch (Exception e) {
                        thrownException = e;
                        result = TestResult.Error;
                    }
                }
            }

            results.get(result).add(new CTest(result, testName, thrownException));
        }

        // Выполнение методов @AfterSuite
        if (afterSuiteMethod != null) {
            try {
                afterSuiteMethod.invoke(null); // Статический метод
            } catch (Exception e) {
                throw new RuntimeException("Ошибка при выполнении @AfterSuite: " + e.getMessage());
            }
        }

        return results;
    }
}
