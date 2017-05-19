
public class LIS
{
	public static void main(String[] args)
	{
		int[] arr={1,-1,2,-3,4,-5,6,-7,8,5,12,4,-7,20};
		System.out.println(lis1(arr));
		System.out.println(lis2(arr));
	}
	private static int  lis1(int[] arr)
	{
		
		int[] len=new int[arr.length];
		for(int i=0;i<arr.length;i++)
		{
			len[i]=1; //默认初始化长度
			for(int j=0;j<i;j++)
			{
				if(arr[i]>arr[j]&&(len[j]+1)>len[i])//len[j]+1>len[i]的条件不可少
														 //程序是由j=0开始逐步循环的
				{
					len[i]=len[j]+1;
				}
			}
		}
		int max=len[0];
		for(int i=1;i<len.length;i++)
		{
			if(len[i]>max)
				max=len[i];
		}
		return max;
	}
	//你妈蛋  写的我头疼 看别人写的怎么看都看不懂 
	//时间复杂度降为 O(n*lg(n))
	private static int lis2(int[] arr)
	{
		int n=arr.length;
		int[] len=new int[n];
		int[] mMax=new int[n+1]; //长度为下标值的递增子序列中最大值中的最小值
		for(int i=0;i<n;i++)
		{
			len[i]=1;
		}
		mMax[0]=0;
		mMax[1]=arr[0];
		int mlen=1;
		for(int i=1;i<n;i++)
		{
			//针对三种情况分别更新
			if(arr[i]>mMax[mlen])
			{
				len[i]=mlen+1;
				mMax[len[i]]=arr[i];
				mlen++;
			}
			else if(arr[i]<mMax[1])
			{
				mMax[1]=arr[i];
			}
			else if(arr[i]>mMax[1]&&arr[i]<mMax[mlen])
			{
				int begin=1;
				int end=mlen;
				int k=(1+mlen)/2;
				while(!(arr[i]>=mMax[k-1]&&arr[i]<mMax[k]))
				{
					if(arr[i]>=mMax[k])
						begin=k;
					else
						end=k-1;
					k=(begin+end)/2;
						
				}
				mMax[k]=arr[i];
			}
		}
		return mlen;
	}
	
}