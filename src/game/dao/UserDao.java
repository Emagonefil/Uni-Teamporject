package game.dao;

import game.entity.User;

import java.sql.*;

public class UserDao {
    private PreparedStatement preparedStatement;
    private Connection conn = this.initializeDB();
    private Connection initializeDB() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

//            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/goldenaxe", "root", "");
            conn = DriverManager.getConnection("jdbc:mysql://119.28.194.192:3306/goldenaxe", "root", "djj0209.");
//            conn = DriverManager.getConnection("jdbc:mysql://localhost/?user=root&password=rootpassword");
            System.out.println("Database connected");
        } catch (Exception ex) {
            System.out.println("THIS HAPPENED");
            ex.printStackTrace();
        }
        return conn;
    }

    /**
     * 新增
     * @param user
     * @return
     */
    public boolean insertUser(User user) {
        String sql="INSERT INTO `user` (username,password )value(?,?)";
        boolean flag ;
        try{
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            flag  = !preparedStatement.execute();//执行查询语句
            System.out.println("The new increasing result is："+flag);
        }catch (Exception e){
            flag =false;
            System.out.println("Error！When adding new user.");
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 根据账户名和密码查询
     * @param user
     * @return
     */
    public boolean login(User user){
        boolean flag ;
        String sql="select * from user where username=? and password = ?";
        try{
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            ResultSet rSet =  preparedStatement.executeQuery();//执行查询语句
            if(rSet.next()){
                System.out.println("Login success！");
                flag = true;
            }else{
                System.out.println("Login failure！");
                flag = false;
            }
        }catch (Exception e){
            flag =false;
            System.out.println("Error！");
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 根据账户名和密码查询
     * @param user
     * @return  用户存在返回true
     */
    public boolean userIsExist(User user) throws Exception{
        boolean flag ;
        String sql="select * from user where username=? ";
        try{
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, user.getUsername());
            ResultSet rSet =  preparedStatement.executeQuery();//执行查询语句
            if(rSet.next()){
                System.out.println("user exist！");
                flag = true;
            }else{
                System.out.println("user not exist！");
                flag = false;
            }
        }catch (Exception e){
            System.out.println("Error when login！");
            e.printStackTrace();
            throw  new RuntimeException("Error when authenticate!");
        }
        return flag;
    }



}

