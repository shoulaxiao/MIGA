import java.util.Random;

public class TestCode {

    public static double[] multiPros;
    public static void main(String[] args) {
//        multiPros = new double[4];
//        multiPros[0] = 0.14;
//        multiPros[1] = 0.49;
//        multiPros[2] = 0.06;
//        multiPros[3] = 0.31;
//        for (int i = 0; i < 10; i++) {
//            int k = nextDiscrete(multiPros);
////            System.out.println(k);
//        }

//        int sum=0;
//        long start=System.currentTimeMillis();
//         for (int i=0;i<800000;i++){
//             sum=sum+1;
//         }
//         long end=System.currentTimeMillis();
//        System.out.println("总共耗时"+(end-start)+"ms");

        Random random=new Random();
        for (int i=0;i<100;i++){
            double r=random.nextInt(100);
            System.out.println(r);
        }


    }



    public static int nextDiscrete(double[] probs)
    {
        double sum = 0.0;
        for (int i = 0; i < probs.length; i++)
            sum += probs[i];

        System.out.println(sum);
        double r = Math.random()  * sum;
        sum = 0.0;
        for (int i = 0; i < probs.length; i++) {
            sum += probs[i];
            if (sum > r)
                return i;
        }
        return probs.length - 1;
    }

}
