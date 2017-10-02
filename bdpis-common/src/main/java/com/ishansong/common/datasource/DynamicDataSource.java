package com.ishansong.common.datasource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 动态数据源
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
	
    private static final Logger logger = LogManager.getLogger(DynamicDataSource.class.getName());


    private AtomicInteger counter = new AtomicInteger();
	
	/**
	 * master库 dataSource
	 */
	private DataSource master;
	
	/**
	 * slaves
	 */
	private List<DataSource> slaves;
	

	@Override
	protected Object determineCurrentLookupKey() {
		//do nothing
		return null;
	}
	
	@Override
	public void afterPropertiesSet(){
		//do nothing
	}

	/**
	 * 根据标识
	 * 获取数据源
     * （同时通过jdbc连接判断数据库是否可以正常访问）
	 */
	@Override
	protected DataSource determineTargetDataSource() {
		DataSource returnDataSource = null;
		if(DataSourceHolder.isMaster()){
            returnDataSource = master;
            try {
                if(!DBHelper(returnDataSource)){
                    returnDataSource = slaves.get(0);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }else if(DataSourceHolder.isSlave()){
			returnDataSource = slaves.get(0);
            try {
                if(!DBHelper(returnDataSource)){
                    returnDataSource = master;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
		}else{
			returnDataSource = master;
            try {
                if(!DBHelper(returnDataSource)){
                    returnDataSource = slaves.get(0);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
		}

        if(returnDataSource instanceof BasicDataSource){
            BasicDataSource source = (BasicDataSource)returnDataSource;
            String jdbcUrl = source.getUrl();
            logger.info("========================当前jdbcUrl的访问地址为:" + jdbcUrl);
        }


        return returnDataSource;
	}

	public DataSource getMaster() {
		return master;
	}

	public void setMaster(DataSource master) {
		this.master = master;
	}

	public List<DataSource> getSlaves() {
		return slaves;
	}

	public void setSlaves(List<DataSource> slaves) {
		this.slaves = slaves;
	}

    /**
     * 通过jdbc 连接来判断数据库是否可以正常访问
     * @param returnDataSource
     * @return
     * @throws SQLException
     */
	public boolean DBHelper(DataSource returnDataSource) throws SQLException {
        boolean bl = false;
        Connection conn = null;
        try {
            BasicDataSource dataSource = (BasicDataSource) returnDataSource;
            String jdbcUrl = dataSource.getUrl();
            logger.debug("jdbcUrl:" + jdbcUrl);

            String url = dataSource.getUrl();
            String username = dataSource.getUsername();
            String password = dataSource.getPassword();
            String drivername = dataSource.getDriverClassName();
            Class.forName(drivername);
            conn = DriverManager.getConnection(url, username, password);
            if (conn != null) {
                bl = true;
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            bl = false;
        }
        return bl;
    }
	
	
}
