import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class TestProcessor {

    public static void runTest(Class<?> testClass) {

        Object testObj = createTestInstance(testClass);


        List<Method> beforeEachMethods = getMethodsAnnotatedWith(testClass, BeforeEach.class);
        List<Method> afterEachMethods = getMethodsAnnotatedWith(testClass, AfterEach.class);


        List<Method> testMethods = getMethodsAnnotatedWith(testClass, Test.class, Skip.class);
        testMethods.sort(Comparator.comparingInt(m -> m.getAnnotation(Test.class).order()));


        for (Method testMethod : testMethods) {
            invokeMethods(testObj, beforeEachMethods);
            invokeTestMethod(testMethod, testObj);
            invokeMethods(testObj, afterEachMethods);
        }
    }


    private static Object createTestInstance(Class<?> testClass) {
        try {
            return testClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Не удалось создать объект класса \"" + testClass.getName() + "\"", e);
        }
    }


    private static List<Method> getMethodsAnnotatedWith(Class<?> testClass, Class<? extends Annotation> annotationClass,
                                                        Class<? extends Annotation>... excluding) {
        List<Method> methods = new ArrayList<>();
        for (Method method : testClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(annotationClass)) {
                checkTestMethod(method);
                boolean excluded = false;
                for (Class<? extends Annotation> exclude : excluding) {
                    if (method.isAnnotationPresent(exclude)) {
                        excluded = true;
                        break;
                    }
                }
                if (!excluded) {
                    methods.add(method);
                }
            }
        }
        return methods;
    }


    private static void checkTestMethod(Method method) {
        if (!method.getReturnType().equals(void.class) || method.getParameterCount() != 0) {
            throw new IllegalArgumentException(
                    "Метод \"" + method.getName() + "\" должен быть void и не иметь аргументов");
        }
    }

    private static void invokeMethods(Object obj, List<Method> methods) {
        for (Method method : methods) {
            try {
                method.invoke(obj);
            } catch (Exception e) {
                throw new RuntimeException("Не удалось запустить метод \"" + method.getName() + "\"", e);
            }
        }
    }


    private static void invokeTestMethod(Method method, Object obj) {
        try {
            method.invoke(obj);
        } catch (Exception e) {
            throw new RuntimeException("Не удалось запустить тестовый метод \"" + method.getName() + "\"", e);
        }
    }
}