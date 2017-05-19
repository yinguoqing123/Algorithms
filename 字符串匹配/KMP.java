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
		int[] p=new int[n];  //p中存放匹配的长度-1，也就是下次匹配的下标值=p[i]+1
		computePrefix(pattern,p);
		int k=-1;  //初始化  下一次要匹配的字符下标是 k+1
		for(int i=0;i<m;i++)
		{
			while(k>-1&&pattern.charAt(k+1)!=str.charAt(i))
				k=p[k];
			if(pattern.charAt(k+1)==str.charAt(i))
				k++;
			if(k==n-1)
			{
				k=p[k];
				System.out.println("匹配成功，偏移量为"+(i-n+1));
			}
		}
	}
	
	public static int[] computePrefix(String pattern,int[] p)
	{
		int n=pattern.length();
		p[0]=-1;  //初始化   第一个元素匹配时 最长真前缀是其真后缀的长度为0
		int k=-1;//k+1是下一个将要匹配的字符  （下标表示）
		for(int i=1;i<n;i++)
		{
			while(k>-1&&pattern.charAt(k+1)!=pattern.charAt(i))
				k=p[k];
			if(pattern.charAt(k+1)==pattern.charAt(i))
				k++;  
			p[i]=k;  //k+1是长度  所以下一次匹配的字符下标是k+1
		}
		return p;
	}
}