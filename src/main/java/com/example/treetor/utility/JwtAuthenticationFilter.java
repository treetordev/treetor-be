package com.example.treetor.utility;

import com.example.treetor.service.CustomUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Component
@Log4j2
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
    private TokenGenerator jwtHelper;


    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected  void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, IOException {

    	ContentCachingRequestWrapper contentCachingRequestWrapper= new ContentCachingRequestWrapper(request);
    	ContentCachingResponseWrapper contentCachingResponseWrapper = new ContentCachingResponseWrapper(response);
    	long startTime = System.currentTimeMillis();
    	
        String requestHeader = request.getHeader("Authorization");
        //Bearer 2352345235sdfrsfgsdfsdf

        String userKey = null;
        String token = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer")) {
            token = requestHeader.substring(7);
            try {

            	userKey = this.jwtHelper.getUsernameFromToken(token);
                RequestContextHolder.currentRequestAttributes().setAttribute("userKey", userKey, RequestAttributes.SCOPE_REQUEST);


            } catch (IllegalArgumentException e) {
                //log.info("Illegal Argument while fetching the username !!");
                e.printStackTrace();
            } catch (ExpiredJwtException e) {
                //log.info("Given jwt token is expired !!");
                e.printStackTrace();
            } catch (MalformedJwtException e) {
                //log.info("Some changed has done in token !! Invalid Token");
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();

            }


        } else {
            System.out.println("Invalid Header Value !! ");
        }


        //
        if (userKey != null && SecurityContextHolder.getContext().getAuthentication() == null) {


            //fetch user detail from username
        	UserDetails userDetails =  this.userDetailsService.loadUserByUserKey(userKey);
            Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);
            if (validateToken) {

                //set the authentication
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);


            } else {
                //log.info("Validation fails !!");
            }


        }
        String requestId = request.getHeader("Requestid");
        String sessionId = request.getHeader("Sessionid");
        if (requestId != null) {
            RequestContextHolder.currentRequestAttributes().setAttribute("Requestid", requestId, RequestAttributes.SCOPE_REQUEST);
        }
        if (sessionId != null) {
            RequestContextHolder.currentRequestAttributes().setAttribute("Sessionid", sessionId, RequestAttributes.SCOPE_REQUEST);
        }
        filterChain.doFilter(contentCachingRequestWrapper, contentCachingResponseWrapper);
        long timeTaken = System.currentTimeMillis() - startTime;
        String requestBody = getStringValue(contentCachingRequestWrapper.getContentAsByteArray(), request.getCharacterEncoding());
        String responseBody = getStringValue(contentCachingResponseWrapper.getContentAsByteArray(), response.getCharacterEncoding());

      /*  if(request.getRequestURI().contains("getAllPatients")||request.getRequestURI().contains("/file/download")||request.getRequestURI().contains("getAllAttorney")||request.getRequestURI().contains("getAllProviderDetails"))
        {
            String truncatedResponseBody = (responseBody != null && !responseBody.isEmpty())
                    ? responseBody.substring(0, Math.min(150, responseBody.length())) + "...truncated"
                    : "Empty Response";
            //log.info("REQUEST LOG : METHOD : {} ,URI : {} , REQUESTID :{} ,SESSIONID:{} ,REQUEST BODY : {} ,RESPONSE BODY : {} ,RESPONSE CODE : {} , RESPONSE TIME :{}",
            //        request.getMethod(), request.getRequestURI(), RequestContextUtil.getRequestId(), RequestContextUtil.getSessionId(), requestBody, truncatedResponseBody, response.getStatus(), timeTaken);
        }
        else {
            //log.info("REQUEST LOG : METHOD : {} ,URI : {} , REQUESTID :{} ,SESSIONID:{} ,REQUEST BODY : {} ,RESPONSE BODY : {} ,RESPONSE CODE : {} , RESPONSE TIME :{}",
            //        request.getMethod(), request.getRequestURI(), RequestContextUtil.getRequestId(), RequestContextUtil.getSessionId(), requestBody, responseBody, response.getStatus(), timeTaken);
        }*/
        contentCachingResponseWrapper.copyBodyToResponse();
    }
    
	private String getStringValue(byte[] contentAsByteArray, String characterEncoding) {
		// TODO Auto-generated method stub
		try {
			return new String(contentAsByteArray, 0, contentAsByteArray.length, characterEncoding);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}// TODO Auto-generated method stub


}
