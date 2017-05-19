public class FindMaxSubarray2
{
	public static void main(String[] args)
	{
		int[] arr={1,2,3,4,5,7,4,3,5,-3,6,-8};
		System.out.println(find(arr));
	}
	public static int find(int[] arr)
	{
		int thissum=0;
		int maxsum=0;
		for(int i=0;i<arr.length;i++)
		{
			thissum+=arr[i];
			if(thissum>maxsum)
			{
				maxsum=thissum;
			}
			if(thissum<0)
			{
				thissum=0;
			}
		}
		return maxsum;
	}
}





