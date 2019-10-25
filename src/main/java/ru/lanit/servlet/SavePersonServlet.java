package ru.lanit.servlet;

import ru.lanit.factory.DBConnectionFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Logger;

@WebServlet("/save-person")
public class SavePersonServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            Connection connection = (new DBConnectionFactory()).create();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO person(firstname,middlename,lastname,birthday) VALUES(?, ?, ?, ?)");
            statement.setString(1, request.getParameter("firstname"));
            statement.setString(2, request.getParameter("middlename"));
            statement.setString(3, request.getParameter("lastname"));
            statement.setString(4, (LocalDate.parse(request.getParameter("birthday"))).toString());
            statement.execute();
            request.getSession().setAttribute("successMessage","Пользователь сохранен");
        }
        catch (SQLException|ClassNotFoundException e){
            request.getSession().setAttribute("errorMessage","Ошибка сохранения");
            Logger.getLogger(this.getClass().getName()).warning(e.getMessage());
        }
        response.sendRedirect("/");
    }
}
