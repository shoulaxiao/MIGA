package com.shoulaxiao;

import java.util.Random;
import java.util.Set;

/**
 * 遗传算法类
 */
public class GeneticAlgorithm {

    /**
     * 遗传算法类
     * @param list
     * @return
     */
    SpeciesIndividual run(SpeciesPopulation list){

        createBeginningSpecies(list);//创建初始种群

        for (int i=0;i<CommityData.DEVELOP_NUM;i++){
            System.out.println("-----------------第"+(i+1)+"代进化中-----------------");

//            mutate(list);//变异

            //局部搜索
            while (CommityData.T>0){

                for (int j=1;j<CommityData.l;j++){
                    //选择两条染色体
                    SpeciesIndividual G1=select(list);
                    SpeciesIndividual G2=select(list);

                    Random random=new Random();
                    float jk=random.nextFloat();

                    //子代
                    SpeciesIndividual g1;
                    SpeciesIndividual g2;

                    //交叉
                    if (jk>CommityData.pcl&&jk<CommityData.pch){
                         g1=crossoverTwo(G1,G2);
                         g2=crossoverTwo(G2,G1);
                    }else {//变异
                        g1=mutateSingle(G1);
                        g2=mutateSingle(G2);
                    }

                    float p=g1.fitness-g2.fitness;

                    float r=random.nextFloat();
                    if (p>0||(Math.expm1(p)/CommityData.T)>r){
                        updateSpeciesPopulation(list,g1);//更新G1
                    }
                }
                CommityData.T=(int) (CommityData.T*CommityData.k);

            }


//            crossover(list);

//            System.out.println("最好的染色体结果为:");
//            SpeciesIndividual cuurent=getBest(list);
//            cuurent.print();
            list.traverse();
        }


        SpeciesIndividual best=getBest(list);
        return best;
    }


    /**
     * 创建初始种群
     * @param list
     */
    private void createBeginningSpecies(SpeciesPopulation list) {

       for (int i=0;i<CommityData.SPECIES_NUM;i++){
           SpeciesIndividual species=new SpeciesIndividual();//创建结点
           species.createByRandGenes();//初始种群基因
           species.cacalateFitness();
           list.add(species);
       }

        System.out.println("初始种群数初始化成功,种群数SPECIES_NUM="+CommityData.SPECIES_NUM);


    }


    /**
     * 计算每个物种被选择的概率
     */
    private void caculatRate(SpeciesPopulation list){
        //计算总适应度
        float totalFitness=0.0f;
        list.sepciesNum=0;

        SpeciesIndividual point=list.head.next;//游标
        while(point != null)//寻找表尾结点
        {
            point.cacalateFitness();//计算适应度

            totalFitness += Math.abs(point.fitness);
            list.sepciesNum++;

            point=point.next;
        }

        SpeciesIndividual point2=list.head.next;//游标

        //计算出其他的累及概率
        while(point2!= null)//寻找表尾结点
        {
            point2.rate=Math.abs(point2.fitness)/totalFitness;
            point2=point2.next;
        }
    }

    /**
     * 轮盘赌选择优秀物种
     * @param list
     */
    private SpeciesIndividual select(SpeciesPopulation list){

        caculatRate(list);  //计算选中概率

        SpeciesIndividual point=list.head.next;//游标

        float selecyt_rate=(float)Math.random();
        float sum=0.0f;
        //计算出其他的累及概率
        while(point!= null)//寻找表尾结点
        {
            sum+=point.rate;
            if (sum>selecyt_rate){
                return point;
            }
            point=point.next;
        }
        return point;//返回尾巴结点
    }


//    /**
//     * 返回两个较好的物种
//     * @param list
//     * @return
//     */
//    private SpeciesIndividual[] selectTwo(SpeciesPopulation list){
//        SpeciesIndividual[] species=new SpeciesIndividual[2];
//        SpeciesIndividual point=list.head.next;
//        float one=-1.0f,second=-1.0f;
//        while (point!=null){
//            if (point.fitness>one){
//                second=one;
//                one=point.fitness;
//                species[0]=point;
//            }else if (point.fitness>second&&point.fitness<one){
//                second=point.fitness;
//                species[1]=point;
//            }
//            point=point.next;
//        }
//        return species;
//    }


    /**
     * 两条染色体交叉
     * @param G1
     * @param G2
     * @return
     */
    private SpeciesIndividual crossoverTwo(SpeciesIndividual G1,SpeciesIndividual G2){

        float rate=(float)Math.random();

        if (rate>CommityData.pcl&&rate<CommityData.pch){
            Random random=new Random();
            int index=random.nextInt(CommityData.NODE_NUM);
            for (int i=0;i<CommityData.NODE_NUM;i++){
                if (G1.genes[i].equals(G1.genes[index])){
                    G2.genes[i]=G1.genes[index];
                }
            }
        }
        G2.cacalateFitness();//重新计算适应度
        return G2.Deepclone();
    }


