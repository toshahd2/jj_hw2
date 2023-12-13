public class Main {

    public static void main(String[] args) {
        TestProcessor.runTest(MyTest.class);
    }


    static class MyTest {

        @BeforeEach
        void beforeTest() {
            System.out.println("Готовимся к тесту");
        }


        @Test(order = -2)
        void firstTest() {
            System.out.println("firstTest");
        }


        @Test
        void secondTest() {
            System.out.println("secondTest");
        }


        @Test(order = 5)
        void thirdTest() {
            System.out.println("thirdTest");
        }


        @AfterEach
        void afterTest() {
            System.out.println("Завершаем тест");
        }
    }
}