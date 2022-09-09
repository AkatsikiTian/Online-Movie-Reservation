package com.usst.myorder.service.Impl;

import com.usst.myorder.service.UploadService;
import com.usst.myorder.vo.Result;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
@Service
public class UploadServiceImpl implements UploadService {
    @Override
    public Result savePicture(MultipartFile file,String name) throws IOException {
        String originalFilenamePath = file.getOriginalFilename();
        String sysPath = ResourceUtils.getURL("classpath:").getPath();
        int index = sysPath.indexOf("target");
        sysPath = sysPath.substring(0,index)+"backgroundmanagement/public/" +"static/"+name;
        sysPath = sysPath.replace('/', '\\').substring(1,sysPath.length());
        String realPath = "/static/"+name+"/"+originalFilenamePath;
        File dest = new File(sysPath,originalFilenamePath);
        file.transferTo(dest);
        return Result.succ(realPath);
    }
}
