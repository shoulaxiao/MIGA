# MIGA
这是一个基于遗传算法的社区发现算法代码  
论文:Community detection based on modularity and an improved
   genetic algorithm  

###使用方法:  

1.在CommityData.java文件里面,更改path变量.

###代码结构:  
1.CommityData.java:定义了全部变量,以及数据的初始化工作    
2.SpeciesIndividual.java:染色体个体  
3.SpeciesPopulation.java:物种群,用链表的形式来存储每一个SpeciesIndividual个体
4.GeneticAlgorithm.java:遗传算法步骤,包括选择交叉变异等操作  
5.MainRun.java:主函数
