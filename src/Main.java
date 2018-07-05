import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

public class Main {

    public static void main(String[] args) {

        //1.加载一个东西，数据库厂商要提供的JDBC驱动程序-对应数据库厂商提供
        // JDBC API java.sql
        // MySQL
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //2. 连接数据库
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/memo?user=root&password=123321");

            //3. 创建命令对象
            statement = connection.createStatement();

            //4. 执行SQL语句
            resultSet = statement.executeQuery("select id , group_id , title, content,created_time from memo_info;");


            //5.结果集处理（展示）
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int groupId = resultSet.getInt("group_id");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                Date createdTime = resultSet.getDate("created_time");

                String rowInfo = String.format(

                        "Id=%d, GroupId=%d, Title=%s , Content=%s, CreateTime=%s",
                        id, groupId, title, content,
                        new SimpleDateFormat("yyyy-MM-DD HH:mm:ss").format(createdTime)
                );
                System.out.println(rowInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //6.关闭资源
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
