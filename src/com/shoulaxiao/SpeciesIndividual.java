package com.shoulaxiao;

import java.util.ArrayList;
import java.util.Random;

public class SpeciesIndividual implements Cloneable {

    int id;//每条染色体唯一表示
    String[] genes;//基因序列,对应一种编码,即社区划分方案
    float fitness;//适应度
    float rate;//被选中的概率

    SpeciesIndividual next;


    /**
     * 构造函数
     */
    SpeciesIndividual(){
       genes=new String[CommityData.NODE_NUM];
       this.fitness=0.0f;
       this.next=null;
    }


    /**
     * 初始化物种基因(随机)
     */
    void createByRandGenes(){
        Random random=new Random();
        for (int i=0;i<genes.length;i++){
            genes[i]=Integer.toString(random.nextInt(CommityData.COMMITY_NUM));
        }
    }

    /**
     * 计算适应度
     */
    void cacalateFitness(){
        float degree_i=0.0f,degree_j=0.0f;
        float sum=0.0f;
        float M=2*CommityData.EDGE_NUM;

        for (int i=0;i<CommityData.adjacentMatrix.length;i++){
            //得到第i个结点的度
            degree_i=CommityData.neigbor_List.get(i).size();

            for (int j=0;j<CommityData.adjacentMatrix.length;j++){
                //不计算对角线
                if (i==j){
                    continue;
                }

                //得到第j个结点的邻接结点
                degree_j=CommityData.neigbor_List.get(j).size();

                sum+=((CommityData.adjacentMatrix[i][j]-(degree_i*degree_j)/M)*JudmentCommunity(this.genes,i,j));
            }
        }

        fitness=(1/M)*sum;
    }


    /**
     * 根据基因判断是否在同一个社区
     * @param genes
     * @param i
     * @param j
     * @return
     */
     int JudmentCommunity(String[] genes, int i, int j){

        //如果对应结点的编号相等,则返回1
        if (genes[i].equals(genes[j])){
            return 1;
        }else {
            return 0;
        }
    }

    @Override
    protected Object clone() {
         SpeciesIndividual specie=null;
         try {
             specie=(SpeciesIndividual)super.clone();
         }catch (CloneNotSupportedException e){
             e.printStackTrace();
         }
         specie.genes=genes.clone();
         return specie;
    }


    /**
     * 深度复制
     * @return
     */
    public SpeciesIndividual Deepclone(){
         SpeciesIndividual newspecies=new SpeciesIndividual();
         for (int i=0;i<this.genes.length;i++){
             newspecies.genes[i]=this.genes[i];
         }

         newspecies.fitness=this.fitness;
         newspecies.rate=this.rate;
         return newspecies;
    }


    public void print(){
        for (int i=0;i<genes.length;i++){
            System.out.print(genes[i]+" ");
        }
        System.out.println("模块度fitNess="+fitness);
    }


}
