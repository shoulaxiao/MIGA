package com.shoulaxiao;

import java.util.Scanner;

public class MainRun {
    public static void main(String[] args) {

        Scanner sc=new Scanner(System.in);
        System.out.println("请输入先验信息(网络社区数):");
        CommityData.COMMITY_NUM=sc.nextInt();

        GeneticAlgorithm GA=new GeneticAlgorithm();

        SpeciesPopulation speciesPopulation = new SpeciesPopulation();

        SpeciesIndividual best=GA.run(speciesPopulation);

//        System.out.println("最终的结果为:");
//        speciesPopulation.traverse();

    }
}
