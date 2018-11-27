package com.mynotebookbot.util;

import com.mynotebookbot.mapper.UserMapper;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import javax.sql.DataSource;

 /*
    This class providing binding between mapper interface and DB
 */

public class MyBatisUtil {
        public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
        public static final String URL = "jdbc:derby:notesDB;create=true";
        public static final String USERNAME = "sa";
        public static final String PASSWORD = "pass1234";
        private static SqlSessionFactory sqlSessionFactory;

        public static SqlSessionFactory buildSqlSessionFactory() {
            DataSource dataSource = new PooledDataSource(DRIVER, URL, USERNAME, PASSWORD);
            Environment environment = new Environment("Development", new JdbcTransactionFactory(), dataSource);
            Configuration configuration = new Configuration(environment);

            //declare our mapper here
            configuration.addMapper(UserMapper.class);
            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
            SqlSessionFactory factory = builder.build(configuration);
            return factory;

        }

        public static SqlSessionFactory getSqlSessionFactory() {
            return sqlSessionFactory;
        }

}
