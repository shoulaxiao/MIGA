import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithms {


    public static void main(String[] args) {

        double T=50000;//初始温度T
        double k=0.99;//常数k,[0,1]
        int l=10;//,每个T中的循环数L

        double crossover_pro=0.8;//交叉概率
        double variation_pro=0.2;//变异概率

        ArrayList<Node> g1_best;//适应度第一染色体
        ArrayList<Node> g2_best;//适应度第二染色体

        int Gmax=50;//迭代次数

        Genetic particale=new Genetic();


        //初始化种群数
        particale.init("/home/shoulaxiao/文档/dataCluster/test2.txt");


        //选择两条染色体
        int[] result=particale.selectChromosomeBest();
        int g1_num=result[0];
        int g2_num=result[1];

        g1_best=Genetic.chromosomeList.get(g1_num);
        g2_best=Genetic.chromosomeList.get(g2_num);


        //局部搜索过程
        while (T>0.0){

            for (int i=0;i<l;i++){



                //交叉操作
                ArrayList<Node> g1=particale.crossover
                        (Genetic.chromosomeList.get(g1_num),Genetic.chromosomeList.get(g2_num));
                ArrayList<Node> g2=particale.crossover
                        (Genetic.chromosomeList.get(g2_num),Genetic.chromosomeList.get(g1_num));

                double p=particale.caculateFitness(g1)-particale.caculateFitness(g2);

                Random random=new Random();
                double r=random.nextInt();
                if (p>0&&Math.expm1(p)/ T>r){
                     g1_best=g1;
                }
            }
            T=T*k;
        }
    }
}
