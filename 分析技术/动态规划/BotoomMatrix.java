public class BotoomMatrix
{
	public static void main(String[] args)
		{
			int[] p={30,35,15,5,10,20,25};
			int[][] m=new int[6][6];
			int[][] s=new int[6][5];
			botoom(p,m,s);
			System.out.println("矩阵最小计算量是: "+m[5][0]);
			System.out.println("矩阵分割点是：");
			print1(s,1,6);
			System.out.println();
			for(int i=0;i<6;i++)
			{
				for(int j=0;j<6;j++)
					System.out.print(m[i][j]+" ");
				System.out.println();
			}
			for(int i=0;i<6;i++)
			{
				for(int j=0;j<5;j++)
					System.out.print(s[i][j]+" ");
				System.out.println();
			}
		}
		//i j分别是矩阵的起点和终点
		
		private static void print1(int[][] s,int i,int j)
		{
			if(i!=j)
				{
					System.out.print(s[i-1][j-2]+" ");
					print1(s,i,s[i-1][j-2]);
					print1(s,s[i-1][j-2]+1,j);
				}
			
		}
		
		private static void botoom(int[] p,int[][] m,int[][]s)
		{
			//更新m[j-1][j+i-2]的值时 注意q的位置
			//int q=Integer.MAX_VALUE;
			int q;
			int n=p.length-1; //输入矩阵个数
			for(int i=0;i<n;i++) //长度为1时 初始值
				m[i][i]=0;
			for(int i=2;i<=n;i++)//i 为矩阵长度
			{
				for(int j=1;j<=n-i+1;j++)//j 表示起点
				{
					m[j-1][j+i-2]=Integer.MAX_VALUE;
					for(int k=j;k<j+i-1;k++) //长为i个矩阵的i-1种分法
					{
						q=m[j-1][k-1]+m[k][j+i-2]+p[j-1]*p[k]*p[j+i-1];
						if(q<m[j-1][j+i-2])
						{
							s[j-1][j+i-3]=k;
							m[j-1][j+i-2]=q;
						}	
					}
					
				}
				
			}
			
		}
}