package com.woyaofenxiang.util;

import com.woyaofenxiang.entity.Userinfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.InetAddress;
import java.time.LocalDate;

/**
 * @author linziming
 * @create 2020-03-24 22:37
 */
@RestController
public class Util {

    @Value("${data.url}")
    String url;
    @Value("${server.port}")
    String port;
    @GetMapping("/getimage")
    public void getimage(HttpServletRequest request , HttpServletResponse response) throws IOException {

        String path=request.getParameter("url");
        //读取路径下面的文件
        File file = new File(path);
        //读取指定路径下面的文件
        InputStream in = new FileInputStream(file);
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        //创建存放文件内容的数组
        byte[] buff =new byte[1024];
        //所读取的内容使用n来接收
        int n;
        //当没有读取完时,继续读取,循环
        while((n=in.read(buff))!=-1){
            //将字节数组的数据全部写入到输出流中
            outputStream.write(buff,0,n);
        }
        //强制将缓存区的数据进行输出
        outputStream.flush();
        //关流
        outputStream.close();
        in.close();
    }
    @PostMapping("/uploadimage")
    public Resp uploadimage(HttpServletRequest request, @RequestParam("file") MultipartFile[] files)throws IOException {
        Resp resp=new Resp();
        String url=request.getScheme()+"://"+ this.url+":"+port+"/getimage?url=";
        String basePath = "c://shangchuanwenjian/" + request.getParameter("uid") + "/" + LocalDate.now().toString() + "/" + Uuid.getUuid();
        File desFile = new File(basePath);
        String iurl=url+basePath;
        if (!desFile.getParentFile().exists()) {
            desFile.mkdirs();
        }
        try {
            files[0].transferTo(desFile);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        resp.setCode(10014);
        resp.setData(iurl);
        resp.setMessage("图片上传成功");
        return resp;
    }
    public static boolean checkFirst(String fstrData) {
        char c = fstrData.charAt(0);
        if (((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
            return true;
        } else {
            return false;
        }
    }
}
