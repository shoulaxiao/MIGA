package com.shoulaxiao;


public class SpeciesPopulation {

    SpeciesIndividual head;//头结点
    int sepciesNum;//物种数量

    SpeciesPopulation(){
       head=new SpeciesIndividual();
       sepciesNum=CommityData.SPECIES_NUM;
    }


    /**
     * 添加物种
     * @param species
     */
    void add(SpeciesIndividual species){

       SpeciesIndividual point=head;//游标
        //寻找尾巴结点
        while (point.next!=null){
            point=point.next;
        }

        point.next=species;
        sepciesNum++;
    }


    /**
     * 遍历物种
     */
    void traverse(){
        SpeciesIndividual point=head.next;//游标
        while (point!=null){ //寻找尾结点
            for (int i=0;i<CommityData.NODE_NUM;i++){
                System.out.print(point.genes[i]+" ");
            }
            System.out.println("适应度="+point.fitness+",rate="+point.rate);
            point=point.next;
            System.out.println("----------");
        }
    }

    /**
     * 返回物种数量
     * @return
     */
    public int getSepciesNum(){
        return sepciesNum;
    }

    public void setSepciesNum(int sepciesNum) {
        this.sepciesNum = sepciesNum;
    }
}
