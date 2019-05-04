package org.woodwhales.king.druid;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DruidConfig {
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        bean.addInitParameter("allow", "127.0.0.1");
        // IP黑名单
        bean.addInitParameter("deny", "192.168.1.100");
        //　登录帐号密码
        bean.addInitParameter("loginUsername", "druid");
        bean.addInitParameter("loginPassword", "123456");
        // 是否可以重置数据
        bean.addInitParameter("resetEnable", "false");

        return bean;
    }

//    @Bean("dataSource")
//    @Primary
//    public DataSource dataSource() {
//        return DataSourceBuilder.create().type(com.alibaba.druid.pool.DruidDataSource.class).build();
//    }
//
//    @Bean
//    public SqlSessionFactoryBean sqlSessionFactoryBean(@Qualifier("dataSource") DataSource dataSource) throws IOException {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dataSource);
//        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        bean.setMapperLocations(resolver.getResources("classpath:/mapper/*.xml"));
//        return bean;
//    }

    @Bean
    public FilterRegistrationBean druidStatFilter2(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        //添加过滤规则
        filterRegistrationBean.addUrlPatterns("/*");
        //添加不需要忽略的格式信息
        filterRegistrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid2/*");
        return filterRegistrationBean;
    }
}
