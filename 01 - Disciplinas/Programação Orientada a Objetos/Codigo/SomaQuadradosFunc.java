import java.util.*;
public class SomaQuadradosFunc {
    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(1,2,3,4,5);
        long s = nums.stream().mapToLong(n -> n*n).sum();
        System.out.println(s);
    }
}
