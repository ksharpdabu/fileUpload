package demo1;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

/**
 * Created by AlexY on 2016/6/27.
 */
public class TestServlet extends HttpServlet {


//    保存上传文件的路径
    private final String UPLOAD_DIRECTORY = "D:/Files/";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        resp.setContentType("text/html");

        PrintWriter out = resp.getWriter();


        out.println("Hello<br>");

        boolean isMultipartContent = ServletFileUpload.isMultipartContent(req);

        if (!isMultipartContent){
            out.println("You are not trying to upload<br>");
        }


        out.println("You are trying to upload <br>");

        FileItemFactory factory = new DiskFileItemFactory();

        ServletFileUpload upload = new ServletFileUpload(factory);

        //		upload.setSizeMax(MAX_UPLOAD_IN_MEGS * 1024 * 1024);
//        创建进程监听器
        TestProgressListener testProgressListener = new TestProgressListener();

//        关键设置进程监听器
        upload.setProgressListener(testProgressListener);

//         将进程监听器保存到session中，方便后面取出来
        HttpSession session = req.getSession();

        session.setAttribute("testProgressListener",testProgressListener);

//        如果存放的目录不存在，就创建
        File dir = new File(UPLOAD_DIRECTORY);
        if ( !dir.exists()){

            dir.mkdirs();
        }





        try {
            List<FileItem>  items = upload.parseRequest(req);


            Iterator<FileItem> iterator = items.iterator();

            if (!iterator.hasNext()){

                out.println("No fields found");
            }

            out.print("<table border=\"1\">");

            while (iterator.hasNext()){
                out.print("<tr>");

                FileItem fileItem = iterator.next();

//                判断当前的fileItem是表单还是文件
                boolean isFormField = fileItem.isFormField();


                if (isFormField){
//                    fileItem.getFieldName()，打印jsp中form字段，即

//                    fileItem.getString()，打印的字段的值
                    out.println("<td>regular form field</td><td>FIELD NAME: " + fileItem.getFieldName() +
                            "<br/>STRING: " + fileItem.getString()
                    );
                    out.println("</td>");
                }else {

//                    将文件保存到指定的目录
                    fileItem.write(new File(UPLOAD_DIRECTORY,fileItem.getName()));

                    //fileItem.getFieldName()  , 获取jsp中表单上传文件时候的字段名。这里就是 “file”。
//                    fileItem.getString()   打印字节流，只是为了测试。
                    //fileItem.getName()，获取文件名
                    //fileItem.getContentType() 获取文件的类型
                    //fileItem.getSize() ，获取文件的大小
                    //fileItem.toString() ，将fileItem的所有信息打印出来
                    out.println("<td>file form field</td><td>FIELD NAME: " + fileItem.getFieldName() +
//							"<br/>STRING: " + fileItem.getString() +
                                    "<br/>NAME: " + fileItem.getName() +
                                    "<br/>CONTENT TYPE: " + fileItem.getContentType() +
                                    "<br/>SIZE (BYTES): " + fileItem.getSize() +
                                    "<br/>TO STRING: " + fileItem.toString()
                    );
                    out.println("</td>");
                }


                out.println("</tr>");

            }


        } catch (FileUploadException e) {
            out.println("Error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
