package com.shoulaxiao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CommityData {


    public static int NODE_NUM;
    public static int EDGE_NUM;
    static int COMMITY_NUM;
    static final int SPECIES_NUM=5;//种群数
    static final int DEVELOP_NUM=50;//进化代数

    static final float pcl=0.2f,pch=0.8f;//交叉概率
    static final float pm=0.2f;//变异概率

    static int T=800000;//初始温度
    static final int l=10;//每个温度循环次数l
    static final float k=0.99f;//常数k


    public static List<ArrayList<Integer>> neigbor_List;//邻接表
    public static int[][] adjacentMatrix;//邻接矩阵

    static String path="/home/shoulaxiao/文档/dataCluster/Dolphin1.txt";

    static {
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
                NODE_NUM=Integer.parseInt(cur2[0]);
                EDGE_NUM=Integer.parseInt(cur2[1]);
                neigbor_List=new ArrayList<ArrayList<Integer>>(NODE_NUM);
                adjacentMatrix=new int[NODE_NUM][NODE_NUM];

                //初始化邻接表
                for (int i=0;i<NODE_NUM;i++){
                    neigbor_List.add(new ArrayList<Integer>());
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
                System.out.println("数据读取成功!");
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
    private static void addEdges(int i, int j) {
        neigbor_List.get(i).add((j));// 给编号为i的顶点一条添加指向编号为j的边
        adjacentMatrix[i][j]=1;
    }


    /**
     * 输出矩阵
     */
    public static void printData(){
        for (int i=0;i<adjacentMatrix.length;i++){
            for (int j=0;j<adjacentMatrix.length;j++){
                System.out.print(adjacentMatrix[i][j]);
            }
            System.out.println();
        }

        for (int i=0;i<neigbor_List.size();i++){
           ArrayList<Integer> tep=neigbor_List.get(i);
            System.out.println("当前结点编号"+i);
           for (int j=0;j<tep.size();j++){
               System.out.print("-->"+tep.get(j)+" ");
           }
           System.out.println();
       }
    }
}
