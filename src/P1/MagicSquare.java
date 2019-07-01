package P1;

import java.io.*;
import java.util.Scanner;

public class MagicSquare {
	static boolean ismagicSquare(String fileName,int rows)//1.�ж��Ƿ�Ϊ����
	{
		//�жϲ���Magic Square�����в���Ȼ���ڿո�
		String[] myword;
		String myline;
		File file = new File(fileName);
		Reader in1;
		boolean result = true;
		try {
			in1 = new FileReader(file);
			BufferedReader  bfr = new BufferedReader(in1);
			while((myline = bfr.readLine()) != null)
			{
				myword = myline.split("\t");
				if(rows != myword.length)
				{
					result = false;
				}
			}
			bfr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	static boolean isLegalMagicSquare(String fileName){
		File file = new File(fileName);
		int i = 0, j = 0, flag = 1, sum = 0, temp,n;
//		boolean result = true;
		try {
			int br[][] = new int[1000][1000];
			String myline = null;
			String[] myword;
			Reader in1 = new FileReader(file);
			BufferedReader  bfr = new BufferedReader(in1);
			//����ȡ�����ݷָ����,���ļ��ж������ݴ洢�ڶ�ά������bfr[][]
			while((myline = bfr.readLine()) != null)
			{
				myword = myline.split(".");
				if(myword.length>1)
				{
					System.out.print("�����д��ڸ�������");
					return false;
				}
				myword = myline.split("-");
				if(myword.length>1)
				{
					System.out.print("�����д��ڸ�����");
	                return false;
				}
				myword = myline.split("\t");
				for(String word: myword)
				{
					try{//����֮�䲢��ʹ�� \t �ָ�
						br[i][j] = Integer.valueOf(word).intValue();
						}catch(NumberFormatException e){
							System.out.print("���ݴ��ڷǷ�����,");
							return false;
						}
					j++;
				}
                j = 0;
				i++;
			}
			bfr.close();
            n = i;
            if(!ismagicSquare(fileName, n))//�ж��Ƿ���magicsquare
            {
            	System.out.print("������ Magic Square�Ķ��壬");
            	return false;
            }
    		for(i=0;i<n;i++) // ���һ�еĺ�sum
    		{ 
    			sum += br[0][i]; 
    		} 
    		//��һ��������sum,������
    		//����ÿ��
    		for (i = 1; i < n; i++) {
    			temp = 0;
    			for (j = 0; j < n; j++) 
    			{
    				temp += br[i][j];
    			}
    			if (sum != temp) {
    				flag = 0;
    			}
    		} 
    		//����ÿ��
    		temp = 0;
    		for (i = 0; i < n; i++) {
    			temp = 0;
    			for (j = 0; j < n; j++) 
    			{
    				temp += br[j][i];
    			}
    			if (sum != temp) {
    				flag = 0;
    			}
    		} 
    		// ���Խ��� 
    		temp = 0; 
    		for(i=0;i<n;i++) 
    		{ 
    			temp += br[i][i]; 
    		}
    		if (sum != temp) {
    			flag = 0;
    		} 
    		// ���Խ���
    		temp = 0;
    		for (i = 0; i < n; i++) {
    			temp += br[i][n - 1 - i];
    		}
    		if (sum != temp) {
    			flag = 0;
    		}
        } catch (IOException e) {
            e.printStackTrace();
        }
		if (flag == 1) {
			return true;
		} 
		else
			return false;
	}
	public static boolean generateMagicSquare(int n) {
		if(n <=0 || n % 2 == 0)
		{
			System.out.println("�������������������");
			return false;
		}
		int magic[][] = new int[n][n];//����һ��n*n�ľ���
		int row = 0, col = n / 2, i, j, square = n * n;
		//squareΪ����Ԫ��������
		for (i = 1; i <= square; i++) 
		{//���Ƚ�1��������һ�е��м䡣������������������ϵ������Խ��߰���Ȼ˳�����
			magic[row][col] = i;
			if (i % n == 0)//��Ҫ�ŵ�λ�����Ѿ����������������һ�������������Ͻ�ʱ����ǰҪ�ڷŵ����������ڽ�������λ�õ��·�
				
				row++;
			else {
				if (row == 0)//�ڵ��ﶥ��ʱ����һ������Ҫ���ڵ��У�����λ�þ��ǰѵ��е��������ϱ�һ��ʱ����Ӧ�÷��õ�λ��
					row = n - 1;
				else
					row--;
				if (col == (n - 1))//���������ұߵ�һ��ʱ����һ������Ҫ��������ߵ�һ���ϣ�
					col = 0;
				else
					col++;
			}
		}
		//�������� magic square д���ļ�\src\P1\txt\6.txt
		try {
			File file = new File("src\\P1\\txt\\6.txt");
			FileWriter writer = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(writer);
			try
			{
				for(i=0;i<n;i++)
				{
					for(j=0;j<n;j++)
					{
						out.write(magic[i][j]+"\t");
					}
					out.write("\r\n");
				}
				out.flush();
			}catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	public static void main(String[] args)  {
		boolean result[] = new boolean[5],result_1,result6;
		int i,n=0;
		String[] fileName = {"src/P1/txt/1.txt",
				"src/P1/txt/2.txt",
				"src/P1/txt/3.txt",
				"src/P1/txt/4.txt",
				"src/P1/txt/5.txt"};
		for(i=0;i<5;i++)
		{
			result[i] = isLegalMagicSquare(fileName[i]);
			if(result[i])
			{
				System.out.println((i+1)+".txt is a magicsquare.");
			}
			else {
				System.out.println((i+1)+".txt is not a magicsquare.");
			}
		}
		while(n != 11)
		{	
			Scanner input = new Scanner(System.in);
			System.out.println("������6.txt�Ĳ�����");
			try {
				n = input.nextInt();			//�������Ĳ�Ϊ���������׳�һ��InputMismatchException�쳣
			}catch(Exception e) {				
				System.out.println("������Ĳ������������������һ��������");
				input.next();						//��������
				}
			result_1 = generateMagicSquare(n);
			if(result_1)
			{
				result6 = isLegalMagicSquare("src/P1/txt/6.txt");
				if(result6)
				{
					System.out.println("6.txt is a magicsquare.");
				}
				else {
					System.out.println("6.txt is not a magicsquare.");
				}
			}
		}
	}
}
