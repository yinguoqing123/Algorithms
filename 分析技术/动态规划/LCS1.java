//减少额外空间 
public class LCS1
{
	public static void main(String[] args)
	{
		char[] p1={'A','B','C','B','D','A','B'};
		char[] p2={'B','D','C','A','B','A'};
		int[][] arr=new int[2][7];
		lcs(p1,p2,arr);
		for(int i=0;i<2;i++)
		{
			for(int j=0;j<7;j++)
				System.out.print(arr[i][j]+"  ");
			System.out.println();
		}
	}
	private static void lcs(char[] p1,char[] p2,int[][] arr)
	{
		int m=p2.length+1;  //m=7
		int n=p1.length+1;  //n=8
		for(int i=0;i<2;i++)
		{
			for(int j=0;j<m;j++)
				arr[i][j]=0;
			
		}
		//只需要两行来保存
		//位运算是错的 要将下面一行复制到上一行 在将计算结过填入下一行  如此反复
		/*
		int t=1;
		for(int i=1;i<n;i++,t^=1)
		{
			for(int j=1;j<m;j++)
			{
				if(p1[i-1]==p2[j-1])
					arr[t][j]=arr[t^1-1][j-1]+1;
				else if(arr[t][j]>arr[t][j-1])
					arr[t][j]=arr[t][j];
				else 
					arr[t^1][j]=arr[t^1][j-1];
				
			}
		}
		*/
		for(int i=1;i<n;i++)
		{
			for(int j=1;j<m;j++)
			{
				if(p1[i-1]==p2[j-1])
					arr[1][j]=arr[0][j-1]+1;
				else if(arr[0][j]>=arr[1][j-1])
					arr[1][j]=arr[0][j];
				else 
					arr[1][j]=arr[1][j-1];
				
			}
			System.arraycopy(arr[1],0,arr[0],0,m);
		}
	}
}