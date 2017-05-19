public class BotoomCutRod
{
	
	public static void main(String[] args)
	{
		int[] p={0,1,5,8,9,10,17,17,20,24,30};
		int[] r=new int[11];
		int[] s=new int[11];
		printcut(p,10,r,s);
		printcut(p,9,r,s);
		printcut(p,8,r,s);
		printcut(p,7,r,s);
		printcut(p,6,r,s);
	}
	//如何返回两个数组 集合
	private static void printcut(int[] p,int n,int[] r,int[] s)
	{
		botoomCut(p,n,r,s);
		System.out.println("长为"+n+"的钢条最大收益和切割情况如下：");
		System.out.println(r[n]);
		while(n>0)
		{
			System.out.print(s[n]+" ");
			n=n-s[n];
		}
		System.out.println();	
	}
	private static int botoomCut(int[] p,int n,int[] r,int[] s)
	{
		r[0]=0;
		s[0]=0;
		int k=-1;
		for(int i=1;i<=n;i++)//n个子问题的求解
		{
			for(int j=1;j<=i;j++)//每个子问题有i个选择
			{
				if(k<p[j]+r[i-j])
					{
						k=p[j]+r[i-j];
						s[i]=j;
					}
				r[i]=k;
			}
		}
		return r[n];
	}
}