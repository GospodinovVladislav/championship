package bg.proxiad.demo.championship.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;

import org.springframework.web.multipart.MultipartFile;

public class PictureOperations {

	public static void deletePicture(String imageName,ServletContext context) {
		if (!imageName.equals("default.jpeg")) {
			String folderPath = context.getRealPath("/") + "\\images\\";
			File picture = new File(folderPath + imageName);
			picture.delete();
		}
	}

	public static void savePicture(String folderPath, String imageName, MultipartFile image) {

		try {
			// Initializing the streams
			InputStream inputStream = null;
			OutputStream outputStream = null;

			// If our file has data in it
			if (image.getSize() > 0) {
				// We are pointing the input stream to it
				inputStream = image.getInputStream();

				// Getting a new filePath with the input file name and we
				// are pointing the output stream there
				String filePath = folderPath + imageName;
				outputStream = new FileOutputStream(filePath);

				// Logging
				System.out.println("======New file entry=====");
				System.out.println(image.getOriginalFilename());
				System.out.println("=============");

				// We are getting the buffer ready for transmitting data
				int readBytes = 0;
				byte[] buffer = new byte[8192];
				while ((readBytes = inputStream.read(buffer, 0, 8192)) != -1) {
					System.out.println("===Process====");
					outputStream.write(buffer, 0, readBytes);
				}

				// Closing the streams
				outputStream.close();
				inputStream.close();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
