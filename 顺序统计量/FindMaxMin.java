//算法比较次数 （3/2）*n
public class FindMaxMin
{
	public static void main(String[] args)
	{
		int[] arr={1,4,5,2,5,8,29,3,0,-3};
		int[] arr1=findMaxMin(arr);
		System.out.println(arr1[0]);
		System.out.println(arr1[1]);
	}
	private static int[] findMaxMin(int[] arr)
	{
		int max=arr[0];
		int min=arr[0];
		int tempmax;
		int tempmin;
		int[] arr1=new int[2];
		for(int i=1;i<arr.length;i+=2)
		{
			if(i==arr.length-1)
			{
				tempmax=arr[i];
				tempmin=arr[i];
			}
			else if(arr[i]>arr[i+1])
			{
				tempmax=arr[i];
				tempmin=arr[i+1];
			}	
			else
			{
				tempmax=arr[i+1];
				tempmin=arr[i];
			}
			if(tempmax>max)
				max=tempmax;
			if(tempmin<min)
				min=tempmin;
		}
		arr1[0]=max;
		arr1[1]=min;
		return arr1;
		
	}
}