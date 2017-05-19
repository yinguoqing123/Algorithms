public class LCS
{
	public static void main(String[] args)
	{
		char[] p1={'A','B','C','B','D','A','B'};
		char[] p2={'B','D','C','A','B','A'};
		int[][] arr=new int[8][7];
		lcs(p1,p2,arr);
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<7;j++)
				System.out.print(arr[i][j]+" ");
			System.out.println();
		}
		int i=arr.length-1;
		int j=arr[0].length-1;
		while(arr[i][j]>0)
		{
			if(p1[i-1]==p2[j-1])
			{
				System.out.print(p1[i-1]+" ");
				i--;
				j--;
			}
			else
			{
				if(arr[i-1][j]>=arr[i][j-1])
				{
					i--;
				}
				else
				{
					j--;
				}
			}
		}
	}
	private static void lcs(char[] p1,char[] p2,int[][] arr)
	{
		int m=arr[0].length;
		int n=arr.length;
		for(int i=0;i<m;i++)
			arr[0][i]=0;
		for(int j=0;j<n;j++)
			arr[j][0]=0;
		for(int i=1;i<n;i++)
		{
			for(int j=1;j<m;j++)
			{
				if(p1[i-1]==p2[j-1])
					arr[i][j]=arr[i-1][j-1]+1;
				else if(arr[i-1][j]>arr[i][j-1])
					arr[i][j]=arr[i-1][j];
				else 
					arr[i][j]=arr[i][j-1];
				
			}
		}
	}
}