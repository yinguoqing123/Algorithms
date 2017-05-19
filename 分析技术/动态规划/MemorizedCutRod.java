
//程序式思维方式
public class MemorizedCutRod
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
	private static void  printcut(int[] p,int n,int[] r,int[] s)
	{
		memorizedCut(p,n,r,s);
		System.out.println("长为"+n+"的钢条最大收益和切割情况如下：");
		System.out.println(r[n]);
		while(n>0)
		{
			System.out.print(s[n]+" ");
			n=n-s[n];
		}
		System.out.println();
	}
	//p是价格数组 n是钢条长度 r是存储的最优收益数组 s存储分配方案
	//r s 数组中多个0的重要性(使用递归时)
	private static int  memorizedCut(int[] p,int n,int[] r,int[] s)
	{
		int q=-1;
		int t=-1;
		if(r[n]!=0)
			{}
		else if(n==0)	
			{
				s[n]=0;
				r[n]=0;
			}
		else
		{
			for(int i=1;i<=n;i++)
			{
				if(q<p[i]+memorizedCut(p,n-i,r,s))
					{
						q=p[i]+memorizedCut(p,n-i,r,s);
						t=i;
					}
			}
			s[n]=t;
			r[n]=q;
		}
		return r[n];
	}
}