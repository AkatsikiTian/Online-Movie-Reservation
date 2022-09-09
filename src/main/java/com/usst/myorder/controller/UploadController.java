package com.usst.myorder.controller;

import com.usst.myorder.service.UploadService;
import com.usst.myorder.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("upload")
public class UploadController {
    @Autowired
    UploadService uploadService;

    @PostMapping("savefilmPicture")
    private Result savefilmPicture(@RequestParam MultipartFile file) throws IOException {
        return uploadService.savePicture(file,"img");
    }

    @PostMapping("savenewsPicture")
    private Result savenewsPicture(@RequestParam MultipartFile file) throws IOException {
        return uploadService.savePicture(file,"news");
    }
}
