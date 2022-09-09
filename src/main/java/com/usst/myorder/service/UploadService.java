package com.usst.myorder.service;

import com.usst.myorder.vo.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadService {
    Result savePicture(MultipartFile file,String name) throws IOException;
}
