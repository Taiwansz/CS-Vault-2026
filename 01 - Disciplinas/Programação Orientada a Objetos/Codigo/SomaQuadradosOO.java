import java.util.Arrays;
import java.util.List;

public class SomaQuadradosOO {
    private List<Integer> nums;
    public SomaQuadradosOO(List<Integer> nums) { this.nums = nums; }
    public long soma() {
        long s = 0;
        for (int n : nums) s += n * n;
        return s;
    }
    public static void main(String[] args) {
        SomaQuadradosOO obj = new SomaQuadradosOO(Arrays.asList(1,2,3,4,5));
        System.out.println(obj.soma());
    }
}
