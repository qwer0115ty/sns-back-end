package com.boot.service;

import java.io.File;

import com.boot.model.AwsS3File;

public interface AwsS3Service {
	public AwsS3File uploadFile(File file, AwsS3File awsS3File) throws Exception;
	public AwsS3File getFile(int seq);
	public int deleteFile(AwsS3File awsS3File) throws Exception;
}
