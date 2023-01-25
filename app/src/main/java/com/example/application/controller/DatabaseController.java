package com.example.application.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

//Нужен для подключения к базе данных которая находится по адресу jdbc:mysql://gadgi95.beget.tech:3306/gadgi95_applicat
public class DatabaseController {

    private Connection connection;
    private String url;
    private String username;
    private String password;

    //Подключает файл application.properties.xml и достает из него url, username и password
    public DatabaseController() throws IOException {
        Properties prop = new Properties();
        InputStream input = DatabaseController.class.getClassLoader().getResourceAsStream("application.properties");
        prop.load(input);
        this.url = prop.getProperty("spring.datasource.url");
        this.username = prop.getProperty("spring.datasource.username");
        this.password = prop.getProperty("spring.datasource.password");
    }


    public void insertTask() throws SQLException, ClassNotFoundException {
        //Создается коннекшн для подключения
        connection = DriverManager.getConnection(url, username, password);
        //Затем создается стэйтент для написания запроса в БД.
        PreparedStatement prSt = connection.prepareStatement("INSERT INTO users (name, email, password) VALUES ('John Doe1', 'johndoe@example.com1', 'admin')");
        //Применение запроса
        prSt.executeUpdate();
    }

    public ArrayList<String> geTasks() throws SQLException {
        //Получаем все колонки, точно известно что там есть колонка Name, к которой обращаемся ниже?
        //Если нам нужна только колонка Name, то надо грузить только одну колонку, без лишних данных

        //Создаем sql запрос
        String sql = "SELECT * FROM users";
        //Создаем для него стейтмент
        Statement statement = connection.createStatement();
        //Затем через стейтмент применяем запрос в БД и сохраняем результат в переменную.
        ResultSet resultSet = statement.executeQuery(sql);

        ArrayList<String> tasks = new ArrayList<>();
        while (resultSet.next()) {
            tasks.add(resultSet.getString("Name"));
        }
        System.out.println(tasks);
        return tasks;
    }
}
