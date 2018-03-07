package com.isa.usersengine.filters;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

@WebFilter(
        filterName = "SalaryIncrementFilter",
        urlPatterns = {"/welcome-user"},
        initParams = {
                @WebInitParam(name = "minSalary", value = "100.0")
        })
public class SalaryIncrementFilter implements Filter {

    double minSalary = 0.0;

    @Override
    public void init(FilterConfig filterConfig) {
        minSalary = Double.parseDouble(filterConfig.getInitParameter("minSalary"));
    }

    @Override
    public void destroy() {
        minSalary = 0.0;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        String reqSalary = servletRequest.getParameter("salary");
        if (reqSalary == null || reqSalary.isEmpty()) {
            reqSalary = "0.0";
        }

        double salary = Double.parseDouble(reqSalary);
        if (salary < minSalary) {
            salary = minSalary;
        }
        servletRequest.setAttribute("salary", salary);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
