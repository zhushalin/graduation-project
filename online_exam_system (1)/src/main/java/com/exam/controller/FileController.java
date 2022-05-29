package com.exam.controller;

import com.exam.common.Msg;
import com.exam.common.ResultUtil;
import com.exam.entity.LearningFile;
import com.exam.service.FileService;
import com.exam.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * 学习资料 Controller
 * @author
 * @since
 */
@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    FileService fileService;
    // 设置保存路径
    @Value("${upload.path}")
//    private static String savePath = "D:\\Projects\\online_exam_system-master\\learningMaterials\\";
    private String savePath ;
    /**
     * 上传学习资料
     * @return
     */
    @PostMapping("/upload")
    public Msg upload(@RequestParam MultipartFile file) {
        System.out.println(file);
        if (file == null){
            return ResultUtil.error(400,"上传文件为空，请重新上传");
        }else {
            //获取文件名后缀
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            //获取当前的时间戳作为保存的文件名
            String fileName = System.currentTimeMillis() + suffix;
            //生成新的文件路径
            String newFileUrl = savePath + fileName;
            File saveFile = new File(newFileUrl);
            //将文件上传到保存路径中
            try {
                file.transferTo(saveFile);
            } catch (IOException e) {
                e.printStackTrace();
                return ResultUtil.error(400, "上传出错");
            }
            return ResultUtil.success(fileName);
        }
    }
    /**
     * 下载学习资料
     * @return
     */
    @RequestMapping("/download")
    public void downloadLocal(String filename, HttpServletResponse response) throws IOException {
        String path = savePath + filename;
        // 读到流中
        InputStream inputStream = new FileInputStream(path);// 文件的存放路径
        response.reset();
        response.setContentType("application/octet-stream");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        //下载文件最大100M
        byte[] b = new byte[102400];
        int len;
        //从输入流中读取一定数量的字节，并将其存储在缓冲区字节数组中，读到末尾返回-1
        while ((len = inputStream.read(b)) > 0) {
            outputStream.write(b, 0, len);
        }
        inputStream.close();
    }

    /**
     * 获得所有学习资料
     * @return
     */
    @GetMapping("/getAll")
    public Msg getAll(){
        return fileService.getAll();
    }

    /**
     * 分页查询所有学习资料
     * @return
     */
    @GetMapping("/getAll/page")
    public Msg getAllByPage(int current){
        //查询所有
        List<LearningFile> list = fileService.list();
        //分页
        Page page = new Page(list.size(), current);
        page.build(list);
        return ResultUtil.success(page);
    }

    /**
     * 根据学习资料id查询
     * @param id
     * @return
     */
    @GetMapping("/getById/{id}")
    public Msg getFileById(@PathVariable(name = "id") int id){
        LearningFile file = fileService.getById(id);
        if (file == null){
            return ResultUtil.error(400, "没有查询到相应id的学习资料信息");
        }
        return ResultUtil.success(file);
    }

    /**
     * 添加学习资料
     * @param file
     * @return
     */
    @PostMapping("/add")
    public Msg add(@RequestBody LearningFile file){
        return fileService.add(file);
    }

    /**
     * 删除学习资料
     * @param id
     * @return
     */
    @GetMapping("/delete/{id}")
    public Msg delete(@PathVariable(name = "id") int id){
        LearningFile file = fileService.getById(id);
        if (file != null){
            String path = savePath + file.getName();
            File file1 = new File(path);
            if (!file1.exists()) { // 要删除的文件不存在
                System.out.println("文件" + file.getName() + "不存在，删除失败！");
            } else { // 要删除的文件存在
                if (file1.delete()) {
                    System.out.println("文件" + file.getName() + "删除成功！");
                }else {
                    System.out.println("文件" + file.getName() + "删除失败！");
                }
            }
        }
        return fileService.delete(id);
    }

    /**
     * 修改学习资料信息
     * @param file
     * @return
     */
    @PostMapping("/update")
    public Msg update(@RequestBody LearningFile file){
        return fileService.update(file);
    }
}
