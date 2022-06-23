package softuni.photostore.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import softuni.photostore.web.interceptor.LogInterceptor;
import softuni.photostore.web.interceptor.StatsInterceptor;
import softuni.photostore.web.interceptor.TestInterceptor;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

  private final StatsInterceptor statsInterceptor;
  private final TestInterceptor testInterceptor;
  private final LogInterceptor logInterceptor;

  public WebConfiguration(StatsInterceptor statsInterceptor, TestInterceptor testInterceptor, LogInterceptor logInterceptor) {
    this.statsInterceptor = statsInterceptor;
    this.testInterceptor = testInterceptor;
    this.logInterceptor = logInterceptor;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(statsInterceptor);
    registry.addInterceptor(testInterceptor);
    registry.addInterceptor(logInterceptor);
  }
}
