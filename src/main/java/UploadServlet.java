import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Iterator;
import java.util.List;

/**
 * Created by AlexY on 2016/6/26.
 */
public class UploadServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        DiskFileItemFactory factory = new DiskFileItemFactory();

//        HttpServletRequest的getRealPath()方法被抛弃了
//        String path = req.getRealPath("/upload");


        String path =  req.getServletContext().getRealPath("/upload");

        System.out.println( path);

//        设置临时文件夹
        factory.setRepository(new File(path));

//        文件大小超过1M，则直接写到硬盘上
        factory.setSizeThreshold(1024*1024);

        ServletFileUpload upload = new ServletFileUpload(factory);

        try {

//            从请求中解析文件 或 表单
            List<FileItem> items = upload.parseRequest(req);

            System.out.println(items.size());

            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory(1024*1024,new File(path));


            Iterator<FileItem> iterator =  items.iterator();


            while ( iterator.hasNext()){

                FileItem  item = iterator.next();

//                必须判断当前FileItem对象是表单还是真正的文件
                if ( !item.isFormField()){


                    processUploadedFile(item, path,req);


                }else {
                    processFormField(item, req);
                }


            }



        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


//        必须确保转发是在表单和文件都处理完成后
        req.getRequestDispatcher("fileUploadResult.jsp").forward(req,resp);

    }


//    处理上传的文件
    private void processUploadedFile(FileItem item, String  path,HttpServletRequest request) throws Exception {


//        获取表单中的字段名
        String fieldName = item.getFieldName();

//        获取文件名
        //getName会返回文件在客户端文件系统上的文件名（不含路径）。但是有的浏览器会将
        //上传的文件的文件名包含文件的路径。所以需要进行特殊处理
        String fileName = item.getName();
        int start =  fieldName.lastIndexOf("\\");
        String pureFileName = fileName.substring(start+1);

        request.setAttribute(fieldName, pureFileName);

//        获取文件类型
        String contentType = item.getContentType();

        boolean isInMemory = item.isInMemory();
        long sizeInBytes = item.getSize();

        /*
        然后是将文件写到磁盘上，有两种方式：
        1.使用字节流 ,FileItem调用 getInputStream()
        2. 直接调用FIleItem的write()方法
         */


//        方法二
//        item.write(new File(path,pureFileName));



//        方法一:使用stream的方式来保存文件，这样可以时刻查看进度
        OutputStream os = new FileOutputStream(new File(path,pureFileName));

        InputStream is = item.getInputStream();

        byte[] buffer = new byte[1024];
        int length = 0;

        while ( -1 != (length = is.read(buffer))){

            os.write(buffer);

            length = is.read(buffer,0,length);


        }


        is.close();
        os.close();


    }


//    处理表单
    private void processFormField(FileItem item, HttpServletRequest req) {

        String name = item.getFieldName();

        String value = item.getString();

        System.out.println(name + "=" +value);


        req.setAttribute(name, value);

    }
}
