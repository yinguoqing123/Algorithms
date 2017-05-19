public class KMP
{
	public static void main(String[] args)
	{
		String str="abababacabadabacababcbabadabac";
		String pattern="ababaca";
		kmpMatcher(str,pattern);
	}
	
	public static void kmpMatcher(String str, String pattern)
	{
		int m=str.length();
		int n=pattern.length();
		int[] p=new int[n];  //p�д��ƥ��ĳ���-1��Ҳ�����´�ƥ����±�ֵ=p[i]+1
		computePrefix(pattern,p);
		int k=-1;  //��ʼ��  ��һ��Ҫƥ����ַ��±��� k+1
		for(int i=0;i<m;i++)
		{
			while(k>-1&&pattern.charAt(k+1)!=str.charAt(i))
				k=p[k];
			if(pattern.charAt(k+1)==str.charAt(i))
				k++;
			if(k==n-1)
			{
				k=p[k];
				System.out.println("ƥ��ɹ���ƫ����Ϊ"+(i-n+1));
			}
		}
	}
	
	public static int[] computePrefix(String pattern,int[] p)
	{
		int n=pattern.length();
		p[0]=-1;  //��ʼ��   ��һ��Ԫ��ƥ��ʱ ���ǰ׺�������׺�ĳ���Ϊ0
		int k=-1;//k+1����һ����Ҫƥ����ַ�  ���±��ʾ��
		for(int i=1;i<n;i++)
		{
			while(k>-1&&pattern.charAt(k+1)!=pattern.charAt(i))
				k=p[k];
			if(pattern.charAt(k+1)==pattern.charAt(i))
				k++;  
			p[i]=k;  //k+1�ǳ���  ������һ��ƥ����ַ��±���k+1
		}
		return p;
	}
}