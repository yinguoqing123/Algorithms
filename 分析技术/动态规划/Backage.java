public class Backage
{
	public static void main(String[] args)
	{
		int[] value={3,6,5,4,6};
		int[] weight={2,2,6,5,4};
		int[][] answer=backage0_1(value,weight,10);
		for(int i=0;i<answer.length;i++)
		{
			for(int j=0;j<answer[0].length;j++)
				System.out.print(answer[i][j]+"  ");
			System.out.println();
		}
		
	}
	private static int[][] backage0_1(int[] value,int[] weight,int backageWeight)
	{
		int a=value.length;
		int[][] m=new int[a][backageWeight+1];
		for(int i=1;i<=backageWeight;i++)
		{
			if(i>=weight[a-1])
			{
				m[a-1][i]=value[a-1];
			}
			else
				m[a-1][i]=0;
		}
		int temp1;
		int temp2;
		for(int j=a-2;j>=0;j--)
		{
			for(int i=1;i<=backageWeight;i++)
			{
				if(i<weight[j])
					m[j][i]=0;
				else
				{
					temp1=m[j+1][i-weight[j]]+value[j]; //i 物体加进去
					temp2=m[j+1][i];                    //i 物体不加进去
					if(temp1>temp2)
						m[j][i]=temp1;
					else
						m[j][i]=temp2;
				}
			}
		}
		return m;
	}
}