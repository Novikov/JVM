import static java.lang.Integer.parseInt;

public class Program {
    public static void main(String[] args) {
        TestClass testClass = new TestClass();
        testClass.testMethod();
    }
}

class TestClass {
    public void testMethod() {
        String str = "Hello";
        int result = Integer.parseInt(str);
    }
}
