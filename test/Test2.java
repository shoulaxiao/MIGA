import com.shoulaxiao.CommityData;

import java.util.Random;

public class Test2 {
    public static void main(String[] args) {

        int T=800000;
        float k=0.99f;
        int sum=0;

        while (T>0){
            sum++;
            T= (int)(T*k);
        }
        System.out.println(sum);
    }
}
