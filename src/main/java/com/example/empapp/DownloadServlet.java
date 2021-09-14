package com.example.empapp;

import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@WebServlet("/files/*")
@Slf4j
public class DownloadServlet extends HttpServlet {

    @Inject
    private EmployeeService employeeService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filename = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/") + 1);
        Path file = employeeService.getFile(filename);
        String contentType = Files.probeContentType(file);
        log.info("Content type: " + contentType);
        response.setContentType(contentType);
        response.setHeader("Content-disposition", "attachment; filename=" + filename);
        Files.copy(file, response.getOutputStream());
    }

}
