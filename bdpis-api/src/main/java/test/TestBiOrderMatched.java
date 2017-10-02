//package test;
//
//
//
//import mapping.BiOrderMatchedMapper;
//import mapping.MessageMapper;
//import model.BiOrderMatched;
//import model.Message;
//import org.apache.ibatis.io.Resources;
//import org.apache.ibatis.session.SqlSession;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.apache.ibatis.session.SqlSessionFactoryBuilder;
//
//import java.io.IOException;
//import java.io.Reader;
//import java.util.List;
//
//
//
///**
// * Created by yangguoliang on 2017/6/16.
// *
// * 测试数据库连接
// *
// */
//public class TestBiOrderMatched {
//
//    public  static void main (String [] args) throws IOException {
//
//
//        //加载mybatis的xml文件（同时加载加载关联的映射文件）
//        //1.使用类加载器加载mybatis的配置文件
////        InputStream config = _Test1SelectOne.class.getClassLoader().getResourceAsStream("conf.xml");
//        //2.使用mybatis的Resources类加载
//        //创建sqlSessionFactory
//        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(config);
//        //创建sqlSession，执行mapper.xml中的sql语句
////        sessionFactory.getConfiguration().addMapper(MessageMapper.class);
//
//        SqlSession sqlSession = sessionFactory.openSession();
//        BiOrderMatchedMapper messageMapper = sqlSession.getMapper(BiOrderMatchedMapper.class);
//        //执行映射文件中的sql(namespace + select的id)
//        List<BiOrderMatched> biOrderMatchedList = messageMapper.queryByAll();
//        if(biOrderMatchedList!= null && biOrderMatchedList.size() > 0){
//            for (BiOrderMatched biOrderMatched:biOrderMatchedList) {
//                System.out.println(biOrderMatched.getCourier_id());
//                System.out.println(biOrderMatched.getCategory_equipment_matched_val());
//            }
//        }
//        //关闭session
//        sqlSession.close();
//
//
//
//    }
//}
