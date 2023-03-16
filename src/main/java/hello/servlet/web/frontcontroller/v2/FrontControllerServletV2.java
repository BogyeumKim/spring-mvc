package hello.servlet.web.frontcontroller.v2;


import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.controller.MemberFormControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberListControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberSaveControllerV2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV2", urlPatterns = "/front-controller/v2/*") // v2 하위에 모든파일은 여기로 들어와짐
public class FrontControllerServletV2 extends HttpServlet {

    private Map<String, ControllerV2> controllerv2Map = new HashMap<>();

    public FrontControllerServletV2() {
        controllerv2Map.put("/front-controller/v2/members/new-form", new MemberFormControllerV2());
        controllerv2Map.put("/front-controller/v2/members/save", new MemberSaveControllerV2());
        controllerv2Map.put("/front-controller/v2/members", new MemberListControllerV2());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletv2.service");

        String requestURI = request.getRequestURI();

        /* Controllerv2 controller = new Member~~~~Controllerv2(); */
        ControllerV2 controller = controllerv2Map.get(requestURI); // URI의 키값으로 get가져와서 value를 반환 -> new Member~~~ 가 반환됨

        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }


        MyView view = controller.process(request, response); // new Myview("/WEB-INF/views/~~~.jsp");
        view.render(request,response);
    }
}
