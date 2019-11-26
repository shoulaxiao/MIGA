import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Genetic {

    private int node_n;//结点数
    private int edge_n;//边数

    private ArrayList<Node> chromosome;//染色体,代表一种解决方案
    private int population=20;//初始种群数

    private List<ArrayList<Node>> neigbor_List;//邻接表
    private int[][] adjacentMatrix;//邻接矩阵
    public static List<ArrayList<Node>> chromosomeList;//初始种群染色体

    /**
     * 读取数据文件
     * @param path
     */
    public void readDataFile(String path){
        String encoding="utf-8";

        try {
            File file=new File(path);
            if (file.isFile()&&file.exists()){
                InputStreamReader reader=new InputStreamReader(new FileInputStream(file),encoding);
                BufferedReader br=new BufferedReader(reader);
                String lineTxt=null;
                lineTxt=br.readLine();


                //读取结点的边数与结点数(数据的第一行)
                String[] cur2=lineTxt.split(" ");
                node_n=Integer.parseInt(cur2[0]);
                edge_n=Integer.parseInt(cur2[1]);
                neigbor_List=new ArrayList<ArrayList<Node>>(node_n);
                adjacentMatrix=new int[node_n][node_n];

                //初始化邻接表
                for (int i=0;i<node_n;i++){
                    neigbor_List.add(new ArrayList<Node>());
                }

                //初始化邻接矩阵
                for (int i=0;i<adjacentMatrix.length;i++){
                    for (int j=0;j<adjacentMatrix.length;j++){
                        adjacentMatrix[i][j]=0;
                    }
                }

                //正式读取数据
                while ((lineTxt=br.readLine())!=null){
                    String cur[] = lineTxt.split(" ");
                    int u = Integer.parseInt(cur[0]);
                    int v = Integer.parseInt(cur[1]);
                    addEdges(u,v);
                    addEdges(v,u);
                }
                reader.close();
            }
            else {
                System.out.println("指定文件不存在,请重新输入文件");
            }
        }catch (Exception e){
            System.out.println("读取文件内容出错,请稍后再试");
            e.printStackTrace();
        }
    }


    /**
     * 添加节点
     * @param i
     * @param j
     */
    private void addEdges(int i, int j) {
        neigbor_List.get(i).add(new Node(j));// 给编号为i的顶点一条添加指向编号为j的边
        adjacentMatrix[i][j]=1;
    }


    /**
     * 初始化
     */
    public void init(String path){

       readDataFile(path);
       chromosomeList=initGroupPopulation(population);
//       for (int i=0;i<chromosomeList.size();i++){
//           ArrayList<Node> tep=chromosomeList.get(i);
//           for (int j=0;j<tep.size();j++){
//               System.out.print(tep.get(j).num+" ");
//           }
//           System.out.println();
//       }
    }


    /**
     * 对问题进行编码,初始化一条染色体
     */
    private ArrayList<Node> encoding_Population(){
        chromosome=new ArrayList<Node>();
        for (int i=0;i<node_n;i++){
            Random random=new Random();
            int num=random.nextInt(node_n);
            Node node=new Node(num);
            chromosome.add(node);
        }
        return chromosome;
    }


    /**
     * 初始化一组种群大小
     * @param groupSize
     * @return
     */
    public List<ArrayList<Node>> initGroupPopulation(int groupSize){
        List<ArrayList<Node>> list=new ArrayList<ArrayList<Node>>();
        for (int i=0;i<groupSize;i++){
            ArrayList<Node> temp=encoding_Population();
            list.add(temp);
        }
        return list;
    }

    /**
     * 选择操作,采用轮盘赌方式
     * @return 返回染色体的编号
     */
    public int selectChromosome(){

       double[] fitNess=new double[population];//适应度函数
       double[] select_Probablty=new double[population];//选择概率
//        double[] accumulation_Probablty=new double[population];//累积概率
       double fitNess_num=0;

       //计算每条染色体的适应度函数Q
       for (int i=0;i<chromosomeList.size();i++){
           fitNess[i]=caculateFitness(chromosomeList.get(i));
           fitNess_num+=fitNess[i];
       }


       //计算每条染色体的选择概率
       for (int i=0;i<select_Probablty.length;i++){
           select_Probablty[i]=fitNess[i]/fitNess_num;
       }

       double sum=0.0;
       for (int i=0;i<select_Probablty.length;i++){
           sum+=select_Probablty[i];
       }

       double rand=Math.random()*sum;
       sum=0.0;
       for (int i=0;i<select_Probablty.length;i++){
           sum+=select_Probablty[i];
           if (sum>rand){
               return i;
           }
       }
       return select_Probablty.length-1;
    }


    /**
     * 选择两个最好的染色体
     * @return
     */
    public int[] selectChromosomeBest(){
        int[] result=new int[2];
        double[] fitNess=new double[population];//适应度函数

        //计算每条染色体的适应度函数Q
        for (int i=0;i<chromosomeList.size();i++){
            fitNess[i]=caculateFitness(chromosomeList.get(i));

            //----------------------测试代码--------------------------------//
            ArrayList<Node> listftg=chromosomeList.get(i);
            for (int j=0;j<listftg.size();j++){
                System.out.print(listftg.get(j).num+" ");
            }
            System.out.println("第"+i+"个染色体的模块度为:"+fitNess[i]);
            //------------------------测试代码------------------------------//
        }

        double max=fitNess[0];
        double max_second=0x80000000;
        for (int i=1;i<fitNess.length;i++){
            if (fitNess[i]>max){
                max_second=max;
                max=fitNess[i];
                result[0]=i;
            }else if (fitNess[i]>max_second){
                max_second=fitNess[i];
                result[1]=i;
            }
        }

        return result;
    }


    /**
     * 交叉算子
     * @param G1 源染色体
     * @param G2 目标染色体
     * @return
     */
    public ArrayList<Node> crossover(ArrayList<Node> G1,ArrayList<Node> G2){
        Random random=new Random();
        int v=random.nextInt(G1.size());//随机选取节点Vi
        int data=G1.get(v).num;//得到选择对应节点的值

        //遍历G1,如果对应节点编号==data,则将其与G2对调
        for (int m=0;m<G1.size();m++){
            if (G1.get(m).num==data){
                G2.get(m).num=data;
            }
        }
        return G2;
    }

    /**
     * 变异操作
     * @param X
     * @return
     */
    public ArrayList<Node> variation(ArrayList<Node> X){
       Random random=new Random();
       int v=random.nextInt(X.size());//随机选取节点
        int data=X.get(v).num;//变异前的标签
        int gole=random.nextInt(node_n);//变异后的标签
        while (data==gole){
            gole=random.nextInt(node_n);
        }
        X.get(v).num=gole;//变异操作
        return X;
    }


    /**
     * 计算适应度函数
     * @param list
     * @return 返回模块度Q的值
     */
    public double caculateFitness(ArrayList<Node> list){

        double degree_i=0.0,degree_j=0.0;
        double sum=0.0;
        double M=2*edge_n;

        for (int i=0;i<adjacentMatrix.length;i++){
            //得到第i个结点的度
            degree_i=neigbor_List.get(i).size();

            for (int j=0;j<adjacentMatrix.length;j++){
                //不计算对角线
                if (i==j){
                   continue;
                }

                //得到第j个结点的邻接结点
                degree_j=neigbor_List.get(j).size();

                sum+=(adjacentMatrix[i][j]-(degree_i*degree_j)/M)*JudmentCommunity(list,i,j);
            }
        }

        double Q=(1/M)*sum;
        return Q;

    }


    /**
     * 判断两个结点是否属于同一个社区
     * @param list
     * @param i
     * @param j
     * @return  同一社区返回1,否则返回0
     */
    public int JudmentCommunity(ArrayList<Node> list,int i,int j){

        //如果对应结点的编号相等,则返回1
        if (list.get(i).num==list.get(j).num){
            return 1;
        }else {
            return 0;
        }
    }



    /**
     * 输出邻接表
     */
    public void printList(){

        for (int i=0;i<neigbor_List.size();i++){
            ArrayList<Node> nodes=neigbor_List.get(i);
            System.out.println("当前结点编号:"+i);
            for (int j=0;j<nodes.size();j++){
                System.out.print(" --> " + nodes.get(j).num);
            }
            System.out.println();
        }

        //初始化邻接矩阵
        for (int i=0;i<adjacentMatrix.length;i++){
            for (int j=0;j<adjacentMatrix.length;j++){
                System.out.print(adjacentMatrix[i][j]);
            }
            System.out.println();
        }
    }
}