    /**
     * 交叉操作
     * @param list
     */
    private void crossover(SpeciesPopulation list){
        float rate=(float) Math.random();

        if (rate>CommityData.pcl&&rate<CommityData.pch){
            SpeciesIndividual point=list.head.next;//游标
            Random rand=new Random();
            int find=rand.nextInt(list.sepciesNum);

            while (point!=null&&find!=0){
                point=point.next;
                find--;
            }

            if (point.next!=null){
                int begin=rand.nextInt(CommityData.NODE_NUM);
                //取point和point.next进行交叉，形成新的两个染色体
                for (int i=begin;i<CommityData.NODE_NUM;++i){

                    //找出point.genes中与point.next.genes[i]相等的位置fir
                    //找出point.next.genes中与point.genes[i]相等的位置sec
                    int fir,sec;
                    for(fir=0;!point.genes[fir].equals(point.next.genes[i]);fir++);
                    for(sec=0;!point.next.genes[sec].equals(point.genes[i]);sec++);

                    //两个基因互换
                    String tmp;
                    tmp=point.genes[i];
                    point.genes[i]=point.next.genes[i];
                    point.next.genes[i]=tmp;

                    //消去互换后重复的那个基因
                    point.genes[fir]=point.next.genes[i];
                    point.next.genes[sec]=point.genes[i];
                }
            }
        }
    }


    /**
     * 变异操作
     * @param list
     */
    private void mutate(SpeciesPopulation list){
        //每一物种均有变异的机会,以概率pm进行
        SpeciesIndividual point=list.head.next;
        while (point!=null){
            float rate=(float)Math.random();
            if (rate<CommityData.pm){
                //寻找变异的点
                Random rand=new Random();
                int index=0;
                int mutateNum=0;
                //变异操作
                while(Integer.parseInt(point.genes[index])==mutateNum){
                    index=rand.nextInt(CommityData.NODE_NUM);
                    mutateNum=rand.nextInt(CommityData.COMMITY_NUM);
                    if (Integer.parseInt(point.genes[index])==mutateNum)
                        point.genes[index]=String.valueOf(mutateNum);
                }
            }
            point.cacalateFitness();
            point=point.next;
        }
    }


    /**
     * 单个染色体变异
     * @param specie
     * @return
     */
    private SpeciesIndividual mutateSingle(SpeciesIndividual specie){
        if (specie!=null){
            //寻找变异的点
            Random rand=new Random();
            int index=0;
            int mutateNum=0;
            //变异操作
            index=rand.nextInt(CommityData.NODE_NUM);
            mutateNum=rand.nextInt(CommityData.COMMITY_NUM);
            specie.genes[index]=String.valueOf(mutateNum);

        }

        specie.cacalateFitness();//重新计算
        return specie.Deepclone();
    }

    /**
     * 寻找适应度最好的染色体
     * @param list
     * @return
     */
    SpeciesIndividual getBest(SpeciesPopulation list){
        SpeciesIndividual bestSpecies=null;
        SpeciesIndividual point=list.head.next;//游标

        float fitNum=-3.0f;

        while (point!=null){
            if (point.fitness>fitNum){
                fitNum=point.fitness;
                bestSpecies=point;
            }
            point=point.next;
        }

        return bestSpecies;
    }


    /**
     * 淘汰适应度最小的
     */
    private void mlimination(SpeciesPopulation list){

        SpeciesIndividual point=list.head.next;
        float fitNum=Float.MIN_VALUE;
        while (point!=null){
            if (point.fitness<fitNum){
                fitNum=point.fitness;
            }
            point=point.next;
        }

        SpeciesIndividual point2=list.head.next;
        while (point2!=null){
            SpeciesIndividual temp=point2;
            if (point2.fitness==fitNum){
                point2.next=null;
                temp.next=point2.next;
            }
            point2=point2.next;
        }
    }

    /**
     * 添加物种到里面,添加一个更好的物种,删除一个适应度最低的物种
     * @param list
     * @param species
     */
    private void addSpecies(SpeciesPopulation list,SpeciesIndividual species){

        if (species!=null){
            list.add(species);
            mlimination(list);
        }
    }


    /**
     * 更新种群
     * @param species
     */
    private void updateSpeciesPopulation(SpeciesPopulation list,SpeciesIndividual species){

        SpeciesIndividual point=list.head;
        while (point!=null){

            SpeciesIndividual temp=point;//临时结点

            point=point.next;
            if (point!=null){
                if (point.equals(species)){
                    temp.next=species;
                    species.next=point.next;
                    point.next=null;
                }
            }
        }
    }
}
