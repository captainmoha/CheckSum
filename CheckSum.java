import java.util.Scanner;

public class CheckSum {
	
	/*
		Author : Mohamed Ali Farouk Mohamed, Third Year CS

		Description : This is a simulation of the checksum algorithm used in networking as an error checking tool
		it is usually implemented in the data link layer or transport layer of the OSI model

	*/

	public static void main(String[] args) {


		System.out.println("Enter data to send: ");
		Scanner in = new Scanner(System.in);
		String data = in.nextLine();

		System.out.println("Simulate damage ? y to damage, otherwise to leave intact");
		boolean isDamaged =  (in.nextLine().equals("y")) ? true : false;
		byte[] dataBytes = data.getBytes();
		String sum = sumBytes(dataBytes);
		String checksum = oneComplement(sum);

		System.out.println("sender sum: " + sum);
		System.out.println("checksum: " + checksum);

		boolean acceptData = checkSumTest(dataBytes, checksum, isDamaged);

		System.out.println("\n\n-------------------------------------------------\n\n");
		if (acceptData) {
			System.out.println("Data is intact accept transmission");
		}

		else {
			System.out.println("Data is damaged");
		}
		
		
	}


	public static String sumBytes(byte[] data) {

		String sum = "00000000";
		for (byte b : data) {
			String binaryStr = Integer.toBinaryString(b & 255 | 256).substring(1);
			sum = addBinary(sum, binaryStr);
			// System.out.println("in: " + sum + "  -  " + binaryStr);
		}

		return sum;
	}


	public static boolean checkSumTest(byte[] data, String checkSum, boolean simulateDamage) {

		if (simulateDamage) {
			data[0] = 23; 
		}
		String sum = sumBytes(data);
		System.out.println("receiver sum: " + sum);
		String sumChecksum = oneComplement(addBinary(sum, checkSum));


		return sumChecksum.equals("00000000");
	}

	public static String oneComplement(String inp) {

		String out = "";
		for (int i = 0; i < inp.length(); i++) {
			out += (inp.charAt(i) == '0') ? '1' : '0';
		}

		return out;
	}

	public static String addBinary(String b1, String b2) {

		String res = "";
		int carry = 0;

		// System.out.println("adding:  " + b1 + "  -  " + b2);
		for (int i = b1.length()-1; i > -1; i--) {
			int s = 0;
			if (carry == 0) {
				s = (b1.charAt(i) - '0') + (b2.charAt(i) - '0');
			}

			else {
				s = carry + (b1.charAt(i) - '0') + (b2.charAt(i) - '0');
				carry = 0;
			}
			
			// System.out.println("res:  " +res);

			if (s == 2) {
				s = 0;
				carry = 1;
			}

			else if (s == 3) {
				s = 1;
				carry = 1;
			}

			res += s;
		}

		res = new StringBuilder(res).reverse().toString();
		/*sum = res;
		res = oneComplement(res);*/

		// System.out.println("sumBin is " + res);
		if (carry == 1) {
			// System.out.println("carry out!");
			return addBinary(res, "00000001");
		}

		return res;
	}

}
