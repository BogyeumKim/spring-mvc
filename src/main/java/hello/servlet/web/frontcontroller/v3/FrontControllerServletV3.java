package hello.servlet.web.frontcontroller.v3;


import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletv3", urlPatterns = "/front-controller/v3/*") // v3 하위에 모든파일은 여기로 들어와짐
public class FrontControllerServletV3 extends HttpServlet {

    private Map<String, ControllerV3> controllerv3Map = new HashMap<>();

    public FrontControllerServletV3() {
        controllerv3Map.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerv3Map.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerv3Map.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletv3.service");

        String requestURI = request.getRequestURI();

        /* Controllerv3 controller = new Member~~~~Controllerv3(); */
        ControllerV3 controller = controllerv3Map.get(requestURI); // URI의 키값으로 get가져와서 value를 반환 -> new Member~~~ 가 반환됨

        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Map<String, String> paramMap = getParamMap(request); // 현재 접속한 URI 파라미터를 따로 뽑아서 Map에 담는 메서드
        ModelView mv = controller.process(paramMap); // 위에서 뽑아온 현재 접속 URI 파라미터 new-form or save or members ... 를 넣음

        String viewName = mv.getViewName(); // 논리이름 new-form , save , members ...

        MyView view = viewResolver(viewName); // 논리이름을 web-inf/views/ 논리이름 .jsp로 반환
        view.render(mv.getModel(),request,response);
    }

    private static MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private static Map<String, String> getParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterMap().forEach((paramName, ParamValue)->paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}
