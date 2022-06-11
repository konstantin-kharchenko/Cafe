package by.kharchenko.cafe.controller;

import java.io.*;
import java.util.Objects;
import java.util.Optional;

import by.kharchenko.cafe.controller.command.Command;
import by.kharchenko.cafe.controller.command.CommandType;
import by.kharchenko.cafe.controller.command.Router;
import by.kharchenko.cafe.exception.CommandException;
import by.kharchenko.cafe.model.pool.ConnectionPool;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(name = "controller", value = {"/controller", "/registration", "/sign-in"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class Controller extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(Controller.class);
    private static final String COMMAND = "command";
    private static final String CONTEXT_TYPE = "text/html";

    public void init() {
        ConnectionPool.getInstance();
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTEXT_TYPE);
        String commandStr = request.getParameter(COMMAND);
        Optional<Command> optionalCommand = CommandType.getCommand(commandStr);
        Router router;
        try {
            if (optionalCommand.isPresent()) {
                Command command = optionalCommand.get();
                router = command.execute(request, response);
                switch (router.getType()) {
                    case REDIRECT -> response.sendRedirect(router.getPage());
                    case FORWARD -> request.getRequestDispatcher(router.getPage()).forward(request, response);
                    default -> throw new ServletException("forward, redirect exception");
                }
            } else {
                throw new ServletException("optional exception");
            }
        } catch (CommandException e) {
            throw new ServletException(e);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.log(Level.INFO, "Controller -> doGet method");
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.log(Level.INFO, "Controller -> doPost method");
        processRequest(request, response);
    }

    public void destroy() {
        ConnectionPool.getInstance().destroyPool();
        ConnectionPool.getInstance().deregisterDriver();
    }
}