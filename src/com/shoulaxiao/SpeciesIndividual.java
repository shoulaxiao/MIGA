package com.shoulaxiao;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class SpeciesIndividual implements Cloneable {

    public String[] genes;//基因序列,对应一种编码,即社区划分方案
    public float fitness;//适应度
    public float rate;//被选中的概率

    SpeciesIndividual next;//下一个结点


    /**
     * 构造函数
     */
    public SpeciesIndividual(){
       genes=new String[CommityData.NODE_NUM];
       this.fitness=0.0f;
       this.rate=0.0f;
       this.next=null;
    }


    /**
     * 初始化物种基因(随机)
     */
    void createByRandGenes(){
        Random random=new Random();
        for (int i=0;i<genes.length;i++){
            this.genes[i]=Integer.toString(random.nextInt(CommityData.COMMITY_NUM));
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
                //得到第j个结点的邻接结点
                degree_j=CommityData.neigbor_List.get(j).size();

                sum+=((CommityData.adjacentMatrix[i][j]-(degree_i*degree_j)/M)*JudmentCommunity(this.genes,i,j));
            }
        }

        this.fitness=(1/M)*sum;
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


    @Override
    public boolean equals(Object obj) {
        if (obj==null){
            return false;
        }

        if (this==obj){
            return true;
        }

        if (obj instanceof SpeciesIndividual){
            SpeciesIndividual sp=(SpeciesIndividual)obj;
            if (sp.fitness==this.fitness&&jugeIsGene(sp.genes)){
                return true;
            }else {
                return false;
            }
        }
        return false;
    }

    private boolean jugeIsGene(String[] genes){
        for (int i=0;i<this.genes.length;i++){

            if (!this.genes[i].equals(genes[i])){
                return false;
            }
        }
        return true;
    }

    public void print(){
        for (int i=0;i<genes.length;i++){
            System.out.print(genes[i]+" ");
        }
        System.out.println("模块度fitNess="+fitness);
    }

    /**
     * 解析基因
     */
    public void analysis(){


        System.out.println("一共有"+CommityData.COMMITY_NUM+"个社区");
        int flag=0;
        while (flag<CommityData.COMMITY_NUM){
            System.out.println("第"+(flag+1)+"社区包含节点:");
            for (int i=0;i<genes.length;i++){
                if (flag==Integer.parseInt(genes[i])){
                    System.out.print((i+1)+" ");
                }
            }
            System.out.println();
            flag++;
        }




    }
}
