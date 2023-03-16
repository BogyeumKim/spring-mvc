package hello.servlet.basic.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/*
* 1. 파라미터 전송 기능
* http://localhost:8080/request-param?username=heelo&age=20
*/
@WebServlet(name = "requestParamServlet",urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("[전체 파라미터 조회] - start");

        request.getParameterMap().forEach((paramName, paramValues) -> {
            System.out.print(paramName + "=");
            for (String paramValue : paramValues) {
                System.out.print(paramValue + " ");
            }
            System.out.println();
        });

        System.out.println("[전체 파라미터 조회] - end");

        System.out.println("[단일 파라미터 조회]");
        String useranme = request.getParameter("username");
        String age = request.getParameter("age");

        System.out.println("useranme = " + useranme);
        System.out.println("age = " + age);
        System.out.println();

        System.out.println("[이름이 같은 복수 파라미터 조회]");
        String[] usernames = request.getParameterValues("username"); // 중복으로 보내는데 위에 단일 getParameter쓰면 맨첫번째값 반환
        for (String s : usernames) {
            System.out.println("name = " + s);
        }

        response.getWriter().write("ok");
    }
}
