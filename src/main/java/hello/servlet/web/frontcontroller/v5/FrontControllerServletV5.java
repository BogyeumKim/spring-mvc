package hello.servlet.web.frontcontroller.v5;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontcontroller.v4.ControllerV4;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name ="frontControllerServletV5" ,urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    /*private Map<String, ControllerV3> controllerv3Map = new HashMap<>(); V3 코드와 차이점은 아래에선 Object로 모든 타입을 받아옴*/
    private final Map<String, Object> handlerMappingMap = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();


    public FrontControllerServletV5() {
        initHandlerMappingMap();
        initHandlerAdapters();
    }

    private void initHandlerMappingMap() {
        /*controllerv3Map.put("/front-controller/v3/members/new-form", new MemberFormControllerV3()); V3 버전예시*/
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());
    }

    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        Object handler = handlerMappingMap.get(requestURI); // new Member XXX ControllerV3 반환

        if (handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        MyHandlerAdapter adapter = getHandlerAdapter(handler); // 핸들러 어댑터가져옴 MemberXXXXControllerV3 -> ControllerV3HandlerAdapter

        ModelView mv = adapter.handle(request, response, handler); // 핸들 호출(MemberXXXXControllerV3 컨트롤러의 프로세스호출)

        String viewName = mv.getViewName();
        MyView view = viewResolver(viewName);

        view.render(mv.getModel(),request,response);
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        // MemberXXXXContorllerV3
        for (MyHandlerAdapter adapter : handlerAdapters) {
            if (adapter.supports(handler)) { // true , flase
                return adapter; // adapter -> MemberXXXContorller 반환
            }
        }
        throw new IllegalArgumentException("handler adapter를 찾을 수 없음 : " + handler);
    }

    private static MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }
}
