package com.blog.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blog.helper.FileHelper;
import com.blog.services.FileService;

@Service
public class FileServiceImpl implements FileService {
	
	@Autowired
	FileHelper fileHelper;

	@Override // we will retrive the data from file and save it in path
	public String uploadImage(String path, MultipartFile file) throws IOException {
		// original file name
		String name = file.getOriginalFilename();
		// creating random filename to avoid mane clash -->
		String randomID = UUID.randomUUID().toString();
		String name1 = randomID.concat(name.substring(name.lastIndexOf('.')));

		// full path of file where to store -->
		String filePath = path + File.separator + name1;
       
		
		// for testing-->
		File createTempDir = fileHelper.createTempDir(name1);
		
		// create image folder if not created -->
		File f = new File(path);
		if (!f.exists()) {
			f.mkdir();
		}
		else {
			f.delete();
		}

		// copy file and save it in full path
		Files.copy(file.getInputStream(), Paths.get(filePath));
		return name1;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		String fullPath = path + File.separator + fileName;
		InputStream inputStream = new FileInputStream(fullPath);
		return inputStream;
	}

}
