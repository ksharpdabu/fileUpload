package demo1;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by AlexY on 2016/6/27.
 */
public class ProgressServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

         doPost(req,resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");

        PrintWriter out = resp.getWriter();

        HttpSession session = req.getSession();

        if ( null == session ){

//            为了安全
            out.print("Sorry, session is null");

            return;
        }


        TestProgressListener listener = (TestProgressListener) session.getAttribute("testProgressListener");

        if ( null == listener){

            out.print("Progress listener is null");

            return;
        }

//        输出上传进度百分比
        out.print(listener.getMessage());



    }
}
