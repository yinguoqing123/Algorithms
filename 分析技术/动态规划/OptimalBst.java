public class OptimalBst
{
	public static void main(String[] args)
	{
		float[] p={0.00f,0.15f,0.10f,0.05f,0.10f,0.20f};
		float[] q={0.05f,0.10f,0.05f,0.05f,0.05f,0.10f};
		int n=p.length+1;
		float[][] w=new float[n][n];
		float[][] e=new float[n][n];
		int[][] root=new int[n][n];
		optimalBst(p,q,w,e,root);
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
				System.out.print(w[i][j]+" ");
			System.out.println();
		}
		System.out.println();
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
				System.out.print(e[i][j]+" ");
			System.out.println();
		}
		System.out.println();
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
				System.out.print(root[i][j]+" ");
			System.out.println();
		}
		System.out.println();
	}
	private static void optimalBst(float[] p,float[] q,float[][] w,float[][] e,int[][] root)
	{
		int n=p.length+1;
		//数组的下标搞得好烦人
		for(int i=0;i<n-1;i++)
		{
			e[i+1][i]=q[i];
			w[i+1][i]=q[i];
		}
		for(int l=1;l<n-1;l++)
		{
			for(int i=1;i<=n-l-1;i++)  //i j是起点和终点的下标
			{
				int j=i+l-1;
				w[i][j]=w[i][j-1]+p[j]+q[j];
				e[i][j]=Float.POSITIVE_INFINITY;
				float t;
				for(int r=i;r<=j;r++)
				{
					t=e[i][r-1]+e[r+1][j]+w[i][j];
					if(t<e[i][j])
						{
							e[i][j]=t;
							root[i][j]=r;
						}
					
				}
			}
		}
	}
}