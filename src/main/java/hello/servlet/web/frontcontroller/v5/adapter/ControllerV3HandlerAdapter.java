package hello.servlet.web.frontcontroller.v5.adapter;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v5.MyHandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerV3HandlerAdapter implements MyHandlerAdapter {

    @Override
    public boolean supports(Object handler) {
        //MemberXXXXControllerV3
        return (handler instanceof ControllerV3); // V3로 구현한거인지 확인
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        ControllerV3 controller = (ControllerV3) handler; // supports로 미리 V3인지 아닌지 거르니까 과감하게 캐스팅하자.

        Map<String, String> paramMap = getParamMap(request);
        ModelView mv = controller.process(paramMap);

        return mv;
    }

    private static Map<String, String> getParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterMap().forEach((paramName, ParamValue)->paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }

}
