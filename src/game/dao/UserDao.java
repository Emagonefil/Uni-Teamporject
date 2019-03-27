package game.dao;

import game.entity.User;

import java.sql.*;

/**
 * user data database operation class
 */
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
     * @param user User entity
     * @return True succeeded in adding new users; false failed in adding new users
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
     * Search by account name and password
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
            ResultSet rSet =  preparedStatement.executeQuery();
            if(rSet.next()){
                //set user points
                user.setPoint(rSet.getInt(4));
                user.setID(rSet.getInt(1));
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
     * Search by account name and password
     * @param user
     * @return  User exist returns true
     */
    public boolean userIsExist(User user) throws Exception{
        boolean flag ;
        String sql="select * from user where username=? ";
        try{
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, user.getUsername());
            ResultSet rSet =  preparedStatement.executeQuery();
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
    /**
     * Update User Integral
     * @param user
     * @return  Update successfully returns true, failure returns false
     */
    public boolean userUpdatePoint(User user) {
        boolean flag =false;
        String sql="update user set  point=? where id=? ";
        try{
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, user.getPoint());
            preparedStatement.setInt(2, user.getID());
            int rSet =  preparedStatement.executeUpdate();
            if(rSet>0){
                System.out.println("update success！");
                flag = true;
            }else{
                System.out.println("update  failure！");
                flag = false;
            }
        }catch (Exception e){
            System.out.println("Error when update！");
            e.printStackTrace();
        }
        return flag;
    }

}

