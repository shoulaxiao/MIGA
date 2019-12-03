package com.shoulaxiao;

import java.util.Scanner;

/**
 * @USER: shoulaxiao
 * @DATE: 19-12-3
 * @TIME: 上午11:39
 **/
public class MainRun {
    public static void main(String[] args) {

        Scanner sc=new Scanner(System.in);
        System.out.println("请输入先验信息(网络社区数):");
        CommityData.COMMITY_NUM=sc.nextInt();

        GeneticAlgorithm GA=new GeneticAlgorithm();

        SpeciesPopulation speciesPopulation = new SpeciesPopulation();

        long start=System.currentTimeMillis();

        SpeciesIndividual best=GA.run(speciesPopulation);

        long end=System.currentTimeMillis();

        System.out.println("运行时间为time="+(end-start)/1000.0+"s");
        System.out.println("======================================================================");
        System.out.println("最终的运行结果为:");
        best.print();
        best.analysis();
    }
}
