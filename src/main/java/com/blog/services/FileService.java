package com.blog.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
 public String uploadImage(String path, MultipartFile file) throws IOException;
 // method to serve file 
 public InputStream getResource(String path, String fileNmae) throws FileNotFoundException;
}
