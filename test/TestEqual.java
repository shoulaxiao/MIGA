import com.shoulaxiao.SpeciesIndividual;

import java.util.Random;

public class TestEqual {

    public static void main(String[] args) {
        SpeciesIndividual species1=new SpeciesIndividual();
        SpeciesIndividual species2=new SpeciesIndividual();

        for (int i=0;i<species1.genes.length;i++){
            Random random=new Random();
            int ndsiv=random.nextInt(50);
            species1.genes[i]=String.valueOf(ndsiv);
            species2.genes[i]=String.valueOf(ndsiv);
        }

        species1.fitness=0.4f;
        species2.fitness=0.4f;

        System.out.println(species1.equals(species2));
    }
}
