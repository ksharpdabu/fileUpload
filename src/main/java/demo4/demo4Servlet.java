package demo4;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

/**
 * Created by AlexY on 2016/6/27.
 */
public class demo4Servlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // create file upload factory and upload servlet
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);



        List<FileItem> uploadedItems = null;
        FileItem fileItem = null;

        // Path to store file on local system
//        设置保存的目录，目录必须存在，如果不想手动创建，就自己加代码来自动创建，这里省略
        String   filePath = "D:\\files";

        try {
            // iterate over all uploaded files
            uploadedItems = upload.parseRequest(req);

            Iterator i = uploadedItems.iterator();

            while (i.hasNext()) {
                fileItem = (FileItem) i.next();

                if (fileItem.isFormField() == false) {
                    if (fileItem.getSize() > 0) {
                        File uploadedFile = null;
                        String myFullFileName = fileItem.getName(),
                                myFileName = "",
//                                有的浏览器，如opera会把文件在客户端上的路径一起作为文件名上传
//                                所以需要特别处理，分理处真正的文件名，这里要区分下是从unix/linux 还是windows上传的
                                slashType = (myFullFileName.lastIndexOf("\\") > 0) ? "\\" : "/";    // Windows or UNIX
                        int startIndex = myFullFileName.lastIndexOf(slashType);

                        // Ignore the path and get the filename
                        myFileName = myFullFileName.substring
                                (startIndex + 1, myFullFileName.length());

                        // Create new File object
                        uploadedFile = new File(filePath, myFileName);

                        // Write the uploaded file to the system
                        fileItem.write(uploadedFile);
                    }
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("success");

        out.close();

    }
}
