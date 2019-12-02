import com.shoulaxiao.CommityData;

public class TestQ {
    public static void main(String[] args) {

       String test="0 0 1 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 1 0 0 0 1 0 1 0 0";
       String[] temp=test.split(" ");
       System.out.println(caculate(temp));
    }

    private static float caculate(String[] genes){

        float degree_i=0.0f,degree_j=0.0f;
        float sum=0.0f;
        float M=2* CommityData.EDGE_NUM;

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

                sum+=((CommityData.adjacentMatrix[i][j]-(degree_i*degree_j)/M)*JudmentCommunity(genes,i,j));
            }
        }

        return (1/M)*sum;
    }

    static int  JudmentCommunity(String[] genes, int i, int j){

        //如果对应结点的编号相等,则返回1
        if (genes[i].equals(genes[j])){
            return 1;
        }else {
            return 0;
        }
    }
}
