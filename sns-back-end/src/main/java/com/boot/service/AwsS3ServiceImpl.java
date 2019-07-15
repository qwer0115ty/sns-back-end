package com.boot.service;

import java.io.File;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.boot.config.AmazonS3Prop;
import com.boot.jpa.AwsS3FileRepository;
import com.boot.model.AwsS3File;

@Service
public class AwsS3ServiceImpl implements AwsS3Service {
	@Autowired
	private AmazonS3Prop s3Prop;

	@Autowired
	private AwsS3FileRepository awsS3FileRepository;
	
	private AmazonS3 s3cClient() {
		BasicAWSCredentials awsCredentials = new BasicAWSCredentials(s3Prop.getAccessKey(), s3Prop.getSecretKey());
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.fromName(s3Prop.getRegion()))
				.withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();
		return s3Client;
	}

	@Transactional
	@Override
	public AwsS3File uploadFile(File file, AwsS3File asf) throws Exception {
		String saveFullPath = asf.getPath() + asf.getSaveFilename();
		
		s3cClient().putObject(s3Prop.getBucketName(), saveFullPath, file);
		String fullPath = s3cClient().getUrl(s3Prop.getBucketName(), saveFullPath).toString();
		
		asf.setFullPath(fullPath);
		asf = awsS3FileRepository.save(asf);
		
		return asf;
	}
	
	@Override
	public AwsS3File getFile(int seq) {
		return awsS3FileRepository.findOne(seq);
	}

	@Transactional
	@Override
	public int deleteFile(AwsS3File awsS3File) throws Exception {
		s3cClient().deleteObject(s3Prop.getBucketName(), awsS3File.getPath() + awsS3File.getSaveFilename());
		awsS3FileRepository.delete(awsS3File.getSeq());
		return 1;
	}
}
