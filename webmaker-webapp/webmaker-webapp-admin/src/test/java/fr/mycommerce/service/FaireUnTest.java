package fr.mycommerce.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

public class FaireUnTest {

	@Test
	public void unTest() throws IOException {
		uploadObject("centering-line-273419", "e-commerce-generic-bucket", "./REF001/001.jpg",
				"/home/njlg6500/Images/CarteIDENTITE.jpg");
	}

	/**
	 * 
	 * @param projectId The ID of your GCP project
	 * @param bucketName The ID of your GCS bucket
	 * @param objectName The ID of your GCS object
	 * @param filePath The path to your file to upload
	 * @throws IOException
	 */
	public static void uploadObject(String projectId, String bucketName, String objectName, String filePath)
			throws IOException {

		Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
		BlobId blobId = BlobId.of(bucketName, objectName);
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
		storage.create(blobInfo, Files.readAllBytes(Paths.get(filePath)));

		System.out.println("File " + filePath + " uploaded to bucket " + bucketName + " as " + objectName);
	}

}
