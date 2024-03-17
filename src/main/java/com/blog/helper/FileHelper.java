package com.blog.helper;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Component;

@Component
public class FileHelper {
	 public  File createTempDir(String name) throws IOException {
		    File tempDir = File.createTempFile(name, "");
		    if (!(tempDir.delete())) {
		      throw new IOException("could not delete" + tempDir.getAbsolutePath());
		    }

		    if (!(tempDir.mkdir())) {
		      throw new IOException("could not create" + tempDir.getAbsolutePath());
		    }
		    tempDir.deleteOnExit();
		    return tempDir;
		  }

}
