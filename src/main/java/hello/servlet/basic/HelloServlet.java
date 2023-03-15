package hello.servlet.basic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "helloServlet" ,urlPatterns="/hello") // 서블릿 애노테이션 , name 무관하며 urlPatterns은 get매핑주소
public class HelloServlet extends HttpServlet { // 서블릿 이용시 상속받는 HttpServlet
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("HelloServlet.service");
        System.out.println("request = " + request);
        System.out.println("response = " + response);

        String username = request.getParameter("username"); // 요청받은 http 쿼리 파라미터와 매칭
        System.out.println("username = " + username);

        response.setContentType("text/plagin");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("hello "+ username); // 리스판스바디에 뿌려줌
    }
}
