public class BotoomMatrix
{
	public static void main(String[] args)
		{
			int[] p={30,35,15,5,10,20,25};
			int[][] m=new int[6][6];
			int[][] s=new int[6][5];
			botoom(p,m,s);
			System.out.println("������С��������: "+m[5][0]);
			System.out.println("����ָ���ǣ�");
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
		//i j�ֱ��Ǿ���������յ�
		
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
			//����m[j-1][j+i-2]��ֵʱ ע��q��λ��
			//int q=Integer.MAX_VALUE;
			int q;
			int n=p.length-1; //����������
			for(int i=0;i<n;i++) //����Ϊ1ʱ ��ʼֵ
				m[i][i]=0;
			for(int i=2;i<=n;i++)//i Ϊ���󳤶�
			{
				for(int j=1;j<=n-i+1;j++)//j ��ʾ���
				{
					m[j-1][j+i-2]=Integer.MAX_VALUE;
					for(int k=j;k<j+i-1;k++) //��Ϊi�������i-1�ַַ�
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