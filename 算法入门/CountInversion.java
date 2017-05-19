//求逆序对数量
public class CountInversion
{
	public static void main(String[] args)
	{
		//有缺陷 当arr={7,6,5,4,3,2,2} 
		//输出结果为1 1 4 1 0（正确值应该为1） 12 19 
		int[] arr=new int[] {7,6,5,4,3,2,2};
		System.out.println(countInversions(arr,0,6));
		
	}
	public static int countInversions(int[] arr,int p,int r)
	{
		int inversions=0;
		if(p<r)
		{
			int q=(p+r)/2;
			inversions=inversions+countInversions(arr,p,q);
			inversions=inversions+countInversions(arr,q+1,r);
			inversions=inversions+mergeInversion(arr,p,q,r);
		}
		return inversions;
	}
	public static int mergeInversion(int[] arr,int p,int q,int r)
	{
		int low1=p;
		int low2=q+1;
		int length=r-p+1;
		int[] arr1=new int[length];
		int inversions=0;
		boolean COUNT=false;
		for(int i=0;i<length;i++)
		{
			if(COUNT==false&&low1<=q&&low2<=r&&arr[low1]>arr[low2])
			{
				inversions=inversions+q-low1+1;
				COUNT=true;
			}
			//如果取大于等于 结果会出错
			if((low1<=q&&low2<=r&&arr[low1]>arr[low2])||low1>q)
			{
				arr1[i]=arr[low2++];
			}
			else
			{
				arr1[i]=arr[low1++];
			}
			COUNT=false;
		}
		System.arraycopy(arr1,0,arr,p,length);
		System.out.println(inversions);
		return inversions;
	}
}