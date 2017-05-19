//需找第i小的数  时间复杂度的期望是O（n）
//感觉应该要求元素互异
import java.util.Random;
public class FindiMin
{
	public static void main(String[] args)
	{
		int[] arr={1,2,3,4,13,6,7,0,9};
		System.out.println(findiMin(arr,0,8,3));
		System.out.println(findiMin(arr,0,8,6));
	}
	private static int partition(int[] arr,int low,int high)
	{
		Random ran=new Random();
		int key=arr[ran.nextInt(high-low+1)+low];
		exchange(arr,ran.nextInt(high-low+1)+low,high);
		int i=low-1;
		for(int j=low;j<high;j++)
		{
			if(arr[j]<key)
			{
				i++;
				exchange(arr,i,j);
			}
		}
		exchange(arr,i+1,high);
		return i+1;
	}
	private static int findiMin(int[] arr,int low,int high,int i)
	{
		if(low==high)
			return arr[low];
		int q=partition(arr,low,high);
		int k=q-low+1;
		if(i==k)
			return arr[q];
		if(i<k)
			return findiMin(arr,low,q-1,i);
		if(i>k&&q+1<=high)
			return findiMin(arr,q+1,high,i-k);
		if(i>k&&q==high)
			return arr[high];
		return 0;
	}
	private static void  exchange(int[] arr,int a,int b)
	{
		int temp;
		temp=arr[a];
		arr[a]=arr[b];
		arr[b]=temp;
	}
}