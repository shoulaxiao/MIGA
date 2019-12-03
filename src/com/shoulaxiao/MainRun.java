package com.shoulaxiao;

import java.util.Scanner;

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
        best.analysis();
//        speciesPopulation.traverse();

    }
}
