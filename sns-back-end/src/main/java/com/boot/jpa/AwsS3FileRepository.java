package com.boot.jpa;

import org.springframework.data.repository.CrudRepository;

import com.boot.model.AwsS3File;

public interface AwsS3FileRepository extends CrudRepository<AwsS3File, Integer> {
}
