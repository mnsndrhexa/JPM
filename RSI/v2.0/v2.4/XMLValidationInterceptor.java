package $$PACKAGE_NAME$$;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
$$IMPORTSTMT$$

@Component
public class XMLValidationInterceptor implements HandlerInterceptor {
	
$$AUTOWIRED$$

    // Request is intercepted by this method before reaching the Controller
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //* Business logic just when the request is received and intercepted by this interceptor before reaching the controller
        try {
			if("POST".equalsIgnoreCase(request.getMethod())&&request.getContentType()!=null&&request.getContentType().contains("application/xml")){
				StringBuilder xmlContent = new StringBuilder();
				try(BufferedReader reader=request.getReader()){
					String line=null;
					while((line=reader.readLine())!=null){
						xmlContent.append(line);
					}
				}
				
				RSIRequest rsiRequest = parseXmlToInputObject(xmlContent.toString());
			}
 $$RequestSetup$$
            System.out.println("1 - preHandle() : Before sending request to the Controller");
            System.out.println("Method Type: " + request.getMethod());
            System.out.println("Request URL: " + request.getRequestURI());
        }
        //* If the Exception is caught, this method will return false
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
	
	private RSIRequest parseXmlToInputObject(String xmlContent) throws JAXBException{
		JAXBContext context = JAXBContext.newInstance(RSIRequest.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		return (RSIRequest) unmarshaller.unmarshal(new StringReader(xmlContent));
	}

    // Response is intercepted by this method before reaching the client
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //* Business logic just before the response reaches the client and the request is served
        try {		
            System.out.println("2 - postHandle() : After the Controller serves the request (before returning back response to the client)");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // This method is called after request & response HTTP communication is done.
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //* Business logic after request and response is Completed
        try {
            System.out.println("3 - afterCompletion() : After the request and Response is completed");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
