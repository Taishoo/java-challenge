package jp.co.axa.apidemo.configs;

import jp.co.axa.apidemo.interceptors.ClientCredentialInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final ClientCredentialInterceptor clientCredentialInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Default interceptor
        registry.addInterceptor(clientCredentialInterceptor).addPathPatterns("/api/**");
    }

}
