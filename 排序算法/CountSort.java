/* 值的范围是0~k
 ** 复杂度是O（k+n）k<n
 ** 稳定 相同元素的次序在输出数组中位置不变
 */
public class CountSort
{
	public static void main(String[] args)
	{
		int[] arr={1,5,3,0,2,4};
		int[] arr1=new int[arr.length];
		arr1=countSort(arr,5);
		for(int i:arr1)
		{
			System.out.print(i+" ");
		}
	}
	private static int[] countSort(int[] arr,int max)
	{
		int[] arr1=new int[max+1];
		int[] arr2=new int[arr.length];
		for(int i=0;i<arr.length;i++)
			arr1[arr[i]]+=1;
		for(int i=1;i<=max;i++)
			arr1[i]+=arr1[i-1];
		for(int i:arr1)
		{
			System.out.print(i+" ");
		}
		System.out.println();
		for(int i=arr.length-1;i>=0;i--)
		{
			arr2[arr1[arr[i]]-1]=arr[i];// 必须要减1 <=
			arr1[arr[i]]--;
		}
		return arr2;
	}
}