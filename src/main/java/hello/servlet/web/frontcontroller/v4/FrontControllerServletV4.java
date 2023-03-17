package hello.servlet.web.frontcontroller.v4;


import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV4", urlPatterns = "/front-controller/v4/*") // v4 하위에 모든파일은 여기로 들어와짐
public class FrontControllerServletV4 extends HttpServlet {

    private Map<String, ControllerV4> controllerv4Map = new HashMap<>();

    public FrontControllerServletV4() {
        controllerv4Map.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
        controllerv4Map.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
        controllerv4Map.put("/front-controller/v4/members", new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletv4.service");

        String requestURI = request.getRequestURI();

        /* Controllerv4 controller = new Member~~~~Controllerv4(); */
        ControllerV4 controller = controllerv4Map.get(requestURI); // URI의 키값으로 get가져와서 value를 반환 -> new Member~~~ 가 반환됨

        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Map<String, String> paramMap = getParamMap(request); // 현재 접속한 URI 파라미터를 따로 뽑아서 Map에 담는 메서드

        Map<String, Object> model = new HashMap<>(); /* 추가 */
        String viewName = controller.process(paramMap, model); /* V3에서 변경 */


        MyView view = viewResolver(viewName); // 논리이름을 web-inf/views/ 논리이름 .jsp로 반환
        view.render(model,request,response);
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
