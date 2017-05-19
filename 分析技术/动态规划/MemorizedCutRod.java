
//����ʽ˼ά��ʽ
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
		System.out.println("��Ϊ"+n+"�ĸ������������и�������£�");
		System.out.println(r[n]);
		while(n>0)
		{
			System.out.print(s[n]+" ");
			n=n-s[n];
		}
		System.out.println();
	}
	//p�Ǽ۸����� n�Ǹ������� r�Ǵ洢�������������� s�洢���䷽��
	//r s �����ж��0����Ҫ��(ʹ�õݹ�ʱ)
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