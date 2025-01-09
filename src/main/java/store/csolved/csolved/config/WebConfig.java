package store.csolved.csolved.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import store.csolved.csolved.auth.etc.LoginRequestInterceptor;
import store.csolved.csolved.auth.etc.LoginUserArgumentResolver;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer
{
    private final LoginUserArgumentResolver loginUserArgumentResolver;
    private final LoginRequestInterceptor loginRequestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        // @LoginRequested가 있는 컨트롤러는 로그인 체크합니다.
        registry.addInterceptor(loginRequestInterceptor);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers)
    {
        // @LoginUser가 있는 User타입 변수에 현재 로그인한 회원정보를 바인딩합니다.
        resolvers.add(loginUserArgumentResolver);
    }
}
