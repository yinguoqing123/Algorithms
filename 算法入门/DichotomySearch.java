public class DichotomySearch
{
	public static void main(String[] args)
	{
		//������ֶ����ֵͬ���������б仯
		int[] arr=new int[] {1,2,3,4,4,6,7,8,9};
		dichotomySearch(arr,0,8,5);
	}
	public static void dichotomySearch(int[] arr,int first,int last,int key)
	{
		if(first<last)
		{
			int middle=(first+last)/2;
			if(arr[middle]>key)
				{
					System.out.println("ִ��һ��");
					dichotomySearch(arr,first,middle,key);
				}
			else 
				if(arr[middle]<key)
				{
					System.out.println("ִ��һ��");
					dichotomySearch(arr,middle+1,last,key);
				}
				else if(arr[middle]==key)
				{
					System.out.println("ִ��һ��");
					System.out.println("�����е�����ֵ"+middle); 
				}
		}
		//�������޵ݹ������ȥ
		else
		{
			if(arr[first]==key)
			{
				System.out.println("ִ��һ��");
				System.out.println("�����е�����ֵ"+first); 
			}
			else
			{
				System.out.println("ִ��һ��");
				System.out.println("����");
			}
		}
	}
}