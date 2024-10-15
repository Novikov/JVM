public class Program {
    public static void main(String[] args) {
        String first = new String("A");
        String second = new String("A");

        System.out.println(System.identityHashCode(first));
        System.out.println(System.identityHashCode(second));

        System.out.println("----");

        System.out.println(first.hashCode());
        System.out.println(second.hashCode());

        if (first.equals(second)){
            System.out.println("Success");
        } else {
            System.out.println("Error");
        }
    }
}
