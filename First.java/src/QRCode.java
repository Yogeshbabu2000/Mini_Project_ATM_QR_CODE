// Java code to read the QR code

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.EncodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRCode {
	
	// Driver code
	public static void main(String[] args)throws WriterException, IOException,NotFoundException
	{

		// Path where the QR code is saved
		String path = "C:\\Users\\Admin\\Desktop\\YOGESH\\yogesh.jpg";

		Map<EncodeHintType, ErrorCorrectionLevel> hashMap= new HashMap<EncodeHintType,ErrorCorrectionLevel>();

		hashMap.put(EncodeHintType.ERROR_CORRECTION,ErrorCorrectionLevel.L);

		System.out.println("QRCode output:"+ Main_Innovative.readQR(path,"UTF-8" , hashMap));
	}

}